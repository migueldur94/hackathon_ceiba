package com.ceiba.micro.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ResponseDto {

	private String hostname;
    private String method;
    private String url;
    private String data;
    private String validitySeconds;
    private String token;

 
}
