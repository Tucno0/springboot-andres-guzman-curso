package org.tucno.springboot.security.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistsByUsernameValidator.class)
@Target(ElementType.FIELD )
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsByUsername {
    // message es un atributo de la anotación que se utiliza para mostrar un mensaje de error personalizado
    String message() default "ya existe en la base de datos";

    // groups es un atributo de la anotación que se utiliza para agrupar restricciones de validación
    Class<?>[] groups() default { };

    // payload es un atributo de la anotación que se utiliza para proporcionar metadatos adicionales sobre la restricción
    Class<? extends Payload>[] payload() default { };
}
