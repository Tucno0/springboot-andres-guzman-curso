package org.tucno.springboot.crud.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class RequiredValidator implements ConstraintValidator<IsRequired, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Validación personalizada para verificar si el campo no está vacío
//        return value != null && !value.isEmpty() && !value.isBlank();

        // Usando StringUtils
        return StringUtils.hasText(value);
    }
}
