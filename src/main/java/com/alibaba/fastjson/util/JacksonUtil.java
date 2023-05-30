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
 */
public class JacksonUtil {
    //    private static final Logger log = LoggerFactory.getLogger(JacksonUtil.class);
    private static ObjectMapper mapper;
    private static final String TRUE = "true";

    private static volatile Set<JsonReadFeature> JSON_READ_FEATURES_ENABLED;

    static {
        try {
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
            //初始化
            mapper = initMapper();
        } catch (Exception e) {
            throw new JacksonException("jackson config error", e);
        }
    }

    public static ObjectMapper initMapper() {
        JsonMapper.Builder builder = JsonMapper.builder().enable(JSON_READ_FEATURES_ENABLED.toArray(new JsonReadFeature[0]));
        return initMapperConfig(builder.build());
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
        return mapper;
    }


    public static ObjectNode createObjectNode() {
        return mapper.createObjectNode();
    }

    /**
     * Java对象转字符串
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new JacksonException("转json字符串失败:{}", obj, e);
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
            JavaType javaType = mapper.getTypeFactory().constructType(type);
            return mapper.readValue(json, javaType);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, json: {}, type: {}", json, type, e);
        }
    }

    public static <V> V from(ObjectNode objectNode, Class<V> type) {
        if (null == objectNode) {
            return null;
        }
        try {
            return mapper.treeToValue(objectNode, type);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, json: {}, type: {}", objectNode, type, e);
        }
    }

    public static <V> V from(ObjectNode objectNode, Type type) {
        if (null == objectNode) {
            return null;
        }
        try {
            JavaType javaType = mapper.getTypeFactory().constructType(type);
            // TODO: zhangzhliang@yonyou.com 2023/5/29
            return null;
        } catch (Exception e) {
            throw new JacksonException("jackson from error, json: {}, type: {}", objectNode, type, e);
        }
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(URL url, Class<V> type) {
        try {
            return mapper.readValue(url, type);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, url: {}, type: {}", url.getPath(), type, e);
        }
    }


    /**
     * JSON反序列化
     */
    public static <V> V from(File file, Class<V> type) {
        try {
            return mapper.readValue(file, type);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, file path: {}, type: {}", file.getPath(), type, e);
        }
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(InputStream inputStream, Class<V> type) {
        try {
            return mapper.readValue(inputStream, type);
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
            return mapper.readValue(url, type);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, url: {}, type: {}", url.getPath(), type, e);
        }
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(File file, TypeReference<V> type) {
        try {
            return mapper.readValue(file, type);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, file path: {}, type: {}", file.getPath(), type, e);
        }
    }

    /**
     * JSON反序列化
     */
    public static <V> V from(InputStream inputStream, TypeReference<V> type) {
        try {
            return mapper.readValue(inputStream, type);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, type: {}", type, e);
        }
    }


    /**
     * JSON反序列化（List）
     */
    public static <V> List<V> fromList(URL url, Class<V> type) {
        try {
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, type);
            return mapper.readValue(url, collectionType);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, url: {}, type: {}", url.getPath(), type, e);
        }
    }


    /**
     * JSON反序列化（List）
     */
    public static <V> List<V> fromList(InputStream inputStream, Class<V> type) {
        try {
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, type);
            return mapper.readValue(inputStream, collectionType);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, type: {}", type, e);
        }
    }


