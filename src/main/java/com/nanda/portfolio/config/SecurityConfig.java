package com.nanda.portfolio.config;
import org.springframework.context.annotation.*; import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.core.userdetails.*; import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.security.provisioning.InMemoryUserDetailsManager; import org.springframework.security.web.SecurityFilterChain;

@Configuration @EnableConfigurationProperties(AdminProperties.class)
public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){ return new BCryptPasswordEncoder(); }
 @Bean UserDetailsService users(AdminProperties p, PasswordEncoder encoder){ return new InMemoryUserDetailsManager(User.withUsername(p.username()).password(encoder.encode(p.password())).roles("ADMIN").build()); }
 @Bean SecurityFilterChain security(HttpSecurity http, AdminProperties p) throws Exception {
  return http.authorizeHttpRequests(a->a.requestMatchers("/admin/login","/css/**","/js/**","/images/**","/portfolio/**","/api/csrf","/api/contact","/api/chat","/api/portfolio/**","/resume.pdf","/","/contact","/error","/robots.txt","/sitemap.xml").permitAll().requestMatchers("/admin/**","/api/admin/**").hasRole("ADMIN").anyRequest().permitAll())
   .formLogin(f->f.loginPage("/admin/login").loginProcessingUrl("/admin/login").defaultSuccessUrl("/admin/dashboard",true).failureUrl("/admin/login?error").permitAll())
   .logout(l->l.logoutUrl("/admin/logout").logoutSuccessUrl("/admin/login?logout").invalidateHttpSession(true).deleteCookies("JSESSIONID"))
   .rememberMe(r->r.key(p.rememberMeKey()).tokenValiditySeconds(604800)).sessionManagement(s->s.sessionFixation(x->x.migrateSession()).maximumSessions(1)).build();
 }
}
