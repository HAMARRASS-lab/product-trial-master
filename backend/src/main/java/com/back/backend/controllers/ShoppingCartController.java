package com.back.backend.controllers;

import com.back.backend.mapper.ShoppingCartDTO;
import com.back.backend.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@PreAuthorize("hasAnyAuthority('SCOPE_USER','SCOPE_ADMIN')")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService cartService;

    @PostMapping("/add/{productId}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<ShoppingCartDTO> addProductToCart(@PathVariable Long productId, Authentication auth) {
        return ResponseEntity.status(201).body(cartService.addProductToCart(auth, productId));
    }

    @DeleteMapping("/remove/{productId}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<Object> removeProductFromCart(@PathVariable Long productId, Authentication auth) {
        cartService.removeProductFromCart(auth, productId);
        return ResponseEntity.status(200).build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<List<ShoppingCartDTO>> getCart(Authentication auth) {
        List<ShoppingCartDTO> cart = cartService.getOrCreateCart(auth);
        return ResponseEntity.ok(cart);
    }
}
