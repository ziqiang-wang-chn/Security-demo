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
import jakarta.persistence.Transient;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户信息表
 */
@Entity(name = "system_user")
@Getter
@Setter
@Table
public class System_User implements UserDetails {

  // 有效
  @Transient
  private static final int VALID = 0;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column
  private String username;
  @Column
  private String password;
  @Column
  private String email;
  @Column
  private Integer age;
  @Column
  private String sex;
  @Column
  private String tel;
  @Column
  private String addr;
  @Column
  private String card;
  /**
   * 0代表未结婚，1代表已结婚
   */
  @Column
  private Byte married;

  /**
   * 是否删除 是1   否0
   */
  @Column
  private Byte isDel;
  @Column
  private Date createTime;

  //--------------------- auth 变量 --------------------------
  @Column
  private Byte accountNonExpired;
  @Column
  private Byte enabled;
  @Column
  private Byte credentialsNonExpired;
  @Column
  private Byte accountNonLocked;

  /**
   * @ManyToMany 作用：用于映射多对多关系 属性： cascade：配置级联操作。 fetch：配置是否采用延迟加载。
   * targetEntity：配置目标的实体类。映射多对多的时候不用写。
   * @JoinTable 作用：针对中间表的配置 属性： nam：配置中间表的名称 joinColumns：中间表的外键字段关联当前实体类所对应表的主键字段
   * inverseJoinColumn：中间表的外键字段关联对方表的主键字段
   * @JoinColumn 作用：用于定义主键字段和外键字段的对应关系。 属性： name：指定外键字段的名称 referencedColumnName：指定引用主表的主键字段名称
   * unique：是否唯一。默认值不唯一 nullable：是否允许为空。默认值允许。 insertable：是否允许插入。默认值允许。 updatable：是否允许更新。默认值允许。
   * columnDefinition：列的定义信息。
   */
  // 一个用户user可能具有多个角色role
  @ManyToMany
  @JoinTable(name = "system_user_role",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
  )
  private List<System_Role> roles;

  @Transient
  private List<System_Permission> authorities;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  // true 有效，false 无效

  /**
   * 1 过期 ， 0 未过期
   */
  @Override
  public boolean isAccountNonExpired() {
    return accountNonExpired == VALID;
  }

  /**
   * 1 锁定 ， 0 未锁定
   */
  @Override
  public boolean isAccountNonLocked() {
    return accountNonLocked == VALID;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return credentialsNonExpired == VALID;
  }

  @Override
  public boolean isEnabled() {
    return enabled == VALID;
  }

}
