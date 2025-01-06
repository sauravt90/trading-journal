package com.system.journal.filter;

import com.system.journal.common.exception.CustomResponseException;
import com.system.journal.common.exception.ErrorResponse;
import com.system.journal.util.CommonUtils;
import com.system.journal.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.ObjectOutputStream;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;
    public final UserDetailsService userDetailsService;

    public JwtFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

       // try{
            String authorizationHeader = request.getHeader("Authorization");
            String username = null;
            String jwt = null;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                username = jwtUtil.extractUsername(jwt,JwtUtil.SECRET_KEY);
            }
            if (username != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtUtil.validateToken(jwt,JwtUtil.SECRET_KEY)) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
//        }catch (JwtException e){
//            log.warn("jwt error");
//            var res =new ErrorResponse(e.getMessage(), HttpStatus.FORBIDDEN);
//            response.setContentType("application/json");
//            response.setCharacterEncoding("UTF-8");
//            String resp = CommonUtils.om.writeValueAsString(res);
//            response.getWriter().print(resp);
//            return;
//        }
        filterChain.doFilter(request, response);
    }
}
