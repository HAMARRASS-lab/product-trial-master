package com.back.backend.service;

import com.back.backend.entities.Product;
import com.back.backend.enums.InventoryStatus;
import com.back.backend.exception.ProductException;
import com.back.backend.exception.ProductValidationException;
import com.back.backend.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ProductValidationException("There are no products.");
        }
        return productRepository.findAll();
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public Product addProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }


    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    public Product updateProduct(Long id, Product productDetails) {

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductException("Product not found with ID: " + id, HttpStatus.NOT_FOUND));

        existingProduct.setCode(productDetails.getCode());
        existingProduct.setName(productDetails.getName());
        existingProduct.setDescription(productDetails.getDescription());
        existingProduct.setImage(productDetails.getImage());
        existingProduct.setCategory(productDetails.getCategory());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setQuantity(productDetails.getQuantity());
        existingProduct.setInternalReference(productDetails.getInternalReference());
        existingProduct.setShellId(productDetails.getShellId());
        existingProduct.setRating(productDetails.getRating());
        existingProduct.setInventoryStatus(productDetails.getInventoryStatus());

        return productRepository.save(existingProduct);
    }

    public Product addProductImage(MultipartFile file, Long productId) throws IOException {
        Path folderPath = Paths.get(System.getProperty("user.home"), "enset-data", "products");

        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        String fileName = UUID.randomUUID().toString() + ".jpg";
        Path filePath = Paths.get(folderPath.toString(), fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        product.setImage(filePath.toString());
        return productRepository.save(product);
    }

    public Product updateProductByInventoryStatus(InventoryStatus inventoryStatus, Long id) {
        Product product = productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        product.setInventoryStatus(inventoryStatus);
        return productRepository.save(product);
    }
}
