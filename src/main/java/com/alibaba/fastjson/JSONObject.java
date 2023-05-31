package com.alibaba.fastjson;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.JacksonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * 临时过渡，请使用Jackson
 */
@Deprecated
public class JSONObject extends JSON implements Map<String, Object>, Cloneable, Serializable, InvocationHandler {

    private static final long serialVersionUID = 1L;

    public final ObjectNode _objectNode;

    public JSONObject() {
        this(false);
    }

    public JSONObject(JsonNode jsonNode) {
        if (jsonNode instanceof TextNode) {
            TextNode node = (TextNode) jsonNode;
            _objectNode = JacksonUtil.from(node.asText(), ObjectNode.class);
        } else {
            _objectNode = jsonNode.deepCopy();
        }
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

    @Override
    public int size() {
        return _objectNode.size();
    }

    @Override
    public boolean isEmpty() {
        return _objectNode.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return _objectNode.has(Objects.toString(key));
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object get(Object key) {
        return JacksonUtil.getObject(_objectNode, Objects.toString(key), Object.class);
    }

    @Override
    public Object getOrDefault(Object key, Object defaultValue) {
        Object v;
        return ((v = get(key)) != null) ? v : defaultValue;
    }

    public JSONObject getJSONObject(String key) {
        JsonNode jn = _objectNode.findValue(key);

        if (jn == null) {
            return null;
        }
        return new JSONObject(jn);
    }

    public JSONArray getJSONArray(String key) {
        JsonNode jn = _objectNode.findValue(key);
        if (jn == null) {
            return null;
        }

        return new JSONArray(jn);
    }

    /*private static JSONArray jsonNodeToJSONArray(JsonNode jn) {
        ArrayNode jsonNodes = jn.deepCopy();
        return new JSONArray(jsonNodes);
    }

    private static JSONObject jsonNodeToJSONObject(JsonNode jn) {
        Iterator<Entry<String, JsonNode>> iterator = jn.fields();
        Map<String, JsonNode> kids = new HashMap<>();
        for (Iterator<Entry<String, JsonNode>> it = iterator; it.hasNext(); ) {
            Entry<String, JsonNode> entry = it.next();
            kids.put(entry.getKey(), entry.getValue().deepCopy());
        }
        return new JSONObject(JsonNodeFactory.instance, kids);
    }*/

    public <T> T getObject(String key, Class<T> clazz) {
        return JacksonUtil.getObject(_objectNode, key, clazz);
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
        return JacksonUtil.getBoolean(_objectNode, key);
    }

    public boolean getBooleanValue(String key) {
        Boolean value = getBoolean(key);

        if (value == null) {
            return false;
        }

        return value;
    }

    public byte[] getBytes(String key) {
        return JacksonUtil.getBytes(_objectNode, key);
    }


    public Byte getByte(String key) {
        return JacksonUtil.getByte(_objectNode, key);
    }

    public byte getByteValue(String key) {
        Byte value = getByte(key);

        return value == null ? 0 : value;
    }

    public Short getShort(String key) {
        return JacksonUtil.getShort(_objectNode, key);
    }

    public short getShortValue(String key) {
        Short value = getShort(key);

        return value == null ? 0 : value;
    }

    public Integer getInteger(String key) {
        return JacksonUtil.getInteger(_objectNode, key);
    }

    public int getIntValue(String key) {
        Integer value = getInteger(key);
        return value == null ? 0 : value;
    }

    public Long getLong(String key) {
        return JacksonUtil.getLong(_objectNode, key);
    }

    public long getLongValue(String key) {
        Long value = getLong(key);
        return value == null ? 0 : value;
    }

    public Float getFloat(String key) {
        return JacksonUtil.getFloat(_objectNode, key);
    }

    public float getFloatValue(String key) {
        Float floatValue = getFloat(key);
        return floatValue == null ? 0.0F : floatValue;
    }

    public Double getDouble(String key) {
        return JacksonUtil.getDouble(_objectNode, key);
    }

    public double getDoubleValue(String key) {
        Double value = getDouble(key);
        return value == null ? 0.0 : value;
    }

    public BigDecimal getBigDecimal(String key) {
        return JacksonUtil.getBigDecimal(_objectNode, key);
    }

    public BigInteger getBigInteger(String key) {
        return JacksonUtil.getBigInteger(_objectNode, key);
    }

    public String getString(String key) {
        return JacksonUtil.getString(_objectNode, key);
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

    @Override
    public Object put(String key, Object value) {
        return JacksonUtil.add(_objectNode, key, value);
    }

    public JSONObject fluentPut(String key, Object value) {
        this.put(key, value);
        return this;
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        for (Map.Entry<? extends String, ?> entry : m.entrySet()) {
            JacksonUtil.add(_objectNode, entry.getKey(), entry.getValue());
        }
    }

    public JSONObject fluentPutAll(Map<? extends String, ?> m) {
        this.putAll(m);
        return this;
    }

    @Override
    public void clear() {
        _objectNode.removeAll();
    }

    public JSONObject fluentClear() {
        this.clear();
        return this;
    }

    @Override
    public Object remove(Object key) {
        return _objectNode.remove(Objects.toString(key));
    }

    public JSONObject fluentRemove(Object key) {
        this.remove(key);
        return this;
    }

    @Override
    public Set<String> keySet() {
        return thisToMap().keySet();
    }

    @Override
    public Collection<Object> values() {
        return thisToMap().values();
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return thisToMap().entrySet();
    }

    private Map<String, Object> thisToMap() {
        Map<String, Object> map = new HashMap<>(_objectNode.size());

        Iterator<Map.Entry<String, JsonNode>> fields = _objectNode.fields();
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> next = fields.next();
            JsonNode value = next.getValue();
            if (value instanceof ObjectNode) {
                map.put(next.getKey(), new JSONObject(value));
            } else if (value instanceof ArrayNode) {
                map.put(next.getKey(), new JSONArray((ArrayNode) value));
            } else {
                map.put(next.getKey(), value);
            }
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

    @Override
    public <T> T toJavaObject(Class<T> clazz) {
        return JacksonUtil.from(_objectNode, clazz);
    }

    public <T> T toJavaObject(Class<T> clazz, ParserConfig config, int features) {
        // TODO: zhangzhliang@yonyou.com 2023/5/29 解析配置
        return JacksonUtil.from(_objectNode, clazz);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
    }
}
