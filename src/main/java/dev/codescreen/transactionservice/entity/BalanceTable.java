package dev.codescreen.transactionservice.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "balance")
public class BalanceTable {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;

    @Column(name = "balance")
    private BigDecimal balance= BigDecimal.ZERO;

     
     /** 
      * @return String
      */
     // Getter for userid
     public String getUserId() {
        return userId;
    }

    // Setter for userid
    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Getter for balance
    public BigDecimal getBalance() {
        return balance;
    }

    // Setter for balance
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
