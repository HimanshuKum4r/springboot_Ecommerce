package com.ecommerce.sb.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class AuthTokenFIlter extends OncePerRequestFilter {
    @Autowired
    private  JwtUtils jwtutils;
    @Autowired
    private UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFIlter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.debug("authtokenfilter called for uri : {}",request.getRequestURI());
        try {
            String jwt = ParseJwt(request);
            if(jwt!=null && jwtutils.validateToken(jwt)){
                String username = jwtutils.getUsernameFromToken(jwt);
                UserDetails userdetails = userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authentication =new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                logger.debug("roles from jwt : {}",userdetails.getAuthorities());

            }
        } catch (Exception e) {
            logger.debug("cannot set user authentication: {}",e);
        }
        filterChain.doFilter(request,response);

    }

    private String ParseJwt(HttpServletRequest request) {
        String jwt = jwtutils.getJwtFromHeader(request);
        logger.debug("AuthTokenFilter.java:{}",jwt);
        return jwt;
    }
}
