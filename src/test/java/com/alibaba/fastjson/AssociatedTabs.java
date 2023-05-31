package com.alibaba.fastjson;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 表格间多页签
 */
@Data
public class AssociatedTabs implements Serializable {

    /**
     * 页签布局 水平或垂直
     */
    private String layout;

    /**
     * 主区域下辖的诸页签 保存区域的区域编码
     */
    private List<ArrayList<String>> tabs = new ArrayList<>();
}