package org.tucno.springboot.aop.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

// La anotación @Order indica el orden en el que se ejecutarán los aspectos de Spring
// En este caso, este aspecto se ejecutará antes que el aspecto GreetingAspect
@Order(1)
@Aspect
@Component
public class GreetingFooAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("GreetingServicePointcuts.greetingFooLoggerPointCut()")
    public void loggerBefore(JoinPoint joinPoint) {
        // Obtenemos el nombre del método que se está ejecutando
        String method = joinPoint.getSignature().getName();
        // Obtenemos el nombre de la clase que contiene el método que se está ejecutando
        String className = joinPoint.getTarget().getClass().getName();
        // Obtenemos los argumentos del método que se está ejecutando
        String args = Arrays.toString(joinPoint.getArgs());

        // Imprimimos un mensaje en el log
        logger.info("Order(1) Before method: {} of class: {} with args: {}", method, className, args);
    }

    // La anotación @After indica que este método se ejecutará después de que se ejecute el método de negocio
    @After("GreetingServicePointcuts.greetingFooLoggerPointCut()")
    public void loggerAfter(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        logger.info("Order(1) After method: {} of class: {} with args: {}", method, className, args);
    }

    // La anotación @Around indica que este método se ejecutará alrededor del método de negocio
    @Around("execution(* org.tucno.springboot.aop.services.*.*(..))")
    public Object loggerAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        logger.info("Order(1) Before method Around: {} of class: {} with args: {}", method, className, args);

        // Aquí se ejecuta el método de negocio
        // Se ejecutará el before y after del método de negocio
        Object result = joinPoint.proceed();

        logger.info("Order(1) After method Around: {} of class: {} with args: {} and result: {}", method, className, args, result);

        return result;
    }
}
