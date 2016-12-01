package org.pqh.util;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.pqh.dao.BiliDao;
import org.pqh.entity.Param;
import org.pqh.test.Test;

import javax.annotation.Resource;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by 10295 on 2016/8/4.
 */
public class PropertiesUtil {
    private static Logger log= TestSlf4j.getLogger(PropertiesUtil.class);
    @Resource
    BiliDao biliDao;
    /**
     * 获取配置文件所有配置项
     * @return
     */
    public static Properties getProperties(){
        InputStream inputStream=null;

        try {
            String path=BiliUtil.class.getClassLoader().getResource("config.properties").getPath();
            inputStream=new FileInputStream(path);
            Properties p = new Properties();
            p.load(inputStream);
            return p;
        } catch (IOException e) {
            TestSlf4j.outputLog(e,log,false);
            return  null;
        }finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                TestSlf4j.outputLog(e,log,false);
            }
        }
    }


    /**
     * 获取配置文件指定配置项
     */
    public static <T>T getProperties(String key,Class<T> type){
        if(type==String.class){
            return (T) getProperties().getProperty(key);
        }
         int index=type.getName().lastIndexOf(".")+1;
         String typename=type.getName().substring(index);
        if(typename.equals("Integer")){
            typename="Int";
        }
        try {
            return (T) type.getDeclaredMethod("parse"+typename,String.class).invoke(null,getProperties().getProperty(key));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("只支持基本八大类型转换");
    }

    /**
     * 更新单个配置项
     * @param key
     * @param value
     * @param desc
     */
    public static void updateProperties(String key,String value,String desc){
        try {
            File file=new File("src/config.properties");
            List<String> strings= FileUtils.readLines(file,"GBK");
            List<String> _strings=new ArrayList<String>();
            for(String s:strings){
                if(s.contains(key)){
                    String _value=s.substring(s.indexOf("=")+1);
                    s=s.replaceAll(_value,value);
                    int descindex = _strings.size() - 1;
                    String _desc=strings.get(descindex);
                    if(desc!=null) {
                        _desc=desc="#"+ Test.gbEncoding(desc);
                        _strings.remove(descindex);
                        _strings.add(desc);
                    }
                }
                _strings.add(s);
            }
            FileUtils.writeLines(file,"GBK",_strings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void updateProperties(){
        File file=new File("src/config.properties");
        try {
            List<String> strings= FileUtils.readLines(file,"GBK");
            for(int i=0;i<strings.size();i+=2){
                String desc=strings.get(i);
                String str=strings.get(i+1);
                int index=str.indexOf("=");
                String key=str.substring(0,index);
                String value=str.substring(index+1,str.length());
                Param param=new Param(key,value,desc);
                if(biliDao.selectParam(key)==null){
                    biliDao.insertParam(param);
                }else{
                    biliDao.updateParam(param);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从数据库创建配置文件
     * @param biliDao
     * @param file 生成的配置文件对象
     */
    public static void createConfig(BiliDao biliDao, File file){
        List<Param> list=biliDao.selectParams();
        List<String> stringList=new ArrayList<String>();
        for(Param param:list){
            stringList.add(param.getDesc());
            stringList.add(param.getKey()+"="+(param.getValue()!=null?param.getValue():""));
        }
        try {
            FileUtils.writeLines(file,"GBK",stringList);
        } catch (IOException e) {
            TestSlf4j.outputLog(e,log,false);
        }
    }
}
