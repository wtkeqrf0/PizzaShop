package com.example.PizzaApi.models;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
 private String email, password, code;
}
