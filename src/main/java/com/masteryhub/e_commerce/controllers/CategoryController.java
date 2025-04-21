package com.masteryhub.e_commerce.controllers;

import com.masteryhub.e_commerce.models.Product;
import com.masteryhub.e_commerce.service.categoryService.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<Product>>SearchProductByCategory(@RequestParam String category) {
        return categoryService.SearchProductByCategory(category);
    }
}
