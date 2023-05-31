package com.alibaba.fastjson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhengzhliang@yonyou.com
 * @date 2023/5/30
 */
public class JSONObjectTest {
    PageMetaDTO pageMetaDTO;

    @Before
    public void setUp() throws Exception {

        ArrayList<String> tabsValue = new ArrayList<String>();
        tabsValue.add("tabsValue-a");
        tabsValue.add("tabsValue-b");
        tabsValue.add("tabsValue-c");

        List<ArrayList<String>> tabs = new ArrayList<>();
        tabs.add(tabsValue);

        AssociatedTabs associatedTabs = new AssociatedTabs();
        associatedTabs.setLayout("layout1");
        associatedTabs.setTabs(tabs);

        Map<String, AssociatedTabs> containerrelation = new HashMap<>();
        containerrelation.put("associatedTabs", associatedTabs);

        pageMetaDTO = new PageMetaDTO();
        pageMetaDTO.setName("测试");
        pageMetaDTO.setContainerrelation(containerrelation);
    }

    @Test
    public void testJackson() throws JsonProcessingException {

        //fastjson 对象转字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(pageMetaDTO);
        System.out.println(s);

        //fastjson 字符串转对象
        PageMetaDTO pageMetaDTO1 = objectMapper.readValue(s, PageMetaDTO.class);
        System.out.println(pageMetaDTO1);

    }

    @Test
    public void testFastJson() {
        // 对象转字符串
        String jsonString = JSON.toJSONString(pageMetaDTO);

        //jsonObject 对象转JSONObject
        JSONObject metaJson = JSON.parseObject(jsonString);

        //jsonObject JSONObject转对象
        PageMetaDTO pageMetaDTO2 = JSON.toJavaObject(metaJson, PageMetaDTO.class);
    }

    @Test
    public void size() {
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