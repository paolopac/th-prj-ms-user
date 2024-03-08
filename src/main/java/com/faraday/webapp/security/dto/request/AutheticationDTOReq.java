package com.faraday.webapp.security.dto.request;

import lombok.Data;

@Data
public class AutheticationDTOReq
{
    private String username; 
    private String password;

}