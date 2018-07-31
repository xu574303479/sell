package com.imooc.enums;

import lombok.Getter;

/**
 * 用途描述: 商品状态
 *
 * @author xuhaibin
 * @version 1.0.0
 * @create ${YEAR}-${MONTH}-${DAY} ${TIME}
 */
@Getter
public enum ProductStatusEnum implements CodeEnum {
    UP(0, "上架"),
    DOWN(1, "下架");

    private Integer code;

    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


}
