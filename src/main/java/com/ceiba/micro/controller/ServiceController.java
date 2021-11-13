package com.ceiba.micro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ceiba.micro.dto.ResponseDto;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.PathParam;

@RestController
@RequiredArgsConstructor
public class ServiceController {

    private static final String ORDER_SERVICE = "hackathon";

    @Value("${application.hackathon.host}?number=")
    private String host;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @GetMapping("/healthz")
    @CircuitBreaker(name = ORDER_SERVICE, fallbackMethod = "orderFallback")
    public ResponseDto createOrder(@RequestParam(value = "number") int numero) {
        return restTemplate.getForObject(host + numero, ResponseDto.class);
    }

    public ResponseEntity<String> orderFallback(Exception e) {
        return new ResponseEntity<String>("El API no puede responder", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
