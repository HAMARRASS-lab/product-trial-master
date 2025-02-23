package com.back.backend.service;

import com.back.backend.mapper.ShoppingCartDTO;
import com.back.backend.entities.Product;
import com.back.backend.entities.ShoppingCart;
import com.back.backend.entities.User;
import com.back.backend.repository.ProductRepository;
import com.back.backend.repository.ShoppingCartRepository;
import com.back.backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public ShoppingCart getOrCreateCarts(Authentication auth) {
        Jwt jwt = (Jwt) auth.getPrincipal();
        String email = jwt.getClaim("sub");
        User user = userRepository.findUserByEmail(email);
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    ShoppingCart cart = new ShoppingCart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    public ShoppingCartDTO addProductToCart(Authentication auth, Long productId) {
        ShoppingCart cart = getOrCreateCarts(auth);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
        cart.getProducts().add(product);
        cartRepository.save(cart);
        return ShoppingCartDTO.convertToDTO(product);
    }

    public ShoppingCart removeProductFromCart(Authentication auth, Long productId) {
        ShoppingCart cart = getOrCreateCarts(auth);
        cart.getProducts().removeIf(product -> product.getId().equals(productId));
        return cartRepository.save(cart);
    }


    public List<ShoppingCartDTO> getOrCreateCart(Authentication auth) {
        Jwt jwt = (Jwt) auth.getPrincipal();
        String email = jwt.getClaim("sub");
        User user = userRepository.findUserByEmail(email);
        return ShoppingCartDTO.fromShoppingCartList(cartRepository.findByUser(user)
                .orElseGet(() -> {
                    ShoppingCart cart = new ShoppingCart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                }));
    }
}
