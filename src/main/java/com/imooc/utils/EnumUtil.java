package com.imooc.utils;

import com.imooc.enums.CodeEnum;

/**
 * 用途描述:
 *
 * @author xuhaibin
 * @version 1.0.0
 * @create ${YEAR}-${MONTH}-${DAY} ${TIME}
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
