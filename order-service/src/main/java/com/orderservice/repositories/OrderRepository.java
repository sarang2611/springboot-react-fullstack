package com.orderservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.orderservice.modals.Order;

import java.util.Set;

public interface OrderRepository extends MongoRepository<Order,String> {

    Set<Order> findByUserIdOrderByIdDesc(String userId);


}
