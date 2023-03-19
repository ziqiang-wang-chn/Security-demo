package cn.ohbug.authcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity(prePostEnabled = true) // 方法级别的授权开启
public class AuthCenterApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthCenterApplication.class, args);
  }

}
