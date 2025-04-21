package com.masteryhub.e_commerce.service.productService;

import com.masteryhub.e_commerce.dto.productDto.ProductDto;
import com.masteryhub.e_commerce.models.Category;
import com.masteryhub.e_commerce.models.Product;
import com.masteryhub.e_commerce.repository.CategoryRepository;
import com.masteryhub.e_commerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseEntity<Product> CreateProduct(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);

        MultipartFile file = productDto.getImage();

        if (file != null && !file.isEmpty()) {
            try {
                if (!file.getContentType().startsWith("image")) {
                    throw new RuntimeException("The uploaded file is not an image");
                }

                if (file.getSize() > 10485760) { // 10MB size limit
                    throw new RuntimeException("The uploaded image is too large");
                }

                product.setImage(file.getBytes());
            } catch (Exception e) {
                logger.error("Failed to process image file", e);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            logger.warn("No image uploaded for product: " + productDto.getName());
        }

        Product savedProduct = productRepository.save(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    public ResponseEntity<String> deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
    }
}
