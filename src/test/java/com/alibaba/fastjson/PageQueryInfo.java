package com.alibaba.fastjson;

import lombok.Data;

/**
 * 模板查询参数
 */
@Data
public class PageQueryInfo {

  /**
   * 应用编码
   */
  private String appcode;

  /**
   * 页面编码
   */
  private String pagecode;

  /**
   * 模板编码
   */
  private String templetid;

  /**
   * 模型主键 合并需要使用模型主键控制按钮权限
   */
  private String cube;

  /**
   * 扩展参数。目前企业表报注册节点用到这个
   */
  private String sysParamJson;

  /**
   * 这个是后端添加的参数，平台规范InvocationInfoProxy后，线程直接上下文传不能使用InvocationInfoProxy
   */
  private boolean supplyMultiLanguage;

}
