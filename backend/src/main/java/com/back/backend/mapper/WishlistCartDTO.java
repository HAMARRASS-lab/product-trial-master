package com.back.backend.mapper;

import com.back.backend.entities.Product;
import com.back.backend.entities.Wishlist;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class WishlistCartDTO {
    private String code;
    private String name;
    private String description;
    private String category;
    private double price;
    private int quantity;

    public static WishlistCartDTO convertToDTO(Product cart) {
        WishlistCartDTO dto = new WishlistCartDTO();
        dto.setCode(cart.getCode());
        dto.setName(cart.getName());
        dto.setDescription(cart.getDescription());
        dto.setCategory(cart.getCategory());
        dto.setPrice(cart.getPrice());
        dto.setQuantity(cart.getQuantity());
        return dto;
    }

    public static List<WishlistCartDTO> fromWishlistCartList(Wishlist wishlist) {
        List<WishlistCartDTO> wishlistCartDTOS = new ArrayList<>();
        wishlist.getProducts().forEach(product -> wishlistCartDTOS.add(WishlistCartDTO.convertToDTO(product)));
        return wishlistCartDTOS;
    }

}