package com.nanda.portfolio.controller;
import com.nanda.portfolio.entity.ContentItem; import com.nanda.portfolio.service.PortfolioService; import jakarta.validation.Valid; import lombok.RequiredArgsConstructor; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.validation.BindingResult; import org.springframework.web.bind.annotation.*;
import java.util.Locale;
@Controller @RequestMapping("/admin")
public class AdminController { private final PortfolioService service;
 public AdminController(PortfolioService service){this.service=service;}
 @GetMapping("/login") String login(){return "admin/login";}
 @GetMapping({"","/dashboard"}) String dashboard(Model m){m.addAttribute("overview",service.overview());return "admin/dashboard";}
 @GetMapping({"/about","/projects","/contact"}) String portfolioManager(){return "admin/portfolio-manager";}
 @GetMapping("/{module}") String module(@PathVariable String module,@RequestParam(defaultValue="0")int page,@RequestParam(defaultValue="")String q,Model m){var type=type(module);m.addAttribute("module",module);m.addAttribute("type",type);m.addAttribute("items",service.page(type,page,q));m.addAttribute("item",new ContentItem());return "admin/module";}
 @PostMapping("/{module}") String create(@PathVariable String module,@Valid @ModelAttribute("item") ContentItem item,BindingResult errors,Model m){var type=type(module);if(errors.hasErrors()){m.addAttribute("module",module);m.addAttribute("type",type);m.addAttribute("items",service.page(type,0,""));return "admin/module";}service.save(item,type);return "redirect:/admin/"+module+"?saved";}
 @PostMapping("/{module}/{id}/delete") String delete(@PathVariable String module,@PathVariable long id){service.softDelete(id);return "redirect:/admin/"+module+"?deleted";}
 private ContentItem.Type type(String module){String normalized=module.replace('-','_').toUpperCase(Locale.ROOT);if(normalized.equals("CERTIFICATIONS"))normalized="CERTIFICATE";if(normalized.equals("PROJECTS"))normalized="PROJECT";if(normalized.equals("SKILLS"))normalized="SKILL";if(normalized.equals("ACHIEVEMENTS"))normalized="ACHIEVEMENT";if(normalized.equals("TESTIMONIALS"))normalized="TESTIMONIAL";if(normalized.equals("SOCIAL_LINKS"))normalized="SOCIAL_LINK";return ContentItem.Type.valueOf(normalized);}
}
