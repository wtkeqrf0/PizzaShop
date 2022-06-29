package com.PizzaApi.models;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
 private String email, password, code;
}
