package com.paulsan.appbank.entity;

import javax.persistence.*;

@Entity
@Table(name = "movement")
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private int id;

    @Column(name = "currency", nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MovementType type;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private double amount;

    @Override
    public boolean equals(Object obj) {
        Movement movement = (Movement) obj;
        return this.amount == movement.amount &&
                this.currency == movement.currency &&
                this.type == movement.type &&
                this.id == movement.id;
    }

    public Movement() {
    }

    public static Movement createCredit(Account account) {
        return new Movement(MovementType.CREDIT, account.getCurrency(), account.getBalance());
    }

    private Movement(MovementType type, Currency currency, double amount) {
        this.type = type;
        this.currency = currency;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public MovementType getType() {
        return type;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
