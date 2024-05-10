package dev.codescreen.transactionservice.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.codescreen.transactionservice.entity.BalanceTable;
import dev.codescreen.transactionservice.entity.EventsTable;
import dev.codescreen.transactionservice.model.Amount;
import dev.codescreen.transactionservice.model.LoadRequest;
import dev.codescreen.transactionservice.model.LoadResponse;
import dev.codescreen.transactionservice.repository.BalanceRepository;
import dev.codescreen.transactionservice.repository.EventsTableRepository;
import dev.codescreen.transactionservice.repository.TransactionTableRepository;

public class LoadServiceTest {

    private LoadService loadService;
    private BalanceRepository balanceRepository;
    private TransactionTableRepository transactionTableRepository;
    private EventsTableRepository eventsTableRepository;

    @BeforeEach
    public void setup() {
        balanceRepository = mock(BalanceRepository.class);
        transactionTableRepository = mock(TransactionTableRepository.class);
        eventsTableRepository = mock(EventsTableRepository.class);
        loadService = new LoadService(balanceRepository, eventsTableRepository, transactionTableRepository);
    }

    @Test
    public void testLoadAmount_Successful() throws Exception {
        // Arrange
        String userId = "userId";
        String messageId = "messageId";
        BigDecimal initialBalance = BigDecimal.valueOf(500); // Initial balance
        Amount transactionAmount = new Amount("100", "USD", "DEBIT");
        LoadRequest loadRequest = new LoadRequest(userId, messageId, transactionAmount);
        
        // Mock balance repository
        BalanceTable balance = new BalanceTable();
        balance.setUserId(userId);
        balance.setBalance(initialBalance);
        when(balanceRepository.findByUserId(userId)).thenReturn(balance);
        
        // Mock transaction and events repository
        EventsTable event = new EventsTable();
        event.setTimestamp(null); // Set timestamp to null to avoid NullPointerException
        
        // Act
        LoadResponse response = loadService.loadAmount(loadRequest);
        initialBalance = initialBalance.add(new BigDecimal(transactionAmount.getAmount()));
        // Assert
        assertNotNull(response);
        assertEquals(userId, response.getUserId());
        assertEquals(messageId, response.getMessageId());
        // assertEquals(transactionAmount, response.getBalance());
        assertEquals(initialBalance.toString(), response.getBalance().getAmount());
        assertEquals(transactionAmount.getCurrency(), response.getBalance().getCurrency());
        assertEquals(transactionAmount.getDebitOrCredit(), response.getBalance().getDebitOrCredit());
    }


    @Test
    public void testLoadAmount_BalanceNotFound() throws Exception {
        // Arrange
        String userId = "nonExistentUserId";
        String messageId = "messageId";
        Amount transactionAmount = new Amount("100", "USD", "DEBIT");
        LoadRequest loadRequest = new LoadRequest(userId, messageId, transactionAmount);
        
        // Mock balance repository to return null when findByUserId is called
        when(balanceRepository.findByUserId(userId)).thenReturn(null);
        
        // Act & Assert
        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> {
            loadService.loadAmount(loadRequest);
        });
        assertEquals("Balance not found for user ID: " + userId, exception.getMessage());
    }
}
