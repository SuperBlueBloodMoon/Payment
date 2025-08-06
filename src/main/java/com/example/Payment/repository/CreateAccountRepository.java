package com.example.Payment.repository;

import com.example.Payment.model.Client;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CreateAccountRepository {
    private final JdbcTemplate jdbcTemplate;
    public CreateAccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createAccount(Client client) {
        String sql = "INSERT INTO account VALUES (?,?,?,?)";

        jdbcTemplate.update(sql,client.getUsername(), client.getPassword(), client.getAmount(), client.getId());
    }
}
