package com.iwm.backend.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;


    public AuthenticationController(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody UserEM user) {
        System.out.println(user.getEmail());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()
                    ));
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String token = jwtUtils.createToken(userDetails.getUsername());
            ResponseCookie cookie = ResponseCookie.from("jwt",token)
                    .httpOnly(true)
                    .secure(false)
                    .path("/")
                    .maxAge(Duration.ofMinutes(10))
                    .sameSite("Lax")
                    .build();
            return ResponseEntity.ok()
                    .header(HttpHeaders.SET_COOKIE,cookie.toString())
                    .body("success");
        }catch (Exception e) {
            e.printStackTrace();
        }
       return ResponseEntity.status(401).build();

    }

    @PostMapping("/signup")
    public String registerUser(@RequestBody UserEM user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            return "Error: email already exists";
        }
        UserEM userEntity = new UserEM();
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userEntity);
        return "Success";
    }
}
