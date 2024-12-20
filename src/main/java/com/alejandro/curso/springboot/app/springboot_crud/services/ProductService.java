package com.alejandro.curso.springboot.app.springboot_crud.services;

import java.util.List;
import java.util.Optional;

import com.alejandro.curso.springboot.app.springboot_crud.entities.Product;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    Product save(Product product);

    /** Actualizar un producto */
    Optional<Product> update(Long id, Product product);

    Optional<Product> delete(Long id);
}
