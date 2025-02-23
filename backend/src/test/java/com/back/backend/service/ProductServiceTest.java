package com.back.backend.service;

import com.back.backend.entities.Product;
import com.back.backend.enums.InventoryStatus;
import com.back.backend.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    public void setup() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setCode("TP001");
        product.setPrice(100.0);
        product.setQuantity(10);
        product.setInventoryStatus(InventoryStatus.INSTOCK);
    }

    @Test
    public void testGetAllProducts() {
        Mockito.when(productRepository.findAll()).thenReturn(Arrays.asList(product));
        List<Product> products = productService.getAllProducts();
        assertEquals(1, products.size());
        Mockito.verify(productRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testGetProductByIdFound() {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Product found = productService.getProductById(1L);
        Assertions.assertNotNull(found);
        assertEquals("Test Product", found.getName());
    }

    @Test
    public void testGetProductByIdNotFound() {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(EntityNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    public void testAddProduct() {
        Mockito.when(productRepository.save(product)).thenReturn(product);
        Product saved = productService.addProduct(product);
        Assertions.assertNotNull(saved);
        Mockito.verify(productRepository, Mockito.times(1)).save(product);
    }

    @Test
    public void testUpdateProduct() {
        Product updatedDetails = new Product();
        updatedDetails.setName("Updated Product");
        updatedDetails.setPrice(150.0);
        updatedDetails.setQuantity(20);

        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(ArgumentMatchers.any(Product.class))).thenAnswer(i -> i.getArgument(0));

        Product updated = productService.updateProduct(1L, updatedDetails);
        assertEquals("Updated Product", updated.getName());
        assertEquals(150.0, updated.getPrice());
        assertEquals(20, updated.getQuantity());
    }

    @Test
    public void testUpdateProductNotFound() {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());
        Product updated = productService.updateProduct(1L, product);
        Assertions.assertNull(updated);
    }

    @Test
    public void testUpdateProductByInventoryStatus() {
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(product)).thenReturn(product);

        Product updated = productService.updateProductByInventoryStatus(InventoryStatus.OUTOFSTOCK, 1L);
        assertEquals(InventoryStatus.OUTOFSTOCK, updated.getInventoryStatus());
    }
}
