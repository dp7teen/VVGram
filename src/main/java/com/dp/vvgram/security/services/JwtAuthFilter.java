package com.dp.vvgram.security.services;

import com.dp.vvgram.dtos.ExceptionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    public JwtAuthFilter(CustomUserDetailsService userDetailsService,
                         ObjectMapper objectMapper) {
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                // JwtHelper.extractUsername might throw; handle below
                username = JwtHelper.extractUsername(token);
            }

            // No token: continue chain (anonymous)
            if (token == null || username == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // If not already authenticated, try to authenticate
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (JwtHelper.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    token,
                                    userDetails.getAuthorities()  // <-- don't pass null
                            );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    log.info("Authenticated user: {}", username);
                } else {
                    // invalid token
                    sendJsonError(response, HttpServletResponse.SC_UNAUTHORIZED,
                            "Invalid or expired token");
                    return;
                }
            }

            // continue chain normally
            filterChain.doFilter(request, response);

        } catch (com.dp.vvgram.exceptions.AccessDeniedException ex) {
            // your own custom access exception
            log.warn("Access denied (custom): {}", ex.getMessage());
            sendJsonError(response, HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
            return;
        } catch (org.springframework.security.access.AccessDeniedException ex) {
            // Spring Security access denied
            log.warn("Access denied: {}", ex.getMessage());
            sendJsonError(response, HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
            return;
        } catch (io.jsonwebtoken.JwtException | IllegalArgumentException ex) {
            // JWT parsing / validation errors (use the library-specific exception you use)
            log.warn("JWT error: {}", ex.getMessage());
            sendJsonError(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        } catch (UsernameNotFoundException ex) {
            log.warn("User not found: {}", ex.getMessage());
            sendJsonError(response, HttpServletResponse.SC_UNAUTHORIZED, "User not found");
            return;
        } catch (Exception ex) {
            log.error("Unexpected error in JwtAuthFilter", ex);
            sendJsonError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
            return;
        }
    }

    private void sendJsonError(HttpServletResponse response, int status, String message) throws IOException {
        response.reset(); // clear any previous state
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");

        ExceptionDto dto = new ExceptionDto();
        dto.setMessage(message);

        String payload = objectMapper.writeValueAsString(dto);
        response.getWriter().write(payload);
        response.getWriter().flush();
    }
}
