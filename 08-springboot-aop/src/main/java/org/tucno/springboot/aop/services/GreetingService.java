package org.tucno.springboot.aop.services;

public interface GreetingService {
    String sayHello( String person, String phrase);
    String errorMethod( String person, String phrase);
}
