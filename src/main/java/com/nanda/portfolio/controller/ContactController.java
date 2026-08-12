package com.nanda.portfolio.controller;
import com.nanda.portfolio.entity.ContactMessage; import com.nanda.portfolio.repository.ContactMessageRepository; import jakarta.validation.Valid; import lombok.RequiredArgsConstructor; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.validation.BindingResult; import org.springframework.web.bind.annotation.*;
@Controller public class ContactController { private final ContactMessageRepository repository; public ContactController(ContactMessageRepository repository){this.repository=repository;}
 @PostMapping("/contact") String submit(@Valid @ModelAttribute ContactMessage contactMessage, BindingResult errors, Model model){if(errors.hasErrors()){model.addAttribute("contactError",true);return "public/index";}repository.save(contactMessage);return "redirect:/?sent";}
}
