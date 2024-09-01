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
// En este caso, este aspecto se ejecutará después que el aspecto GreetingFooAspect
@Order(2)
// La anotación @Aspect indica que esta clase es un aspecto de Spring
// Los aspectos son clases que contienen métodos que se ejecutan antes, después o alrededor de un punto de corte en una aplicación
@Aspect
@Component
public class GreetingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * La anotación @Before indica que este método se ejecutará antes de que se ejecute el método de negocio
     * La expresión dentro de esta anotación indica lo siguiente:
     * - execution(* org.tucno.springboot.aop..*.*(..)): este método se ejecutará antes de que se ejecute cualquier método en cualquier clase dentro del paquete org.tucno.springboot.aop
     * - execution(* org.tucno.springboot.aop.services..*.*(..)): este método se ejecutará antes de que se ejecute cualquier método en cualquier clase dentro del paquete org.tucno.springboot.aop.services
     * - execution(* org.tucno.springboot.aop.services.GreetingService.*(..)): este método se ejecutará antes de que se ejecute cualquier método en la clase GreetingService dentro del paquete org.tucno.springboot.aop.services
     * - execution(* org.tucno.springboot.aop.services.GreetingService.sayHello(..)): este método se ejecutará antes de que se ejecute el método sayHello en la clase GreetingService dentro del paquete org.tucno.springboot.aop.services
     * - execution(* org.tucno.springboot.aop.services.GreetingService.sayHello(String, String)): este método se ejecutará antes de que se ejecute el método sayHello en la clase GreetingService dentro del paquete org.tucno.springboot.aop.services con los argumentos String, String
     * execution(* *.*(..)): este método se ejecutará antes de que se ejecute cualquier método en cualquier clase
     */
//    @Before("execution(* org.tucno.springboot.aop.services.GreetingService.*(..))")
    @Before("GreetingServicePointcuts.greetingLoggerPointCut()")
    // Este método se ejecutará antes de que se ejecute el método de negocio
    // JoinPoint es un objeto que contiene información sobre el método que se está ejecutando
    public void loggerBefore(JoinPoint joinPoint) {
        // Obtenemos el nombre del método que se está ejecutando
        String method = joinPoint.getSignature().getName();
        // Obtenemos el nombre de la clase que contiene el método que se está ejecutando
        String className = joinPoint.getTarget().getClass().getName();
        // Obtenemos los argumentos del método que se está ejecutando
        String args = Arrays.toString(joinPoint.getArgs());

        // Imprimimos un mensaje en el log
        logger.info("Order(2) Before method: {} of class: {} with args: {}", method, className, args);
    }

     // La anotación @After indica que este método se ejecutará después de que se ejecute el método de negocio
    @After("GreetingServicePointcuts.greetingLoggerPointCut()")
    public void loggerAfter(JoinPoint joinPoint) {
        String method = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        logger.info("Order(2) After method: {} of class: {} with args: {}", method, className, args);
    }

    // La anotación @AfterReturning indica que este método se ejecutará después de que se ejecute el método de negocio y obtendrá el resultado devuelto por el método de negocio
    @AfterReturning(pointcut = "GreetingServicePointcuts.greetingLoggerPointCut()", returning = "result")
    public void loggerAfterReturning(JoinPoint joinPoint, Object result) {
        String method = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        logger.info("Order(2) After returning method: {} of class: {} with args: {} and result: {}", method, className, args, result);
    }

    // La anotación @AfterThrowing indica que este método se ejecutará después de que se ejecute el método de negocio y obtendrá la excepción lanzada por el método de negocio
    @AfterThrowing(pointcut = "GreetingServicePointcuts.greetingLoggerPointCut()", throwing = "error")
    public void loggerAfterThrowing(JoinPoint joinPoint, Throwable error) {
        String method = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        logger.info("Order(2) After throwing method: {} of class: {} with args: {} and error: {}", method, className, args, error.getMessage());
    }

    // La anotación @Around indica que este método se ejecutará alrededor del método de negocio
    @Around("execution(* org.tucno.springboot.aop.services.*.*(..))")
    public Object loggerAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String method = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        logger.info("Order(2) Before method Around: {} of class: {} with args: {}", method, className, args);

        // Ejecutamos el método de negocio y obtenemos el resultado
        Object result = joinPoint.proceed();

        logger.info("Order(2) After method Around: {} of class: {} with args: {} and result: {}", method, className, args, result);

        return result;
    }
}
