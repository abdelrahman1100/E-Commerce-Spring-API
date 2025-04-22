package com.masteryhub.e_commerce.service.cartService;

import com.masteryhub.e_commerce.dto.cartDto.CartProductDTO;
import com.masteryhub.e_commerce.models.Cart;
import com.masteryhub.e_commerce.repository.CartRepository;
import com.masteryhub.e_commerce.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public ResponseEntity<List<CartProductDTO>> ViewCart() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Cart cart = cartRepository.findByUserId(userDetails.getId());

        if (cart == null || cart.getItems().isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<CartProductDTO> cartItems = cart.getItems().stream()
                .map(item -> new CartProductDTO(item.getProduct(), item.getQuantity()))
                .toList();

        return ResponseEntity.ok(cartItems);
    }
}

