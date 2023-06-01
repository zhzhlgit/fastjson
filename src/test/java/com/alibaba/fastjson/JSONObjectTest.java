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
    ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();

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
    public void name() {
        String str = "{\"code\":\"taskCenter\",\"containerrelation\":{\"tableArea\":{\"layout\":\"vertical\",\"tabs\":[[\"tableArea\"]]}},\"gridrelation\":{\"tableArea\":{\"srcAreaCode\":\"tableArea\",\"tabRelation\":[\"tableArea\"]}},\"index\":{\"searchArea\":{\"code\":\"searchArea\",\"name\":\"搜索区域\",\"oid\":\"1001ZZ100000000CCNQE\",\"areaVisible\":true,\"isunfold\":true,\"isnotmeta\":false,\"items\":[{\"attrcode\":\"sysCode\",\"datatype\":204,\"disabled\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":false,\"isMultiSelectedEnabled\":true,\"isnotmeta\":true,\"itemtype\":\"refer\",\"label\":\"应用\",\"maxlength\":20,\"position\":1,\"required\":false,\"scale\":0,\"visible\":true,\"col\":3,\"colnum\":\"1\",\"hyperlinkflag\":false,\"isnextrow\":false,\"leftspace\":0,\"rightspace\":0,\"rows\":0,\"queryOperateType\":\"=@>@>=@<@<=@like@\",\"isfixedcondition\":false,\"isdrag\":true,\"visibleposition\":0,\"usefunc\":false,\"checkStrictly\":false,\"showUnit\":false},{\"attrcode\":\"pk_cube\",\"datatype\":204,\"disabled\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":false,\"isMultiSelectedEnabled\":true,\"isnotmeta\":true,\"itemtype\":\"refer\",\"label\":\"应用模型\",\"maxlength\":20,\"position\":2,\"required\":false,\"scale\":0,\"visible\":true,\"col\":3,\"colnum\":\"1\",\"hyperlinkflag\":false,\"isnextrow\":false,\"leftspace\":0,\"rightspace\":0,\"rows\":0,\"queryOperateType\":\"=@>@>=@<@<=@like@\",\"isfixedcondition\":false,\"isdrag\":true,\"visibleposition\":0,\"usefunc\":false,\"checkStrictly\":false,\"showUnit\":false},{\"attrcode\":\"sysCode\",\"datatype\":204,\"disabled\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":false,\"isMultiSelectedEnabled\":true,\"isnotmeta\":true,\"itemtype\":\"refer\",\"label\":\"应用\",\"maxlength\":20,\"position\":1,\"required\":false,\"scale\":0,\"visible\":true,\"col\":3,\"colnum\":\"1\",\"hyperlinkflag\":false,\"isnextrow\":false,\"leftspace\":0,\"rightspace\":0,\"rows\":0,\"queryOperateType\":\"=@>@>=@<@<=@like@\",\"isfixedcondition\":false,\"isdrag\":true,\"visibleposition\":0,\"usefunc\":false,\"checkStrictly\":false,\"showUnit\":false},{\"attrcode\":\"pk_cube\",\"datatype\":204,\"disabled\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":false,\"isMultiSelectedEnabled\":true,\"isnotmeta\":true,\"itemtype\":\"refer\",\"label\":\"应用模型\",\"maxlength\":20,\"position\":2,\"required\":false,\"scale\":0,\"visible\":true,\"col\":3,\"colnum\":\"1\",\"hyperlinkflag\":false,\"isnextrow\":false,\"leftspace\":0,\"rightspace\":0,\"rows\":0,\"queryOperateType\":\"=@>@>=@<@<=@like@\",\"isfixedcondition\":false,\"isdrag\":true,\"visibleposition\":0,\"usefunc\":false,\"checkStrictly\":false,\"showUnit\":false}],\"position\":1,\"moduletype\":\"search\"},\"headerBtnArea\":{\"code\":\"headerBtnArea\",\"name\":\"按钮区域\",\"oid\":\"1001ZZ100000000CCNQF\",\"areaVisible\":false,\"isunfold\":true,\"isnotmeta\":false,\"areastatus\":\"browse\",\"position\":2,\"items\":[],\"moduletype\":\"form\"},\"tableArea\":{\"code\":\"tableArea\",\"name\":\"表格区域\",\"oid\":\"1001ZZ100000000CCNQG\",\"areaVisible\":true,\"isunfold\":true,\"isnotmeta\":false,\"items\":[{\"propertyId\":\"1001ZZ100000000CCNQI\",\"attrcode\":\"task\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"任务\",\"maxlength\":20,\"position\":1,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"140px\"},{\"propertyId\":\"1001DD100000000CODTM\",\"attrcode\":\"pkCube\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"应用模型\",\"maxlength\":20,\"position\":2,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"140px\"},{\"propertyId\":\"1001ZZ100000000CCNQJ\",\"attrcode\":\"explain\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"说明\",\"maxlength\":20,\"position\":3,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"140px\"},{\"propertyId\":\"1001ZZ100000000CCNQK\",\"attrcode\":\"progress\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"进度\",\"maxlength\":20,\"position\":4,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"120px\"},{\"propertyId\":\"1001ZZ100000000CCNQL\",\"attrcode\":\"state\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"状态\",\"maxlength\":20,\"position\":5,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"140px\"},{\"propertyId\":\"1001ZZ100000000CCNQM\",\"attrcode\":\"result\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"结果\",\"maxlength\":20,\"position\":6,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"140px\"},{\"propertyId\":\"1001ZZ100000000CCNQN\",\"attrcode\":\"lastUpdateTime\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"上次更新时间\",\"maxlength\":20,\"position\":7,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"160px\"},{\"propertyId\":\"1001ZZ100000000CCNQO\",\"attrcode\":\"user\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"用户\",\"maxlength\":20,\"position\":8,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"120px\"},{\"propertyId\":\"1001ZZ100000000CCNQP\",\"attrcode\":\"clientSideIP\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"服务实例IP\",\"maxlength\":20,\"position\":9,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"160px\"},{\"propertyId\":\"1001ZZ100000000CCNQQ\",\"attrcode\":\"taskStartTime\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"任务开始时间\",\"maxlength\":20,\"position\":10,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"160px\"},{\"propertyId\":\"1001ZZ100000000CCNQI\",\"attrcode\":\"task\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"任务\",\"maxlength\":20,\"position\":1,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"140px\"},{\"propertyId\":\"1001DD100000000CODTM\",\"attrcode\":\"pkCube\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"应用模型\",\"maxlength\":20,\"position\":2,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"140px\"},{\"propertyId\":\"1001ZZ100000000CCNQJ\",\"attrcode\":\"explain\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"说明\",\"maxlength\":20,\"position\":3,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"140px\"},{\"propertyId\":\"1001ZZ100000000CCNQK\",\"attrcode\":\"progress\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"进度\",\"maxlength\":20,\"position\":4,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"120px\"},{\"propertyId\":\"1001ZZ100000000CCNQL\",\"attrcode\":\"state\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"状态\",\"maxlength\":20,\"position\":5,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"140px\"},{\"propertyId\":\"1001ZZ100000000CCNQM\",\"attrcode\":\"result\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"结果\",\"maxlength\":20,\"position\":6,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"140px\"},{\"propertyId\":\"1001ZZ100000000CCNQN\",\"attrcode\":\"lastUpdateTime\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"上次更新时间\",\"maxlength\":20,\"position\":7,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"160px\"},{\"propertyId\":\"1001ZZ100000000CCNQO\",\"attrcode\":\"user\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"用户\",\"maxlength\":20,\"position\":8,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"120px\"},{\"propertyId\":\"1001ZZ100000000CCNQP\",\"attrcode\":\"clientSideIP\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"服务实例IP\",\"maxlength\":20,\"position\":9,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"160px\"},{\"propertyId\":\"1001ZZ100000000CCNQQ\",\"attrcode\":\"taskStartTime\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"任务开始时间\",\"maxlength\":20,\"position\":10,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"160px\"}],\"moduletype\":\"table\",\"pagination\":false,\"position\":3}},\"name\":\"任务中心\",\"pageid\":\"1001ZZ100000000CCNQD\",\"ts\":\"2023-03-07 16:10:39\",\"validateFlag\":false,\"allAreaMetas\":[{\"code\":\"searchArea\",\"name\":\"搜索区域\",\"oid\":\"1001ZZ100000000CCNQE\",\"areaVisible\":true,\"isunfold\":true,\"isnotmeta\":false,\"items\":[{\"attrcode\":\"sysCode\",\"datatype\":204,\"disabled\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":false,\"isMultiSelectedEnabled\":true,\"isnotmeta\":true,\"itemtype\":\"refer\",\"label\":\"应用\",\"maxlength\":20,\"position\":1,\"required\":false,\"scale\":0,\"visible\":true,\"col\":3,\"colnum\":\"1\",\"hyperlinkflag\":false,\"isnextrow\":false,\"leftspace\":0,\"rightspace\":0,\"rows\":0,\"queryOperateType\":\"=@>@>=@<@<=@like@\",\"isfixedcondition\":false,\"isdrag\":true,\"visibleposition\":0,\"usefunc\":false,\"checkStrictly\":false,\"showUnit\":false},{\"attrcode\":\"pk_cube\",\"datatype\":204,\"disabled\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":false,\"isMultiSelectedEnabled\":true,\"isnotmeta\":true,\"itemtype\":\"refer\",\"label\":\"应用模型\",\"maxlength\":20,\"position\":2,\"required\":false,\"scale\":0,\"visible\":true,\"col\":3,\"colnum\":\"1\",\"hyperlinkflag\":false,\"isnextrow\":false,\"leftspace\":0,\"rightspace\":0,\"rows\":0,\"queryOperateType\":\"=@>@>=@<@<=@like@\",\"isfixedcondition\":false,\"isdrag\":true,\"visibleposition\":0,\"usefunc\":false,\"checkStrictly\":false,\"showUnit\":false},{\"attrcode\":\"sysCode\",\"datatype\":204,\"disabled\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":false,\"isMultiSelectedEnabled\":true,\"isnotmeta\":true,\"itemtype\":\"refer\",\"label\":\"应用\",\"maxlength\":20,\"position\":1,\"required\":false,\"scale\":0,\"visible\":true,\"col\":3,\"colnum\":\"1\",\"hyperlinkflag\":false,\"isnextrow\":false,\"leftspace\":0,\"rightspace\":0,\"rows\":0,\"queryOperateType\":\"=@>@>=@<@<=@like@\",\"isfixedcondition\":false,\"isdrag\":true,\"visibleposition\":0,\"usefunc\":false,\"checkStrictly\":false,\"showUnit\":false},{\"attrcode\":\"pk_cube\",\"datatype\":204,\"disabled\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":false,\"isMultiSelectedEnabled\":true,\"isnotmeta\":true,\"itemtype\":\"refer\",\"label\":\"应用模型\",\"maxlength\":20,\"position\":2,\"required\":false,\"scale\":0,\"visible\":true,\"col\":3,\"colnum\":\"1\",\"hyperlinkflag\":false,\"isnextrow\":false,\"leftspace\":0,\"rightspace\":0,\"rows\":0,\"queryOperateType\":\"=@>@>=@<@<=@like@\",\"isfixedcondition\":false,\"isdrag\":true,\"visibleposition\":0,\"usefunc\":false,\"checkStrictly\":false,\"showUnit\":false}],\"position\":1,\"moduletype\":\"search\"},{\"code\":\"headerBtnArea\",\"name\":\"按钮区域\",\"oid\":\"1001ZZ100000000CCNQF\",\"areaVisible\":false,\"isunfold\":true,\"isnotmeta\":false,\"areastatus\":\"browse\",\"position\":2,\"items\":[],\"moduletype\":\"form\"},{\"code\":\"tableArea\",\"name\":\"表格区域\",\"oid\":\"1001ZZ100000000CCNQG\",\"areaVisible\":true,\"isunfold\":true,\"isnotmeta\":false,\"items\":[{\"propertyId\":\"1001ZZ100000000CCNQI\",\"attrcode\":\"task\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"任务\",\"maxlength\":20,\"position\":1,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"140px\"},{\"propertyId\":\"1001DD100000000CODTM\",\"attrcode\":\"pkCube\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"应用模型\",\"maxlength\":20,\"position\":2,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"140px\"},{\"propertyId\":\"1001ZZ100000000CCNQJ\",\"attrcode\":\"explain\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"说明\",\"maxlength\":20,\"position\":3,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"140px\"},{\"propertyId\":\"1001ZZ100000000CCNQK\",\"attrcode\":\"progress\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"进度\",\"maxlength\":20,\"position\":4,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"120px\"},{\"propertyId\":\"1001ZZ100000000CCNQL\",\"attrcode\":\"state\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"状态\",\"maxlength\":20,\"position\":5,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"140px\"},{\"propertyId\":\"1001ZZ100000000CCNQM\",\"attrcode\":\"result\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"结果\",\"maxlength\":20,\"position\":6,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"140px\"},{\"propertyId\":\"1001ZZ100000000CCNQN\",\"attrcode\":\"lastUpdateTime\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"上次更新时间\",\"maxlength\":20,\"position\":7,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"160px\"},{\"propertyId\":\"1001ZZ100000000CCNQO\",\"attrcode\":\"user\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"用户\",\"maxlength\":20,\"position\":8,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"120px\"},{\"propertyId\":\"1001ZZ100000000CCNQP\",\"attrcode\":\"clientSideIP\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"服务实例IP\",\"maxlength\":20,\"position\":9,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"160px\"},{\"propertyId\":\"1001ZZ100000000CCNQQ\",\"attrcode\":\"taskStartTime\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"任务开始时间\",\"maxlength\":20,\"position\":10,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"160px\"},{\"propertyId\":\"1001ZZ100000000CCNQI\",\"attrcode\":\"task\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"任务\",\"maxlength\":20,\"position\":1,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"140px\"},{\"propertyId\":\"1001DD100000000CODTM\",\"attrcode\":\"pkCube\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"应用模型\",\"maxlength\":20,\"position\":2,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"140px\"},{\"propertyId\":\"1001ZZ100000000CCNQJ\",\"attrcode\":\"explain\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"说明\",\"maxlength\":20,\"position\":3,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"140px\"},{\"propertyId\":\"1001ZZ100000000CCNQK\",\"attrcode\":\"progress\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"进度\",\"maxlength\":20,\"position\":4,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"120px\"},{\"propertyId\":\"1001ZZ100000000CCNQL\",\"attrcode\":\"state\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"状态\",\"maxlength\":20,\"position\":5,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"140px\"},{\"propertyId\":\"1001ZZ100000000CCNQM\",\"attrcode\":\"result\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"结果\",\"maxlength\":20,\"position\":6,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"140px\"},{\"propertyId\":\"1001ZZ100000000CCNQN\",\"attrcode\":\"lastUpdateTime\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"上次更新时间\",\"maxlength\":20,\"position\":7,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"160px\"},{\"propertyId\":\"1001ZZ100000000CCNQO\",\"attrcode\":\"user\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"用户\",\"maxlength\":20,\"position\":8,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"120px\"},{\"propertyId\":\"1001ZZ100000000CCNQP\",\"attrcode\":\"clientSideIP\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"服务实例IP\",\"maxlength\":20,\"position\":9,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"160px\"},{\"propertyId\":\"1001ZZ100000000CCNQQ\",\"attrcode\":\"taskStartTime\",\"color\":\"#555555\",\"datatype\":1,\"disabled\":true,\"editAfterFlag\":false,\"fieldDisplayed\":\"refname\",\"fieldValued\":\"refpk\",\"isDataPowerEnable\":true,\"isrevise\":false,\"itemtype\":\"input\",\"label\":\"任务开始时间\",\"maxlength\":20,\"position\":10,\"required\":false,\"scale\":0,\"visible\":true,\"hyperlinkflag\":false,\"islock\":false,\"istotal\":false,\"width\":\"160px\"}],\"moduletype\":\"table\",\"pagination\":false,\"position\":3}]}";
        JSONObject metaJson = JSON.parseObject(str);
        System.out.println(metaJson);
    }

    @Test
    public void testPageQueryInfo() throws JsonProcessingException {
        String str = "{\"busiParamJson\":\"[{\\\"rqUrl\\\":\\\"/platform/templet/querypage.do\\\",\\\"rqJson\\\":\\\"{\\\\\\\"pagecode\\\\\\\":\\\\\\\"EPMP1010_main\\\\\\\",\\\\\\\"appcode\\\\\\\":\\\\\\\"EPMP1010\\\\\\\",\\\\\\\"sysParamJson\\\\\\\":\\\\{\\\\\\\"serviceCode\\\\\\\":\\\\\\\"EPMP1010\\\\\\\"\\\\},\\\\\\\"compareTs\\\\\\\":false}\\\",\\\"rqCode\\\":\\\"template\\\"},{\\\"rqUrl\\\":\\\"/platform/appregister/queryallbtns.do\\\",\\\"rqJson\\\":\\\"{\\\\\\\"pagecode\\\\\\\":\\\\\\\"EPMP1010_main\\\\\\\",\\\\\\\"appcode\\\\\\\":\\\\\\\"EPMP1010\\\\\\\",\\\\\\\"sysParamJson\\\\\\\":{\\\\\\\"serviceCode\\\\\\\":\\\\\\\"EPMP1010\\\\\\\"}}\\\",\\\"rqCode\\\":\\\"button\\\"},{\\\"rqUrl\\\":\\\"/platform/appregister/queryappcontext.do\\\",\\\"rqJson\\\":\\\"{\\\\\\\"appcode\\\\\\\":\\\\\\\"EPMP1010\\\\\\\",\\\\\\\"sysParamJson\\\\\\\":{\\\\\\\"serviceCode\\\\\\\":\\\\\\\"EPMP1010\\\\\\\"}}\\\",\\\"rqCode\\\":\\\"context\\\"}]\",\"sysParamJson\":{\"busiaction\":\"\",\"appcode\":\"EPMP1010\",\"tabid\":\"\",\"ts\":1685410812207,\"from\":\"\",\"pagecs\":1685410715389}}";
        MergeRequestParam pageQueryInfo = objectMapper.readValue(str, MergeRequestParam.class);
        System.out.println(pageQueryInfo);
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