package com.categoryservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.categoryservice.modals.Category;


@Repository
public interface CategoryRepository extends MongoRepository<Category,String> {
   boolean existsByCategoryName(String categoryName);

}
