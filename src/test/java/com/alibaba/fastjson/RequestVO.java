package com.alibaba.fastjson;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 合并请求实体
 */
@Data
public class RequestVO {

  /**
   * 请求地址
   */
  private String rqUrl;

  /**
   * 请求参数
   */
  private String rqJson;

  /**
   * 请求编码
   */
  private String rqCode;

  /**
   * 获取rqJson对应的对象
   *
   * @return
   */
  public PageQueryInfo getRqJsonBean() {
    PageQueryInfo pageQueryInfo = null;

    String json = getRqJson();
    if (StringUtils.isNotBlank(json)) {
      pageQueryInfo = JSONObject.parseObject(json, PageQueryInfo.class);
    }

    return pageQueryInfo;
  }
}
