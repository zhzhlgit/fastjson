package com.alibaba.fastjson;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 区域关系
 */
@Data
public class AreaRelation {

    /**
     * 表格间多页签。
     */
    private AssociatedTab associatedTabs;

    /**
     * 浏览态下拉
     */
    private String destBrowseAreaCode;

    /**
     * 编辑态侧拉
     */
    private List<String> destEditAreaCode;

    /**
     * 浏览态下拉备份
     */
    private String initialDestBrowseAreaCode;

    /**
     * 编辑态侧拉备份
     */
    private List<String> initialDestEditAreaCode;

    /**
     * 关联的表格区域。表格内多页签。 tabRelation的备份。 associatedTabs存在时才会set
     */
    private String[] initialTabRelation;

    /**
     * 主区域
     */
    private String srcAreaCode;

    /**
     * 关联的表格区域。表格内多页签。
     */
    private String[] tabRelation;

    public void addDestEditAreaCode(String areaCode) {
        if (this.destEditAreaCode == null) {
            this.destEditAreaCode = new ArrayList<>();
        }
        this.destEditAreaCode.add(areaCode);
    }

}