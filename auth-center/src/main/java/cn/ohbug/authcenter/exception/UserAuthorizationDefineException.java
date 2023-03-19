package cn.ohbug.authcenter.exception;

/**
 * User_System 类的 自定义异常类
 */
public class UserAuthorizationDefineException extends RuntimeException {

  private UserAuthorizationDefineException() {
  }

  public UserAuthorizationDefineException(String message) {
    super(message);
  }

  public UserAuthorizationDefineException(String message, Throwable cause) {
    super(message, cause);
  }

  public UserAuthorizationDefineException(Throwable cause) {
    super(cause);
  }

  public UserAuthorizationDefineException(String message, Throwable cause,
      boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
