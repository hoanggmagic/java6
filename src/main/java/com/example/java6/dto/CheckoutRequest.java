package com.example.java6.dto;

import lombok.Data;
import java.util.List;

@Data
public class CheckoutRequest {
    private String address;
    private List<CartItemDTO> items;
}
