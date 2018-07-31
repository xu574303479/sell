package com.imooc.utils;

import java.lang.reflect.Constructor;

public class TestUtil {


    public static <T> T builderEmpty(Class<T> tClass) {

        Constructor declaredConstructors;
        T t = null;
        try {
            declaredConstructors = tClass.getDeclaredConstructor();
            declaredConstructors.setAccessible(true);
            t = (T) declaredConstructors.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }


    public static <E> E codeToEnum(int code, E... es) throws Exception {
        E result = null;
        for (E e : es) {
            int code1 = (int) e.getClass().getMethod("getCode").invoke(e);
            if (code1 == code) {
                result = e;
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {

        MyEnum o = codeToEnum(1, MyEnum.values());
        System.out.println(o);
    }


}
