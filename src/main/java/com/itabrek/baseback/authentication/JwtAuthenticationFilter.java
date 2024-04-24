package com.itabrek.baseback.authentication;

import com.itabrek.baseback.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final AccessTokenService accessTokenService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        logger.info("JWT FILTER, SOME REQUEST TRY");

        var authHeader = request.getHeader(AUTHORIZATION_HEADER_NAME);
        if (!StringUtils.hasLength(authHeader) || !StringUtils.startsWithIgnoreCase(authHeader, BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        var jwt = authHeader.substring(BEARER_PREFIX.length());
        String username;
        try{
            username = accessTokenService.extractUsername(jwt);
        } catch (Exception e) {
            logger.error("Failed to extract username from JWT token");
            filterChain.doFilter(request, response);
            return;
        }

        if (StringUtils.hasLength(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails;

            try {
                userDetails = userService
                        .userDetailsService()
                        .loadUserByUsername(username);
            } catch (Exception e) {
                logger.error("Failed to load user details by username: {}", username);
                filterChain.doFilter(request, response);
                return;
            }

            if (accessTokenService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request, response);
    }
}
