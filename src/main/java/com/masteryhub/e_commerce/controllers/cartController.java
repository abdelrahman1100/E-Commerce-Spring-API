package com.masteryhub.e_commerce.controllers;

import com.masteryhub.e_commerce.dto.cartDto.CartProductDTO;
import com.masteryhub.e_commerce.models.Product;
import com.masteryhub.e_commerce.service.cartService.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class cartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartProductDTO>> ViewCart(@PathVariable Long userId) {
        return cartService.ViewCart(userId);
    }
}
