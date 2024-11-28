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
    public Optional<Product> delete(Long id) {

        /** Simplemente modificamos para eliminar por id */
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.ifPresent(prod -> {
            productRepository.delete(prod);
        });

        return optionalProduct;
    }

    @Override
    @Transactional
    public Optional<Product> update(Long id, Product product) {

        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {

            /** Recuperamos el de la bbdd y seteamos el nuevo */
            Product productDb = productOptional.orElseThrow();

            productDb.setName(product.getName());
            productDb.setPrice(product.getPrice());
            productDb.setDescription(product.getDescription());

        }

        return productOptional;
    }

}
