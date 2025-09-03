package com.shop.shop.Entity;

import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String mobileNumber;
    private String password;
}
