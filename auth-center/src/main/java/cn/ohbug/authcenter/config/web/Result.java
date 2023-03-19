package cn.ohbug.authcenter.config.web;

import lombok.Data;

/**
 * 错误或者操作成功的统一返回体
 */
@Data
public class Result {

  private String msg;
  private Object data;

  private Result() {
  }

  private Result(String msg, Object data) {
    this.msg = msg;
    this.data = data;
  }

  /**
   * @param data 要返回的提示信息
   * @return Result 包装类
   */
  public static Result ok(Object data) {
    return new Result("操作成功！", data);
  }


  /**
   * @param data 要返回的提示信息
   * @return Result 包装类
   */
  public static Result error(Object data) {
    return new Result("操作失败！", data);
  }
}

