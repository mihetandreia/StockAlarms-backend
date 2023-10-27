package com.project.StockAlarms.dto;

import com.project.StockAlarms.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;

public class AlarmDTO {
    private Long id;

    private User user;


    private String stock;


    private Double upperTarget;


    private Double lowerTarget;


    private boolean status;

    public AlarmDTO() {
    }

    public AlarmDTO(Long id, User user, String stock, Double upperTarget, Double lowerTarget, boolean status) {
        this.id = id;
        this.user = user;
        this.stock = stock;
        this.upperTarget = upperTarget;
        this.lowerTarget = lowerTarget;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public Double getUpperTarget() {
        return upperTarget;
    }

    public void setUpperTarget(Double upperTarget) {
        this.upperTarget = upperTarget;
    }

    public Double getLowerTarget() {
        return lowerTarget;
    }

    public void setLowerTarget(Double lowerTarget) {
        this.lowerTarget = lowerTarget;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
