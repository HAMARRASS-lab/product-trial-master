package com.back.backend.service;

import com.back.backend.mapper.ShoppingCartDTO;
import com.back.backend.entities.Product;
import com.back.backend.entities.ShoppingCart;
import com.back.backend.entities.User;
import com.back.backend.repository.ProductRepository;
import com.back.backend.repository.ShoppingCartRepository;
import com.back.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ShoppingCartService shoppingCartService;

    private Authentication authentication;
    private Jwt jwt;
    private User user;

    @BeforeEach
    public void setup() {
        jwt = mock(Jwt.class);
        when(jwt.getClaim("sub")).thenReturn("test@example.com");

        authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(jwt);

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        when(userRepository.findUserByEmail("test@example.com")).thenReturn(user);
    }

    @Test
    public void testGetOrCreateCarts_existingCart() {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));

        ShoppingCart result = shoppingCartService.getOrCreateCarts(authentication);
        assertNotNull(result);
        assertEquals(user, result.getUser());
    }

    @Test
    public void testGetOrCreateCarts_createNewCart() {
        when(cartRepository.findByUser(user)).thenReturn(Optional.empty());
        ShoppingCart newCart = new ShoppingCart();
        newCart.setUser(user);
        when(cartRepository.save(any(ShoppingCart.class))).thenReturn(newCart);

        ShoppingCart result = shoppingCartService.getOrCreateCarts(authentication);
        assertNotNull(result);
        assertEquals(user, result.getUser());
    }

    @Test
    public void testAddProductToCart() {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));

        Product product = new Product();
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(cartRepository.save(any(ShoppingCart.class))).thenReturn(cart);

        ShoppingCartDTO dto = shoppingCartService.addProductToCart(authentication, 1L);
        assertNotNull(dto);
        // Verify that the product was added
        assertTrue(cart.getProducts().contains(product));
    }

    @Test
    public void testRemoveProductFromCart() {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        Product product = new Product();
        product.setId(1L);
        cart.getProducts().add(product);
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenReturn(cart);

        ShoppingCart updatedCart = shoppingCartService.removeProductFromCart(authentication, 1L);
        assertFalse(updatedCart.getProducts().contains(product));
    }

    @Test
    public void testGetOrCreateCart() {
        ShoppingCart cart = new ShoppingCart();
        cart.setUser(user);
        when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));

        List<ShoppingCartDTO> dtoList = shoppingCartService.getOrCreateCart(authentication);
        assertNotNull(dtoList);
    }
}
