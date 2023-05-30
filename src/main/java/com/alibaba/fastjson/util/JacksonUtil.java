package com.alibaba.fastjson.util;

import com.alibaba.fastjson.exception.JacksonException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Jackson工具类
 * 优势：
 * 数据量高于百万的时候，速度和FastJson相差极小
 * API和注解支持最完善，可定制性最强
 * 支持的数据源最广泛（字符串，对象，文件、流、URL）
 *
 * @author zhzhl
 */
public class JacksonUtil {

    private static final ObjectMapper OBJECT_MAPPER;

    private static final Set<JsonReadFeature> JSON_READ_FEATURES_ENABLED;

    static {
        //https://www.cnblogs.com/yourbatman/p/13390444.html#%E6%94%AF%E6%8C%81%E9%9D%9E%E6%A0%87%E5%87%86%E6%A0%BC%E5%BC%8F
        JSON_READ_FEATURES_ENABLED = new HashSet<>();
        //允许在JSON中使用Java注释
        JSON_READ_FEATURES_ENABLED.add(JsonReadFeature.ALLOW_JAVA_COMMENTS);
        //允许 json 存在没用双引号括起来的 field
        JSON_READ_FEATURES_ENABLED.add(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES);
        //允许 json 存在使用单引号括起来的 field
        JSON_READ_FEATURES_ENABLED.add(JsonReadFeature.ALLOW_SINGLE_QUOTES);
        //允许 json 存在没用引号括起来的 ascii 控制字符
        JSON_READ_FEATURES_ENABLED.add(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS);
        //允许 json number 类型的数存在前导 0 (例: 0001)
        JSON_READ_FEATURES_ENABLED.add(JsonReadFeature.ALLOW_LEADING_ZEROS_FOR_NUMBERS);
        //允许 json 存在 NaN, INF, -INF 作为 number 类型
        JSON_READ_FEATURES_ENABLED.add(JsonReadFeature.ALLOW_NON_NUMERIC_NUMBERS);
        //允许数组json的结尾多逗号
        JSON_READ_FEATURES_ENABLED.add(JsonReadFeature.ALLOW_TRAILING_COMMA);
        //否允许**反斜杠**转义任何字符
        JSON_READ_FEATURES_ENABLED.add(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER);

        try {
            //初始化
            JsonMapper.Builder builder = JsonMapper.builder().enable(JSON_READ_FEATURES_ENABLED.toArray(new JsonReadFeature[0]));
            JsonMapper jsonMapper = builder.build();

            OBJECT_MAPPER = initMapperConfig(jsonMapper);
        } catch (Exception e) {
            throw new JacksonException("jackson config error", e);
        }
    }

    public static ObjectMapper initMapperConfig(ObjectMapper objectMapper) {
        String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
        objectMapper.setDateFormat(new SimpleDateFormat(dateTimeFormat));
        //配置序列化级别
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //配置JSON缩进支持
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, false);
        //允许单个数值当做数组处理
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        //禁止重复键, 抛出异常
        objectMapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
        //禁止使用int代表Enum的order()來反序列化Enum, 抛出异常
        objectMapper.enable(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS);
        //有属性不能映射的时候不报错
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        //对象为空时不抛异常
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        //时间格式
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        //允许未知字段
        objectMapper.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);
        //序列化BigDecimal时之间输出原始数字还是科学计数, 默认false, 即是否以toPlainString()科学计数方式来输出
        objectMapper.enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN);
        //识别Java8时间
        objectMapper.registerModule(new ParameterNamesModule());
        objectMapper.registerModule(new Jdk8Module());
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateTimeFormat);
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dtf)).addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dtf));
        objectMapper.registerModule(javaTimeModule);
        //识别Guava包的类
