package cn.ohbug.authcenter.controller;

import cn.ohbug.authcenter.config.web.Result;
import cn.ohbug.authcenter.entity.System_User;
import cn.ohbug.authcenter.service.SystemUserService;
import cn.ohbug.authcenter.utils.JwtUtils;
import jakarta.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemUserController {

  @Resource
  private SystemUserService systemUserService;

  @PostMapping("/login")
  public Result login(@RequestBody System_User user) {
    systemUserService.login(user);
    Map<String, String> claims = new HashMap<>();
    claims.put("username", user.getUsername());
    claims.put("password", user.getPassword());
    String token = JwtUtils.getToken(claims);
    return Result.ok(token);
  }

  @PreAuthorize(value = "hasAnyAuthority({'System:permission:all','System:permission:read'})")
  @GetMapping("/user/{id}")
  public System_User getSystemUser(@PathVariable Long id) {
    Optional<System_User> userOptional = systemUserService.findSystemUserById(id);
    System_User user = userOptional.orElseThrow(() -> new RuntimeException("无此用户"));
    return user;
  }

  @PreAuthorize(value = "hasAnyAuthority({'System:permission:all','System:permission:delete'})")
  @DeleteMapping("/user/{id}")
  public Result deleteById(@PathVariable Long id) {
    int rows = systemUserService.deleteById(id);
    if (rows >= 1) {
      return Result.ok("deleteById");
    }
    return Result.error("fail");
  }

  /**
   * 只查询出未删除的用户
   *
   * @return
   */
  @PreAuthorize(value = "hasAnyAuthority({'System:permission:all'})")
  @GetMapping("/all")
  public List<System_User> getAll() {
    List<System_User> all = systemUserService.findAll();
    return all;
  }

  @PreAuthorize(value = "hasAnyAuthority({'System:permission:all','System:permission:add'})")
  @PostMapping("/save")
  public Result saveSystemUser(@RequestBody System_User user) {
    systemUserService.saveSystemUser(user);
    return Result.ok(user.getUsername() + " please redirect to login");
  }

}
