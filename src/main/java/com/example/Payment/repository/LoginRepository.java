package com.example.Payment.repository;

import com.example.Payment.model.Client;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoginRepository extends CrudRepository<Client,String> {

    @Query("SELECT * FROM account WHERE id = :id")
    List<Client> findByClientId(String id);
}
