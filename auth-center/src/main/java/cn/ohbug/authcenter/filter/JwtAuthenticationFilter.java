package cn.ohbug.authcenter.filter;

import cn.ohbug.authcenter.config.auth.SecurityIgnoreRequestPaths;
import cn.ohbug.authcenter.exception.UserAuthorizationDefineException;
import cn.ohbug.authcenter.utils.JwtUtils;
import com.auth0.jwt.interfaces.Claim;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  // token 携带头
  private static final String authorization = "Authorization";
  // 路径匹配器
  private static final AntPathMatcher pathMatcher = new AntPathMatcher();
  @Resource
  private UserDetailsService userDetailsService;
  @Resource
  private SecurityIgnoreRequestPaths ignoreRequestPaths;
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String requestURI = request.getRequestURI();
    String[] ignoreRequestPathsUrls = ignoreRequestPaths.getUrls();
    boolean is_ignore = Arrays.stream(ignoreRequestPathsUrls).anyMatch(url -> url.equals(requestURI));
    if (is_ignore) {
      filterChain.doFilter(request, response);
      return;
    }
    String token = request.getHeader("Authorization");
    if (!StringUtils.hasLength(token)) {
      // token is null
      throw new UserAuthorizationDefineException("token 不可以为空");
    }

    if (JwtUtils.verify(token)) {
      // token 合法
      if (!JwtUtils.isExpired(token)) {
        throw new UserAuthorizationDefineException("token 已过期");
      }
    } else {
      throw new UserAuthorizationDefineException("token 不合法");
    }
    Claim claim_username = JwtUtils.getTokenInfo(token).getClaim("username");
    String username = claim_username.asString();
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    filterChain.doFilter(request, response);
  }
}
