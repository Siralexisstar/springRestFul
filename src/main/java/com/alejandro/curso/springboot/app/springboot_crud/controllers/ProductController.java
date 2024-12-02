package com.alejandro.curso.springboot.app.springboot_crud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.query.NativeQuery.ReturnProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import com.alejandro.curso.springboot.app.springboot_crud.entities.Product;
import com.alejandro.curso.springboot.app.springboot_crud.services.ProductService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/hello")
    public List<Product> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {

        Optional<Product> productOptional = service.findById(id);
        if (productOptional.isPresent()) {

            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    /** Añadimos el valid para que nos valide los campos */
    @PostMapping("insertData")
    public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result) {

        /**
         * Vamos a validad con el Binding result
         * Ponemos un signo pregunta para poder devolver algo que no sea Producto
         */
        if (result.hasFieldErrors()) {

            return validation(result);

        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product));

    }

    /** Este simplemente lo que hace es recuperar el objeto y actualizarlo */
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@Valid @PathVariable Long id, @RequestBody Product product) {

        product.setId(id);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product));

    }

    @PutMapping("update2/{id}")
    public ResponseEntity<?> update2(@Valid @RequestBody Product product, BindingResult result,
            @PathVariable Long id) {

        if (result.hasErrors()) {
            return validation(result);
        }

        product.setId(id);

        /*** Le metemos validacion */
        Optional<Product> proOptional = service.update(id, product);
        return (proOptional.isPresent()) ? ResponseEntity.ok(proOptional.orElseThrow())
                : ResponseEntity.notFound().build();

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id) {
        Product product = new Product();
        product.setId(id);
        Optional<Product> proOptional = service.delete(id); // LOGICA DE ELIMINADO

        return (proOptional.isPresent()) ? ResponseEntity.ok(proOptional.orElseThrow())
                : ResponseEntity.notFound().build();

    }

    /**
     * Funcion que se encarga de recoger los errores de binding y construir un
     * map con los errores y sus correspondientes mensajes.
     * 
     * @param result objeto que contiene los errores de binding
     * @return un ResponseEntity con un map de errores y un status 400
     */
    private ResponseEntity<?> validation(BindingResult result) {

        /** Hacemos un mapa con los errores */
        Map<String, String> errors = new HashMap();

        // Obtenemos la lista de los errores
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField()
                    + " tiene un error " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }

}
