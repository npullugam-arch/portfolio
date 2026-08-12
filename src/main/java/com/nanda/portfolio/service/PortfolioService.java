package com.nanda.portfolio.service;
import com.nanda.portfolio.dto.DashboardOverview; import com.nanda.portfolio.entity.*; import com.nanda.portfolio.repository.*; import lombok.RequiredArgsConstructor; import org.springframework.data.domain.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.*;
@Service @Transactional(readOnly=true)
public class PortfolioService {
 private final ContentItemRepository items; private final SiteSectionRepository sections; private final ContactMessageRepository messages;
 public PortfolioService(ContentItemRepository items,SiteSectionRepository sections,ContactMessageRepository messages){this.items=items;this.sections=sections;this.messages=messages;}
 public Map<String,SiteSection> sections(){ Map<String,SiteSection> result=new HashMap<>(); sections.findByVisibleTrueAndDeletedFalse().forEach(s->result.put(s.getSectionKey(),s)); return result; }
 public Map<String,List<ContentItem>> publicContent(){ Map<String,List<ContentItem>> result=new LinkedHashMap<>(); for(var type:ContentItem.Type.values()) result.put(type.name().toLowerCase(),items.findByTypeAndVisibleTrueAndDeletedFalseOrderByDisplayOrderAsc(type)); return result; }
 public DashboardOverview overview(){ return new DashboardOverview(count(ContentItem.Type.PROJECT),count(ContentItem.Type.SKILL),count(ContentItem.Type.CERTIFICATE),count(ContentItem.Type.EDUCATION),count(ContentItem.Type.EXPERIENCE),messages.countByDeletedFalse(),0,count(ContentItem.Type.BLOG)); }
 public Page<ContentItem> page(ContentItem.Type type,int page,String query){ return items.findByTypeAndDeletedFalse(type,PageRequest.of(Math.max(page,0),10,Sort.by("displayOrder","title"))); }
 @Transactional public ContentItem save(ContentItem item,ContentItem.Type type){ item.setType(type); return items.save(item); }
 @Transactional public void softDelete(long id){ items.findById(id).ifPresent(x->{x.setDeleted(true);items.save(x);}); }
 private long count(ContentItem.Type type){return items.countByTypeAndDeletedFalse(type);}
}
