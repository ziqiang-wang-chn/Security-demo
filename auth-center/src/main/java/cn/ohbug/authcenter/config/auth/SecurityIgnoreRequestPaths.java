package cn.ohbug.authcenter.config.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 统一在application.yml中配置spring security的忽略请求路径
 */
@Component
@ConfigurationProperties(prefix = "jwt.ignore")
@Data
public class SecurityIgnoreRequestPaths {

  private String urls[];

}
