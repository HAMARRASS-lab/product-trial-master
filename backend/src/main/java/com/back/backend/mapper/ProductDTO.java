package com.back.backend.mapper;


import lombok.Data;

@Data
public class ProductDTO {
    private String code;
    private String name;
    private String description;
    private String category;
    private Double price;

}
