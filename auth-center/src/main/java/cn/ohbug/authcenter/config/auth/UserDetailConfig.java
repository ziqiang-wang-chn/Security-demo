package cn.ohbug.authcenter.config.auth;

import cn.ohbug.authcenter.dao.SystemUserDao;
import cn.ohbug.authcenter.entity.System_Permission;
import cn.ohbug.authcenter.entity.System_Role;
import cn.ohbug.authcenter.entity.System_User;
import jakarta.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailConfig implements UserDetailsService {

  @Resource
  private SystemUserDao systemUserDao;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    List<System_User> all = systemUserDao.findAll();
    List<System_User> users = all.stream().filter(user -> username.equals(user.getUsername()))
        .collect(Collectors.toList());
    if (users == null) {
      throw new UsernameNotFoundException("UserDetailsService 用户名密码错误");
    }
    // 到这表示此用户存在
    System_User system_user = users.get(0);
    List<System_Role> roles = system_user.getRoles();
    List<System_Permission> permissionList = roles.stream()
        .flatMap(role -> role.getPermissions().stream()).toList();
    system_user.setAuthorities(permissionList);
    return system_user;
  }

}
