package com.nanda.portfolio.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Skill extends BaseEntity {
 @Column(nullable=false) private String name;
 private String category;
 private String iconUrl;
 private int displayOrder;
 public String getName(){return name;} public void setName(String v){name=v;} public String getCategory(){return category;} public void setCategory(String v){category=v;} public String getIconUrl(){return iconUrl;} public void setIconUrl(String v){iconUrl=v;} public int getDisplayOrder(){return displayOrder;} public void setDisplayOrder(int v){displayOrder=v;}
}
