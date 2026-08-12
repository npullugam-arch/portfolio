package com.nanda.portfolio.controller;
import com.nanda.portfolio.dto.ContactRequest; import com.nanda.portfolio.entity.ContactMessage; import com.nanda.portfolio.repository.ContactMessageRepository; import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.security.web.csrf.CsrfToken; import org.springframework.web.bind.annotation.*; import java.util.Map;
@RestController @RequestMapping("/api")
public class PortfolioApiController { private final ContactMessageRepository messages; public PortfolioApiController(ContactMessageRepository messages){this.messages=messages;}
 @GetMapping("/csrf") Map<String,String> csrf(CsrfToken token){return Map.of("token",token.getToken(),"headerName",token.getHeaderName(),"parameterName",token.getParameterName());}
 @PostMapping("/contact") @ResponseStatus(HttpStatus.CREATED) Map<String,String> contact(@Valid @RequestBody ContactRequest request){ContactMessage m=new ContactMessage();m.setName(request.name().strip());m.setEmail(request.email().strip());m.setSubject(request.subject()==null?null:request.subject().strip());m.setMessage(request.message().strip());messages.save(m);return Map.of("status","received");}
}
