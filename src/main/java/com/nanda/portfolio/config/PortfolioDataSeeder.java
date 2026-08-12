package com.nanda.portfolio.config;
import com.nanda.portfolio.dto.PortfolioDtos.*; import com.nanda.portfolio.repository.*; import com.nanda.portfolio.service.PortfolioContentService; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.*;
@Configuration public class PortfolioDataSeeder {
 @Bean CommandLineRunner seedPortfolio(ProfileRepository profiles,PortfolioProjectRepository projects,PortfolioContentService service){return args->{
  if(profiles.count()==0)service.saveProfile(new ProfileData(null,"Nanda Kishore","AI Engineer / Full Stack Developer","I build interactive digital products.","Builds interactive 3D experiences and real-time systems that are fast, responsive, and fun to use.","Hyderabad","Telangana","India","nandakishore@example.com","",null,"/resume.pdf"));
  if(service.skills().isEmpty()){String[] values={"Three.js & WebGL","Node.js & WebSockets","React & Vue","Spring Boot & PostgreSQL","AI / ML"};for(int i=0;i<values.length;i++)service.saveSkill(null,new SkillData(null,values[i],i==4?"AI / ML":"Tools",null,i));}
  if(projects.count()==0){String[][] values={{"cubewar","CubeWar","Multiplayer strategy game"},{"quibbo","Quibbo","Multiplayer gaming platform"},{"sharkie","Sharkie","2D browser game"},{"particles","Particles","Interactive particle experience"},{"pokedex","Pokédex","Pokémon web application"}};for(int i=0;i<values.length;i++)service.saveProject(null,new ProjectData(null,values[i][0],values[i][1],values[i][1],null,values[i][2],values[i][1],values[i][2],values[i][2],null,null,i,true,null,null,null,null));}
  if(service.contact().id()==null)service.saveContact(new ContactData(null,"nandakishore@example.com","https://github.com/","https://linkedin.com/","", "", "", ""));
 };}
}
