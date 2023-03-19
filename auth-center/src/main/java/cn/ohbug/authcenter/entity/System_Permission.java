package cn.ohbug.authcenter.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

/**
 * 权限表，一个Role角色对应多种权限 System:permission:all System:permission:read System:permission:add
 * System:permission:delete System:permission:modify
 */
@Entity(name = "system_permission")
@Getter
@Setter
@Table
public class System_Permission implements GrantedAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private String permissionName;

  /**
   * 是否删除 是1   否0
   */
  @Column
  private Byte isDel;

  @ManyToMany(mappedBy = "permissions")
  @JsonBackReference
  private List<System_Role> roles;

  @Override
  public String getAuthority() {
    return permissionName;
  }

}
