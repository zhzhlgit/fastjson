package com.alibaba.fastjson;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author zhengzhliang@yonyou.com
 * @date 2023/5/30
 */
public class JSONObjectTest {
    private JSONObject jsonObject;

    @Test
    public void name() {
        String json = "{\"pagecode\":\"EPMP1010_main\",\"appcode\":\"EPMP1010\",\"sysParamJson\":{\"serviceCode\":\"EPMP1010\"},\"compareTs\":false}";
        PageQueryInfo pageQueryInfo = JSONObject.parseObject(json, PageQueryInfo.class);

    }

    @Before
    public void setUp() throws Exception {
        jsonObject = new JSONObject();
    }

    @Test
    public void size() {
        Assert.assertEquals(0, jsonObject.size());
    }

    @Test
    public void isEmpty() {
    }

    @Test
    public void containsKey() {

    }

    @Test
    public void containsValue() {
    }

    @Test
    public void get() {
        jsonObject.put("a", 1);
        jsonObject.put("B", 1.0);
        jsonObject.put("C", "SADFASDF");
        jsonObject.put("D", new JSONObject());

        System.out.println(jsonObject);
    }

    @Test
    public void getOrDefault() {
    }

    @Test
    public void getJSONObject() {
    }

    @Test
    public void getJSONArray() {
    }

    @Test
    public void getObject() {
    }

    @Test
    public void getBoolean() {
    }

    @Test
    public void getBytes() {
    }

    @Test
    public void getBooleanValue() {
    }

    @Test
    public void getByte() {
    }

    @Test
    public void getByteValue() {
    }

    @Test
    public void getShort() {
    }

    @Test
    public void getShortValue() {
    }

    @Test
    public void getInteger() {
    }

    @Test
    public void getIntValue() {
    }

    @Test
    public void getLong() {
    }

    @Test
    public void getLongValue() {
    }

    @Test
    public void getFloat() {
    }

    @Test
    public void getFloatValue() {
    }

    @Test
    public void getDouble() {
    }

    @Test
    public void getDoubleValue() {
    }

    @Test
    public void getBigDecimal() {
    }

    @Test
    public void getBigInteger() {
    }

    @Test
    public void getString() {
    }

    @Test
    public void put() {
    }

    @Test
    public void fluentPut() {
    }

    @Test
    public void putAll() {
    }

    @Test
    public void fluentPutAll() {
    }

    @Test
    public void clear() {
    }

    @Test
    public void fluentClear() {
    }

    @Test
    public void remove() {
    }

    @Test
    public void fluentRemove() {
    }

    @Test
    public void keySet() {
    }

    @Test
    public void values() {
    }

    @Test
    public void entrySet() {
    }

    @Test
    public void testEquals() {
    }

    @Test
    public void testHashCode() {
    }

    @Test
    public void toJavaObject() {
    }

    @Test
    public void testToJavaObject() {
    }

    @Test
    public void invoke() {
    }
}