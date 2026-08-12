package com.nanda.portfolio.repository;
import com.nanda.portfolio.entity.SiteSection; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface SiteSectionRepository extends JpaRepository<SiteSection,Long>{ Optional<SiteSection> findBySectionKeyAndDeletedFalse(String key); List<SiteSection> findByVisibleTrueAndDeletedFalse(); }
