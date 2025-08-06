package com.example.Payment.repository;

import com.example.Payment.model.Client;
import com.example.Payment.model.Purchased;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CreateRefundRepository {
    private final JdbcTemplate jdbcTemplate;
    public CreateRefundRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createRefund(Purchased purchased) {
        String sql = "INSERT INTO purchased(id,quantity,item) VALUES (?,?,?)";

        jdbcTemplate.update(sql,purchased.getId(),purchased.getQuantity(),purchased.getItem());
    }
}
