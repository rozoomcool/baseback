package com.itabrek.baseback.dto;

import com.itabrek.baseback.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    public String username;
    public String email;

    public UserResponse(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
