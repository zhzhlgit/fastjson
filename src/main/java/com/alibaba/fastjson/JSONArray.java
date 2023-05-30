package com.alibaba.fastjson;

import com.alibaba.fastjson.exception.JacksonException;
import com.alibaba.fastjson.util.JacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

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
public class JSONArray extends JSON implements List<Object> {
    private final ArrayNode _arrayNode;
    private static final Pattern NUMBER_WITH_TRAILING_ZEROS_PATTERN = Pattern.compile("\\.0*$");
    private static final long serialVersionUID = 7031906528012587403L;
    private static final String DEFAULT_ZERO = "0";
    protected transient Object relatedArray;
    protected transient Type componentType;

    private ObjectMapper mapper = new ObjectMapper();

    public JSONArray() {
        this(JsonNodeFactory.instance);
    }

    public JSONArray(List<Object> list) {
        if (list == null) {
            throw new IllegalArgumentException("list is null.");
        }


        List<JsonNode> collect = list.stream().map(x -> {
            JsonNode jsonNode = mapper.valueToTree(x);
            return jsonNode;
        }).collect(Collectors.toList());
        _arrayNode = new ArrayNode(JsonNodeFactory.instance, collect);

    }

    public JSONArray(JsonNodeFactory nf) {
        _arrayNode = new ArrayNode(JsonNodeFactory.instance);
    }

    public JSONArray(JsonNodeFactory nf, int capacity) {
        _arrayNode = new ArrayNode(nf, capacity);
    }

    public JSONArray(JsonNodeFactory nf, List<JsonNode> children) {
        _arrayNode = new ArrayNode(nf, children);
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
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<Object> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        Object[] objects = new Object[_arrayNode.size()];
        Iterator<JsonNode> iterator = _arrayNode.elements();
        int index = 0;
        while (iterator.hasNext()) {
            Object o = iterator.next();
            objects[index] = o;
            index++;
        }
        return objects;
    }


    @Override
    public <T> T[] toArray(T[] a) {
        Object[] elementData = toArray();
        if (a.length < _arrayNode.size())
        // Make a new array of a's runtime type, but my contents:
        {
            return (T[]) Arrays.copyOf(elementData, _arrayNode.size(), a.getClass());
        }
        System.arraycopy(elementData, 0, a, 0, _arrayNode.size());
        if (a.length > _arrayNode.size()) {
            a[_arrayNode.size()] = null;
        }
        return a;
    }

    @Override
    public boolean add(Object o) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public ListIterator<Object> listIterator() {
        List<Object> list = new ArrayList<>(_arrayNode.size());
        Iterator<JsonNode> elements = _arrayNode.elements();
        while (elements.hasNext()) {
            list.add(elements.next());
        }
        return list.listIterator();
    }


    @Override
    public ListIterator<Object> listIterator(int index) {
        return null;
    }


    @Override
    public List<Object> subList(int fromIndex, int toIndex) {
        return null;
    }


    public JSONObject getJSONObject(int index) {
        //              ObjectNode objectNode=mapper.readValue(get(index).asText(),ObjectNode.class);
        JsonNode jsonNode = _arrayNode.get(index);
        Iterator<Map.Entry<String, JsonNode>> iterator = jsonNode.fields();
        Map<String, JsonNode> kids = new HashMap<>();
        for (Iterator<Map.Entry<String, JsonNode>> it = iterator; it.hasNext(); ) {
            Map.Entry<String, JsonNode> entry = it.next();
            kids.put(entry.getKey(), entry.getValue().deepCopy());
        }
        return new JSONObject(JsonNodeFactory.instance, kids);
    }

    public JSONArray getJSONArray(int index) {
        try {
            JsonNode jn = mapper.readValue(get(index).asText(), JsonNode.class);
            JSONArray ja = new JSONArray(JsonNodeFactory.instance);
            ja.add(jn);
            return ja;
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
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
        if (len == JSON.DEFFAULT_DATE_FORMAT.length()
                || (len == 22 && JSON.DEFFAULT_DATE_FORMAT.equals("yyyyMMddHHmmssSSSZ"))) {
            format = JSON.DEFFAULT_DATE_FORMAT;
        } else if (len == 10) {
            format = "yyyy-MM-dd";
        } else if (len == "yyyy-MM-dd HH:mm:ss".length()) {
            format = "yyyy-MM-dd HH:mm:ss";
        } else if (len == 29
                && value.charAt(26) == ':'
                && value.charAt(28) == '0') {
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

    @Override
    public Object set(int index, Object element) {
        return null;
    }

    @Override
    public void add(int index, Object element) {

    }

    @Override
    public Object remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    public ObjectMapper getMapper() {
        return this.mapper;
    }

    public String toJSONString() {
        return toString();
    }


    public static JSONObject parseObject(String text) {
        return JacksonUtil.from(text, JSONObject.class);
    }

    public static <T> T parseObject(String text, Class<T> clazz) {
        return JacksonUtil.from(text, clazz);
    }

    public static JSONArray parseArray(String text) {
        return JSON.parseArray(text);
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        return JacksonUtil.fromList(text, clazz);
    }

}
