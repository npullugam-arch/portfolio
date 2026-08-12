package com.nanda.portfolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDate;

@Entity @Table(name="content_items", indexes={@Index(name="idx_content_type_visible_order", columnList="type,visible,displayOrder"),@Index(name="idx_content_slug",columnList="slug")})
@Getter @Setter @NoArgsConstructor
public class ContentItem extends BaseEntity {
    public enum Type { SKILL, PROJECT, EDUCATION, EXPERIENCE, CERTIFICATE, ACHIEVEMENT, BLOG, TESTIMONIAL, SOCIAL_LINK, RESUME }
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=30) private Type type;
    @NotBlank @Column(nullable=false) private String title;
    @Column(unique=true) private String slug;
    private String subtitle;
    @Column(length=1000) private String summary;
    @Column(columnDefinition="TEXT") private String description;
    private String category; private String imageUrl; private String secondaryImageUrl;
    private String externalUrl; private String secondaryUrl; private String location;
    private LocalDate startDate; private LocalDate endDate;
    @Column(columnDefinition="TEXT") private String metadata;
    private Integer percentage; private Integer rating;
    @Column(nullable=false) private int displayOrder = 0;
    @Column(nullable=false) private boolean visible = true;
    @Column(nullable=false) private boolean featured = false;
    @Column(nullable=false) private boolean published = false;
    public Type getType(){return type;} public void setType(Type v){type=v;} public String getTitle(){return title;} public void setTitle(String v){title=v;}
    public String getSlug(){return slug;} public void setSlug(String v){slug=v;} public String getSubtitle(){return subtitle;} public void setSubtitle(String v){subtitle=v;}
    public String getSummary(){return summary;} public void setSummary(String v){summary=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;}
    public String getCategory(){return category;} public void setCategory(String v){category=v;} public String getImageUrl(){return imageUrl;} public void setImageUrl(String v){imageUrl=v;}
    public String getSecondaryImageUrl(){return secondaryImageUrl;} public void setSecondaryImageUrl(String v){secondaryImageUrl=v;} public String getExternalUrl(){return externalUrl;} public void setExternalUrl(String v){externalUrl=v;}
    public String getSecondaryUrl(){return secondaryUrl;} public void setSecondaryUrl(String v){secondaryUrl=v;} public String getLocation(){return location;} public void setLocation(String v){location=v;}
    public LocalDate getStartDate(){return startDate;} public void setStartDate(LocalDate v){startDate=v;} public LocalDate getEndDate(){return endDate;} public void setEndDate(LocalDate v){endDate=v;}
    public String getMetadata(){return metadata;} public void setMetadata(String v){metadata=v;} public Integer getPercentage(){return percentage;} public void setPercentage(Integer v){percentage=v;}
    public Integer getRating(){return rating;} public void setRating(Integer v){rating=v;} public int getDisplayOrder(){return displayOrder;} public void setDisplayOrder(int v){displayOrder=v;}
    public boolean isVisible(){return visible;} public void setVisible(boolean v){visible=v;} public boolean isFeatured(){return featured;} public void setFeatured(boolean v){featured=v;} public boolean isPublished(){return published;} public void setPublished(boolean v){published=v;}
}
