package com.nanda.portfolio.controller;
import com.nanda.portfolio.service.PortfolioService; import lombok.RequiredArgsConstructor; import org.springframework.stereotype.Controller; import org.springframework.ui.Model; import org.springframework.web.bind.annotation.GetMapping;
@Controller public class HomeController { private final PortfolioService service; public HomeController(PortfolioService service){this.service=service;} @GetMapping({"/","/project/{slug}"}) String home(){return "forward:/portfolio/index.html";} }
