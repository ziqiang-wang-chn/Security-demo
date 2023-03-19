package cn.ohbug.authcenter.service;

import cn.ohbug.authcenter.dao.SystemUserDao;
import cn.ohbug.authcenter.entity.System_User;
import cn.ohbug.authcenter.exception.UserAuthorizationDefineException;
import jakarta.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SystemUserService {

  @Resource
  private SystemUserDao systemUserDao;
  @Resource
  private PasswordEncoder encoder;

  @Resource
  private AuthenticationManager authenticationManager;

  public Optional<System_User> findSystemUserById(Long id) {
    return systemUserDao.findById(id);
  }

  public List<System_User> findAll() {
    List<System_User> all = systemUserDao.findAll();
    return all;
  }

  public int deleteById(Long id) {
    int rows = systemUserDao.logicDeleteById(id);
    return rows;
  }

  public void saveSystemUser(System_User user) {
    user.setPassword(encoder.encode(user.getPassword()));
    user.setCreateTime(new Date());
    systemUserDao.save(user);
  }

  public void login(System_User user) {
    UsernamePasswordAuthenticationToken up = new UsernamePasswordAuthenticationToken(
        user.getUsername(), user.getPassword());
    try {
      // authenticate 后 会调用UserDetailConfig#loadUserByUsername方法
      // authenticate 用来鉴权
      authenticationManager.authenticate(up);
    } catch (AuthenticationException e) {
      throw new UserAuthorizationDefineException(e.getMessage(), e);
    }
  }
}
