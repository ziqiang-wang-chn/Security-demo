package cn.ohbug.authcenter.filter;

import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * 因为JwtAuthenticationFilter属于filter，而GlobalExceptionHandler只会处理restcontroller中的异常
 * 并不会处理filter中的异常，所以此类是添加到spring security过滤链中，过滤所有filter爆出来的异常
 */
@Component
public class GlobalAuthenticationExceptionChainFilter extends OncePerRequestFilter {

  @Resource(name = "handlerExceptionResolver")
  private HandlerExceptionResolver handlerExceptionResolver;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (Exception e) {
      handlerExceptionResolver.resolveException(request, response, null, e);
    }
  }
}
