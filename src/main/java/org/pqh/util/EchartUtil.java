package org.pqh.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.json.GsonUtil;
import com.github.abel533.echarts.json.OptionUtil;

/**
 * Created by reborn on 2017/6/2.
 */
public class EchartUtil {
    public static void main(String[] args) {
        ObjectMapper mapper=new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        Option option= GsonUtil.fromJSON("{\n" +
                "    title: {\n" +
                "        text: '堆叠区域图'\n" +
                "    },\n" +
                "    tooltip : {\n" +
                "        trigger: 'axis',\n" +
                "        axisPointer: {\n" +
                "            type: 'cross',\n" +
                "            label: {\n" +
                "                backgroundColor: '#6a7985'\n" +
                "            }\n" +
                "        }\n" +
                "    },\n" +
                "    legend: {\n" +
                "        data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']\n" +
                "    },\n" +
                "    toolbox: {\n" +
                "        feature: {\n" +
                "            saveAsImage: {}\n" +
                "        }\n" +
                "    },\n" +
                "    grid: {\n" +
                "        left: '3%',\n" +
                "        right: '4%',\n" +
                "        bottom: '3%',\n" +
                "        containLabel: true\n" +
                "    },\n" +
                "    xAxis : [\n" +
                "        {\n" +
                "            type : 'category',\n" +
                "            boundaryGap : false,\n" +
                "            data : ['周一','周二','周三','周四','周五','周六','周日']\n" +
                "        }\n" +
                "    ],\n" +
                "    yAxis : [\n" +
                "        {\n" +
                "            type : 'value'\n" +
                "        }\n" +
                "    ],\n" +
                "    series : [\n" +
                "        {\n" +
                "            name:'邮件营销',\n" +
                "            type:'line',\n" +
                "            stack: '总量',\n" +
                "            areaStyle: {normal: {}},\n" +
                "            data:[120, 132, 101, 134, 90, 230, 210]\n" +
                "        },\n" +
                "        {\n" +
                "            name:'联盟广告',\n" +
                "            type:'line',\n" +
                "            stack: '总量',\n" +
                "            areaStyle: {normal: {}},\n" +
                "            data:[220, 182, 191, 234, 290, 330, 310]\n" +
                "        },\n" +
                "        {\n" +
                "            name:'视频广告',\n" +
                "            type:'line',\n" +
                "            stack: '总量',\n" +
                "            areaStyle: {normal: {}},\n" +
                "            data:[150, 232, 201, 154, 190, 330, 410]\n" +
                "        },\n" +
                "        {\n" +
                "            name:'直接访问',\n" +
                "            type:'line',\n" +
                "            stack: '总量',\n" +
                "            areaStyle: {normal: {}},\n" +
                "            data:[320, 332, 301, 334, 390, 330, 320]\n" +
                "        },\n" +
                "        {\n" +
                "            name:'搜索引擎',\n" +
                "            type:'line',\n" +
                "            stack: '总量',\n" +
                "            label: {\n" +
                "                normal: {\n" +
                "                    show: true,\n" +
                "                    position: 'top'\n" +
                "                }\n" +
                "            },\n" +
                "            areaStyle: {normal: {}},\n" +
                "            data:[820, 932, 901, 934, 1290, 1330, 1320]\n" +
                "        }\n" +
                "    ]\n" +
                "}\n");

        LogUtil.getLogger().info(OptionUtil.exportToHtml(option,"E:\\"));
    }
}
