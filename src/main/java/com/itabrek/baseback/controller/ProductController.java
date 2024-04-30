package com.itabrek.baseback.controller;

import com.itabrek.baseback.dto.ProductResponse;
import com.itabrek.baseback.exception.ProductNotFoundException;
import com.itabrek.baseback.exception.UserNotFoundException;
import com.itabrek.baseback.repository.ProductRepository;
import com.itabrek.baseback.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
