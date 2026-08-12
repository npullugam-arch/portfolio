package com.nanda.portfolio.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Entity @Table(name="portfolio_project") @Getter @Setter
public class PortfolioProject extends BaseEntity {
 @Column(nullable=false,unique=true) private String slug;
 @Column(nullable=false) private String name;
 private String shortTitle;
 private String thumbnailUrl;
 @Column(length=1500) private String shortDescription;
 private String projectTitle;
 private String projectSubtitle;
 @Column(length=10000) private String detailedDescription;
 private String liveUrl;
 private String githubUrl;
 private int displayOrder;
 private boolean published=true;
 @OneToMany(mappedBy="project",cascade=CascadeType.ALL,orphanRemoval=true) @OrderBy("displayOrder ASC")
 private List<ProjectTechnology> technologies=new ArrayList<>();
 @OneToMany(mappedBy="project",cascade=CascadeType.ALL,orphanRemoval=true) @OrderBy("displayOrder ASC")
 private List<ProjectMedia> media=new ArrayList<>();
 public String getSlug(){return slug;} public void setSlug(String v){slug=v;} public String getName(){return name;} public void setName(String v){name=v;} public String getShortTitle(){return shortTitle;} public void setShortTitle(String v){shortTitle=v;} public String getThumbnailUrl(){return thumbnailUrl;} public void setThumbnailUrl(String v){thumbnailUrl=v;} public String getShortDescription(){return shortDescription;} public void setShortDescription(String v){shortDescription=v;} public String getProjectTitle(){return projectTitle;} public void setProjectTitle(String v){projectTitle=v;} public String getProjectSubtitle(){return projectSubtitle;} public void setProjectSubtitle(String v){projectSubtitle=v;} public String getDetailedDescription(){return detailedDescription;} public void setDetailedDescription(String v){detailedDescription=v;} public String getLiveUrl(){return liveUrl;} public void setLiveUrl(String v){liveUrl=v;} public String getGithubUrl(){return githubUrl;} public void setGithubUrl(String v){githubUrl=v;} public int getDisplayOrder(){return displayOrder;} public void setDisplayOrder(int v){displayOrder=v;} public boolean isPublished(){return published;} public void setPublished(boolean v){published=v;} public List<ProjectTechnology> getTechnologies(){return technologies;} public List<ProjectMedia> getMedia(){return media;}
}
