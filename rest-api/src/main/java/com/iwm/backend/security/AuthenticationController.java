package com.iwm.backend.security;

import com.iwm.backend.modules.employee.EmployeeService;
import com.iwm.backend.modules.employee.ExistingUserDTO;
import com.iwm.backend.modules.employee.NewUserDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;


/**
 * REST controller responsible for handling user authentication operations.
 * <p>
 * This controller exposes endpoints for user login, token issuance, and potentially
 * token validation or refresh, depending on the configured security architecture.
 * Typically interacts with authentication services, token providers (e.g., JWT),
 * and user detail services to perform credential validation and secure access control.
 * <p>
 * Common endpoint responsibilities may include:
 * <ul>
 *   <li>Authenticating user credentials (e.g., email/username and password)</li>
 *   <li>Issuing JWTs or session tokens upon successful login</li>
 *   <li>Handling user logout or token invalidation (if applicable)</li>
 * </ul>
 *
 * <p>Example usage via HTTP:
 * <pre>
 * POST /api/auth/login
 * {
 *   "username": "user@example.com",
 *   "password": "securePassword"
 * }
 * </pre>
 *
 * @see org.springframework.security.authentication.AuthenticationManager
 * @see org.springframework.security.core.userdetails.UserDetailsService
 * @see com.iwm.backend.security.JwtUtils
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final EmployeeService employeeService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;


    public AuthenticationController(
            AuthenticationManager authenticationManager, EmployeeService employeeService,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.employeeService = employeeService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody ExistingUserDTO user) {

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
    public String registerUser(@RequestBody NewUserDTO user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            return "Error: email already exists";
        }
        UserEM userEntity = new UserEM();
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setEmployee(employeeService.getEmployeeEMbyId(user.getEmployeeId()));
        userRepository.save(userEntity);
        return "Success";
    }
}
