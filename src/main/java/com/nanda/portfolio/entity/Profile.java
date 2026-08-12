package com.nanda.portfolio.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
public class Profile extends BaseEntity {
 @Column(nullable=false) private String fullName="Nanda Kishore";
 private String professionalTitle;
 @Column(length=1000) private String shortIntro;
 @Column(length=5000) private String description;
 private String city;
 private String state;
 private String country;
 private String email;
 private String phone;
 private String profileImageUrl;
 private String resumeUrl;
 public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;} public String getProfessionalTitle(){return professionalTitle;} public void setProfessionalTitle(String v){professionalTitle=v;} public String getShortIntro(){return shortIntro;} public void setShortIntro(String v){shortIntro=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;} public String getCity(){return city;} public void setCity(String v){city=v;} public String getState(){return state;} public void setState(String v){state=v;} public String getCountry(){return country;} public void setCountry(String v){country=v;} public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getPhone(){return phone;} public void setPhone(String v){phone=v;} public String getProfileImageUrl(){return profileImageUrl;} public void setProfileImageUrl(String v){profileImageUrl=v;} public String getResumeUrl(){return resumeUrl;} public void setResumeUrl(String v){resumeUrl=v;}
}
