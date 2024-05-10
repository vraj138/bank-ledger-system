package dev.codescreen.transactionservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dev.codescreen.transactionservice.model.Amount;
import dev.codescreen.transactionservice.model.AuthorizationRequest;
import dev.codescreen.transactionservice.model.AuthorizationResponse;
import dev.codescreen.transactionservice.model.ErrorSchema;
import dev.codescreen.transactionservice.service.AuthorizationService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class AuthorizationControllerUnitTest {
    
    @InjectMocks
    private AuthorizationController authorizationController;

    @Mock
    private AuthorizationService authorizationService;

    @Test
    public void testAuthorization_Successful() throws Exception {
        // Mock service response
        AuthorizationResponse mockResponse = new AuthorizationResponse("1", "1", "APPROVED", new Amount("100", "USD", "DEBIT"));
        when(authorizationService.authorizeAmount(any())).thenReturn(mockResponse);

        // Create a authorizationRequest object
        AuthorizationRequest request = new AuthorizationRequest("1", "1", new Amount("100", "USD", "DEBIT"));

        // Send request to controller
        ResponseEntity<?> responseEntity = authorizationController.authorize(request);

        // Verify response
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(mockResponse);
    }

    @Test
    public void testAuthorization_Exception() throws Exception {
        // Mock service to throw an exception
        when(authorizationService.authorizeAmount(any())).thenThrow(new RuntimeException("Service unavailable"));

        // Create a authorizationRequest object
        AuthorizationRequest request = new AuthorizationRequest("2", "4", new Amount("1340", "USD", "CREDIT"));

        // Send request to controller
        ResponseEntity<?> responseEntity = authorizationController.authorize(request);

        // Verify response
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(responseEntity.getBody()).isInstanceOf(ErrorSchema.class);
        ErrorSchema error = (ErrorSchema) responseEntity.getBody();
        assertThat(error.getMessage()).isEqualTo("Service unavailable");
    }
    
}
