package org.tucno.springboot.security.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tucno.springboot.security.services.UserService;

@Component
public class ExistsByUsernameValidator implements ConstraintValidator<ExistsByUsername, String> {

    @Autowired
    private UserService userService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        // Si el servicio es nulo, se retorna verdadero para evitar errores
        if (userService == null) {
            return true;
        }

        return !userService.existsByUsername(username);
    }
}
