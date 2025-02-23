package com.back.backend.mapper;

import com.back.backend.entities.Product;
import com.back.backend.entities.ShoppingCart;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ShoppingCartDTO {
    private String code;
    private String name;
    private String description;
    private String category;
    private double price;
    private int quantity;

    public static ShoppingCartDTO convertToDTO(Product cart) {
        ShoppingCartDTO dto = new ShoppingCartDTO();
        dto.setCode(cart.getCode());
        dto.setName(cart.getName());
        dto.setDescription(cart.getDescription());
        dto.setCategory(cart.getCategory());
        dto.setPrice(cart.getPrice());
        dto.setQuantity(cart.getQuantity());
        return dto;
    }

    public static List<ShoppingCartDTO> fromShoppingCartList(ShoppingCart shoppingCart) {
        List<ShoppingCartDTO> shoppingCartDTOS = new ArrayList<>();
        shoppingCart.getProducts().forEach(cart -> shoppingCartDTOS.add(ShoppingCartDTO.convertToDTO(cart)));
        return shoppingCartDTOS;
    }
}