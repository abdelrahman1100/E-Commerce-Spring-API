package com.masteryhub.e_commerce.controllers.userController;

import com.masteryhub.e_commerce.service.userService.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/product/{productId}")
    public ResponseEntity<String> addProduct(@PathVariable Long productId) {
        return userService.addProductToUserCart(productId);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<String> removeProductFromCart(@PathVariable Long productId) {
        return userService.removeProductFromUserCart(productId);
    }

}


