package org.product.back.controller;

import org.product.back.entity.Account;
import org.product.back.entity.Product;
import org.product.back.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Validated Product product, Authentication authentication) {
        Account createdAccount = (Account) authentication.getPrincipal();
        if (!"admin@admin.com".equals(createdAccount.getEmail())) {
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) return ResponseEntity.ok(product);
        else return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody @Validated Product updatedProduct, Authentication authentication) {
        Account createdAccount = (Account) authentication.getPrincipal();
        if (!"admin@admin.com".equals(createdAccount.getEmail())) {
            return ResponseEntity.status(403).body(null);
        }
        return ResponseEntity.ok(productService.updateProduct(id, updatedProduct));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

}
