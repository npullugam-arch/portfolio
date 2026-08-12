package com.nanda.portfolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity @Table(name="site_sections", indexes=@Index(name="idx_section_key",columnList="sectionKey",unique=true))
@Getter @Setter @NoArgsConstructor
public class SiteSection extends BaseEntity {
    @NotBlank @Column(nullable=false, unique=true) private String sectionKey;
    private String title; private String subtitle;
    @Column(columnDefinition="TEXT") private String shortDescription;
    @Column(columnDefinition="TEXT") private String longDescription;
    private String primaryImageUrl; private String backgroundImageUrl; private String primaryActionLabel; private String primaryActionUrl;
    @Column(columnDefinition="TEXT") private String metadata;
    @Column(nullable=false) private boolean visible = true;
    public String getSectionKey(){return sectionKey;} public void setSectionKey(String v){sectionKey=v;} public String getTitle(){return title;} public void setTitle(String v){title=v;}
    public String getSubtitle(){return subtitle;} public void setSubtitle(String v){subtitle=v;} public String getShortDescription(){return shortDescription;} public void setShortDescription(String v){shortDescription=v;}
    public String getLongDescription(){return longDescription;} public void setLongDescription(String v){longDescription=v;} public String getPrimaryImageUrl(){return primaryImageUrl;} public void setPrimaryImageUrl(String v){primaryImageUrl=v;}
    public String getBackgroundImageUrl(){return backgroundImageUrl;} public void setBackgroundImageUrl(String v){backgroundImageUrl=v;} public boolean isVisible(){return visible;} public void setVisible(boolean v){visible=v;}
}
