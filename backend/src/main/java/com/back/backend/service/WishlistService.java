package com.back.backend.service;

import com.back.backend.mapper.WishlistCartDTO;
import com.back.backend.entities.Product;
import com.back.backend.entities.User;
import com.back.backend.entities.Wishlist;
import com.back.backend.repository.ProductRepository;
import com.back.backend.repository.UserRepository;
import com.back.backend.repository.WishlistRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public Wishlist getOrCreateWishlists(Authentication auth) {
        Jwt jwt = (Jwt) auth.getPrincipal();
        String email = jwt.getClaim("sub");
        User user = userRepository.findUserByEmail(email);
        return wishlistRepository.findByUser(user)
                .orElseGet(() -> {
                    Wishlist wishlist = new Wishlist();
                    wishlist.setUser(user);
                    return wishlistRepository.save(wishlist);
                });
    }

    public WishlistCartDTO addProductToWishlist(Authentication user, Long productId) {
        Wishlist wishlist = getOrCreateWishlists(user);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
        wishlist.getProducts().add(product);
        wishlistRepository.save(wishlist);
        return WishlistCartDTO.convertToDTO(product);

    }

    public Wishlist removeProductFromWishlist(Authentication user, Long productId) {
        Wishlist wishlist = getOrCreateWishlists(user);
        wishlist.getProducts().removeIf(product -> product.getId().equals(productId));
        return wishlistRepository.save(wishlist);
    }

    public List<WishlistCartDTO> getOrCreateWishlist(Authentication auth) {
        Jwt jwt = (Jwt) auth.getPrincipal();
        String email = jwt.getClaim("sub");
        User user = userRepository.findUserByEmail(email);
        return WishlistCartDTO.fromWishlistCartList(wishlistRepository.findByUser(user)
                .orElseGet(() -> {
                    Wishlist wishlist = new Wishlist();
                    wishlist.setUser(user);
                    return wishlistRepository.save(wishlist);
                }));
    }
}
