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
import dev.codescreen.transactionservice.model.LoadRequest;
import dev.codescreen.transactionservice.model.LoadResponse;
import dev.codescreen.transactionservice.repository.BalanceRepository;
import dev.codescreen.transactionservice.repository.EventsTableRepository;
import dev.codescreen.transactionservice.repository.TransactionTableRepository;

@Service
public class LoadService {
    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private EventsTableRepository eventsTableRepository;

    @Autowired
    private TransactionTableRepository transactionTableRepository;

    // LoadRequest: (UserId, messageID, TransactionAmount(amount, currency,
    // debitorcredit))

    @Autowired
    public LoadService(BalanceRepository balanceRepository, 
                       EventsTableRepository eventsTableRepository, 
                       TransactionTableRepository transactionTableRepository) {
        this.balanceRepository = balanceRepository;
        this.eventsTableRepository = eventsTableRepository;
        this.transactionTableRepository = transactionTableRepository;
    }

    
    /** 
     * @param loadRequest
     * @return LoadResponse
     * @throws Exception
     */
    @Transactional
    public LoadResponse loadAmount(LoadRequest loadRequest) throws Exception {

        String messageId = loadRequest.getMessageId();
        String userId = loadRequest.getUserId();
        Amount transactionAmount = loadRequest.getTransactionAmount();

        BalanceTable balance = balanceRepository.findByUserId(userId);
        if (balance == null) {
            throw new Exception("Balance not found for user ID: " + userId);
        }

        BigDecimal amount = new BigDecimal(transactionAmount.getAmount());
        String currency = transactionAmount.getCurrency();
        String debitOrCredit = transactionAmount.getDebitOrCredit();

        balance.setBalance(balance.getBalance().add(amount));

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
        event.setResponseCode("APPROVED"); // Assuming the transaction is approved
        eventsTableRepository.save(event);

        // Create and return the load response
        LoadResponse loadResponse = new LoadResponse(debitOrCredit, debitOrCredit, transactionAmount);
        loadResponse.setMessageId(messageId);
        loadResponse.setUserId(userId);
        loadResponse.setBalance(new Amount(balance.getBalance().toString(), currency, debitOrCredit));

        return loadResponse;

    }

}
