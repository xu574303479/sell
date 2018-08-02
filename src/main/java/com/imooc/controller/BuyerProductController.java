package com.imooc.controller;

import com.imooc.VO.ResultVO;
import com.imooc.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author xuhaibin
 * @ClassName: BuyerProductController
 * @Description:买家商品
 * @Version 1.0.0
 * @Date 2018/8/3 2:04
 */
@RestController
@RequestMapping(value = "/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductServiceImpl productService;


    @GetMapping(value = "/list")
    public ResultVO list() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        return resultVO;
    }


}
