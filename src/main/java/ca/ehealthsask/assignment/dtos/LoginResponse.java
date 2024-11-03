package ca.ehealthsask.assignment.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {
    private final String token;
    private final Date createdAt;
    private final Date expiresAt;
    private final String username;
    private final String role;
}
