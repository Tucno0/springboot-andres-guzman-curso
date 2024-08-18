package org.tucno.springboot.springbootweb.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import org.tucno.springboot.springbootweb.models.User;
import org.tucno.springboot.springbootweb.models.dtos.ParamDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/values")
public class ValuesPropertiesController {

    // @Value es una anotación que se utiliza para inyectar valores de propiedades de configuración en Spring (application.properties)
    @Value("${config.code}")
    private String code;

    @Value("${config.username}")
    private String username;

    @Value("${config.password}")
    private String password;

//    @Value("${config.message}")
//    private String message;

    @Value("${config.listOfValues}")
    private List<String> listOfValues;

    // Lenguaje de expresión de Spring (SpEL) para inyectar un mapa de valores de propiedades de configuración en Spring (application.properties)
    @Value("#{ '${config.listOfValues}'.toUpperCase().split(',') }")
    private List<String> listOfValuesSpel;

    @Value("#{ '${config.listOfValues}'.toUpperCase() }")
    private String listOfValuesString;

    // inyecta un mapa de valores de propiedades de configuración en Spring (values.properties)
    @Value("#{${config.valuesMap}}")
    private Map<String, Object> valuesMap;

    @Value("#{${config.valuesMap}.product}")
    private String product;

    @Value("#{${config.valuesMap}.price}")
    private Integer price;


    // inyecta un objeto de tipo Environment para acceder a las propiedades de configuración
    @Autowired
    private Environment environment;


    @GetMapping("/config")
    // se inyecta el valor de la propiedad config.message en el método config
    public Map<String, Object> config(@Value("${config.message}") String message) {
        Map<String, Object> json = new HashMap<>();

        // se accede a las propiedades de configuración mediante la anotación @Value
        json.put("code", code);
        json.put("username", username);
        json.put("password", password);
        json.put("message", message);
        json.put("listOfValues", listOfValues);
        json.put("listOfValuesSpel", listOfValuesSpel);
        json.put("listOfValuesString", listOfValuesString);
        json.put("valuesMap", valuesMap);
        json.put("product", product);
        json.put("price", price);

        // se accede a las propiedades de configuración mediante el objeto Environment
        json.put("config.message", environment.getProperty("config.message"));
        json.put("config.code", Integer.parseInt(environment.getProperty("config.code", "0")));
        json.put("config.code.long", environment.getProperty("config.code", Long.class, 0L));

        return json;
    }
}
