package ca.ehealthsask.assignment.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    private final String AUTH_HEADER = "Authorization";
    private final String AUTH_TYPE = "Bearer";

    public JwtAuthFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String token = extractToken(request);
        final String tokenUser = token == null ? null : jwtUtil.extractUsername(token);
        final boolean isTokenValid = tokenUser == null ? false : jwtUtil.isTokenValid(token, tokenUser);

        if (token == null || tokenUser == null || !isTokenValid
                || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }
        

        final UserDetails userDetails = userDetailsService.loadUserByUsername(tokenUser);
        final SecurityContext context = SecurityContextHolder.createEmptyContext();
        final UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        final String headerValue = request.getHeader(AUTH_HEADER);

        if (headerValue == null || !headerValue.startsWith(AUTH_TYPE)) {
            return null;
        }
        String token = headerValue.substring(AUTH_TYPE.length()).trim();

        return token.isBlank() ? null : token;
    }
}