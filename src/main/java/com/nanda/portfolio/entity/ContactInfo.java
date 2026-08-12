package com.nanda.portfolio.entity;
import jakarta.persistence.Entity; import lombok.Getter; import lombok.Setter;
@Entity @Getter @Setter public class ContactInfo extends BaseEntity {
 private String email; private String githubUrl; private String linkedinUrl; private String twitterUrl;
 private String whatsapp; private String instagramUrl; private String phone;
 public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getGithubUrl(){return githubUrl;} public void setGithubUrl(String v){githubUrl=v;} public String getLinkedinUrl(){return linkedinUrl;} public void setLinkedinUrl(String v){linkedinUrl=v;} public String getTwitterUrl(){return twitterUrl;} public void setTwitterUrl(String v){twitterUrl=v;} public String getWhatsapp(){return whatsapp;} public void setWhatsapp(String v){whatsapp=v;} public String getInstagramUrl(){return instagramUrl;} public void setInstagramUrl(String v){instagramUrl=v;} public String getPhone(){return phone;} public void setPhone(String v){phone=v;}
}
