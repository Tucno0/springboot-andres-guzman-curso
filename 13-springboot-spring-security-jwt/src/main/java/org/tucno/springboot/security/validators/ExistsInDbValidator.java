package org.tucno.springboot.security.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tucno.springboot.security.services.ProductService;

@Component
public class ExistsInDbValidator implements ConstraintValidator<ExistsInDb, String> {
    @Autowired
    private ProductService productService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !productService.existsBySku(value);
    }
}
