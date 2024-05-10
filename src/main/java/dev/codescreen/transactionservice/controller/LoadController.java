package dev.codescreen.transactionservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.codescreen.transactionservice.model.ErrorSchema;
import dev.codescreen.transactionservice.model.LoadRequest;
import dev.codescreen.transactionservice.model.LoadResponse;
import dev.codescreen.transactionservice.service.LoadService;

@RestController
public class LoadController {

    @Autowired
    private LoadService loadService;

    
    /** 
     * @param request
     * @return ResponseEntity<?>
     */
    @PutMapping("/load")
    public ResponseEntity<?> load(@RequestBody LoadRequest request){
        try {
            LoadResponse response = loadService.loadAmount(request);
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

