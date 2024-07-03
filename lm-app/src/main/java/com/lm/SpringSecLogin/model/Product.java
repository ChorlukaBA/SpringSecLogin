package com.lm.SpringSecLogin.model;

import javax.persistence.Entity;                // JPA Entity, allows us to map this class to a table in the database
import javax.persistence.GeneratedValue;        // JPA annotation to specify how the primary key should be generated
import javax.persistence.GenerationType;        // JPA annotation to specify the generation strategy for the primary key
import javax.persistence.Id;                    // JPA annotation to specify which is the primary key of an entity

/**
 * The Product class is a JPA Entity, which means that it is a class that is mapped to a table in the database.
 * The table name is the same as the class name, and the columns are the fields of the class.
 * The @Id annotation specifies the primary key of the entity, and the @GeneratedValue annotation specifies how the primary key should be generated.
 */

@Entity
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double price;

    public Product()
    {
    }

    public Product(String name, String description, double price)
    {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }
}