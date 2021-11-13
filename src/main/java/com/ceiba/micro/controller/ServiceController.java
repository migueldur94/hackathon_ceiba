package com.ceiba.micro.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
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

    private static final String HACKATHON_SERVICE = "hackathon";

    @Value("${application.hackathon.host}?number=")
    private String host;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @GetMapping("/healthz")
    @CircuitBreaker(name = HACKATHON_SERVICE, fallbackMethod = "hackathonFallback")
    @Bulkhead(name = HACKATHON_SERVICE, fallbackMethod = "hackathonFallback")
    @Retry(name = HACKATHON_SERVICE, fallbackMethod = "hackathonFallback")
    @Cacheable(value = "condition", condition = "#number <= 4")
    public ResponseEntity<ResponseDto> callApi(@RequestParam(value = "number") int numero) {
        ResponseDto objeto = restTemplate.getForObject(host + numero, ResponseDto.class);
        return new ResponseEntity<ResponseDto>(objeto, HttpStatus.OK);
    }

    @GetMapping("/healthz")
    public ResponseEntity<HttpStatus> healthz() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<String> hackathonFallback(Exception e) {
        return new ResponseEntity<String>("El API no puede responder", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
