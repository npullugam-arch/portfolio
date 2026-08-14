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
 private static final String SKILLS_RESET_KEY="portfolio.skills.seed-version";
 private static final String SKILLS_RESET_VERSION="manual-only-v1";

 @Bean CommandLineRunner seedPortfolio(ProfileRepository profiles,PortfolioProjectRepository projects,
   SkillRepository skills,SiteSettingRepository settings,PortfolioContentService service){return args->{
  if(profiles.count()==0)service.saveProfile(new ProfileData(null,"Nanda Kishore","AI Engineer / Full Stack Developer","I build interactive digital products.","Builds interactive 3D experiences and real-time systems that are fast, responsive, and fun to use.","Hyderabad","Telangana","India","nandakishore@example.com","",null,"/resume.pdf"));
  clearSkillsForManualManagement(skills,settings);
  if(projects.count()==0){String[][] values={{"cubewar","CubeWar","Multiplayer strategy game"},{"quibbo","Quibbo","Multiplayer gaming platform"},{"sharkie","Sharkie","2D browser game"},{"particles","Particles","Interactive particle experience"},{"pokedex","Pokédex","Pokémon web application"}};for(int i=0;i<values.length;i++)service.saveProject(null,new ProjectData(null,values[i][0],values[i][1],values[i][1],null,values[i][2],values[i][1],values[i][2],values[i][2],null,null,i,true,null,null,null,null));}
  if(service.contact().id()==null)service.saveContact(new ContactData(null,"nandakishore@example.com","https://github.com/","https://linkedin.com/","", "", "", ""));
 };}

 private void clearSkillsForManualManagement(SkillRepository skills,SiteSettingRepository settings){
  var version=settings.findBySettingKeyAndDeletedFalse(SKILLS_RESET_KEY);
  if(version.isPresent()&&SKILLS_RESET_VERSION.equals(version.get().getValue()))return;
  skills.deleteAllInBatch();
  SiteSetting marker=version.orElseGet(SiteSetting::new);
  marker.setSettingKey(SKILLS_RESET_KEY);
  marker.setValue(SKILLS_RESET_VERSION);
  marker.setDescription("Prevents built-in skills from being restored; skills are managed only in Admin.");
  settings.save(marker);
 }
}
