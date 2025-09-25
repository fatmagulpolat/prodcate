package com.prodcate.prodcate.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Category {

    @GeneratedValue
    UUID id;
    String name;
    String description;

}
