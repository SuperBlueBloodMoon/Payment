package com.example.Payment.repository;

import com.example.Payment.model.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CreateLogRepository {
    private final JdbcTemplate jdbcTemplate;
    public CreateLogRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createLog(Log log){
        String sql = "INSERT INTO log(id,item,status) VALUES (?,?,?)";

        jdbcTemplate.update(sql,log.getId(),log.getItem(),log.getStatus());
    }
}

