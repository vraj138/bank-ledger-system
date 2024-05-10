package dev.codescreen.transactionservice.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.codescreen.transactionservice.model.PingResponse;

@RestController
public class PingController {
    
    /** 
     * @return PingResponse
     */
    @GetMapping("/ping")
    public PingResponse ping() {
        String serverTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return new PingResponse(serverTime);
    }
}
