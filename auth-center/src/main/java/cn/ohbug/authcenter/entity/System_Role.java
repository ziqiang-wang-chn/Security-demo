package cn.ohbug.authcenter.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户表
 * <p>
 * 分为内部用户以及外部需要认证的用户 一个用户需要对应多种权限 System:role:inner:user System:role:outer:admin
 * System:role:outer:user
 */
@Entity(name = "system_role")
@Getter
@Setter
@Table
public class System_Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column
  private String roleName;


  // 一个角色有多个权限
  @ManyToMany
  @JoinTable(name = "system_role_permission",
      joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id")
  )
  private List<System_Permission> permissions;

  // 一个角色有多个用户
  @JsonBackReference
  @ManyToMany(mappedBy = "roles")
  private List<System_User> users;

  /**
   * 是否删除 是1   否0
   */
  @Column
  private Byte isDel;


}
