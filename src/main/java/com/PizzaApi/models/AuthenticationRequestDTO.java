package com.PizzaApi.models;

import lombok.Getter;

@Getter
public class AuthenticationRequestDTO {
 private String phoneNumber, password, code;
}