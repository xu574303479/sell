package com.imooc.service;


import com.imooc.dataobject.ProductCategory;

import java.util.List;

/**
 * 用途描述: 类目service
 *
 * @author xuhaibin
 * @version 1.0.0
 * @create 2018-06-19 23:18:00
 */
public interface CategoryService {

    /**
     * 通过id查询类目---后台管理
     *
     * @param categoryId 类目id
     * @return
     */
    ProductCategory findOne(Integer categoryId) throws Exception;

    /**
     * 查询所有类目---后台管理
     *
     * @return
     */
    List<ProductCategory> findAll() throws Exception;

    /**
     * 通过类目类型查询所有类目
     *
     * @param category
     * @return
     */
    List<ProductCategory> findByCategoryTypeIn(List<Integer> category) throws Exception;


    /**
     * 保存类目
     *
     * @param productCategory
     * @return
     */
    ProductCategory save(ProductCategory productCategory) throws Exception;

}
