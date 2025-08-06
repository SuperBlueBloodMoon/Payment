package com.example.Payment.repository;

import com.example.Payment.model.Purchased;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CheckQuantityRefundRepository extends CrudRepository<Purchased, Integer>{
    @Query("SELECT quantity FROM purchased WHERE id = :id and item = :item")
    int getItemQuantity(String id, String item);
}
