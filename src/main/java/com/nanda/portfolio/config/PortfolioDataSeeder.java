package com.nanda.portfolio.config;

import com.nanda.portfolio.dto.PortfolioDtos.*;
import com.nanda.portfolio.entity.SiteSetting;
import com.nanda.portfolio.repository.*;
import com.nanda.portfolio.service.PortfolioContentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PortfolioDataSeeder {
 private static final String SKILLS_VERSION_KEY="portfolio.skills.seed-version";
 private static final String SKILLS_VERSION="2026-08-14-v1";
 private static final String[] SKILLS={
  "Java","Spring Boot","Data Structures & Algorithms","REST APIs","MySQL / PostgreSQL",
  "Spring Data JPA","Hibernate","Object-Oriented Programming","SQL","Python","HTML5","CSS3",
  "JavaScript","Supabase","Git & GitHub","Maven","Postman","Spring Security","API Integration",
  "n8n","AI Automation","AI Agent Development","LLM Integration","Prompt Engineering",
  "Workflow Automation","OpenCV","Computer Vision","Face Recognition","Cloudinary","Render"
 };

 @Bean CommandLineRunner seedPortfolio(ProfileRepository profiles,PortfolioProjectRepository projects,
   SkillRepository skills,SiteSettingRepository settings,PortfolioContentService service){return args->{
  if(profiles.count()==0)service.saveProfile(new ProfileData(null,"Nanda Kishore","AI Engineer / Full Stack Developer","I build interactive digital products.","Builds interactive 3D experiences and real-time systems that are fast, responsive, and fun to use.","Hyderabad","Telangana","India","nandakishore@example.com","",null,"/resume.pdf"));
  seedSkills(skills,settings,service);
  if(projects.count()==0){String[][] values={{"cubewar","CubeWar","Multiplayer strategy game"},{"quibbo","Quibbo","Multiplayer gaming platform"},{"sharkie","Sharkie","2D browser game"},{"particles","Particles","Interactive particle experience"},{"pokedex","Pokédex","Pokémon web application"}};for(int i=0;i<values.length;i++)service.saveProject(null,new ProjectData(null,values[i][0],values[i][1],values[i][1],null,values[i][2],values[i][1],values[i][2],values[i][2],null,null,i,true,null,null,null,null));}
  if(service.contact().id()==null)service.saveContact(new ContactData(null,"nandakishore@example.com","https://github.com/","https://linkedin.com/","", "", "", ""));
 };}

 private void seedSkills(SkillRepository skills,SiteSettingRepository settings,PortfolioContentService service){
  var version=settings.findBySettingKeyAndDeletedFalse(SKILLS_VERSION_KEY);
  if(version.isPresent()&&SKILLS_VERSION.equals(version.get().getValue()))return;
  skills.deleteAllInBatch();
  for(int i=0;i<SKILLS.length;i++)service.saveSkill(null,new SkillData(null,SKILLS[i],"Tools & Technologies",null,i));
  SiteSetting marker=version.orElseGet(SiteSetting::new);
  marker.setSettingKey(SKILLS_VERSION_KEY);
  marker.setValue(SKILLS_VERSION);
  marker.setDescription("Tracks the one-time portfolio skills replacement.");
  settings.save(marker);
 }
}
