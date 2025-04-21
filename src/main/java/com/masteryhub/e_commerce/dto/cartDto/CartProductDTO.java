package com.masteryhub.e_commerce.dto.cartDto;

import com.masteryhub.e_commerce.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartProductDTO {
    private Product product;
    private int quantity;
}
