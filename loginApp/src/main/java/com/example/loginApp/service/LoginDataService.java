//package com.example.loginApp.service;
//
//import com.example.loginApp.model.Role;
//import com.example.loginApp.model.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import javax.annotation.PostConstruct;
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.logging.Logger;
//
//public class LoginDataService {
//    private static final Logger LOGGER = Logger.getLogger(LoginDataService.class.getName());
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private RoleService roleService;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @PostConstruct
//    public void insertSampleData(){
//        LOGGER.info("Inserting sample data...");
//
//        Role managerRole = createRoleIfNotExists("MANAGER");
//        Role userRole = createRoleIfNotExists("USER");
//
//        createUserIfNotExists("manager","password123", "Manager Sally", managerRole);
//        createUserIfNotExists("manager2","password123", "Manager Adam", managerRole);
//        createUserIfNotExists("user","password123", "User Sam", userRole);
//        createUserIfNotExists("user2","password123", "User Mary", userRole);
//
//        LOGGER.info("Sample data insertion complete.");
//    }
//
//    private Role createRoleIfNotExists(String roleName){
//        Optional<Role> existingRole = roleService.findByName(roleName);
//        return existingRole.orElseGet(() -> {
//            Role newRole = new Role(null, roleName, new HashSet<>());
//            roleService.saveRole(newRole);
//            LOGGER.info("Role created: " + newRole.getName());
//            return newRole;
//        });
//    }
//
//    private void createUserIfNotExists(String username, String password, String fullName, Role role){
//        Optional<User> existingUser = userService.findByUsername(username);
//        if(existingUser.isEmpty()){
//            User newUser = new User(null, username, passwordEncoder.encode(password), fullName, new HashSet<>());
//            newUser.getRoles().add(role);
//            userService.saveUser(newUser);
//            LOGGER.info("User created: " + newUser.getUsername());
//        }
//    }
//}
