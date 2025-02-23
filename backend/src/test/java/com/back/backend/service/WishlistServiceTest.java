package com.back.backend.service;

import com.back.backend.mapper.WishlistCartDTO;
import com.back.backend.entities.Product;
import com.back.backend.entities.User;
import com.back.backend.entities.Wishlist;
import com.back.backend.repository.ProductRepository;
import com.back.backend.repository.UserRepository;
import com.back.backend.repository.WishlistRepository;
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
public class WishlistServiceTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WishlistService wishlistService;

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
    public void testGetOrCreateWishlists_existing() {
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        when(wishlistRepository.findByUser(user)).thenReturn(Optional.of(wishlist));

        Wishlist result = wishlistService.getOrCreateWishlists(authentication);
        assertNotNull(result);
        assertEquals(user, result.getUser());
    }

    @Test
    public void testGetOrCreateWishlists_createNew() {
        when(wishlistRepository.findByUser(user)).thenReturn(Optional.empty());
        Wishlist newWishlist = new Wishlist();
        newWishlist.setUser(user);
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(newWishlist);

        Wishlist result = wishlistService.getOrCreateWishlists(authentication);
        assertNotNull(result);
        assertEquals(user, result.getUser());
    }

    @Test
    public void testAddProductToWishlist() {
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        when(wishlistRepository.findByUser(user)).thenReturn(Optional.of(wishlist));

        Product product = new Product();
        product.setId(1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(wishlist);

        WishlistCartDTO dto = wishlistService.addProductToWishlist(authentication, 1L);
        assertNotNull(dto);
        // Verify that the product was added
        assertTrue(wishlist.getProducts().contains(product));
    }

    @Test
    public void testRemoveProductFromWishlist() {
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        Product product = new Product();
        product.setId(1L);
        wishlist.getProducts().add(product);
        when(wishlistRepository.findByUser(user)).thenReturn(Optional.of(wishlist));
        when(wishlistRepository.save(wishlist)).thenReturn(wishlist);

        Wishlist updated = wishlistService.removeProductFromWishlist(authentication, 1L);
        assertFalse(updated.getProducts().contains(product));
    }

    @Test
    public void testGetOrCreateWishlist() {
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        when(wishlistRepository.findByUser(user)).thenReturn(Optional.of(wishlist));

        List<WishlistCartDTO> dtoList = wishlistService.getOrCreateWishlist(authentication);
        assertNotNull(dtoList);
    }
}
