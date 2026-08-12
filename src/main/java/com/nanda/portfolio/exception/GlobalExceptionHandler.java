package com.nanda.portfolio.exception;
import org.springframework.ui.Model; import org.springframework.web.bind.annotation.ControllerAdvice; import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice public class GlobalExceptionHandler { @ExceptionHandler({IllegalArgumentException.class}) String invalid(Exception e,Model m){m.addAttribute("message","The requested resource is not available.");return "error";} }
