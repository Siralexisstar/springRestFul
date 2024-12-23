package com.alejandro.curso.springboot.app.springboot_crud;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.alejandro.curso.springboot.app.springboot_crud.entities.Product;

@Component
public class ProductValidation implements Validator {

    // DOS METODOS
    // UNO DA SOPORTE A LA CLASE ENTITY
    @Override
    public boolean supports(Class<?> clazz) {

        return Product.class.isAssignableFrom(clazz);
    }

    // Este es el metodo para las validaciones
    @Override
    public void validate(Object target, Errors errors) {

        Product product = (Product) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", null, "NotEmpty.product.name");
        // ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", null, "NotBlank.product.description");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", null, "no puede ser nulo, ok!");

        // Podemos hacer la validacion mas personalizada
        // Esta es la forma programatica
        if (product.getDescription().isEmpty() || product.getDescription().isBlank()) {
           
            errors.rejectValue("description", "NotBlank.product.description");

        }
    }

}
