package com.alibaba.fastjson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.RawValue;
import org.junit.Test;

/**
 * @author zhengzhliang@yonyou.com
 * @date 2023/6/1
 */
public class MyTest {
    @Test
    public void name() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();


        String str = "{\"key\":\"value\",\"key1\":{\"a\":\"b\"},\"key2\":[\"aa\",\"bb\"]}";

        //readTree方法解析为JsonNode
        JsonNode jsonNode = objectMapper.readTree(str);
        System.out.println(jsonNode);

        //readValue 解析为具体类型
        ObjectNode objectNode = objectMapper.readValue(str, ObjectNode.class);
        System.out.println(objectNode);

        PageQueryInfo pageQueryInfo = new PageQueryInfo();

        //put
        objectNode.putPOJO("pojo", pageQueryInfo);
        objectNode.put("pojoToString", pageQueryInfo.toString());
        objectNode.put("pojoText", objectMapper.writeValueAsString(pageQueryInfo));
        objectNode.put("boolean", true);

        Object nullObj = null;
        objectNode.putPOJO("null", nullObj);
        objectNode.put("int", 1);
        objectNode.put("float", 1.0F);

        //put特殊类型
        objectNode.putNull("putNull");
        objectNode.putObject("putObject");
        objectNode.putArray("putArray");

        RawValue rawValue = new RawValue("ab\"\"");
        objectNode.putRawValue("raw", rawValue);

        System.out.println(objectNode);


    }
}
