package com.alibaba.fastjson;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 平台参数交互json包装VO
 */
@Data
public class RequestSysJsonVO {

  /**
   * 应用编码
   */
  @NotNull(message = "应用编码不能为空")
  private String appcode;

  /**
   * 业务动作
   */
  private String busiaction;

  /**
   * 页面动作来源
   */
  private String from;

  /**
   * 验证双提交cookies
   */
  private String pagecs;
  /**
   * websocket中识别页签 tabid
   */
  private String tabid;

  /**
   * 签名后返回的值
   */
  private String signdata;

  /**
   * 用户页面参数
   */
  private String custom;

  /**
   *
   */
  private String sn;

  /**
   * 是否按照ts比较进行返回，如果ts不变，则返回空，如果ts变化，则返回新模板
   */
  private boolean compareTs = false;

  /**
   * 前端的ts
   */
  private String ts;

}
