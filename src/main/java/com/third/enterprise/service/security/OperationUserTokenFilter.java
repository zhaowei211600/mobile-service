package com.third.enterprise.service.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OperationUserTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(OperationUserTokenFilter.class);

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private OperationToken operationToken;

    @Autowired
    private OperationUserHelper jwtHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(operationToken.getHeader());
        if (authHeader != null && authHeader.startsWith(operationToken.getTokenHead())) {
            final String authToken = authHeader.substring(operationToken.getTokenHead().length());
            String username = jwtHelper.getUsernameFromToken(authToken);
            logger.info("checking authentication " + username);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                JwtUser userDetails = (JwtUser)this.userDetailsService.loadUserByUsername(username);
                if (jwtHelper.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.info("authenticated user " + username + ", setting security context");
                    request.setAttribute("username",username);
                    request.setAttribute("userId", userDetails.getId());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
