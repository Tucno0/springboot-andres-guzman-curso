package org.tucno.springboot.interceptors.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component("loadingTimeInterceptor")
public class LoadingTimeInterceptor implements HandlerInterceptor {
    /**
     * INTERCEPTORES EN SPRING
     * Los interceptores en Spring son clases que permiten ejecutar código antes o después de que se ejecute un controlador.
     * Se utilizan para realizar tareas comunes a varios controladores, como la autenticación, la autorización, el manejo de errores, etc.
     * Los interceptores se pueden utilizar para:
     * - Realizar tareas comunes a varios controladores.
     * - Realizar tareas antes o después de que se ejecute un controlador.
     * - Modificar la petición o la respuesta HTTP.
     * - Realizar tareas de autenticación, autorización, manejo de errores, etc.
     * - Realizar tareas de log, auditoría, etc.
     * - Realizar tareas de internacionalización, manejo de excepciones, etc.
     * - Realizar tareas de caché, compresión, etc.
     */

    private static final Logger logger = LoggerFactory.getLogger(LoadingTimeInterceptor.class);

    /**
     * Método que se ejecuta antes de que se ejecute el controlador
     * @param request HttpServletRequest -> Se utiliza para obtener información de la petición HTTP
     * @param response HttpServletResponse -> Se utiliza para enviar información de la respuesta HTTP
     * @param handler Object -> Se utiliza para obtener información del controlador que se va a ejecutar
     * @return boolean -> Se retorna true si se permite la ejecución del controlador, de lo contrario false
     * @throws Exception -> Se lanza una excepción si ocurre un error
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // HandlerMethod: Clase que representa un método de un controlador de Spring
        // Sirve para obtener información sobre el método que se está ejecutando en el controlador
        HandlerMethod method = (HandlerMethod) handler;
        logger.info(
                "LoadingTimeInterceptor: preHandle() -- {} -- {}",
                request.getRequestURI(),
                method.getMethod().getName()
        );

        // Se obtiene el tiempo en milisegundos en el que se ejecuta el método
        long startTime = System.currentTimeMillis();

        // Se guarda el tiempo de inicio en el request
        request.setAttribute("startTime", startTime);

        // Se simula un tiempo de carga aleatorio
        Random random = new Random(); // Se crea un objeto Random
        int delay = random.nextInt(500); // Se genera un número aleatorio entre 0 y 500
        Thread.sleep(delay); // Se detiene el hilo actual durante el tiempo indicado1

        // Se retorna true para permitir la ejecución del controlador
        return true;

//        // Para retornar un error de autenticación cuando el interceptor retorna false
//        Map<String, String> json = new HashMap<>();
//        json.put("error", "No autorizado");
//        json.put("mensaje", "Necesita autenticarse para acceder al recurso");
//        json.put("ruta", request.getRequestURI());
//
//        // Se convierte el mapa a un JSON con ObjectMapper
//        ObjectMapper mapper = new ObjectMapper();
//
//        // Se escribe el JSON en la respuesta HTTP
//        String jsonString = mapper.writeValueAsString(json);
//        response.setContentType("application/json"); // Se indica que el contenido de la respuesta es un JSON
//        response.setStatus(401); // Se establece el código de estado de la respuesta
//        response.getWriter().write(jsonString); // Se escribe el JSON en la respuesta
//
//        // Si se retorna false, no se ejecuta el controlador y se detiene la petición
//        return false;
    }

    /**
     * Método que se ejecuta después de que se ejecuta el controlador
     * @param request HttpServletRequest -> Se utiliza para obtener información de la petición HTTP
     * @param response HttpServletResponse -> Se utiliza para enviar información de la respuesta HTTP
     * @param handler Object -> Se utiliza para obtener información del controlador que se va a ejecutar
     * @param modelAndView ModelAndView -> Se utiliza para obtener información del modelo y la vista que se va a mostrar
     * @throws Exception -> Se lanza una excepción si ocurre un error
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        // Se obtiene el tiempo de inicio del request
        long startTime = (long) request.getAttribute("startTime");

        // Se obtiene el tiempo en milisegundos en el que se termina de ejecutar el método
        long endTime = System.currentTimeMillis();

        // Se calcula el tiempo de carga
        long loadingTime = endTime - startTime;

        logger.info(
                "LoadingTimeInterceptor: postHandle() -- {} -- {} -- Tiempo de carga: {} ms",
                request.getRequestURI(),
                ((HandlerMethod) handler).getMethod().getName(),
                loadingTime
        );
    }
}
