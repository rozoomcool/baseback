package com.itabrek.baseback.repository;

import com.itabrek.baseback.entity.ProductCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends CrudRepository<ProductCategory, Long> {
}
