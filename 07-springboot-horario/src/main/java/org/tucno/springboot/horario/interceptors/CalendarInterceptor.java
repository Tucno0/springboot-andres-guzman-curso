package org.tucno.springboot.horario.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class CalendarInterceptor implements HandlerInterceptor {
    @Value("${config.calendar.open}")
    private Integer open;

    @Value("${config.calendar.close}")
    private Integer close;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        if (hour >= open && hour < close) {
            StringBuilder message = new StringBuilder("Bienvenido al sistema de atención a clientes");
            message.append(", disponible desde las ").append(open).append(" hrs. ");
            message.append("hasta las ").append(close).append(" hrs. ");
            message.append("Gracias por su visita.");

            request.setAttribute("message", message.toString());

            return true;
        }

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> data = new HashMap<>();
        StringBuilder message = new StringBuilder("El sistema de atención a clientes no está disponible");
        message.append(", disponible desde las ").append(open).append(" hrs. ");
        message.append("hasta las ").append(close).append(" hrs. ");
        message.append("Gracias por su visita.");

        data.put("message", message.toString());
        data.put("error", "Fuera de horario de atención");
        data.put("date", new Date());

        response.setContentType("application/json");
        response.setStatus(401);

        // Se escribe el objeto data en el cuerpo de la respuesta
        response.getWriter().write(mapper.writeValueAsString(data));

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
}