    /**
     * JSON反序列化（List）
     */
    public static <V> List<V> fromList(File file, Class<V> type) {
        try {
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, type);
            return mapper.readValue(file, collectionType);
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
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, type);
            return mapper.readValue(json, collectionType);
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
            MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class);
            return mapper.readValue(json, mapType);
        } catch (IOException e) {
            throw new JacksonException("jackson from error, json: {}, type: {}", json, e);
        }
    }

    /**
     * 序列化为JSON
     */
    public static <V> String to(List<V> list) {
        try {
            return mapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            throw new JacksonException("jackson to error, data: {}", list, e);
        }
    }

    /**
     * 序列化为JSON
     */
    public static <V> String to(V v) {
        try {
            return mapper.writeValueAsString(v);
        } catch (JsonProcessingException e) {
            throw new JacksonException("jackson to error, data: {}", v, e);
        }
    }

    /**
     * 序列化为JSON
     */
    public static <V> void toFile(String path, List<V> list) {
        try (Writer writer = new FileWriter(new File(path), true)) {
            mapper.writer().writeValues(writer).writeAll(list);
        } catch (Exception e) {
            throw new JacksonException("jackson to file error, path: {}, list: {}", path, list, e);
        }
    }

    /**
     * 序列化为JSON
     */
    public static <V> void toFile(String path, V v) {
        try (Writer writer = new FileWriter(new File(path), true)) {
            mapper.writer().writeValues(writer).write(v);
        } catch (Exception e) {
            throw new JacksonException("jackson to file error, path: {}, data: {}", path, v, e);
        }
    }

    /**
     * 从json串中获取某个字段
     *
     * @return String，默认为 null
     */
    public static String getAsString(String json, String key) {
        if (isEmpty(json)) {
            return null;
        }
        try {
            JsonNode jsonNode = getAsJsonObject(json, key);
            if (null == jsonNode) {
                return null;
            }
            return getAsString(jsonNode);
        } catch (Exception e) {
            throw new JacksonException("jackson get string error, json: {}, key: {}", json, key, e);
        }
    }

    private static String getAsString(JsonNode jsonNode) {
        return jsonNode.isTextual() ? jsonNode.textValue() : jsonNode.toString();
    }

    /**
     * 从json串中获取某个字段
     *
     * @return int，默认为 0
     */
    public static int getAsInt(String json, String key) {
        if (isEmpty(json)) {
            return 0;
        }
        try {
            JsonNode jsonNode = getAsJsonObject(json, key);
            if (null == jsonNode) {
                return 0;
            }
            return jsonNode.isInt() ? jsonNode.intValue() : Integer.parseInt(getAsString(jsonNode));
        } catch (Exception e) {
            throw new JacksonException("jackson get int error, json: {}, key: {}", json, key, e);
        }
    }

    public static int getAsInt(ObjectNode json, String key) {
        JsonNode jsonNode = getAsJsonObject(json, key);
        if (null == jsonNode) {
            return 0;
        }
        return jsonNode.isInt() ? jsonNode.intValue() : Integer.parseInt(getAsString(jsonNode));
    }

    public static Integer getAsInteger(ObjectNode json, String key) {
        JsonNode jsonNode = getAsJsonObject(json, key);
        if (null == jsonNode || !jsonNode.isInt()) {
            return null;
        }
        return jsonNode.intValue();
    }


    /**
     * 从json串中获取某个字段
     *
     * @return long，默认为 0
     */
    public static long getAsLong(String json, String key) {
        if (isEmpty(json)) {
            return 0L;
        }
        try {
            JsonNode jsonNode = getAsJsonObject(json, key);
            if (null == jsonNode) {
                return 0L;
            }
            return jsonNode.isLong() ? jsonNode.longValue() : Long.parseLong(getAsString(jsonNode));
        } catch (Exception e) {
            throw new JacksonException("jackson get long error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * 从json串中获取某个字段
     *
     * @return double，默认为 0.0
     */
    public static double getAsDouble(String json, String key) {
        if (isEmpty(json)) {
            return 0.0;
        }
        try {
            JsonNode jsonNode = getAsJsonObject(json, key);
            if (null == jsonNode) {
                return 0.0;
            }
            return jsonNode.isDouble() ? jsonNode.doubleValue() : Double.parseDouble(getAsString(jsonNode));
        } catch (Exception e) {
            throw new JacksonException("jackson get double error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * 从json串中获取某个字段
     *
     * @return BigInteger，默认为 0.0
     */
    public static BigInteger getAsBigInteger(String json, String key) {
        if (isEmpty(json)) {
            return new BigInteger(String.valueOf(0.00));
        }
        try {
            JsonNode jsonNode = getAsJsonObject(json, key);
            if (null == jsonNode) {
                return new BigInteger(String.valueOf(0.00));
            }
            return jsonNode.isBigInteger() ? jsonNode.bigIntegerValue() : new BigInteger(getAsString(jsonNode));
        } catch (Exception e) {
            throw new JacksonException("jackson get big integer error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * 从json串中获取某个字段
     *
     * @return BigDecimal，默认为 0.00
     */
    public static BigDecimal getAsBigDecimal(String json, String key) {
        if (isEmpty(json)) {
            return new BigDecimal("0.00");
        }
        try {
            JsonNode jsonNode = getAsJsonObject(json, key);
            if (null == jsonNode) {
                return new BigDecimal("0.00");
            }
            return jsonNode.isBigDecimal() ? jsonNode.decimalValue() : new BigDecimal(getAsString(jsonNode));
        } catch (Exception e) {
            throw new JacksonException("jackson get big decimal error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * 从json串中获取某个字段
     *
     * @return boolean, 默认为false
     */
    public static boolean getAsBoolean(String json, String key) {
        if (isEmpty(json)) {
            return false;
        }
        try {
            JsonNode jsonNode = getAsJsonObject(json, key);
            if (null == jsonNode) {
                return false;
            }
            if (jsonNode.isBoolean()) {
                return jsonNode.booleanValue();
            } else {
                if (jsonNode.isTextual()) {
                    String textValue = jsonNode.textValue();
                    if ("1".equals(textValue)) {
                        return true;
                    } else {
                        return toBoolean(textValue);
                    }
                } else {//number
                    return toBoolean(jsonNode.intValue());
                }
            }
        } catch (Exception e) {
            throw new JacksonException("jackson get boolean error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * 从json串中获取某个字段
     *
     * @return byte[], 默认为 null
     */
    public static byte[] getAsBytes(String json, String key) {
        if (isEmpty(json)) {
            return null;
        }
        try {
            JsonNode jsonNode = getAsJsonObject(json, key);
            if (null == jsonNode) {
                return null;
            }
            return jsonNode.isBinary() ? jsonNode.binaryValue() : getAsString(jsonNode).getBytes();
        } catch (Exception e) {
            throw new JacksonException("jackson get byte error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * 从json串中获取某个字段
     *
     * @return object, 默认为 null
     */
    public static <V> V getAsObject(String json, String key, Class<V> type) {
        if (isEmpty(json)) {
            return null;
        }
        try {
            JsonNode jsonNode = getAsJsonObject(json, key);
            if (null == jsonNode) {
                return null;
            }
            JavaType javaType = mapper.getTypeFactory().constructType(type);
            return from(getAsString(jsonNode), javaType);
        } catch (Exception e) {
            throw new JacksonException("jackson get list error, json: {}, key: {}, type: {}", json, key, type, e);
        }
    }

    public static <V> V getAsObject(ObjectNode json, String key, Class<V> type) {
        JsonNode jsonNode = getAsJsonObject(json, key);
        if (null == jsonNode) {
            return null;
        }
        JavaType javaType = mapper.getTypeFactory().constructType(type);
        return from(getAsString(jsonNode), javaType);
    }


    /**
     * 从json串中获取某个字段
     *
     * @return list, 默认为 null
     */
    public static <V> List<V> getAsList(String json, String key, Class<V> type) {
        if (isEmpty(json)) {
            return null;
        }
        try {
            JsonNode jsonNode = getAsJsonObject(json, key);
            if (null == jsonNode) {
                return null;
            }
            CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, type);
            return from(getAsString(jsonNode), collectionType);
        } catch (Exception e) {
            throw new JacksonException("jackson get list error, json: {}, key: {}, type: {}", json, key, type, e);
        }
    }

    /**
     * 从json串中获取某个字段
     *
     * @return JsonNode, 默认为 null
     */
    public static JsonNode getAsJsonObject(String json, String key) {
        try {
            JsonNode node = mapper.readTree(json);
            if (null == node) {
                return null;
            }
            return node.get(key);
        } catch (IOException e) {
            throw new JacksonException("jackson get object from json error, json: {}, key: {}", json, key, e);
        }
    }

    public static JsonNode getAsJsonObject(ObjectNode json, String key) {
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
    public static <V> String add(String json, String key, V value) {
        try {
            JsonNode node = mapper.readTree(json);
            ObjectNode add = add(node, key, value);
            return add.toString();
        } catch (IOException e) {
            throw new JacksonException("jackson add error, json: {}, key: {}, value: {}", json, key, value, e);
        }
    }

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
    public static String remove(String json, String key) {
        try {
            JsonNode node = mapper.readTree(json);
            ((ObjectNode) node).remove(key);
            return node.toString();
        } catch (IOException e) {
            throw new JacksonException("jackson remove error, json: {}, key: {}", json, key, e);
        }
    }

    /**
     * 修改json中的属性
     */
    public static <V> String update(String json, String key, V value) {
        try {
            JsonNode node = mapper.readTree(json);
            ((ObjectNode) node).remove(key);
            add(node, key, value);
            return node.toString();
        } catch (IOException e) {
            throw new JacksonException("jackson update error, json: {}, key: {}, value: {}", json, key, value, e);
        }
    }

    /**
     * 格式化Json(美化)
     *
     * @return json
     */
    public static String format(String json) {
        try {
            JsonNode node = mapper.readTree(json);
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
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
            mapper.readTree(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    private static boolean toBoolean(final String str) {
        return toBooleanObject(str).equals(Boolean.TRUE);
    }

    private static Boolean toBooleanObject(final String str) {
        // Previously used equalsIgnoreCase, which was fast for interned 'true'.
        // Non interned 'true' matched 15 times slower.
        //
        // Optimisation provides same performance as before for interned 'true'.
        // Similar performance for null, 'false', and other strings not length 2/3/4.
        // 'true'/'TRUE' match 4 times slower, 'tRUE'/'True' 7 times slower.
        if (str == TRUE) {
            return Boolean.TRUE;
        }
        if (str == null) {
            return null;
        }
        switch (str.length()) {
            case 1: {
                final char ch0 = str.charAt(0);
                if (ch0 == 'y' || ch0 == 'Y' || ch0 == 't' || ch0 == 'T' || ch0 == '1') {
                    return Boolean.TRUE;
                }
                if (ch0 == 'n' || ch0 == 'N' || ch0 == 'f' || ch0 == 'F' || ch0 == '0') {
                    return Boolean.FALSE;
                }
                break;
            }
            case 2: {
                final char ch0 = str.charAt(0);
                final char ch1 = str.charAt(1);
                if ((ch0 == 'o' || ch0 == 'O') && (ch1 == 'n' || ch1 == 'N')) {
                    return Boolean.TRUE;
                }
                if ((ch0 == 'n' || ch0 == 'N') && (ch1 == 'o' || ch1 == 'O')) {
                    return Boolean.FALSE;
                }
                break;
            }
            case 3: {
                final char ch0 = str.charAt(0);
                final char ch1 = str.charAt(1);
                final char ch2 = str.charAt(2);
                if ((ch0 == 'y' || ch0 == 'Y') && (ch1 == 'e' || ch1 == 'E') && (ch2 == 's' || ch2 == 'S')) {
                    return Boolean.TRUE;
                }
                if ((ch0 == 'o' || ch0 == 'O') && (ch1 == 'f' || ch1 == 'F') && (ch2 == 'f' || ch2 == 'F')) {
                    return Boolean.FALSE;
                }
                break;
            }
            case 4: {
                final char ch0 = str.charAt(0);
                final char ch1 = str.charAt(1);
                final char ch2 = str.charAt(2);
                final char ch3 = str.charAt(3);
                if ((ch0 == 't' || ch0 == 'T') && (ch1 == 'r' || ch1 == 'R') && (ch2 == 'u' || ch2 == 'U') && (ch3 == 'e' || ch3 == 'E')) {
                    return Boolean.TRUE;
                }
                break;
            }
            case 5: {
                final char ch0 = str.charAt(0);
                final char ch1 = str.charAt(1);
                final char ch2 = str.charAt(2);
                final char ch3 = str.charAt(3);
                final char ch4 = str.charAt(4);
                if ((ch0 == 'f' || ch0 == 'F') && (ch1 == 'a' || ch1 == 'A') && (ch2 == 'l' || ch2 == 'L') && (ch3 == 's' || ch3 == 'S') && (ch4 == 'e' || ch4 == 'E')) {
                    return Boolean.FALSE;
                }
                break;
            }
            default:
                break;
        }

        return null;
    }

    private static boolean toBoolean(final int value) {
        return value != 0;
    }

    /**
     * 将字符串转json
     *
     * @param json
     * @return
     */
    public static JsonNode readTree(String json) {
        try {
            return mapper.readTree(json);
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
