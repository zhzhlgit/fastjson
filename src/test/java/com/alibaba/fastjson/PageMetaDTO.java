package com.alibaba.fastjson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 页面模板信息
 */
@Data
public class PageMetaDTO {

    /**
     * 页面编码
     */
    private String code;

    /**
     * 区域关系
     */
    private Map<String, AssociatedTabs> containerrelation = new HashMap<>();

    /**
     * 主区域与主区域的子区域间关系
     */
    private Map<String, String[]> formrelation = new HashMap<>();

    /**
     * 表格区域关系
     */
    private Map<String, AreaRelation> gridrelation =
            new HashMap<>();

    /**
     * 区域编码与区域的映射
     */
    private final Map<String, AbstractAreaMeta> index =
            new LinkedHashMap<>();

    /**
     * 页面名称
     */
    private String name;

    /**
     * 页面主键
     */
    private String pageid;

    /**
     * 页面模板ts
     */
    private String ts;

    /**
     * 验证公式标志
     */
    private Boolean validateFlag = Boolean.FALSE;

    /**
     * 增加区域信息
     *
     * @param area
     */
    public void addAreaMeta(AbstractAreaMeta area) {
        this.index.put(area.getCode(), area);
    }

    /**
     * 获取所有区域
     *
     * @return 所有区域
     */
    @JsonIgnore
    public AbstractAreaMeta[] getAllAreaMetas() {
        int size = this.index.size();
        AbstractAreaMeta[] vos = new AbstractAreaMeta[size];
        vos = this.index.values().toArray(vos);
        return vos;
    }

    /**
     * 根据编码获取区域信息
     *
     * @param areacode
     * @return 区域信息
     */
    public AbstractAreaMeta getAreaMeta(String areacode) {
        return this.index.get(areacode);
    }
}