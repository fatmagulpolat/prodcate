package com.prodcate.prodcate.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;


@Data
@Entity
public class Category extends BaseEntity {

    public String name;
    public String description;
    @OneToMany(mappedBy = "category")
    public List<Product> products;
}
