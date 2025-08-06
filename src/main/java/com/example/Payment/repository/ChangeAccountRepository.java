package com.example.Payment.repository;

import com.example.Payment.model.Client;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ChangeAccountRepository extends CrudRepository<Client, String> {
    @Modifying
    @Query("UPDATE account SET amount = :amount WHERE id = :id")
    void updateAccount(String id, BigDecimal amount);
}
