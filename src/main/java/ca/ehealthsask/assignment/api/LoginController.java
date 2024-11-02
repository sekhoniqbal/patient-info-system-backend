package ca.ehealthsask.assignment.api;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import ca.ehealthsask.assignment.dtos.LoginRequest;
import ca.ehealthsask.assignment.dtos.LoginResponse;
import ca.ehealthsask.assignment.security.JwtUtil;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final Integer jwtTtl;

    public LoginController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
            @Value("${application.security.jwt-ttl}") Integer jwtTtl) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.jwtTtl = jwtTtl;
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {
        UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken
                .unauthenticated(request.getUsername(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);

        if (!authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("Failed to authenticate");
        }
        String token = jwtUtil.generate(request.getUsername(), jwtTtl);
        return new LoginResponse(
                token,
                jwtUtil.extractCreatedAt(token),
                jwtUtil.extractExpirationDate(token));
    }
}