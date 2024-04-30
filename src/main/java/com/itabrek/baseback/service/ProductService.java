package com.itabrek.baseback.service;

import com.itabrek.baseback.dto.ProductResponse;
import com.itabrek.baseback.dto.UserDataResponse;
import com.itabrek.baseback.dto.UserResponse;
import com.itabrek.baseback.entity.*;
import com.itabrek.baseback.exception.ProductNotFoundException;
import com.itabrek.baseback.exception.UserNotFoundException;
import com.itabrek.baseback.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final UserDataService userDataService;
    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final ReviewRepository reviewRepository;
    private final TagRepository tagRepository;

    public ResponseEntity<List<ProductResponse>> getAll() {
        ModelMapper modelMapper = new ModelMapper();
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return new ResponseEntity<>(
                products.stream().map((value) -> modelMapper.map(value, ProductResponse.class)).toList(),
                HttpStatus.OK
        );
    }

    public ProductResponse getById(Long id) throws ProductNotFoundException {
        return new ModelMapper().map(productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("PRODUCT NOT FOUND")), ProductResponse.class);
    }

    @Transactional
    public ProductResponse save(Product product, String username) throws UserNotFoundException {
        List<Review> reviews = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();

        UserData currentUserData = userDataService.getUserDataByUsername(username);

        reviewRepository.saveAll(product.getReviews().stream().peek((value) -> value.setUserdata(currentUserData)).toList()).forEach(reviews::add);
        tagRepository.saveAll(product.getTags());

        product.setOwnerUserData(currentUserData);

        Product saved = productRepository.save(product);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(saved, ProductResponse.class);

    }
}
