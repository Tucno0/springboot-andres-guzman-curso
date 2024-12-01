package org.tucno.springboot.security.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.tucno.springboot.security.entities.Product;

// VALIDATORS EN SPRING BOOT
// Los validadores en Spring Boot son clases que implementan la interfaz Validator y que se utilizan para validar objetos de un tipo específico.
// Se pueden utilizar para validar objetos en el lado del servidor antes de enviarlos a la base de datos.
@Component
public class ProductValidator implements Validator {

    // La interfaz Validator tiene tres métodos: supports(), validate() y validateObject().

    // El método validateObject() es un método predeterminado que se utiliza para validar un objeto.
    @Override
    public Errors validateObject(Object target) {
        return Validator.super.validateObject(target);
    }

    // El método supports() se utiliza para comprobar si el validador es compatible con el objeto que se va a validar.
    // En este caso, el método supports() comprueba si el objeto que se va a validar es de tipo Product.
    // Como parámetro, el método supports() recibe un objeto de tipo Class<?>. Este objeto es la clase del objeto que se va a validar.
    @Override
    public boolean supports(Class<?> clazz) {
        // El método supports() devuelve true si la clase del objeto que se va a validar es de tipo Product.
        return Product.class.isAssignableFrom(clazz);
    }

    // El método validate() se utiliza para validar el objeto.
    // En este caso, el método validate() no hace nada, ya que no hay ninguna validación que realizar.
    // El método validate() recibe dos parámetros: el objeto que se va a validar y un objeto de tipo Errors.
    // El objeto de tipo Errors se utiliza para almacenar los errores de validación.
    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;

        // La clase ValidationUtils proporciona métodos estáticos para validar campos de un objeto.
        // El método rejectIfEmptyOrWhitespace() se utiliza para comprobar si un campo de un objeto es nulo o está en blanco.
        // Si el campo es nulo o está en blanco, se añade un error al objeto de tipo Errors.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", null, "Name is required");
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "NotEmpty.product.price");

        // Otra forma de añadir errores al objeto de tipo Errors es utilizando el método rejectValue().
        if (product.getDescription() == null || product.getDescription().isBlank()) {
            // El método rejectValue() recibe tres parámetros: el nombre del campo, el código del error y el mensaje de error.
            // En este caso, el nombre del campo es "description", el código del error es "NotEmpty.product.description" y el mensaje de error es "Description is required".
            errors.rejectValue("description", "NotBlank.product.description", "Description is required");
        }

        if (product.getPrice() == null) {
            errors.rejectValue("price", "NotNull.product.price", "Price is required");
        } else if (product.getPrice() < 500) {
            errors.rejectValue("price", "Min.product.price", "Price must be greater than or equal to 500");
        }
    }
}
