package com.masteryhub.e_commerce.controllers.productController;

import com.masteryhub.e_commerce.dto.productDto.ProductDto;
import com.masteryhub.e_commerce.models.Product;
import com.masteryhub.e_commerce.service.productService.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Product> CreateProduct(@ModelAttribute @Valid ProductDto productDto) {
        return productService.CreateProduct(productDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        return productService.getAllProducts();
    }
}
