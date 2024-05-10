package dev.codescreen.transactionservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Test;

import dev.codescreen.transactionservice.model.Amount;
import dev.codescreen.transactionservice.model.ErrorSchema;
import dev.codescreen.transactionservice.model.LoadRequest;
import dev.codescreen.transactionservice.model.LoadResponse;
import dev.codescreen.transactionservice.service.LoadService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class LoadControllerUnitTest {

    @InjectMocks
    private LoadController loadController;

    @Mock
    private LoadService loadService;

    @Test
    public void testLoad_Successful() throws Exception {
        // Mock service response
        LoadResponse mockResponse = new LoadResponse("2", "6", new Amount("1340", "USD", "CREDIT"));
        when(loadService.loadAmount(any())).thenReturn(mockResponse);

        // Create a LoadRequest object
        LoadRequest request = new LoadRequest("2", "4", new Amount("1340", "USD", "CREDIT"));

        // Send request to controller
        ResponseEntity<?> responseEntity = loadController.load(request);

        // Verify response
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(mockResponse);
    }

    @Test
    public void testLoad_Exception() throws Exception {
        // Mock service to throw an exception
        when(loadService.loadAmount(any())).thenThrow(new RuntimeException("Service unavailable"));

        // Create a LoadRequest object
        LoadRequest request = new LoadRequest("2", "4", new Amount("1340", "USD", "CREDIT"));

        // Send request to controller
        ResponseEntity<?> responseEntity = loadController.load(request);

        // Verify response
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(responseEntity.getBody()).isInstanceOf(ErrorSchema.class);
        ErrorSchema error = (ErrorSchema) responseEntity.getBody();
        assertThat(error.getMessage()).isEqualTo("Service unavailable");
    }

    // Additional test cases for error scenarios, invalid requests, etc.
}
