package com.emprestimos.exception;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.UnexpectedTypeException;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandlerController {

    @Bean
    public ErrorAttributes errorAttributes() {
      // Hide exception field in the return object
      return new DefaultErrorAttributes() {
        @Override
        public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
          return super.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
        }
      };
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity handleCustomException(HttpServletResponse res, CustomException ex) throws IOException {
      return ResponseEntity.status(ex.getHttpStatus().value()).body(ex.getMessage());
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity handleException(HttpServletResponse res, UnexpectedTypeException ex) throws IOException {
        log.error("handleException UnexpectedTypeException: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
    }
}
