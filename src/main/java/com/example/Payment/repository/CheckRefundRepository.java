package com.example.Payment.repository;

import com.example.Payment.model.ItemCount;
import com.example.Payment.model.Purchased;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckRefundRepository extends CrudRepository<Purchased, Integer> {
    @Query("SELECT item, quantity FROM purchased WHERE id = :id ORDER BY item ASC")
    List<ItemCount> getItemCount(String id);
}
