package com.nanda.portfolio.repository;
import com.nanda.portfolio.entity.ContentItem; import org.springframework.data.domain.*; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface ContentItemRepository extends JpaRepository<ContentItem,Long> {
 Page<ContentItem> findByTypeAndDeletedFalse(ContentItem.Type type, Pageable pageable);
 List<ContentItem> findByTypeAndVisibleTrueAndDeletedFalseOrderByDisplayOrderAsc(ContentItem.Type type);
 long countByTypeAndDeletedFalse(ContentItem.Type type);
}
