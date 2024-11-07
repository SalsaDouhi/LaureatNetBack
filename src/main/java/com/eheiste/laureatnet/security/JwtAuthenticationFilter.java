package com.eheiste.laureatnet.security;

import com.eheiste.laureatnet.exception.exceptions.InvalidJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SignatureException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwtoken;
        final Long accountId;
        final String accountEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwtoken = authHeader.substring(7);
        try {
            accountId = jwtService.extractAccountId(jwtoken);
            accountEmail = jwtService.extractUsername(jwtoken);

            if (accountId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails accountDetails = this.userDetailsService.loadUserByUsername(accountEmail);

                if (jwtService.isTokenValid(jwtoken, accountDetails)) {
                    if (!accountDetails.isEnabled()) {
                        logger.warn("Attempt to access using a disabled account: " + accountEmail);
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Account is disabled.");
                        return;
                    }

                    if (!accountDetails.isAccountNonLocked()) {
                        logger.warn("Attempt to access using a locked account: " + accountEmail);
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Account is locked.");
                        return;
                    }

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            accountDetails,
                            null,
                            accountDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                } else {
                    logger.warn("Invalid JWT token for account: " + accountEmail);
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token.");
                    return;
                }
            }
        }catch (SignatureException e) {
            throw new InvalidJwtException("JWT signature does not match.", e);
        } catch (ExpiredJwtException e) {
            throw new com.eheiste.laureatnet.exception.exceptions.ExpiredJwtException("JWT expired.", e);
        }
        filterChain.doFilter(request, response);
    }
}
