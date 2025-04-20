package com.masteryhub.e_commerce.models;

import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;

    @Lob
    private byte[] image;

    @ManyToOne
    private Category category;
}

