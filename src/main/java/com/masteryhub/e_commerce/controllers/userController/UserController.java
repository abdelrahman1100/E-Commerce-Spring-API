package com.masteryhub.e_commerce.controllers.userController;

import com.masteryhub.e_commerce.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/{userId}/product/{productId}")
    public ResponseEntity<String>addProduct(@PathVariable Long productId, @PathVariable Long userId) {
        return userService.addProductToUserCart(productId, userId);
    }
}
