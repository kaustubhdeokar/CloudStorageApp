package com.example.S3FileHosting.security;

import com.example.S3FileHosting.exception.CustomerAlreadyExistsException;
import com.example.S3FileHosting.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;
    private UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromRequest(request);
        if (!isRegisterOrLoginRequest(request) && token == null) {
            throw new CustomerAlreadyExistsException(HttpStatus.UNAUTHORIZED, "Token is null or invalid. Please provide a valid authentication token");
        }
        if (StringUtils.hasText(token) && jwtTokenProvider.validateJwtToken(token)) {
            String usernameFromJWTToken = jwtTokenProvider.getUsernameFromJWTToken(token);
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(usernameFromJWTToken);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            //converts HttpServletRequest into WebAuthenticationDetails class.
            WebAuthenticationDetails webAuthenticationDetails = new WebAuthenticationDetailsSource().buildDetails(request);
            authToken.setDetails(webAuthenticationDetails);
            SecurityContextHolder.getContext().setAuthentication(authToken);

        }

        filterChain.doFilter(request, response);

    }

    private boolean isRegisterOrLoginRequest(HttpServletRequest request) {
        return request.getRequestURI().contains("register") || request.getRequestURI().contains("login");
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = "Bearer ";
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(bearer)) {
            return bearerToken.substring(bearer.length(), bearerToken.length());
        }
        return null;
    }

}
