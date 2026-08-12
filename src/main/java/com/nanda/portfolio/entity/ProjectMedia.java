package com.nanda.portfolio.entity;
import jakarta.persistence.*; import lombok.Getter; import lombok.Setter;
@Entity @Getter @Setter public class ProjectMedia extends BaseEntity {
 public enum MediaType { IMAGE, VIDEO }
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private PortfolioProject project;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private MediaType mediaType;
 @Column(nullable=false,length=2000) private String mediaUrl;
 private String caption;
 private int displayOrder;
 public PortfolioProject getProject(){return project;} public void setProject(PortfolioProject v){project=v;} public MediaType getMediaType(){return mediaType;} public void setMediaType(MediaType v){mediaType=v;} public String getMediaUrl(){return mediaUrl;} public void setMediaUrl(String v){mediaUrl=v;} public String getCaption(){return caption;} public void setCaption(String v){caption=v;} public int getDisplayOrder(){return displayOrder;} public void setDisplayOrder(int v){displayOrder=v;}
}
