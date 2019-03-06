package cn.itrip.test;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDemo1 {
    public static void main(String[] args) throws Exception {
        TestDemo1.test2();
    }

    public static void test2() throws Exception {
        Animal animal1=new Animal("小狗",100);
        Animal animal2=new Animal("小猫",200);
        List<Animal> list=new ArrayList<>();
        list.add(animal1);
        list.add(animal2);

        Map<String,Object> map=new HashMap<>();
        map.put("l1",list);

        Configuration configuration=new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(new File("D:\\练习\\itripproject\\itripbackend\\itripauth\\src\\main\\Resources"));
        Template template=configuration.getTemplate("TestFreemarkerList.flt");

        template.process(map,new FileWriter("D:\\2.txt"));
    }

    public static void test1() throws Exception {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("v1","v1的值");

        Configuration configuration=new Configuration();
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(new File("D:\\练习\\itripproject\\itripbackend\\itripauth\\src\\main\\Resources"));
        Template template=configuration.getTemplate("TestFreeMarker.flt");

        template.process(map,new FileWriter("D:\\1.html"));
    }
}
