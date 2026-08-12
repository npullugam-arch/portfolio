package com.nanda.portfolio.repository;
import com.nanda.portfolio.entity.ContactMessage; import org.springframework.data.jpa.repository.JpaRepository;
public interface ContactMessageRepository extends JpaRepository<ContactMessage,Long>{ long countByDeletedFalse(); long countByReadMessageFalseAndDeletedFalse(); }
