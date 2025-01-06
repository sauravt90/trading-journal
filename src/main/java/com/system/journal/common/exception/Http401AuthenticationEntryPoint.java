package com.system.journal.common.exception;

import com.system.journal.util.CommonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class Http401AuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         org.springframework.security.core.AuthenticationException authException)
            throws IOException, ServletException {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            ErrorResponse errorResponse = new ErrorResponse(authException.getMessage(), HttpStatus.UNAUTHORIZED);
            String resp = CommonUtils.om.writeValueAsString(errorResponse);
            response.getWriter().print(resp);


     //   response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());

    }
}