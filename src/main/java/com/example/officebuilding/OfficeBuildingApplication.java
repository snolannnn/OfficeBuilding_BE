package com.example.officebuilding;

import com.example.officebuilding.security.entities.Role;
import com.example.officebuilding.security.entities.User;
import com.example.officebuilding.security.service.IUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class OfficeBuildingApplication {
    @Autowired
    private IUserService userService;
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }

    @PostConstruct
    public void init() {
        List<User> users = userService.findAll();

        if (users.isEmpty()) {
            User admin = new User();
            Set<Role> roles = new HashSet<>();
            Role roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            roles.add(roleAdmin);
            admin.setUsername("admin");
            admin.setPassword("12345678");
            admin.setEmail("admin1@gmail.com");
            admin.setRoles(roles);
            userService.save(admin);
        }

    }

    public static void main(String[] args) {
        SpringApplication.run(OfficeBuildingApplication.class, args);
    }

}
