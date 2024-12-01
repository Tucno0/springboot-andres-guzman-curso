package org.tucno.springboot.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tucno.springboot.security.entities.Role;
import org.tucno.springboot.security.entities.User;
import org.tucno.springboot.security.repositories.RoleRepository;
import org.tucno.springboot.security.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {
        // Buscar el rol ROLE_USER en la base de datos
        Optional<Role> optionalRoleUser = roleRepository.findByName("ROLE_USER");

        // Se verifica si el rol existe en la base de datos y se agrega a la lista de roles del usuario
        List<Role> roles = new ArrayList<>();
        optionalRoleUser.ifPresent(role -> roles.add(role));

        // Si el usuario es administrador, se busca el rol ROLE_ADMIN en la base de datos y se agrega a la lista de roles
        if (user.isAdmin()) {
            // Buscar el rol ROLE_ADMIN en la base de datos
            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");

            // Se verifica si el rol existe en la base de datos y se agrega a la lista de roles del usuario
            optionalRoleAdmin.ifPresent(roles::add);
        }

        // Al usuario se le asigna la lista de roles obtenida
        user.setRoles(roles);

        // Se cifra la contraseña del usuario antes de guardarla en la base de datos
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Se guarda el usuario en la base de datos
        return userRepository.save(user);
    }
}
