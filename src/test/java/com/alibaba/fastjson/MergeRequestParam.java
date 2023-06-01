package com.alibaba.fastjson;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 页面模版请求参数
 */
public class MergeRequestParam {

  /**
   * 系统参数
   */
  @Valid
  @NotNull(message = "系统参数不能为空")
  @Setter
  @Getter
  private RequestSysJsonVO sysParamJson;

  /**
   * 业务参数 前端传入的
   */
  @NotNull(message = "业务参数不能为空")
  @Setter
  private String busiParamJson;

  public List<RequestVO> getRequestVOList() {
    return JSONArray.parseArray(busiParamJson, RequestVO.class);
  }

}
