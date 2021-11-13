package com.ceiba.micro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ceiba.micro.dto.ResponseDto;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ServiceController {

	private static final String ORDER_SERVICE = "orderService";


	@Autowired
    private RestTemplate restTemplate;
	
	@Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
	
	@GetMapping("/healthz")
    @CircuitBreaker(name=ORDER_SERVICE, fallbackMethod = "orderFallback")
    public ResponseEntity<ResponseDto> createOrder(){
		
        ResponseDto response = restTemplate.getForObject("http://api-9.hack.local/?number=2", ResponseDto.class);
        
        return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);
    }
	
	
    public ResponseEntity<String> orderFallback(Exception e){
        return new ResponseEntity<String>("Item service is down", HttpStatus.OK);

    }
}
