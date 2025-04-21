package com.masteryhub.e_commerce.service.categoryService;

import com.masteryhub.e_commerce.models.Product;
import com.masteryhub.e_commerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<List<Product>> SearchProductByCategory(String category) {
        List<Product> products = productRepository.findByCategory_Name(category);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

}
