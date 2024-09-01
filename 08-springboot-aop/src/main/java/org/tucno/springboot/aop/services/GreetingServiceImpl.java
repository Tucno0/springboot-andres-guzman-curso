package org.tucno.springboot.aop.services;

import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements GreetingService {
    @Override
    public String sayHello(String person, String phrase) {
        System.out.println("Hello " + person + ", " + phrase);
        return "Hello " + person + ", " + phrase;
    }

    @Override
    public String errorMethod(String person, String phrase) {
        throw new RuntimeException("An error occurred in the errorMethod");
    }
}
