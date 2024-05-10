package dev.codescreen.transactionservice.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "TransactionsTable")
public class TransactionTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "amount")
    private BigDecimal amount;
    
    @Column(name = "currency")
    private String currency;

    @Column(name = "debitOrCredit")
    private String debitOrCredit;
    
    
    /** 
     * @return Long
     */
    // Getter for id
    public Long getId() {
        return id;
    }

    // Setter for id
    public void setId(Long id) {
        this.id = id;
    }

    // Getter for amount
    public BigDecimal getAmount() {
        return amount;
    }

    // Setter for amount
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    // Getter for currency
    public String getCurrency() {
        return currency;
    }

    // Setter for currency
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    // Getter for debitOrCredit
    public String getDebitOrCredit() {
        return debitOrCredit;
    }

    // Setter for debitOrCredit
    public void setDebitOrCredit(String debitOrCredit) {
        this.debitOrCredit = debitOrCredit;
    }
}
