package com.alibaba.fastjson;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.JacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import java.lang.reflect.Type;
import java.util.*;

/**
 * 请使用Jackson
 */
@Deprecated
public abstract class JSON {

    protected static final TimeZone defaultTimeZone = TimeZone.getDefault();
    protected static final Locale defaultLocale = Locale.getDefault();
    protected static final String DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static ObjectMapper mapper = new ObjectMapper();

    public static Object parse(String text) {
        return JacksonUtil.readTree(text);
    }

    public static JSONObject parseObject(String text) {
        return JacksonUtil.from(text, JSONObject.class);
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        return JacksonUtil.from(text, clazz);
    }

    public static JSONArray parseArray(String text) {
        try {
            JsonNode jn = mapper.readTree(text);
            if (jn.isArray()) {
                JSONArray ja = new JSONArray(JsonNodeFactory.instance);
                ArrayNode an = (ArrayNode) jn;
                for (JsonNode j : an) {
                    ja.add(j);
                }
                return ja;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
//		return JacksonUtil.from(text, JSONArray.class);
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        return JacksonUtil.fromList(text, clazz);
    }

    public static String toJSONString(Object object) {
        return JacksonUtil.toJson(object);
    }


    public static <T> T toJavaObject(String json, Class<T> clazz) {
        return JacksonUtil.from(json, clazz);
    }

    /**
     * jackson parse出来的是map和list,所以把map和list转换为jsonObject和jsonArray
     *
     * @param map
     * @return
     */
 /*   private static JSONObject mapToJsonObject(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            final Object value = entry.getValue();
            if (value instanceof Map) {
                jsonObject.put(entry.getKey(), mapToJsonObject((Map<String, Object>) value));
            } else if (value instanceof List) {
                final List listVal = (List) value;
                JSONArray objects = new JSONArray();
                for (Object o : listVal) {
                    if (o instanceof Map) {
                        objects.add(mapToJsonObject((Map<String, Object>) o));
                    } else if (o instanceof List) {
                        objects.add(listConvertToJsonArray((List) o));
                    }
                }
                jsonObject.put(entry.getKey(), objects);
            } else {
                jsonObject.set(entry.getKey(), (JsonNode) value);
            }
        }
        return jsonObject;
    }
*/
   /* public static JSONArray listConvertToJsonArray(List list) {
        List<JsonNode> jsonObjects = new ArrayList<>(list.size());
        for (Object obj : list) {
            jsonObjects.add(mapToJsonObject((Map<String, Object>) obj));
        }
        return new JSONArray(JsonNodeFactory.instance, jsonObjects);
    }
*/
//    private static JSONArray listConvertToJsonArray(List list) {
//        List<Object> jsonObjects = new ArrayList<>(list.size());
//        for (Object obj : list) {
//            jsonObjects.add(mapToJsonObject((Map<String, Object>) obj));
//        }
//        return new JSONArray(jsonObjects);
//    }

//    /**
//     * jackson parse出来的是map和list,所以把map和list转换为jsonObject和jsonArray
//     *
//     * @param map
//     * @return
//     */
//    private static JSONObject mapToJsonObject(Map<String, Object> map) {
//        JSONObject jsonObject = new JSONObject(map.size());
//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            final Object value = entry.getValue();
//            if (value instanceof Map) {
//                jsonObject.put(entry.getKey(), mapToJsonObject((Map<String, Object>) value));
//            } else if (value instanceof List) {
//                final List listVal = (List) value;
//                JSONArray objects = new JSONArray(JsonNodeFactory.instance, listVal.size());
//                for (Object o : listVal) {
//                    if (o instanceof Map) {
//                        objects.add(mapToJsonObject((Map<String, Object>) o));
//                    } else if (o instanceof List) {
//                        objects.add(listConvertToJsonArray((List) o));
//                    }
//                }
//                jsonObject.put(entry.getKey(), objects);
//            } else {
//                jsonObject.put(entry.getKey(), value.toString());
//            }
//        }
//        return jsonObject;
//    }


}
