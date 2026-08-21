package com.nanda.portfolio.service;

import com.nanda.portfolio.dto.PortfolioDtos.ContactData;
import com.nanda.portfolio.dto.PortfolioDtos.ProfileData;
import com.nanda.portfolio.dto.PortfolioDtos.ProjectData;
import com.nanda.portfolio.dto.PortfolioDtos.SkillData;
import com.nanda.portfolio.entity.ContentItem;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatPortfolioContextService {
    private static final int MAX_CONTEXT_LENGTH = 30000;

    private final PortfolioContentService portfolioContent;
    private final PortfolioService portfolioService;

    public ChatPortfolioContextService(PortfolioContentService portfolioContent, PortfolioService portfolioService) {
        this.portfolioContent = portfolioContent;
        this.portfolioService = portfolioService;
    }

    @Transactional(readOnly = true)
    public String context() {
        StringBuilder context = new StringBuilder("VERIFIED PORTFOLIO DATA FROM THE DATABASE\n");
        appendProfile(context, portfolioContent.profile());
        appendSkills(context);
        appendContact(context, portfolioContent.contact());
        appendProjects(context);
        appendOtherPublicContent(context);
        return context.length() > MAX_CONTEXT_LENGTH
            ? context.substring(0, MAX_CONTEXT_LENGTH) + "\n[Context truncated]"
            : context.toString();
    }

    private void appendProfile(StringBuilder context, ProfileData profile) {
        section(context, "PROFILE");
        field(context, "Name", profile.fullName());
        field(context, "Title", profile.professionalTitle());
        field(context, "Introduction", profile.shortIntro());
        field(context, "Description", profile.description());
        field(context, "Location", join(profile.city(), profile.state(), profile.country()));
        field(context, "Email", profile.email());
        field(context, "Phone", profile.phone());
        field(context, "Resume", profile.resumeUrl());
    }

    private void appendSkills(StringBuilder context) {
        section(context, "SKILLS AND TECHNOLOGIES");
        for (SkillData skill : portfolioContent.skills()) {
            field(context, skill.category(), skill.name());
        }
    }

    private void appendContact(StringBuilder context, ContactData contact) {
        section(context, "SOCIAL AND CONTACT LINKS");
        field(context, "Email", contact.email());
        field(context, "Phone", contact.phone());
        field(context, "WhatsApp", contact.whatsapp());
        field(context, "GitHub", contact.githubUrl());
        field(context, "LinkedIn", contact.linkedinUrl());
        field(context, "Twitter", contact.twitterUrl());
        field(context, "Instagram", contact.instagramUrl());
    }

    private void appendProjects(StringBuilder context) {
        section(context, "PUBLISHED PROJECTS");
        for (ProjectData summary : portfolioContent.publicProjects()) {
            ProjectData project = portfolioContent.publicProject(summary.slug());
            field(context, "Project", project.name());
            field(context, "Slug", project.slug());
            field(context, "Title", project.projectTitle());
            field(context, "Subtitle", project.projectSubtitle());
            field(context, "Summary", project.shortDescription());
            field(context, "Complete description", project.detailedDescription());
            field(context, "Technologies", project.technologies().stream().map(technology -> technology.technologyName()).toList().toString());
            field(context, "Live URL", project.liveUrl());
            field(context, "GitHub URL", project.githubUrl());
            context.append('\n');
        }
    }

    private void appendOtherPublicContent(StringBuilder context) {
        section(context, "OTHER PUBLIC PORTFOLIO CONTENT");
        for (Map.Entry<String, java.util.List<ContentItem>> entry : portfolioService.publicContent().entrySet()) {
            if (entry.getKey().equalsIgnoreCase("project") || entry.getKey().equalsIgnoreCase("skill")) {
                continue;
            }
            for (ContentItem item : entry.getValue()) {
                field(context, "Type", entry.getKey());
                field(context, "Title", item.getTitle());
                field(context, "Subtitle", item.getSubtitle());
                field(context, "Summary", item.getSummary());
                field(context, "Description", item.getDescription());
                field(context, "Category", item.getCategory());
                field(context, "Location", item.getLocation());
                field(context, "URL", item.getExternalUrl());
                field(context, "Secondary URL", item.getSecondaryUrl());
                field(context, "Metadata", item.getMetadata());
                context.append('\n');
            }
        }
    }

    private void section(StringBuilder context, String title) {
        context.append('\n').append(title).append('\n');
    }

    private void field(StringBuilder context, String label, String value) {
        if (value != null && !value.isBlank()) {
            context.append(label).append(": ").append(value.strip()).append('\n');
        }
    }

    private String join(String... values) {
        return java.util.Arrays.stream(values)
            .filter(value -> value != null && !value.isBlank())
            .map(String::strip)
            .collect(java.util.stream.Collectors.joining(", "));
    }
}