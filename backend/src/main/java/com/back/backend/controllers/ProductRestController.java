package com.back.backend.controllers;

import com.back.backend.entities.Product;
import com.back.backend.enums.InventoryStatus;
import com.back.backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ProductRestController {


    @Autowired
    private ProductService productService;


    public ProductRestController(ProductService productService) {

        this.productService = productService;
    }

    @GetMapping("/products")
    @PreAuthorize("hasAuthority('SCOPE_USER') || hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PutMapping("/productInvenStatus/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Product updateProductByInventoryStatus(@RequestParam InventoryStatus inventoryStatus, @PathVariable Long id) {
        return productService.updateProductByInventoryStatus(inventoryStatus, id);
    }


    @GetMapping("/products/{id}")
    @PreAuthorize("hasAuthority('SCOPE_USER') || hasAuthority('SCOPE_ADMIN')")
    public Product getProductById(Long id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/deleteProduct/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteProductById(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
    }

    @PostMapping("/addProduct")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PostMapping(path = "/PostImgForProductId", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Product addProductImage(@RequestParam MultipartFile file, @RequestParam Long productId) throws IOException {
        return productService.addProductImage(file, productId);
    }


    @PutMapping("/products/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(id, productDetails);
        return updatedProduct != null ? ResponseEntity.ok(updatedProduct) : ResponseEntity.notFound().build();
    }

}




