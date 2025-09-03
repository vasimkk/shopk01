package com.shop.shop.Entity;

import lombok.Data;

@Data
public class LoginRequest {
    private String mobileNumber;
    private String password;
}
