package com.ceiba.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ServiceController {

	
	@GetMapping(value = "/healthz")
	public ResponseEntity<String> validationMutant() {


		return new ResponseEntity<>("Por fin", HttpStatus.OK);

	}
}
