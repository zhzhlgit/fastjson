package com.alibaba.fastjson;

import com.alibaba.fastjson.exception.JacksonException;
import com.alibaba.fastjson.util.JacksonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * 请使用Jackson
 */
@Deprecated
public class JSONArray extends JSON implements List<Object>, Cloneable, RandomAccess, Serializable {
    private final ArrayNode _arrayNode;
    private static final Pattern NUMBER_WITH_TRAILING_ZEROS_PATTERN = Pattern.compile("\\.0*$");
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_ZERO = "0";
    protected transient Object relatedArray;
    protected transient Type componentType;

    public JSONArray() {
        _arrayNode = new ArrayNode(JsonNodeFactory.instance);
    }

    public JSONArray(List<Object> list) {
        if (list == null) {
            throw new IllegalArgumentException("list is null.");
        }

        List<JsonNode> collect = list.stream().map(x -> {
            JsonNode jsonNode = JacksonUtil.getObjectMapper().valueToTree(x);
            return jsonNode;
        }).collect(Collectors.toList());
        _arrayNode = new ArrayNode(JsonNodeFactory.instance, collect);
    }

    public JSONArray(int initialCapacity) {
        _arrayNode = new ArrayNode(JsonNodeFactory.instance, initialCapacity);
    }

    public JSONArray(JsonNode arrayNode) {
        _arrayNode = arrayNode.deepCopy();
    }

    /**
     * @return
     * @since 1.1.16
     */
    public Object getRelatedArray() {
        return relatedArray;
    }

    public void setRelatedArray(Object relatedArray) {
        this.relatedArray = relatedArray;
    }

    public Type getComponentType() {
        return componentType;
    }

    public void setComponentType(Type componentType) {
        this.componentType = componentType;
    }

    @Override
    public int size() {
        return _arrayNode.size();
    }

    @Override
    public boolean isEmpty() {
        return _arrayNode.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return _arrayNode.has(Objects.toString(o));
    }

    @Override
    public Iterator<Object> iterator() {
        return thisToList().iterator();
    }

    private List<Object> thisToList() {
        List<Object> list = new ArrayList<>(_arrayNode.size());
        Iterator<JsonNode> iterator = _arrayNode.iterator();
        while (iterator.hasNext()) {
            list.add(JacksonUtil.jsonNodeToJSONObject(iterator.next()));
        }
        return list;
    }

    @Override
    public Object[] toArray() {
        return thisToList().toArray();
    }


    @Override
    public <T> T[] toArray(T[] a) {
        return thisToList().toArray(a);
    }

