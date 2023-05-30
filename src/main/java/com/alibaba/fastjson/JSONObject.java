package com.alibaba.fastjson;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.JacksonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * 临时过渡，请使用Jackson
 */
@Deprecated
public class JSONObject extends JSON {

    private static final long serialVersionUID = 1L;

    public final ObjectNode _objectNode;

    public JSONObject() {
        this(false);
    }


    public JSONObject(Map<String, JsonNode> map) {
        if (map == null) {
            throw new IllegalArgumentException("map is null.");
        }
        _objectNode = new ObjectNode(JsonNodeFactory.instance, map);
    }


    public JSONObject(boolean ordered) {
        _objectNode = new ObjectNode(JsonNodeFactory.instance);
    }


    public JSONObject(int initialCapacity) {
        this(initialCapacity, false);
    }

    public JSONObject(int initialCapacity, boolean ordered) {
        _objectNode = new ObjectNode(JsonNodeFactory.instance);
    }

    public JSONObject(JsonNodeFactory jsonNodeFactory, Map<String, JsonNode> kids) {
        _objectNode = new ObjectNode(jsonNodeFactory, kids);
    }

    public int size() {
        return _objectNode.size();
    }

    public boolean isEmpty() {
        return _objectNode.isEmpty();
    }

    public boolean containsKey(Object key) {
        return _objectNode.has(Objects.toString(key));
    }

    public Object get(Object key) {
        return JacksonUtil.getAsObject(_objectNode, Objects.toString(key), Object.class);
    }

    public Object getOrDefault(Object key, Object defaultValue) {
        Object v;
        return ((v = get(key)) != null) ? v : defaultValue;
    }

    public JSONObject getJSONObject(String key) {
        JsonNode jn = _objectNode.findValue(key);
        Iterator<Map.Entry<String, JsonNode>> iterator = jn.fields();
        Map<String, JsonNode> kids = new HashMap<>();
        for (Iterator<Map.Entry<String, JsonNode>> it = iterator; it.hasNext(); ) {
            Map.Entry<String, JsonNode> entry = it.next();
            kids.put(entry.getKey(), entry.getValue().deepCopy());
        }
        return new JSONObject(JsonNodeFactory.instance, kids);
    }

    public JSONArray getJSONArray(String key) {
        JsonNode jn = _objectNode.findValue(key);
        List<JsonNode> children = new ArrayList<>();
        children.add(jn);
        return new JSONArray(JsonNodeFactory.instance, children);
    }

    public <T> T getObject(String key, Class<T> clazz) {
        return JacksonUtil.getAsObject(_objectNode, key, clazz);
    }

  /*  public <T> T getObject(String key, Type type) {
        return JacksonUtil.getAsObject(_objectNode, key, type);
    }

    public <T> T getObject(String key, TypeReference typeReference) {
        JsonNode obj = _objectNode.get(key);
        if (typeReference == null) {
            return (T) obj;
        }
        return TypeUtils.cast(obj, typeReference.getType(), ParserConfig.getGlobalInstance());
    }*/

    public Boolean getBoolean(String key) {
        JsonNode value = _objectNode.get(key);

        if (value == null) {
            return null;
        }

        return value.asBoolean();
    }

