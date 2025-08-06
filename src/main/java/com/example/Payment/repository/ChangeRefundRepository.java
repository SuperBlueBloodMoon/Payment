package com.example.Payment.repository;

import com.example.Payment.model.Purchased;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface ChangeRefundRepository extends CrudRepository<Purchased, Integer> {
    @Modifying
    @Query("UPDATE purchased SET quantity = :quantity WHERE id = :id and item = :item")
    void updateRefundAccount(String id, int quantity, String item);
}
