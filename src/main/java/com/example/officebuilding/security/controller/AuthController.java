package com.example.officebuilding.security.controller;


import com.example.officebuilding.security.entities.Role;
import com.example.officebuilding.security.entities.User;
import com.example.officebuilding.security.jwt.JwtResponse;
import com.example.officebuilding.security.jwt.JwtService;
import com.example.officebuilding.security.service.IRoleService;
import com.example.officebuilding.security.service.IUserService;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@CrossOrigin("*")
@RestController
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class.getName());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            try{
                User currentUser1 = userService.findByUsername(user.getUsername()).get();
            }catch (Exception e) {
                logger.info("khong ton tai username. Message - {}");
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Tài khoản không tồn tại");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User currentUser = userService.findByUsername(user.getUsername()).orElse(null);

            logger.info("User Sign In. Message - {}", currentUser);
            return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), currentUser.getEmail(), userDetails.getAuthorities()));
        } catch (BadCredentialsException e) {
            // Xử lý khi mật khẩu không đúng
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Mật khẩu không đúng");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            // Xử lý các lỗi khác
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Lỗi đăng nhập");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        logger.info("User Sign Up. Message - {}", user);
        try {
            if(userService.findByUsername(user.getUsername()).isPresent()){
                throw new Exception("Đã tồn tại người dùng, vui lòng chọn tên đăng nhập khác");
            }
            if(userService.findByEmail(user.getEmail()).isPresent()){
                throw new Exception("Email đã tồn tại, vui lòng chọn Email khác");
            }
            String password = user.getPassword();
            Role role = roleService.findByName("ROLE_USER");
            if (role == null) {
                throw new Exception("Vai trò không tồn tại");
            }
            Set<Role> roles = new HashSet<>();
            roles.add(role);

            user.setRoles(roles);
            userService.save(user);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(),password));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User currentUser = userService.findByUsername(user.getUsername()).get();
            return ResponseEntity.ok(new JwtResponse(jwt, currentUser.getId(), userDetails.getUsername(), currentUser.getEmail(), userDetails.getAuthorities()));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
