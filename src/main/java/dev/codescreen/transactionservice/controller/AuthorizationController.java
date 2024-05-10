package dev.codescreen.transactionservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.codescreen.transactionservice.model.AuthorizationRequest;
import dev.codescreen.transactionservice.model.AuthorizationResponse;
import dev.codescreen.transactionservice.model.ErrorSchema;
import dev.codescreen.transactionservice.service.AuthorizationService;

@RestController
public class AuthorizationController {
    
    @Autowired
    private AuthorizationService authorizationService;

    
    /** 
     * @param request
     * @return ResponseEntity<?>
     */
    @PutMapping("/authorization")
    public ResponseEntity<?> authorize(@RequestBody AuthorizationRequest request) {
        try {
            AuthorizationResponse response = authorizationService.authorizeAmount(request);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace(); 
            String errorMessage = e.getMessage() != null ? e.getMessage() : "INTERNAL_SERVER_ERROR";
            ErrorSchema error = new ErrorSchema();
            error.setMessage(errorMessage);
            error.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}