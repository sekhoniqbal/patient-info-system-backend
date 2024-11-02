package ca.ehealthsask.assignment.dtos;

import java.util.Date;
import lombok.Getter;

@Getter
public class LoginResponse {
    private final String token;
    private final Date createdAt;
    private final Date expiresAt;

    public LoginResponse(String token, Date createdAt, Date expiresAt) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }
}
