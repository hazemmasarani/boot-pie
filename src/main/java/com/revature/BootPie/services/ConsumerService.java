package com.revature.BootPie.services;

import java.util.ArrayList;
import java.util.List;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.BootPie.exceptions.ResourceNotFoundException;
import com.revature.BootPie.models.Consumer;
import com.revature.BootPie.models.Pie;

@Service
public class ConsumerService {
    
    private List<Consumer> consumers = new ArrayList<>();

    @Autowired
    private PieService pieService;

    {
        consumers.add(new Consumer("john doe", "password123", null));
    }

    public void registerConsumer(Consumer consumer) {
        if (consumer == null || consumer.getUsername() == null || consumer.getPassword() == null) {
            throw new IllegalArgumentException("Consumer details cannot be null");
        }
        consumers.add(consumer);
    }

    public void orderPie(Consumer consumer, String pieName) {
        if (consumer == null || pieName == null) {
            throw new IllegalArgumentException("Consumer or pie name cannot be null");
        }
        if(!consumers.contains(consumer)) {
            throw new IllegalArgumentException("Consumer not registered");
        }
        Pie pie = pieService.getAllPies().stream()
            .filter(p -> p.getPieName().equalsIgnoreCase(pieName))
            .findFirst()
            .orElse(null);
        if (pie != null) {
            consumer.setLastPie(pie);
            return;
        } 
        throw new ResourceNotFoundException("Pie not available: " + pieName);
    }

    public Consumer login(Consumer consumer) throws AuthenticationException {
        if (consumer == null || consumer.getUsername() == null || consumer.getPassword() == null) {
            throw new IllegalArgumentException("Consumer details cannot be null");
        }
        for (Consumer c : consumers) {
            if (c.getUsername().equals(consumer.getUsername()) && c.getPassword().equals(consumer.getPassword())) {
                System.out.println("Login successful for " + c.getUsername());
                return c;
            }
        }
        throw new AuthenticationException("Invalid username or password");
    }
    
    public Consumer getConsumerByUsername(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        for (Consumer consumer : consumers) {
            if (consumer.getUsername().equals(username)) {
                return consumer;
            }
        }
        throw new ResourceNotFoundException("Consumer not found with username: " + username);
    }

}
