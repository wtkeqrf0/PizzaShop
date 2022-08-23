package com.PizzaApi.models;

import lombok.Getter;

@Getter
public class AuthenticationRequestDTO {
 private String email, password, code;
}