package org.tucno.springboot.springbootweb.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tucno.springboot.springbootweb.models.dtos.ParamDto;
import org.tucno.springboot.springbootweb.models.dtos.ParamMixDto;

@RestController
@RequestMapping("/api/params")
public class RequestParamsController {

    @GetMapping("/foo")
    // @RequestParam es una anotación que se utiliza para vincular un parámetro de solicitud web a un método controlador en Spring MVC.
    // Por ejemplo, en la URL http://localhost:8080/api/params/foo?message=Hello se envía el parámetro message con el valor Hello
    // required = false indica que el parámetro no es obligatorio
    public ParamDto foo(@RequestParam( required = false, defaultValue = "No message") String message) {
        ParamDto paramDto = new ParamDto();
        paramDto.setMessage(message != null ? message : "No");
        return paramDto;
    }

    @GetMapping("/bar")
    // En este caso se envían dos parámetros en la URL http://localhost:8080/api/params/bar?text=Hello&code=200
    public ParamMixDto bar(@RequestParam String text, @RequestParam( required = false ) Integer code) {
        ParamMixDto paramMixDto = new ParamMixDto();
        paramMixDto.setMessage(text);
        paramMixDto.setCode(code);
        return paramMixDto;
    }

    @GetMapping("/request")
    // HttpServletRequest es una interfaz nativa de Java que permite a los desarrolladores obtener información sobre la solicitud HTTP.
    // En este caso se obtienen los parámetros de la URL a través del objeto request y se asignan a un objeto ParamMixDto
    // Por ejemplo, en la URL http://localhost:8080/api/params/request?text=Hello&code=200
    public ParamMixDto request(HttpServletRequest request) {
        Integer code = 0;

        // Se maneja la excepción NumberFormatException en caso de que el parámetro code no sea un número
        try {
            // Se obtienen los parámetros de la URL a través del objeto request
            code = Integer.parseInt(request.getParameter("code"));
        } catch (NumberFormatException e) {
        }

        ParamMixDto paramMixDto = new ParamMixDto();
        paramMixDto.setMessage(request.getParameter("text"));
        paramMixDto.setCode(code);

        return paramMixDto;
    }
}