//        objectMapper.registerModule(new GuavaModule());
        return objectMapper;
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }


    public static ObjectNode createObjectNode() {
        return OBJECT_MAPPER.createObjectNode();
    }

    /**
     * Java对象转字符串
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JacksonException("转json字符串失败:{}", obj, e);
        }
    }


    /**
     * 序列化为JSON
     */
    public static <V> String toJson(List<V> list) {
        try {
            return OBJECT_MAPPER.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new JacksonException("jackson to error, data: {}", list, e);
        }
    }

    /**
     * 序列化为JSON
     */
    public static <V> String to(V v) {
        try {
            return OBJECT_MAPPER.writeValueAsString(v);
        } catch (JsonProcessingException e) {
            throw new JacksonException("jackson to error, data: {}", v, e);
        }
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(String json, Class<V> type) {
        return from(json, (Type) type);
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(String json, Type type) {
        if (isEmpty(json)) {
            return null;
        }
        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructType(type);
            return OBJECT_MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, json: {}, type: {}", json, type, e);
        }
    }

    /**
     * ObjectNode转Class
     *
     * @param objectNode
     * @param type
     * @param <V>
     * @return
     */
    public static <V> V from(ObjectNode objectNode, Class<V> type) {
        if (null == objectNode) {
            return null;
        }
        try {
            return OBJECT_MAPPER.treeToValue(objectNode, type);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, json: {}, type: {}", objectNode, type, e);
        }
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(URL url, Class<V> type) {
        try {
            return OBJECT_MAPPER.readValue(url, type);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, url: {}, type: {}", url.getPath(), type, e);
        }
    }


    /**
     * JSON反序列化
     */
    public static <V> V from(File file, Class<V> type) {
        try {
            return OBJECT_MAPPER.readValue(file, type);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, file path: {}, type: {}", file.getPath(), type, e);
        }
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(InputStream inputStream, Class<V> type) {
        try {
            return OBJECT_MAPPER.readValue(inputStream, type);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, type: {}", type, e);
        }
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(String json, TypeReference<V> type) {
        return from(json, type.getType());
    }


    /**
     * JSON反序列化
     */
    public static <V> V from(URL url, TypeReference<V> type) {
        try {
            return OBJECT_MAPPER.readValue(url, type);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, url: {}, type: {}", url.getPath(), type, e);
        }
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(File file, TypeReference<V> type) {
        try {
            return OBJECT_MAPPER.readValue(file, type);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, file path: {}, type: {}", file.getPath(), type, e);
        }
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(InputStream inputStream, TypeReference<V> type) {
        try {
            return OBJECT_MAPPER.readValue(inputStream, type);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, type: {}", type, e);
        }
    }


    /**
     * JSON反序列化（List）
     */
    public static <V> List<V> fromList(URL url, Class<V> type) {
        try {
            CollectionType collectionType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, type);
            return OBJECT_MAPPER.readValue(url, collectionType);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, url: {}, type: {}", url.getPath(), type, e);
        }
    }


    /**
     * JSON反序列化（List）
     */
    public static <V> List<V> fromList(InputStream inputStream, Class<V> type) {
        try {
            CollectionType collectionType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, type);
            return OBJECT_MAPPER.readValue(inputStream, collectionType);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, type: {}", type, e);
        }
    }


    /**
     * JSON反序列化（List）
     */
    public static <V> List<V> fromList(File file, Class<V> type) {
        try {
            CollectionType collectionType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, type);
            return OBJECT_MAPPER.readValue(file, collectionType);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, file path: {}, type: {}", file.getPath(), type, e);
        }
    }


    /**
     * JSON反序列化（List）
     */
    public static <V> List<V> fromList(String json, Class<V> type) {
        if (isEmpty(json)) {
            return null;
        }
        try {
            CollectionType collectionType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, type);
            return OBJECT_MAPPER.readValue(json, collectionType);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, json: {}, type: {}", json, type, e);
        }
    }

    /**
     * JSON反序列化（Map）
     */
    public static Map<String, Object> fromMap(String json) {
        if (isEmpty(json)) {
            return null;
        }
        try {
            MapType mapType = OBJECT_MAPPER.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class);
            return OBJECT_MAPPER.readValue(json, mapType);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, json: {}, type: {}", json, e);
        }
    }

    /**
     * 序列化为JSON
     */
    public static <V> void toFile(String path, List<V> list) {
        try (Writer writer = new FileWriter(new File(path), true)) {
            OBJECT_MAPPER.writer().writeValues(writer).writeAll(list);
        } catch (Exception e) {
            throw new JacksonException("jackson to file error, path: {}, list: {}", path, list, e);
        }
    }

    /**
     * 序列化为JSON
     */
    public static <V> void toFile(String path, V v) {
        try (Writer writer = new FileWriter(new File(path), true)) {
            OBJECT_MAPPER.writer().writeValues(writer).write(v);
        } catch (Exception e) {
            throw new JacksonException("jackson to file error, path: {}, data: {}", path, v, e);
        }
    }

    /**
     * 从json串中获取某个字段
     *
     * @return String，默认为 null
     */
    public static String getString(JsonNode json, String key) {
        JsonNode jsonNode = getJsonNode(json, key);
        if (null == jsonNode) {
            return null;
        }
        return castToString(jsonNode);
    }

    private static String castToString(JsonNode jsonNode) {
        return jsonNode.isTextual() ? jsonNode.textValue() : jsonNode.toString();
    }

    public static Integer getInteger(ObjectNode json, String key) {
        JsonNode jsonNode = getJsonNode(json, key);
        if (null == jsonNode) {
            return null;
        }

        return jsonNode.isInt() ? jsonNode.intValue() : TypeUtils.castToInt(castToString(jsonNode));
    }

    /**
     * 从json串中获取某个字段
     *
     * @return long，默认为 0
     */
    public static Long getLong(ObjectNode json, String key) {
        JsonNode jsonNode = getJsonNode(json, key);
        if (null == jsonNode) {
            return null;
        }

        return jsonNode.isLong() ? jsonNode.longValue() : TypeUtils.castToLong(castToString(jsonNode));
    }

    /**
     * 从json串中获取某个字段
     *
     * @return double，默认为 0.0
     */
    public static Double getDouble(ObjectNode json, String key) {
        JsonNode jsonNode = getJsonNode(json, key);
        if (null == jsonNode) {
            return null;
        }

        return jsonNode.isDouble() ? jsonNode.doubleValue() : TypeUtils.castToDouble(castToString(jsonNode));
    }

    /**
     * 从json串中获取某个字段
     *
     * @return BigInteger，默认为 0.0
     */
    public static BigInteger getBigInteger(ObjectNode json, String key) {
        JsonNode jsonNode = getJsonNode(json, key);
        if (null == jsonNode) {
            return null;
        }

        return jsonNode.isBigInteger() ? jsonNode.bigIntegerValue() : TypeUtils.castToBigInteger(castToString(jsonNode));
    }

    /**
     * 从json串中获取某个字段
     *
     * @return BigDecimal，默认为 0.00
     */
    public static BigDecimal getBigDecimal(ObjectNode json, String key) {
        JsonNode jsonNode = getJsonNode(json, key);
        if (null == jsonNode) {
            return null;
        }

        return jsonNode.isBigDecimal() ? jsonNode.decimalValue() : TypeUtils.castToBigDecimal(castToString(jsonNode));
    }

    /**
     * 从json串中获取某个字段
     *
     * @return boolean, 默认为false
     */
    public static Boolean getBoolean(ObjectNode json, String key) {
        JsonNode jsonNode = getJsonNode(json, key);
        if (null == jsonNode) {
            return null;
        }

        return jsonNode.isBoolean() ? jsonNode.booleanValue() : TypeUtils.castToBoolean(castToString(jsonNode));
    }

    /**
     * 从json串中获取某个字段
     *
     * @return byte[], 默认为 null
     */
    public static byte[] getBytes(ObjectNode json, String key) {
        try {
            JsonNode jsonNode = getJsonNode(json, key);
            if (null == jsonNode) {
                return null;
            }
            return jsonNode.isBinary() ? jsonNode.binaryValue() : castToString(jsonNode).getBytes();
        } catch (Exception e) {
            throw new JacksonException("jackson get byte error, json: {}, key: {}", json, key, e);
        }
    }

    public static Byte getByte(ObjectNode json, String key) {
        JsonNode jsonNode = getJsonNode(json, key);
        if (null == jsonNode) {
            return null;
        }

        return TypeUtils.castToByte(castToString(jsonNode));
    }

    public static Short getShort(ObjectNode json, String key) {
        JsonNode jsonNode = getJsonNode(json, key);
        if (null == jsonNode) {
            return null;
        }

        return TypeUtils.castToShort(castToString(jsonNode));
    }

    public static Float getFloat(ObjectNode json, String key) {
        JsonNode jsonNode = getJsonNode(json, key);
        if (null == jsonNode) {
            return null;
        }

        return jsonNode.isFloat() ? jsonNode.floatValue() : TypeUtils.castToFloat(castToString(jsonNode));
    }

    public static <V> V getObject(ObjectNode json, String key, Class<V> type) {
        JsonNode jsonNode = getJsonNode(json, key);
        if (null == jsonNode) {
            return null;
        }
        JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructType(type);
        return from(castToString(jsonNode), javaType);
    }


    /**
     * 从json串中获取某个字段
     *
     * @return list, 默认为 null
     *//*
    public static <V> List<V> getList(String json, String key, Class<V> type) {
        JsonNode jsonNode = getAsJsonObject(json, key);
        if (null == jsonNode) {
            return null;
        }
        CollectionType collectionType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, type);
        return from(castToString(jsonNode), collectionType);
    }*/

    /**
     * 从json串中获取某个字段
     *
     * @return JsonNode, 默认为 null
     */
    /*private static JsonNode getAsJsonObject(String json, String key) {
        try {
            JsonNode node = OBJECT_MAPPER.readTree(json);
            if (null == node) {
                return null;
            }
            return node.get(key);
        } catch (IOException e) {
            throw new JacksonException("jackson get object from json error, json: {}, key: {}", json, key, e);
        }
    }
*/
    public static JsonNode getJsonNode(JsonNode json, String key) {
        if (null == json) {
            return null;
        }
        return json.get(key);
    }

    /**
     * 向json中添加属性
     *
     * @return json
     */
    /*public static <V> String add(String json, String key, V value) {
        try {
            JsonNode node = OBJECT_MAPPER.readTree(json);
            ObjectNode add = add(node, key, value);
            return add.toString();
        } catch (IOException e) {
            throw new JacksonException("jackson add error, json: {}, key: {}, value: {}", json, key, value, e);
        }
    }*/

    /**
     * 向json中添加属性
     */
    public static <V> ObjectNode add(JsonNode jsonNode, String key, V value) {
        if (value instanceof String) {
            return ((ObjectNode) jsonNode).put(key, (String) value);
        } else if (value instanceof Short) {
            return ((ObjectNode) jsonNode).put(key, (Short) value);
        } else if (value instanceof Integer) {
            return ((ObjectNode) jsonNode).put(key, (Integer) value);
        } else if (value instanceof Long) {
            return ((ObjectNode) jsonNode).put(key, (Long) value);
        } else if (value instanceof Float) {
            return ((ObjectNode) jsonNode).put(key, (Float) value);
        } else if (value instanceof Double) {
            return ((ObjectNode) jsonNode).put(key, (Double) value);
        } else if (value instanceof BigDecimal) {
            return ((ObjectNode) jsonNode).put(key, (BigDecimal) value);
        } else if (value instanceof BigInteger) {
            return ((ObjectNode) jsonNode).put(key, (BigInteger) value);
        } else if (value instanceof Boolean) {
            return ((ObjectNode) jsonNode).put(key, (Boolean) value);
        } else if (value instanceof byte[]) {
            return ((ObjectNode) jsonNode).put(key, (byte[]) value);
        } else {
            return ((ObjectNode) jsonNode).put(key, to(value));
        }
    }

    /**
     * 除去json中的某个属性
     *
     * @return json
     */
    /*public static String remove(String json, String key) {
        try {
            JsonNode node = OBJECT_MAPPER.readTree(json);
            ((ObjectNode) node).remove(key);
            return node.toString();
        } catch (IOException e) {
            throw new JacksonException("jackson remove error, json: {}, key: {}", json, key, e);
        }
    }*/

    /**
     * 修改json中的属性
     */
    /*public static <V> String update(String json, String key, V value) {
        try {
            JsonNode node = OBJECT_MAPPER.readTree(json);
            ((ObjectNode) node).remove(key);
            add(node, key, value);
            return node.toString();
        } catch (IOException e) {
            throw new JacksonException("jackson update error, json: {}, key: {}, value: {}", json, key, value, e);
        }
    }*/

    /**
     * 格式化Json(美化)
     *
     * @return json
     */
    public static String format(String json) {
        try {
            JsonNode node = OBJECT_MAPPER.readTree(json);
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        } catch (IOException e) {
            throw new JacksonException("jackson format json error, json: {}", json, e);
        }
    }

    /**
     * 判断字符串是否是json
     *
     * @return json
     */
    public static boolean isJson(String json) {
        try {
            OBJECT_MAPPER.readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 将字符串转json
     *
     * @param json
     * @return
     */
    public static JsonNode readTree(String json) {
        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (IOException e) {
            throw new JacksonException("解析json字符串转失败:{}", json, e);
        }
    }

    public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
        if (map == null) {
            return null;
        }

        T obj = beanClass.newInstance();

        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }

            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }

        return obj;
    }

}
