package cn.ohbug.authcenter.handler;

import cn.ohbug.authcenter.config.web.Result;
import cn.ohbug.authcenter.exception.UserAuthorizationDefineException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(UserAuthorizationDefineException.class)
  public Result handleUserDefineException(UserAuthorizationDefineException e) {
    log.error(e.getMessage(), e);
    return Result.error(e.getMessage());
  }

  /**
   * 兜底的异常处理器
   */
  @ExceptionHandler(Exception.class)
  public Result endExceptionHandle(Exception e) {
    log.error(e.getMessage(), e);
    return Result.error(e.getMessage());
  }

}
