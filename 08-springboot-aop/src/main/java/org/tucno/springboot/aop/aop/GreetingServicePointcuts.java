package org.tucno.springboot.aop.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class GreetingServicePointcuts {
    // La anotación @Pointcut indica que este método es un punto de corte
    // Un punto de corte es una expresión que define cuándo se ejecutará un aspecto en una aplicación
    // En este caso, este punto de corte se ejecutará antes de que se ejecute cualquier método en la clase GreetingService dentro del paquete org.tucno.springboot.aop.services
    @Pointcut("execution(* org.tucno.springboot.aop.services.GreetingService.*(..))")
    public void greetingLoggerPointCut() {}

    @Pointcut("execution(* org.tucno.springboot.aop.services.GreetingService.*(..))")
    public void greetingFooLoggerPointCut() {}
}
