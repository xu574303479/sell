package com.imooc.controller;

import com.imooc.VO.ProductInfoVO;
import com.imooc.VO.ProductVO;
import com.imooc.VO.ResultVO;
import com.imooc.dataobject.ProductCategory;
import com.imooc.dataobject.ProductInfo;
import com.imooc.service.CategoryService;
import com.imooc.service.impl.ProductServiceImpl;
import com.imooc.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    @Qualifier("categoryServiceImpl")
    private CategoryService categoryService;

    @GetMapping(value = "/list")
    public ResultVO list() throws Exception {
        // 1.查询所有上架商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        // 2.查询类目(一次性查询)
//        List<Integer> categoryTypeList = new ArrayList<>();
        // 传统方式：遍历，组装
//        for (ProductInfo productInfo : productInfoList) {
//            categoryTypeList.add(productInfo.getCategoryType());
//        }

        // 精简方式---(Java8 lambda表达式)
        List<Integer> categoryTypeList = productInfoList.stream()       // 先转成stream流
                .map(e -> e.getCategoryType())      // 需要的数据
//                .filter(o -> o == 2)                // 过滤：只需要type=2的数据
                .collect(Collectors.toList());      // 作为一个List来收集

        // 通过类目类型查询所有类目
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);

        // 3.数据拼接
        List<ProductVO> productVOList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().intValue() == productCategory.getCategoryType().intValue()) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);

                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }


    public static void main(String[] args) {
        List<ProductInfo> productInfoList = null;

        try {
            for (ProductInfo productInfo : productInfoList) {
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            for (int i = 0, length = productInfoList.size(); i < length; i++) {
                System.out.println();
                ProductInfo productInfo = productInfoList.get(i);
                System.out.println(productInfo.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
