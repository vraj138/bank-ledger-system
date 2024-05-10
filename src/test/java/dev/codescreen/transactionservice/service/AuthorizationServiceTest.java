package dev.codescreen.transactionservice.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import dev.codescreen.transactionservice.entity.BalanceTable;
import dev.codescreen.transactionservice.entity.EventsTable;
import dev.codescreen.transactionservice.model.Amount;
import dev.codescreen.transactionservice.model.AuthorizationRequest;
import dev.codescreen.transactionservice.model.AuthorizationResponse;
import dev.codescreen.transactionservice.repository.BalanceRepository;
import dev.codescreen.transactionservice.repository.EventsTableRepository;
import dev.codescreen.transactionservice.repository.TransactionTableRepository;

public class AuthorizationServiceTest {

    private AuthorizationService authorizationService;
    private BalanceRepository balanceRepository;
    private TransactionTableRepository transactionTableRepository;
    private EventsTableRepository eventsTableRepository;

    @BeforeEach
    public void setup() {
        balanceRepository = mock(BalanceRepository.class);
        transactionTableRepository = mock(TransactionTableRepository.class);
        eventsTableRepository = mock(EventsTableRepository.class);
        authorizationService = new AuthorizationService(balanceRepository, eventsTableRepository, transactionTableRepository);
    }

    @Test
    public void testAuthorizeAmount_Approved() throws Exception {
        // Arrange
        String userId = "userId";
        String messageId = "messageId";
        BigDecimal initialBalance = BigDecimal.valueOf(500); // Initial balance
        Amount transactionAmount = new Amount("100", "USD", "DEBIT");
        AuthorizationRequest authorizeRequest = new AuthorizationRequest(userId, messageId, transactionAmount);
        
        // Mock balance repository
        BalanceTable balance = new BalanceTable();
        balance.setUserId(userId);
        balance.setBalance(initialBalance);
        when(balanceRepository.findByUserId(userId)).thenReturn(balance);
        
        // Mock transaction and events repository
        // TransactionTable transaction = new TransactionTable();
        // transaction.setId(UUID.randomUUID().toString());
        EventsTable event = new EventsTable();
        event.setTimestamp(LocalDateTime.now()); // Set a timestamp for event
        when(eventsTableRepository.save(event)).thenReturn(event);
        
        // Act
        AuthorizationResponse response = authorizationService.authorizeAmount(authorizeRequest);
        initialBalance = initialBalance.subtract(new BigDecimal(transactionAmount.getAmount()));
        
        // Assert
        assertNotNull(response);
        assertEquals(userId, response.getUserId());
        assertEquals(messageId, response.getMessageId());
        assertEquals("APPROVED", response.getResponseCode());
        assertEquals(initialBalance.toString(), response.getBalance().getAmount());
        assertEquals(transactionAmount.getCurrency(), response.getBalance().getCurrency());
        assertEquals(transactionAmount.getDebitOrCredit(), response.getBalance().getDebitOrCredit());
    }

    @Test
    public void testAuthorizeAmount_Declined() throws Exception {
        // Arrange
        String userId = "userId";
        String messageId = "messageId";
        BigDecimal initialBalance = BigDecimal.valueOf(50); // Initial balance
        Amount transactionAmount = new Amount("100", "USD", "DEBIT");
        AuthorizationRequest authorizeRequest = new AuthorizationRequest(userId, messageId, transactionAmount);
        
        // Mock balance repository
        BalanceTable balance = new BalanceTable();
        balance.setUserId(userId);
        balance.setBalance(initialBalance);
        when(balanceRepository.findByUserId(userId)).thenReturn(balance);
        
        // Act
        AuthorizationResponse response = authorizationService.authorizeAmount(authorizeRequest);
        
        // Assert
        assertNotNull(response);
        assertEquals(userId, response.getUserId());
        assertEquals(messageId, response.getMessageId());
        assertEquals("DECLINED", response.getResponseCode());
        assertEquals(initialBalance.toString(), response.getBalance().getAmount());
        assertEquals(transactionAmount.getCurrency(), response.getBalance().getCurrency());
        assertEquals(transactionAmount.getDebitOrCredit(), response.getBalance().getDebitOrCredit());
    }
}