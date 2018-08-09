package com.imooc.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @Author xuhaibin
 * @ClassName: OrderMaster
 * @Description:买家订单状态枚举
 * @Version 1.0.0
 * @Date 2018-08-09 23:03:03
 */
@Getter
public enum OrderStatusEnum {

    NEW(0, "新订单"),
    FINISHED(1, "完结"),
    CANCEL(2, "已取消"),;


    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
