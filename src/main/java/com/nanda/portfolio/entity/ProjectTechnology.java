package com.nanda.portfolio.entity;
import jakarta.persistence.*; import lombok.Getter; import lombok.Setter;
@Entity @Getter @Setter public class ProjectTechnology extends BaseEntity {
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private PortfolioProject project;
 @Column(nullable=false) private String technologyName;
 private int displayOrder;
 public PortfolioProject getProject(){return project;} public void setProject(PortfolioProject v){project=v;} public String getTechnologyName(){return technologyName;} public void setTechnologyName(String v){technologyName=v;} public int getDisplayOrder(){return displayOrder;} public void setDisplayOrder(int v){displayOrder=v;}
}
