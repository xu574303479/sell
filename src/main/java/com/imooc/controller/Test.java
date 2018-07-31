package com.imooc.controller;


import com.imooc.dataobject.ProductCategory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;

@RestController
public class Test {


    @RequestMapping(value = "/test")
    public String test() {


        return "springboot......";
    }


    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Integer a = 1;
        Integer b = 2;

        System.out.printf("a=%s,b=%s\r\n", a, b);
        swap(a, b);
        System.out.printf("a=%s,b=%s\r\n", a, b);
        System.out.println(Integer.valueOf(1));
        System.out.println(Integer.valueOf(2));
        System.out.println(Integer.valueOf(3));

        System.out.println("---------------------------------------");


        String name = "徐海滨";
        System.out.println(name);
        Field value = name.getClass().getDeclaredField("value");
        value.setAccessible(true);
        value.set(name, new char[]{'周', '超'});

        System.out.println(name);
        System.out.println("徐海滨");

        Field value2 = name.getClass().getDeclaredField("value");
        value2.setAccessible(true);
        value.set(name, new char[]{'海', '斌'});
        System.out.println(name);


        ProductCategory productCategory = new ProductCategory("男生专享", 10);



    }

    private static void swap(Integer a, Integer b) throws NoSuchFieldException, IllegalAccessException {

//        int a1 = a;
//        int b1 = b;
//        Field valueA = a.getClass().getDeclaredField("value");
//        valueA.setAccessible(true);
//        valueA.set(a,b1);
//        Field valueB = b.getClass().getDeclaredField("value");
//        valueB.setAccessible(true);
//        valueB.set(b,a1);

        int a1 = a;
        int b1 = b;
        Field valueA = a.getClass().getDeclaredField("value");
        valueA.setAccessible(true);
        valueA.set(a, b1);
        Field valueB = b.getClass().getDeclaredField("value");
        valueB.setAccessible(true);
        valueB.set(b, a1);
        valueB.set(b, new Integer(1));


    }


}
