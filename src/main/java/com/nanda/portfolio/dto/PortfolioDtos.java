package com.nanda.portfolio.dto;

import com.nanda.portfolio.entity.*;
import jakarta.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

public final class PortfolioDtos {
 private PortfolioDtos(){}
 public record ProfileData(Long id,@NotBlank String fullName,String professionalTitle,String shortIntro,String description,String city,String state,String country,String email,String phone,String profileImageUrl,String resumeUrl){}
 public record SkillData(Long id,@NotBlank String name,String category,String iconUrl,int displayOrder){}
 public record TechnologyData(Long id,@NotBlank String technologyName,int displayOrder){}
 public record MediaData(Long id,ProjectMedia.MediaType mediaType,@NotBlank String mediaUrl,String caption,int displayOrder){}
 public record ProjectData(Long id,@NotBlank String slug,@NotBlank String name,String shortTitle,String thumbnailUrl,String shortDescription,String projectTitle,String projectSubtitle,String detailedDescription,String liveUrl,String githubUrl,int displayOrder,boolean published,Instant createdAt,Instant updatedAt,List<TechnologyData> technologies,List<MediaData> media){}
 public record ContactData(Long id,String email,String githubUrl,String linkedinUrl,String twitterUrl,String whatsapp,String instagramUrl,String phone){}
}
