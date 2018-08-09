package com.imooc.utils;

import com.imooc.VO.ResultVO;

/**
 * @Author xuhaibin
 * @ClassName: ResultVOUtil
 * @Description:
 * @Version 1.0.0
 * @Date 2018-08-08 00:43:49
 */
public class ResultVOUtil {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

}
