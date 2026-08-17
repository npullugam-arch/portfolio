package com.nanda.portfolio.service;

import com.nanda.portfolio.dto.PortfolioDtos.*;
import com.nanda.portfolio.entity.*;
import com.nanda.portfolio.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service @Transactional(readOnly=true)
public class PortfolioContentService {
 private final ProfileRepository profiles; private final SkillRepository skills; private final PortfolioProjectRepository projects;
 private final ProjectTechnologyRepository technologies; private final ProjectMediaRepository media; private final ContactInfoRepository contacts;
 public PortfolioContentService(ProfileRepository a,SkillRepository b,PortfolioProjectRepository c,ProjectTechnologyRepository d,ProjectMediaRepository e,ContactInfoRepository f){profiles=a;skills=b;projects=c;technologies=d;media=e;contacts=f;}
 public ProfileData profile(){return profileDto(profiles.findAll().stream().findFirst().orElseGet(Profile::new));}
 public List<SkillData> skills(){return skills.findAllByOrderByDisplayOrderAsc().stream().map(this::skillDto).toList();}
 public List<ProjectData> publicProjects(){return projects.findByPublishedTrueOrderByDisplayOrderAsc().stream().map(this::projectSummaryDto).toList();}
 public List<ProjectData> adminProjects(){return projects.findAllByOrderByDisplayOrderAsc().stream().map(this::projectSummaryDto).toList();}
 public ProjectData publicProject(String slug){return projectDto(projects.findBySlugAndPublishedTrue(slug).orElseThrow(EntityNotFoundException::new));}
 public ProjectData project(long id){return projectDto(findProject(id));}
 public ContactData contact(){return contactDto(contacts.findAll().stream().findFirst().orElseGet(ContactInfo::new));}
 @Transactional public ProfileData saveProfile(ProfileData d){Profile x=profiles.findAll().stream().findFirst().orElseGet(Profile::new);x.setFullName(d.fullName());x.setProfessionalTitle(d.professionalTitle());x.setShortIntro(d.shortIntro());x.setDescription(d.description());x.setCity(d.city());x.setState(d.state());x.setCountry(d.country());x.setEmail(d.email());x.setPhone(d.phone());x.setProfileImageUrl(d.profileImageUrl());x.setResumeUrl(d.resumeUrl());return profileDto(profiles.save(x));}
 @Transactional public SkillData saveSkill(Long id,SkillData d){Skill x=id==null?new Skill():skills.findById(id).orElseThrow(EntityNotFoundException::new);x.setName(d.name());x.setCategory(d.category());x.setIconUrl(d.iconUrl());x.setDisplayOrder(d.displayOrder());return skillDto(skills.save(x));}
 @Transactional public void deleteSkill(long id){skills.deleteById(id);}
 @Transactional public ProjectData saveProject(Long id,ProjectData d){
  String slug=normalizeSlug(d.slug());
  projects.findBySlug(slug).filter(existing->!Objects.equals(existing.getId(),id)).ifPresent(existing->{throw new IllegalArgumentException("A project with the slug '"+slug+"' already exists.");});
  PortfolioProject x=id==null?new PortfolioProject():findProject(id);x.setSlug(slug);x.setName(d.name().strip());x.setShortTitle(d.shortTitle());x.setThumbnailUrl(d.thumbnailUrl());x.setShortDescription(d.shortDescription());x.setProjectTitle(d.projectTitle());x.setProjectSubtitle(d.projectSubtitle());x.setDetailedDescription(d.detailedDescription());x.setLiveUrl(d.liveUrl());x.setGithubUrl(d.githubUrl());x.setDisplayOrder(d.displayOrder());x.setPublished(d.published());return projectDto(projects.saveAndFlush(x));
 }
 @Transactional public void deleteProject(long id){projects.deleteById(id);}
 @Transactional public TechnologyData addTechnology(long projectId,TechnologyData d){ProjectTechnology x=new ProjectTechnology();x.setProject(findProject(projectId));x.setTechnologyName(d.technologyName());x.setDisplayOrder(d.displayOrder());return technologyDto(technologies.save(x));}
 @Transactional public void deleteTechnology(long projectId,long id){ProjectTechnology x=technologies.findById(id).orElseThrow(EntityNotFoundException::new);if(x.getProject().getId()!=projectId)throw new EntityNotFoundException();technologies.delete(x);}
 @Transactional public MediaData saveMedia(long projectId,Long id,MediaData d){ProjectMedia x=id==null?new ProjectMedia():media.findById(id).orElseThrow(EntityNotFoundException::new);if(id!=null&&x.getProject().getId()!=projectId)throw new EntityNotFoundException();x.setProject(findProject(projectId));x.setMediaType(d.mediaType());x.setMediaUrl(d.mediaUrl());x.setCaption(d.caption());x.setDisplayOrder(d.displayOrder());return mediaDto(media.save(x));}
 @Transactional public void deleteMedia(long projectId,long id){ProjectMedia x=media.findById(id).orElseThrow(EntityNotFoundException::new);if(x.getProject().getId()!=projectId)throw new EntityNotFoundException();media.delete(x);}
 @Transactional public ContactData saveContact(ContactData d){ContactInfo x=contacts.findAll().stream().findFirst().orElseGet(ContactInfo::new);x.setEmail(d.email());x.setGithubUrl(d.githubUrl());x.setLinkedinUrl(d.linkedinUrl());x.setTwitterUrl(d.twitterUrl());x.setWhatsapp(d.whatsapp());x.setInstagramUrl(d.instagramUrl());x.setPhone(d.phone());return contactDto(contacts.save(x));}
 private PortfolioProject findProject(long id){return projects.findById(id).orElseThrow(EntityNotFoundException::new);}
 private String normalizeSlug(String value){String slug=value==null?"":value.strip().toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+","-").replaceAll("(^-|-$)","");if(slug.isBlank())throw new IllegalArgumentException("Project slug is required.");return slug;}
 private ProfileData profileDto(Profile x){return new ProfileData(x.getId(),x.getFullName(),x.getProfessionalTitle(),x.getShortIntro(),x.getDescription(),x.getCity(),x.getState(),x.getCountry(),x.getEmail(),x.getPhone(),x.getProfileImageUrl(),x.getResumeUrl());}
 private SkillData skillDto(Skill x){return new SkillData(x.getId(),x.getName(),x.getCategory(),x.getIconUrl(),x.getDisplayOrder());}
 private TechnologyData technologyDto(ProjectTechnology x){return new TechnologyData(x.getId(),x.getTechnologyName(),x.getDisplayOrder());}
 private MediaData mediaDto(ProjectMedia x){return new MediaData(x.getId(),x.getMediaType(),x.getMediaUrl(),x.getCaption(),x.getDisplayOrder());}
 private ProjectData projectSummaryDto(PortfolioProject x){return new ProjectData(x.getId(),x.getSlug(),x.getName(),x.getShortTitle(),x.getThumbnailUrl(),x.getShortDescription(),x.getProjectTitle(),x.getProjectSubtitle(),x.getDetailedDescription(),x.getLiveUrl(),x.getGithubUrl(),x.getDisplayOrder(),x.isPublished(),x.getCreatedAt(),x.getUpdatedAt(),List.of(),List.of());}
 private ProjectData projectDto(PortfolioProject x){return new ProjectData(x.getId(),x.getSlug(),x.getName(),x.getShortTitle(),x.getThumbnailUrl(),x.getShortDescription(),x.getProjectTitle(),x.getProjectSubtitle(),x.getDetailedDescription(),x.getLiveUrl(),x.getGithubUrl(),x.getDisplayOrder(),x.isPublished(),x.getCreatedAt(),x.getUpdatedAt(),x.getTechnologies().stream().map(this::technologyDto).toList(),x.getMedia().stream().map(this::mediaDto).toList());}
 private ContactData contactDto(ContactInfo x){return new ContactData(x.getId(),x.getEmail(),x.getGithubUrl(),x.getLinkedinUrl(),x.getTwitterUrl(),x.getWhatsapp(),x.getInstagramUrl(),x.getPhone());}
}
