package com.alibaba.fastjson;


import lombok.Getter;
import lombok.Setter;

/**
 * 抽象区域
 */
@Getter
@Setter
public abstract class AbstractAreaMeta {

    /**
     * 模板编码
     */
    private String code;

    private String name;

    /**
     * 主键
     */
    private String oid;

    /**
     * 获取区域类型
     *
     * @return 区域类型
     */
    public abstract String getModuletype();

    /**
     * 区域是否隐藏
     */
    private boolean areaVisible;

    /**
     * 区域是否展开
     */
    private boolean isunfold;

    /**
     * 是否非元数据条件
     */
    private boolean isnotmeta;

}
