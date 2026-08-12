package com.nanda.portfolio.exception;
import jakarta.persistence.EntityNotFoundException; import org.springframework.dao.DataIntegrityViolationException; import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import org.springframework.core.annotation.Order; import java.util.Map;
@RestControllerAdvice(assignableTypes=com.nanda.portfolio.controller.AdminPortfolioApiController.class)
@Order(0)
public class AdminApiExceptionHandler {
 @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<Map<String,String>> invalid(IllegalArgumentException e){return ResponseEntity.badRequest().body(Map.of("message",e.getMessage()));}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<Map<String,String>> validation(MethodArgumentNotValidException e){String message=e.getBindingResult().getFieldErrors().stream().findFirst().map(x->x.getField()+" "+x.getDefaultMessage()).orElse("Project data is invalid.");return ResponseEntity.badRequest().body(Map.of("message",message));}
 @ExceptionHandler(DataIntegrityViolationException.class) ResponseEntity<Map<String,String>> conflict(){return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message","That project slug is already in use. Please choose a different slug."));}
 @ExceptionHandler(EntityNotFoundException.class) ResponseEntity<Map<String,String>> missing(){return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message","Project was not found. Refresh the project list and try again."));}
}
