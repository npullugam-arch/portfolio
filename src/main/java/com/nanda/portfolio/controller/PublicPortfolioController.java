package com.nanda.portfolio.controller;
import com.nanda.portfolio.service.PortfolioContentService; import org.springframework.web.bind.annotation.*; import org.springframework.http.CacheControl; import org.springframework.http.ResponseEntity;
@RestController @RequestMapping("/api/portfolio") public class PublicPortfolioController {
 private final PortfolioContentService service; public PublicPortfolioController(PortfolioContentService s){service=s;}
 @GetMapping("/profile") Object profile(){return service.profile();} @GetMapping("/skills") ResponseEntity<Object> skills(){return ResponseEntity.ok().cacheControl(CacheControl.noStore()).body(service.skills());}
 @GetMapping("/projects") ResponseEntity<Object> projects(){return ResponseEntity.ok().cacheControl(CacheControl.noStore()).body(service.publicProjects());} @GetMapping("/projects/{slug}") ResponseEntity<Object> project(@PathVariable String slug){return ResponseEntity.ok().cacheControl(CacheControl.noStore()).body(service.publicProject(slug));}
 @GetMapping("/contact") Object contact(){return service.contact();}
}
