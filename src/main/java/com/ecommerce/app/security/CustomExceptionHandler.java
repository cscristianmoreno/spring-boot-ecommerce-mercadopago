package com.ecommerce.app.security;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import io.jsonwebtoken.JwtException;

@ControllerAdvice
@ResponseBody
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public void sqlIntegrityConstraintViolationException() {
        // return "Este usuario ya está registrado";
        
    }
    
    @ExceptionHandler(JwtException.class)
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public ResponseEntity<String> jwtException(Exception exception) {
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ResponseEntity<String> badCredentialsException(Exception exception) {
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({MPApiException.class, MPException.class})
    public ResponseEntity<String> mpApiException(MPApiException exception) {
        return new ResponseEntity<String>(exception.getApiResponse().getContent(), HttpStatus.CONFLICT);
    }
}
