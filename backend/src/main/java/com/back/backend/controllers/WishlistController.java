package com.back.backend.controllers;

import com.back.backend.mapper.WishlistCartDTO;
import com.back.backend.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@PreAuthorize("hasAnyAuthority('SCOPE_USER','SCOPE_ADMIN')")
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @PostMapping("/add/{productId}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<WishlistCartDTO>  addProductToWishlist(@PathVariable Long productId, Authentication auth) {
        return ResponseEntity.status(201).body(wishlistService.addProductToWishlist(auth, productId));
    }

    @DeleteMapping("/remove/{productId}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<Object> removeProductFromWishlist(@PathVariable Long productId, Authentication auth) {
        wishlistService.removeProductFromWishlist(auth, productId);
        return ResponseEntity.status(200).build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<List<WishlistCartDTO>> getWishlist(Authentication auth) {
        List<WishlistCartDTO> wishlist = wishlistService.getOrCreateWishlist(auth);
        return ResponseEntity.ok(wishlist);
    }
}
