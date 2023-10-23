package com.project.StockAlarms.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable=false)
    private String stock; // symbol stock

    @Column(nullable = false)
    private Double priceWhenAlarmWasDefined;

    @Column(nullable = false)
    private Double currentPrice;

    @Column(nullable = false)
    private Double changePercent;


    @Column(nullable=false)
    private Double upperTarget;

    @Column(nullable=false)
    private Double lowerTarget;

    @Column(nullable=false)
    private boolean status;

    public Alarm(User user, String stock, Double priceWhenAlarmWasDefined, Double currentPrice, Double changePercent,
                 Double upperTarget, Double lowerTarget, boolean status) {
        this.user = user;
        this.stock = stock;
        this.priceWhenAlarmWasDefined = priceWhenAlarmWasDefined;
        this.currentPrice = currentPrice;
        this.changePercent = changePercent;
        this.upperTarget = upperTarget;
        this.lowerTarget = lowerTarget;
        this.status = status;
    }

    public Alarm() {
    }

    public Double getPriceWhenAlarmWasDefined() {
        return priceWhenAlarmWasDefined;
    }

    public void setPriceWhenAlarmWasDefined(Double priceWhenAlarmWasDefined) {
        this.priceWhenAlarmWasDefined = priceWhenAlarmWasDefined;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(Double changePercent) {
        this.changePercent = changePercent;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Alarm{" +
                "id=" + id +
                ", user=" + user +
                ", stock='" + stock + '\'' +
                ", priceWhenAlarmWasDefined=" + priceWhenAlarmWasDefined +
                ", currentPrice=" + currentPrice +
                ", changePercent=" + changePercent +
                ", upperTarget=" + upperTarget +
                ", lowerTarget=" + lowerTarget +
                ", status=" + status +
                '}';
    }
}
