package cn.ohbug.authcenter.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

  /**
   * header : 包括声明类型 jwt 和 使用的算法 payload: iss：jwt的签发者/发行人； sub：主题； aud：接收方； exp：jwt过期时间；
   * nbf：jwt生效时间； iat：签发时间 jti：jwt唯一身份标识，可以避免重放攻击
   */

  private static String Secret;
  private static int expires;


  /**
   * 生成Token  header.payload.sign
   */
  public static String getToken(Map<String, String> claim) {
    Map<String, Object> header = new HashMap<>();
    header.put("type", "jwt");
    header.put("alg", "HMAC256");
    Calendar instance = Calendar.getInstance();
    instance.add(Calendar.DATE, expires); //默认7天过期
    //创建jwt builder
    JWTCreator.Builder builder = JWT.create();
    //payload
    claim.forEach((k, v) -> builder.withClaim(k, v));
    //header
    return builder.withHeader(header).withExpiresAt(instance.getTime())
        .sign(Algorithm.HMAC256(Secret));
  }

  /**
   * 验证token的合法性
   */
  public static boolean verify(String token) {
    try {
      JWT.require(Algorithm.HMAC256(Secret)).build().verify(token);
    } catch (JWTVerificationException e) {
      return false;
    }
    return true;
  }

  /**
   * 获取token的信息方法
   */
  public static DecodedJWT getTokenInfo(String token) {
    DecodedJWT verify = JWT.require(Algorithm.HMAC256(Secret)).build().verify(token);
    return verify;
  }

  /**
   * 是否过期
   */
  public static boolean isExpired(String token) {
    DecodedJWT info = getTokenInfo(token);
    long old_time = info.getExpiresAt().getTime();
    long now_time = new Date().getTime();
    return (old_time - now_time) > 0;
  }

  @Value("${jwt.secret}")
  public void setSecret(String secret) {
    JwtUtils.Secret = secret;
  }

  @Value("${jwt.expires}")
  public void setExpires(int expires) {
    JwtUtils.expires = expires;
  }
}