    @Override
    public boolean add(Object o) {
        try {
            _arrayNode.add(JacksonUtil.convert(o, ObjectNode.class));
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public JSONArray fluentAdd(Object e) {
        this.add(e);
        return this;
    }

    @Override
    public boolean remove(Object o) {
        Iterator<JsonNode> iterator = _arrayNode.iterator();
        while (iterator.hasNext()) {
            JsonNode next = iterator.next();
            boolean findFlag = o == null ?
                    (next == null || next.isNull())
                    : o.equals(next);
            if (findFlag) {
                iterator.remove();
                return true;
            }
        }

        return false;
    }

    public JSONArray fluentRemove(Object o) {
        this.remove(o);
        return this;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO: zhangzhliang@yonyou.com 2023/5/31
        throw new RuntimeException("");
    }

    @Override
    public boolean addAll(Collection<?> c) {
        List<JsonNode> collect = c.stream().map(x -> JacksonUtil.convert(x, JsonNode.class)).collect(Collectors.toList());
        _arrayNode.addAll(collect);
        return true;
    }

    public JSONArray fluentAddAll(Collection<?> c) {
        this.addAll(c);
        return this;
    }

    @Override
    public boolean addAll(int index, Collection<?> c) {
        throw new RuntimeException("");
    }

    public JSONArray fluentAddAll(int index, Collection<?> c) {
        throw new RuntimeException("");
    }

    @Override
    public boolean removeAll(Collection<?> c) {

// TODO: zhangzhliang@yonyou.com 2023/5/31
        throw new RuntimeException("");
    }

    public JSONArray fluentRemoveAll(Collection<?> c) {
        this.removeAll(c);
        return this;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
// TODO: zhangzhliang@yonyou.com 2023/5/31
        throw new RuntimeException("");
    }

    @Override
    public void clear() {
        _arrayNode.removeAll();
    }

    public JSONArray fluentClear() {
        this.clear();
        return this;
    }

    @Override
    public Object set(int index, Object element) {
        JsonNode jsonNode = _arrayNode.set(index, JacksonUtil.convert(element, ObjectNode.class));
        if (null == jsonNode) {
            return null;
        }

        return JacksonUtil.convert(jsonNode, element.getClass());
    }

    public JSONArray fluentSet(int index, Object element) {
        this.set(index, element);
        return this;
    }

    @Override
    public void add(int index, Object element) {
        _arrayNode.insert(index, JacksonUtil.convert(element, ObjectNode.class));
    }

    public JSONArray fluentAdd(int index, Object element) {
        this.add(index, element);
        return this;
    }

    @Override
    public Object remove(int index) {
        JsonNode remove = _arrayNode.remove(index);
        return remove.deepCopy();
    }

    public JSONArray fluentRemove(int index) {
        this.remove(index);
        return this;
    }

    @Override
    public int indexOf(Object o) {
        throw new RuntimeException("");
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new RuntimeException("");
    }

    @Override
    public ListIterator<Object> listIterator() {
        List<Object> list = thisToList();
        return list.listIterator();
    }


    @Override
    public ListIterator<Object> listIterator(int index) {
        throw new RuntimeException("");
    }


    @Override
    public List<Object> subList(int fromIndex, int toIndex) {
        throw new RuntimeException("");
    }


    public JSONObject getJSONObject(int index) {
        JsonNode jsonNode = _arrayNode.get(index);
        if (jsonNode == null) {
            return null;
        }
        return new JSONObject(jsonNode);
    }

    public JSONArray getJSONArray(int index) {
        JsonNode jsonNode = get(index);
        if (jsonNode == null) {
            return null;
        }
        return new JSONArray(jsonNode);
    }

    public <T> T getObject(int index, Class<T> clazz) {
        try {
            String value = get(index).asText();
            if (isEmpty(value)) {
                return null;
            }
            return JSON.parseObject(value, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public Boolean getBoolean(int index) {
        String value = get(index).asText();
        if (isEmpty(value)) {
            return Boolean.FALSE;
        }
        if (value.length() == 0 //
                || "null".equals(value) //
                || "NULL".equals(value)) {
            return null;
        }
        if ("true".equalsIgnoreCase(value) //
                || "1".equals(value)) {
            return Boolean.TRUE;
        }
        if ("false".equalsIgnoreCase(value) //
                || "0".equals(value)) {
            return Boolean.FALSE;
        }
        if ("Y".equalsIgnoreCase(value) //
                || "T".equals(value)) {
            return Boolean.TRUE;
        }
        if ("F".equalsIgnoreCase(value) //
                || "N".equals(value)) {
            return Boolean.FALSE;
        }
        throw new JacksonException("can not cast to boolean, value : " + value);
    }

    public boolean getBooleanValue(int index) {
        String value = get(index).asText();
        if (isEmpty(value)) {
            return false;
        }

        return getBoolean(index).booleanValue();
    }

    public Short getShort(int index) {
        String value = get(index).asText();
        if (value.length() == 0 //
                || "null".equals(value) //
                || "NULL".equals(value)) {
            return null;
        }
        return Short.parseShort(value);
    }

    public Date getDate(int index) {
        String value = getString(index);
        if (isEmpty(value)) {
            return new Date();
        }
        String format;
        final int len = value.length();
        if (len == JSON.DEFFAULT_DATE_FORMAT.length() || (len == 22 && JSON.DEFFAULT_DATE_FORMAT.equals("yyyyMMddHHmmssSSSZ"))) {
            format = JSON.DEFFAULT_DATE_FORMAT;
        } else if (len == 10) {
            format = "yyyy-MM-dd";
        } else if (len == "yyyy-MM-dd HH:mm:ss".length()) {
            format = "yyyy-MM-dd HH:mm:ss";
        } else if (len == 29 && value.charAt(26) == ':' && value.charAt(28) == '0') {
            format = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
        } else if (len == 23 && value.charAt(19) == ',') {
            format = "yyyy-MM-dd HH:mm:ss,SSS";
        } else {
            format = "yyyy-MM-dd HH:mm:ss.SSS";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, JSON.defaultLocale);
        dateFormat.setTimeZone(JSON.defaultTimeZone);
        try {
            return dateFormat.parse(value);
        } catch (ParseException e) {
            throw new JacksonException("can not cast to Date, value : " + value);
        }
    }

    public short getShortValue(int index) {
        Short shortVal = getShort(index);
        if (shortVal == null) {
            return 0;
        }

        return shortVal.shortValue();
    }

    public Integer getInteger(int index) {
        String value = get(index).asText();
        if (value.length() == 0 //
                || "null".equals(value) //
                || "NULL".equals(value)) {
            return null;
        }
        if (value.indexOf(',') != -1) {
            value = value.replaceAll(",", "");
        }

        Matcher matcher = NUMBER_WITH_TRAILING_ZEROS_PATTERN.matcher(value);
        if (matcher.find()) {
            value = matcher.replaceAll("");
        }
        return Integer.parseInt(value);
    }

    public int getIntValue(int index) {
        Integer intVal = getInteger(index);
        if (intVal == null) {
            return 0;
        }

        return intVal.intValue();
    }

    public Long getLong(int index) {
        String value = get(index).asText();
        if (isEmpty(value)) {
            return Long.valueOf(DEFAULT_ZERO);
        }

        return Long.valueOf(value);
    }

    public long getLongValue(int index) {
        String value = get(index).asText();
        if (isEmpty(value)) {
            return Long.parseLong(DEFAULT_ZERO);
        }

        return Long.parseLong(value);
    }

    public Float getFloat(int index) {
        String value = get(index).asText();
        if (isEmpty(value)) {
            return Float.valueOf(DEFAULT_ZERO);
        }

        return Float.valueOf(value);
    }

    public float getFloatValue(int index) {
        String value = get(index).asText();
        if (isEmpty(value)) {
            return Float.parseFloat(DEFAULT_ZERO);
        }

        return Float.parseFloat(value);
    }

    public Double getDouble(int index) {
        String value = get(index).asText();
        if (isEmpty(value)) {
            return Double.valueOf(DEFAULT_ZERO);
        }

        return Double.valueOf(value);
    }

    public double getDoubleValue(int index) {
        String value = get(index).asText();
        if (isEmpty(value)) {
            return Double.parseDouble(DEFAULT_ZERO);
        }

        return Double.parseDouble(value);
    }

    public BigDecimal getBigDecimal(int index) {
        String value = get(index).asText();
        if (isEmpty(value)) {
            return BigDecimal.valueOf(Long.parseLong(DEFAULT_ZERO));
        }

        return BigDecimal.valueOf(Long.parseLong(value));
    }

    public BigInteger getBigInteger(int index) {
        String value = get(index).asText();
        if (isEmpty(value)) {
            return BigInteger.ZERO;
        }
        return BigInteger.valueOf(Long.parseLong(value));
    }

    public String getString(int index) {
        return get(index).asText();
    }

    public static boolean isEmpty(Object value) {
        return (value == null || "".equals(value) || "null".equals(value));
    }

    @Override
    public JsonNode get(int index) {
        return _arrayNode.get(index);
    }

}
