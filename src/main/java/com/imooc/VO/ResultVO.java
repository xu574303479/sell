package com.imooc.VO;

import lombok.Data;

/**
 * @Author xuhaibin
 * @ClassName: ResultVO
 * @Description:返回结果集视图对象
 * @Version 1.0.0
 * @Date 2018-08-03 02:12:14
 */
@Data
public class ResultVO<T> {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 结果集内容
     */
    private T data;

}
