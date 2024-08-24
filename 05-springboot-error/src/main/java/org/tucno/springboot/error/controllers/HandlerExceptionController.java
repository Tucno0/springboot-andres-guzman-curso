package org.tucno.springboot.error.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.tucno.springboot.error.exceptions.UserNotFoundException;
import org.tucno.springboot.error.models.Error;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * MANEJO DE EXCEPCIONES
 * @RestControllerAdvice es una anotación de marcador especial que se utiliza para anotar clases de anotación @ControllerAdvice o @RestControllerAdvice.
 * Es una especialización de @Component, que se utiliza para mappear excepciones a métodos de manejo de excepciones.
 */
@RestControllerAdvice
public class HandlerExceptionController {

    // @ExceptionHandler es una anotación de marcador especial que se utiliza para anotar métodos de manejo de excepciones.
    // Se utiliza para manejar excepciones específicas y enviar respuestas de error al cliente.
    // En este caso, se maneja la excepción ArithmeticException.
    // Dentro de las llaves se especifica el o los tipos de excepciones que se van a manejar.
    @ExceptionHandler({ArithmeticException.class})
    // ResponseEntity es una clase de Spring que representa toda la respuesta HTTP: código de estado, encabezados y cuerpo.
    public ResponseEntity<Error> divideByZero(Exception ex) {
        Error error = new Error();
        error.setMessage(ex.getMessage());
        error.setError("División por cero");
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setDate(new Date());

        // ResponseEntity.internalServerError() crea una instancia de ResponseEntity con un código de estado 500.
//        return ResponseEntity.internalServerError().body(error);

        // Otra forma
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    // Manejo de excepciones NoHandlerFoundException (Recurso no encontrado / 404)
    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<Error> noHandlerFound(NoHandlerFoundException ex) {
        Error error = new Error();
        error.setMessage(ex.getMessage());
        error.setError("Recurso no encontrado");
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setDate(new Date());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Manejo de excepciones NullPointerException y HttpMessageNotWritableException
    @ExceptionHandler({NullPointerException.class, HttpMessageNotWritableException.class, UserNotFoundException.class})
    public ResponseEntity<Error> userNotFound(Exception ex) {
        Error error = new Error();
        error.setMessage(ex.getMessage());
        error.setError("Usuario no encontrado");
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setDate(new Date());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Manejo de excepciones NumberFormatException
    @ExceptionHandler({NumberFormatException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> numberFormatException(NumberFormatException ex) {
        Map<String, Object> error = new HashMap<>();

        error.put("message", ex.getMessage());
        error.put("error", "Formato de número incorrecto");
        error.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("date", new Date());

        return error;
    }
}
