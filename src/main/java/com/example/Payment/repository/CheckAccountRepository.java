package com.example.Payment.repository;

import com.example.Payment.model.Client;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckAccountRepository extends CrudRepository<Client, String> {
    @Query("SELECT * FROM account WHERE username = :username")
    List<Client> findByUsername(String username);
}
