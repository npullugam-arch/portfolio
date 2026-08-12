package com.nanda.portfolio.repository;
import com.nanda.portfolio.entity.SiteSetting; import org.springframework.data.jpa.repository.JpaRepository; import java.util.Optional;
public interface SiteSettingRepository extends JpaRepository<SiteSetting,Long>{ Optional<SiteSetting> findBySettingKeyAndDeletedFalse(String key); }
