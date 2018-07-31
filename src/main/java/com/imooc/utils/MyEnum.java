package com.imooc.utils;

public enum MyEnum {

    A(1), B(2), C(3);

    private int code;

    MyEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
