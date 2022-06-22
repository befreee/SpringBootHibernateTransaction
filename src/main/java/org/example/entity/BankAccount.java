package org.example.entity;

import javax.persistence.*;

@Entity
@Table(name="bank_account")
public class BankAccount {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "full_name", length = 128, nullable = false)
    private String fullName;
    @Column(name = "balance", nullable = false)
    private double balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
