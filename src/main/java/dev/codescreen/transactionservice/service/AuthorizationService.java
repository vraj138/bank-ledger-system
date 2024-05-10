package dev.codescreen.transactionservice.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.codescreen.transactionservice.entity.BalanceTable;
import dev.codescreen.transactionservice.entity.EventsTable;
import dev.codescreen.transactionservice.entity.TransactionTable;
import dev.codescreen.transactionservice.model.Amount;
import dev.codescreen.transactionservice.model.AuthorizationRequest;
import dev.codescreen.transactionservice.model.AuthorizationResponse;
import dev.codescreen.transactionservice.repository.BalanceRepository;
import dev.codescreen.transactionservice.repository.EventsTableRepository;
import dev.codescreen.transactionservice.repository.TransactionTableRepository;

@Service
public class AuthorizationService {
    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private EventsTableRepository eventsTableRepository;

    @Autowired
    private TransactionTableRepository transactionTableRepository;

    
    public AuthorizationService(BalanceRepository balanceRepository, EventsTableRepository eventsTableRepository,
            TransactionTableRepository transactionTableRepository) {
                this.balanceRepository = balanceRepository;
                this.eventsTableRepository = eventsTableRepository;
                this.transactionTableRepository = transactionTableRepository;
    }


    /** 
     * @param authorizeRequest
     * @return AuthorizationResponse
     * @throws Exception
     */
    @Transactional
    public AuthorizationResponse authorizeAmount(AuthorizationRequest authorizeRequest) throws Exception {
        // Logic to authorize transaction
        String messageId = authorizeRequest.getMessageId();
        String userId = authorizeRequest.getUserId();
        Amount transactionAmount = authorizeRequest.getTransactionAmount();

        // Fetch the user's balance
        BalanceTable balance = balanceRepository.findByUserId(userId);

        BigDecimal amount = new BigDecimal(transactionAmount.getAmount());
        String currency = transactionAmount.getCurrency();
        String debitOrCredit = transactionAmount.getDebitOrCredit();

        int compariosnResult =  balance.getBalance().compareTo(amount);

        if(compariosnResult >= 0){
            balance.setBalance(balance.getBalance().subtract(amount));
        }

        String responseCode = compariosnResult >= 0 ? "APPROVED" : "DECLINED";

        // Save the updated balance
        balanceRepository.save(balance);

        // Create a transaction record
        TransactionTable transaction = new TransactionTable();
        // transaction.setId(UUID.randomUUID().toString());
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setDebitOrCredit(debitOrCredit);
        transactionTableRepository.save(transaction);

        // Create an event record
        EventsTable event = new EventsTable();
        event.setTimestamp(LocalDateTime.now());
        event.setUserId(balance);
        event.setMessageId(messageId);
        event.setTransaction_id(transaction);
        event.setAfterBalance(balance.getBalance());
        
        // Set response code based on transaction approval status
        
        event.setResponseCode(responseCode);

        eventsTableRepository.save(event);

        // Create and return the authorization response
        AuthorizationResponse authorizationResponse = new AuthorizationResponse(responseCode, responseCode, responseCode, transactionAmount);
        authorizationResponse.setMessageId(messageId);
        authorizationResponse.setUserId(userId);
        authorizationResponse.setResponseCode(responseCode);
        authorizationResponse.setBalance(new Amount(balance.getBalance().toString(), currency, debitOrCredit));

        return authorizationResponse;
    }

    
}
