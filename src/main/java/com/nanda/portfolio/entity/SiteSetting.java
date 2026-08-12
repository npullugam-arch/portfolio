package com.nanda.portfolio.entity;
import jakarta.persistence.*; import jakarta.validation.constraints.NotBlank; import lombok.*;
@Entity @Table(name="site_settings") @Getter @Setter @NoArgsConstructor
public class SiteSetting extends BaseEntity { @NotBlank @Column(nullable=false,unique=true) private String settingKey; @Column(name="setting_value",columnDefinition="TEXT") private String value; private String description;
 public String getSettingKey(){return settingKey;} public void setSettingKey(String v){settingKey=v;} public String getValue(){return value;} public void setValue(String v){value=v;} public String getDescription(){return description;} public void setDescription(String v){description=v;}
}
