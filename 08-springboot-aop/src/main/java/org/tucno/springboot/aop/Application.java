package org.tucno.springboot.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

// Enable AspectJ support
// Este anotación habilita el soporte de AspectJ en Spring
//@EnableAspectJAutoProxy // Ya no es necesario ya que se habilita por defecto
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
