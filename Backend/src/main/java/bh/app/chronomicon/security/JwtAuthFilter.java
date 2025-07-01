package bh.app.chronomicon.security;

import bh.app.chronomicon.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AuthService authService;


    public JwtAuthFilter(JwtUtil jwtUtil, AuthService authService){
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = extractToken (request);
        if(token==null){
            filterChain.doFilter (request, response);
            return;
        }
        String username = jwtUtil.getUsername (token);

        //if there is a valid token and there are no valid authentication, authenticate
        if(username != null && SecurityContextHolder.getContext ().getAuthentication () == null){
            if(jwtUtil.isTokenValid (token)){
                UserDetails userDetails = authService.loadUserByUsername (username);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken (userDetails, null, userDetails.getAuthorities ());
                authenticationToken.setDetails (new WebAuthenticationDetailsSource ().buildDetails (request));
                SecurityContextHolder.getContext ().setAuthentication (authenticationToken);
            }
        }
        filterChain.doFilter (request, response);

    }

    private String extractToken(HttpServletRequest request){
        String authHeader = request.getHeader ("Authorization");
        if(authHeader==null || !authHeader.startsWith ("Bearer ")){
            return null;
        }else{
            return authHeader.replace ("Bearer ","");
        }
    }
}
