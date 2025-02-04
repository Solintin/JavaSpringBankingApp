package org.bankapp.bankingapp.config.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.bankapp.bankingapp.service.Impl.JwtService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

import static org.bankapp.bankingapp.config.security.SecurityConfiguration.WHITE_LIST_URL;


@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userUserDetailsService;


    public JwtAuthFilter(
            JwtService jwtService,
            @Qualifier("userUserDetailsService") UserDetailsService userUserDetailsService
    ) {
        this.jwtService = jwtService;
        this.userUserDetailsService = userUserDetailsService;

    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String requestURIWhiteList = request.getRequestURI();

        // Skip filtering for whitelisted endpoints
        if (isWhitelisted(requestURIWhiteList)) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = null;

            try {
                String requestURI = request.getRequestURI();
                if (requestURI.startsWith("/api/v1/account/")) {
                    userDetails = userUserDetailsService.loadUserByUsername(userEmail);
                }

                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            } catch (Exception e) {
                logger.error("Error occurred while authenticating user", e);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"message\": \"Unauthorized\", \"status\": 401}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isWhitelisted(String requestURI) {
        return Arrays.stream(WHITE_LIST_URL).anyMatch(requestURI::startsWith);
    }
}
