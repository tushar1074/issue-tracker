package com.issuetracker.issuetracker.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtFilter intercepts every HTTP request to check for a valid JWT token
 * in the Authorization header and authenticates the user accordingly.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailService;

    // Constructor injection since we are not using Lombok
    public JwtFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailService) {
        this.jwtUtil = jwtUtil;
        this.userDetailService = userDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Step 1: Get token from Authorization header
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            // Step 2: Extract the token (remove "Bearer ")
            String token = authHeader.substring(7);

            // Step 3: Extract email (subject) from token
            String email = jwtUtil.extractEmail(token);

            // Step 4: If user is not yet authenticated, proceed
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Step 5: Load user details using the extracted email
                var userDetails = userDetailService.loadUserByUsername(email);

                // Step 6: Validate token
                if (jwtUtil.validateToken(token)) {
                    // Step 7: Create authentication token
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    // Step 8: Set request details
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Step 9: Register the authentication in the SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        // Step 10: Continue with the next filter or controller
        filterChain.doFilter(request, response);
    }
}
