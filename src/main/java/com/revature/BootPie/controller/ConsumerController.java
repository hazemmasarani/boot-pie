package com.revature.BootPie.controller;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.revature.BootPie.models.Consumer;
import com.revature.BootPie.services.ConsumerService;

@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    private ConsumerService consumerService;
    
    @Autowired
    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @PostMapping("/register")
    public @ResponseBody ResponseEntity<String> registerConsumer(@RequestBody Consumer consumer) {
        try {
            consumerService.registerConsumer(consumer);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Consumer registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error registering consumer: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Consumer> loginConsumer(@RequestBody Consumer consumer) {
        try {
            Consumer foundConsumer = consumerService.login(consumer);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(foundConsumer);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/orderPie")
    public ResponseEntity<String> orderPie(@RequestHeader String username, @RequestBody String pieName) {
        try {
            Consumer consumer = consumerService.getConsumerByUsername(username);
            consumerService.orderPie(consumer, pieName);
            return ResponseEntity.status(HttpStatus.OK).body("Pie ordered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error ordering pie: " + e.getMessage());
        }
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
    }

}
