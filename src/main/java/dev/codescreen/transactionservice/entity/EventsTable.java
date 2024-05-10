package dev.codescreen.transactionservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "EventsTable")
public class EventsTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    
    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    private BalanceTable userId;
    
    @Column(name = "messageId")
    private String messageId;

    @ManyToOne
    @JoinColumn(name = "transaction_id", referencedColumnName = "id")
    private TransactionTable transaction_id;
    
    @Column(name = "afterBalance")
    private BigDecimal afterBalance;
    
    @Column(name = "responseCode")
    private String responseCode;

    
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

    // Getter for timestamp
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // Setter for timestamp
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // Getter for userid
    public BalanceTable getUserId() {
        return userId;
    }

    // Setter for userid
    public void setUserId(BalanceTable userId) {
        this.userId = userId;
    }

    // Getter for messageId
    public String getMessageId() {
        return messageId;
    }

    // Setter for messageId
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    // Getter for transaction_id
    public TransactionTable getTransaction_id() {
        return transaction_id;
    }

    // Setter for transaction_id
    public void setTransaction_id(TransactionTable transaction_id) {
        this.transaction_id = transaction_id;
    }

    // Getter for afterBalance
    public BigDecimal getAfterBalance() {
        return afterBalance;
    }

    // Setter for afterBalance
    public void setAfterBalance(BigDecimal afterBalance) {
        this.afterBalance = afterBalance;
    }

    // Getter for responseCode
    public String getResponseCode() {
        return responseCode;
    }

    // Setter for responseCode
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

}
