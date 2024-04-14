package com.duchung.shopappspring.filters;

import com.duchung.shopappspring.components.JwtTokenUtils;
import com.duchung.shopappspring.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Value("${api.prefix}")
    private String apiPrefix;

    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            if (isBypassToken(request)) {
                filterChain.doFilter(request, response);
                return;
            }
            String authorizeHeader = request.getHeader("Authorization");
            if (authorizeHeader == null || !authorizeHeader.startsWith("Bearer ")) {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Unauthorized"
                );
                return;
            }
            final String token = authorizeHeader.substring(7);
            final String phoneNumber = jwtTokenUtils.exactPhoneNumber(token);
            if (phoneNumber != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = (User) userDetailsService.loadUserByUsername(phoneNumber);
                if (jwtTokenUtils.validateToken(token, user)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Unauthorized"
            );
        }
    }

    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of(String.format("%s/users/register", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/login", apiPrefix), "POST"),
                Pair.of(String.format("%s/categories", apiPrefix), "GET"),
                Pair.of(String.format("%s/products", apiPrefix), "GET"),
                Pair.of(String.format("%s/orders", apiPrefix), "GET"),
                Pair.of(String.format("%s/order-details", apiPrefix), "GET"),
                Pair.of(String.format("%s/roles", apiPrefix), "GET"),
                Pair.of(String.format("%s/products/by-ids", apiPrefix), "GET")
        );
        String requestPath = request.getServletPath();
        String requestMethod = request.getMethod();
        for (var bypassToken : bypassTokens) {
            String bypassTokenPath = bypassToken.getFirst();
            String bypassTokenMethod = bypassToken.getSecond();
            if (requestPath.contains(bypassTokenPath)
                && requestMethod.equalsIgnoreCase(bypassTokenMethod)) {
                return true;
            }
        }
        return false;
    }

}
