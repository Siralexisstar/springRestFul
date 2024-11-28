package com.alejandro.curso.springboot.app.springboot_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alejandro.curso.springboot.app.springboot_crud.entities.Product;
import com.alejandro.curso.springboot.app.springboot_crud.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {

        return (List<Product>) productRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long id) {
        
        return productRepository.findById(id);
    }

    @Transactional
    @Override
    public Product save(Product product) {

        return productRepository.save(product);
    }

    @Transactional
    @Override
    public Optional<Product> delete(Product product) {

        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        optionalProduct.ifPresent(prod -> {
            productRepository.delete(product);
        });

        return optionalProduct;
    }

}
