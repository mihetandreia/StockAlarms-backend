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
    private String stock; // inca nu stiu cum sa pun stock-ul

    @Column(nullable=false)
    private BigDecimal upperTarget;

    @Column(nullable=false)
    private BigDecimal lowerTarget;

    @Column(nullable=false)
    private boolean status;

    public Alarm() {
    }

    public Alarm(User user, String stock, BigDecimal upperTarget, BigDecimal lowerTarget, boolean status) {
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

    public BigDecimal getUpperTarget() {
        return upperTarget;
    }

    public void setUpperTarget(BigDecimal upperTarget) {
        this.upperTarget = upperTarget;
    }

    public BigDecimal getLowerTarget() {
        return lowerTarget;
    }

    public void setLowerTarget(BigDecimal lowerTarget) {
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
                ", user=" + user.getEmail() +
                ", stock='" + stock + '\'' +
                ", upperTarget=" + upperTarget +
                ", lowerTarget=" + lowerTarget +
                ", status=" + status +
                '}';
    }
}
