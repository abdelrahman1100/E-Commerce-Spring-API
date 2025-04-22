package com.masteryhub.e_commerce.service.userService;

import com.masteryhub.e_commerce.models.Cart;
import com.masteryhub.e_commerce.models.CartItem;
import com.masteryhub.e_commerce.models.Product;
import com.masteryhub.e_commerce.models.User;
import com.masteryhub.e_commerce.repository.CartItemRepository;
import com.masteryhub.e_commerce.repository.CartRepository;
import com.masteryhub.e_commerce.repository.ProductRepository;
import com.masteryhub.e_commerce.repository.UserRepository;
import com.masteryhub.e_commerce.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public ResponseEntity<String> addProductToUserCart(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userDetails.getId()));

        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
            user.setCart(cart);
            userRepository.save(user);
        }

        CartItem existingItem = cart.getItems() != null
                ? cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null)
                : null;

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            cartItemRepository.save(existingItem);
            return ResponseEntity.ok("Product quantity updated in cart");
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(1);
            cartItemRepository.save(newItem);
            return ResponseEntity.ok("Product added to cart");
        }
    }

    public ResponseEntity<String> removeProductFromUserCart(Long productId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userDetails.getId()));

        Cart cart = user.getCart();
        if (cart == null || cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty or not found.");
        }

        CartItem itemToRemove = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (itemToRemove == null) {
            throw new IllegalArgumentException("Product not found in cart.");
        }

        cartItemRepository.delete(itemToRemove);

        return ResponseEntity.ok("Product removed from cart");
    }

}