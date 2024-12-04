package org.tucno.springboot.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tucno.springboot.security.entities.User;
import org.tucno.springboot.security.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// UserDetails es una interfaz que representa a un usuario en el sistema de seguridad de Spring
// UserDetailsService es una interfaz que se utiliza para cargar un usuario a partir de un nombre de usuario (username)

// Esta clase implementa la interfaz UserDetailsService de Spring Security para cargar un usuario a partir de un nombre de usuario
// Sera parte del contexto de Spring y se inyectará en la configuración de seguridad de SpringSecurityConfig
@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    // El método loadUserByUsername recibe un nombre de usuario y debe devolver un objeto UserDetails
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);

        // Si el usuario no existe, se lanza una excepción UsernameNotFoundException
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("No existe el usuario " + username);
        }

        User user = userOptional.orElseThrow();

        // Se crea una lista de GrantedAuthority a partir de los roles del usuario
        // GrantedAuthority es una interfaz que representa un permiso o autoridad concedida a un usuario en el sistema de seguridad de Spring
        List<GrantedAuthority> authorities = user.getRoles().stream() // con .stream() se convierte la lista de roles en un Stream
                .map(role -> new SimpleGrantedAuthority(role.getName())) // se mapea cada rol a un SimpleGrantedAuthority con el nombre del rol
                .collect(Collectors.toList()); // se convierte el Stream en una lista de GrantedAuthority

        // Se crea y devuelve un objeto UserDetails con el nombre de usuario, la contraseña, y la lista de autoridades
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                authorities
        );
    }
}
