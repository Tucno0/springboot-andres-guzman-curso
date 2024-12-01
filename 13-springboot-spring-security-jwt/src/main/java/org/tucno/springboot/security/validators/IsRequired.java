package org.tucno.springboot.security.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// @Constraint es una anotación que indica que la anotación marcada es una anotación de restricción
// RequiredValidator.class es la clase que implementa la lógica de validación
@Constraint(validatedBy = RequiredValidator.class)

// @Retention es una anotación que indica cómo se conserva la anotación marcada
// RetentionPolicy.RUNTIME indica que la anotación se conserva en tiempo de ejecución
@Retention(RetentionPolicy.RUNTIME)

// @Target es una anotación que indica dónde se puede aplicar la anotación marcada
// ElementType.FIELD indica que la anotación se puede aplicar a los campos de una clase
// ElementType.METHOD indica que la anotación se puede aplicar a los métodos de una clase
@Target({ ElementType.FIELD, ElementType.METHOD })

// @interface indica que la clase es una anotación personalizada
public @interface IsRequired {
    // message es un atributo de la anotación que se utiliza para mostrar un mensaje de error personalizado
    String message() default "This field is required using custom annotation";

    // groups es un atributo de la anotación que se utiliza para agrupar restricciones de validación
    Class<?>[] groups() default { };

    // payload es un atributo de la anotación que se utiliza para proporcionar metadatos adicionales sobre la restricción
    Class<? extends Payload>[] payload() default { };
}
