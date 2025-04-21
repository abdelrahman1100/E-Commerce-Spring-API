package com.masteryhub.e_commerce.service.orderService;

import com.masteryhub.e_commerce.models.*;
import com.masteryhub.e_commerce.repository.OrderRepository;
import com.masteryhub.e_commerce.repository.CartRepository;
import com.masteryhub.e_commerce.repository.ProductRepository;
import com.masteryhub.e_commerce.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public ResponseEntity<Order> checkout(Long userId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if (!userDetails.getId().equals(userId)) {
            return ResponseEntity.badRequest().body(null);
        }

        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null || cart.getItems().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        Order order = new Order();
        order.setUser(cart.getUser());
        order.setStatus("COMPLETED");

        double totalPrice = 0;
        for (CartItem cartItem : cart.getItems()) {
            Product product = cartItem.getProduct();
            int quantity = cartItem.getQuantity();
            totalPrice += product.getPrice() * quantity;

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);

            order.getOrderItems().add(orderItem);
        }

        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);

        cart.getItems().clear();
        cartRepository.save(cart);

        return ResponseEntity.ok(savedOrder);
    }
}