    public byte[] getBytes(String key) {
        JsonNode value = _objectNode.get(key);

        if (value == null) {
            return null;
        }

        try {
            return value.binaryValue();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean getBooleanValue(String key) {
        Boolean booleanVal = getBoolean(key);

        if (booleanVal == null) {
            return false;
        }

        return booleanVal.booleanValue();
    }

    public Byte getByte(String key) {
        byte[] bytes = getBytes(key);
        if (bytes != null && bytes.length > 0) {
            return bytes[0];
        }

        return null;
    }

    public byte getByteValue(String key) {
        Byte byteVal = getByte(key);
        if (byteVal == null) {
            return 0;
        }

        return byteVal.byteValue();
    }

    public Short getShort(String key) {
        Integer integer = getInteger(key);
        if (integer == null) {
            return null;
        }

        return integer.shortValue();
    }

    public short getShortValue(String key) {
        Short shortVal = getShort(key);
        if (shortVal == null) {
            return 0;
        }

        return shortVal.shortValue();
    }

    public Integer getInteger(String key) {
        JsonNode value = _objectNode.get(key);
        if (value == null) {
            return null;
        }

        if (value.isInt()) {
            return value.intValue();
        }

        if (value.canConvertToInt()) {
            return value.asInt();
        }

        return null;
    }

    public int getIntValue(String key) {
        return JacksonUtil.getAsInt(_objectNode, key);
    }

    public Long getLong(String key) {
        JsonNode value = _objectNode.get(key);
        if (value == null) {
            return null;
        }

        if (value.isLong()) {
            return value.longValue();
        }

        if (value.canConvertToLong()) {
            return value.asLong();
        }

        return null;
    }

    public long getLongValue(String key) {
        Long longVal = getLong(key);

        if (longVal == null) {
            return 0;
        }

        return longVal.intValue();
    }

    public Float getFloat(String key) {
        JsonNode value = _objectNode.get(key);
        if (value == null) {
            return null;
        }

        if (value.isFloat()) {
            return value.floatValue();
        }

        return null;
    }

    public float getFloatValue(String key) {
        Float floatValue = getFloat(key);

        if (floatValue == null) {
            return 0F;
        }

        return floatValue;
    }

    public Double getDouble(String key) {
        JsonNode value = _objectNode.get(key);
        if (value == null) {
            return null;
        }

        if (value.isDouble()) {
            return value.doubleValue();
        }

        return null;
    }

    public double getDoubleValue(String key) {
        Double doubleValue = getDouble(key);

        if (doubleValue == null) {
            return 0D;
        }

        return doubleValue.doubleValue();
    }

    public BigDecimal getBigDecimal(String key) {
        JsonNode value = _objectNode.get(key);

        if (value == null) {
            return null;
        }

        if (value.isBigDecimal()) {
            return value.decimalValue();
        }

        return null;
    }

    public BigInteger getBigInteger(String key) {
        JsonNode value = _objectNode.get(key);

        if (value == null) {
            return null;
        }

        if (value.isBigInteger()) {
            return value.bigIntegerValue();
        }

        return null;
    }


    public String getString(String key) {
        JsonNode value = _objectNode.get(key);

        if (value == null) {
            return null;
        }

        return value.toString();
    }
/*

    public Date getDate(String key) {
        JsonNode value = _objectNode.get(key);

        if (value == null) {
            return null;
        }

        return castToDate(value);
    }

    public Object getSqlDate(String key) {
        JsonNode value = _objectNode.get(key);

        if (value == null) {
            return null;
        }

        return castToSqlDate(value);
    }

    public Object getTimestamp(String key) {
        Object value = get(key);

        return castToTimestamp(value);
    }*/

    public Object put(String key, Object value) {
        return JacksonUtil.add(_objectNode, key, value);
    }

    public JSONObject fluentPut(String key, Object value) {
        this.put(key, value);
        return this;
    }

    public void putAll(Map<? extends String, ?> m) {
        for (Map.Entry<? extends String, ?> entry : m.entrySet()) {
            JacksonUtil.add(_objectNode, entry.getKey(), entry.getValue());
        }
    }

    public JSONObject fluentPutAll(Map<? extends String, ?> m) {
        this.putAll(m);
        return this;
    }

    public void clear() {
        _objectNode.removeAll();
    }

    public JSONObject fluentClear() {
        this.clear();
        return this;
    }

    public Object remove(Object key) {
        return _objectNode.remove(Objects.toString(key));
    }

    public JSONObject fluentRemove(Object key) {
        this.remove(key);
        return this;
    }

    public Set<String> keySet() {
        return thisToMap().keySet();
    }

    public Collection<Object> values() {
        return thisToMap().values();
    }

    public Set<Map.Entry<String, Object>> entrySet() {
        return thisToMap().entrySet();
    }

    private Map<String, Object> thisToMap() {
        Map<String, Object> map = new HashMap<>(_objectNode.size());

        Iterator<Map.Entry<String, JsonNode>> fields = _objectNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> next = fields.next();
            map.put(next.getKey(), next.getValue());
        }
        return map;
    }


    /**
     * @Override public JSONObject clone() {
     * ObjectNode jsonNodes = _objectNode.deepCopy();
     * return new JSONObject(map instanceof LinkedHashMap //
     * ? new LinkedHashMap<String, Object>(map) //
     * : new HashMap<String, Object>(map)
     * );
     * }
     */

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof JSONObject) {
            return this._objectNode.equals(((JSONObject) obj)._objectNode);
        }

        return this._objectNode.equals(obj);
    }

    @Override
    public int hashCode() {
        return this._objectNode.hashCode();
    }
/*

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 1) {
            if (method.getName().equals("equals")) {
                return this.equals(args[0]);
            }

            Class<?> returnType = method.getReturnType();
            if (returnType != void.class) {
                throw new JSONException("illegal setter");
            }

            String name = null;
            JSONField annotation = TypeUtils.getAnnotation(method, JSONField.class);
            if (annotation != null) {
                if (annotation.name().length() != 0) {
                    name = annotation.name();
                }
            }

            if (name == null) {
                name = method.getName();

                if (!name.startsWith("set")) {
                    throw new JSONException("illegal setter");
                }

                name = name.substring(3);
                if (name.length() == 0) {
                    throw new JSONException("illegal setter");
                }
                name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
            }

            map.put(name, args[0]);
            return null;
        }

        if (parameterTypes.length == 0) {
            Class<?> returnType = method.getReturnType();
            if (returnType == void.class) {
                throw new JSONException("illegal getter");
            }

            String name = null;
            JSONField annotation = TypeUtils.getAnnotation(method, JSONField.class);
            if (annotation != null) {
                if (annotation.name().length() != 0) {
                    name = annotation.name();
                }
            }

            if (name == null) {
                name = method.getName();
                if (name.startsWith("get")) {
                    name = name.substring(3);
                    if (name.length() == 0) {
                        throw new JSONException("illegal getter");
                    }
                    name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
                } else if (name.startsWith("is")) {
                    name = name.substring(2);
                    if (name.length() == 0) {
                        throw new JSONException("illegal getter");
                    }
                    name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
                } else if (name.startsWith("hashCode")) {
                    return this.hashCode();
                } else if (name.startsWith("toString")) {
                    return this.toString();
                } else {
                    throw new JSONException("illegal getter");
                }
            }

            Object value = map.get(name);
            return TypeUtils.cast(value, method.getGenericReturnType(), ParserConfig.getGlobalInstance());
        }

        throw new UnsupportedOperationException(method.toGenericString());
    }

    public Map<String, Object> getInnerMap() {
        return this.map;
    }
*/

    public <T> T toJavaObject(Class<T> clazz) {
        return JacksonUtil.from(_objectNode, clazz);
    }

    public <T> T toJavaObject(Class<T> clazz, ParserConfig config, int features) {
        // TODO: zhangzhliang@yonyou.com 2023/5/29 解析配置
        return JacksonUtil.from(_objectNode, clazz);

    }
}
