package com.nanda.portfolio.entity;
import jakarta.persistence.*; import jakarta.validation.constraints.*; import lombok.*;
@Entity @Table(name="contact_messages", indexes=@Index(name="idx_message_read",columnList="readMessage,createdAt"))
@Getter @Setter @NoArgsConstructor
public class ContactMessage extends BaseEntity {
 @NotBlank @Size(max=100) private String name; @NotBlank @Email @Size(max=180) private String email; @Size(max=160) private String subject;
 @NotBlank @Size(max=5000) @Column(columnDefinition="TEXT") private String message; @Column(nullable=false) private boolean readMessage=false;
 public String getName(){return name;} public void setName(String v){name=v;} public String getEmail(){return email;} public void setEmail(String v){email=v;} public String getSubject(){return subject;} public void setSubject(String v){subject=v;} public String getMessage(){return message;} public void setMessage(String v){message=v;} public boolean isReadMessage(){return readMessage;} public void setReadMessage(boolean v){readMessage=v;}
}
