package com.imooc.service;


import com.imooc.dataobject.ProductInfo;
import com.imooc.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用途描述: 商品列表service
 *
 * @author xuhaibin
 * @version 1.0.0
 * @create 2018-06-19 23:18:00
 */
public interface ProductService {

    /**
     * 通過id查詢商品详情
     *
     * @param productId
     * @return
     * @throws Exception
     */
    ProductInfo findOne(String productId);


    /**
     * 查询所有商品列表---上架中
     *
     * @return
     * @throws Exception
     */
    List<ProductInfo> findUpAll();


    /**
     * 分页查询所有商品列表
     *
     * @param pageable 分页参数对象
     * @return
     * @throws Exception
     */
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 保存商品
     *
     * @param productInfo
     * @return
     * @throws Exception
     */
    ProductInfo save(ProductInfo productInfo);


    /**
     * 加库存
     *
     * @param cartDTOList
     */
    void increaseStock(List<CartDTO> cartDTOList);

    /**
     * 减库存
     *
     * @param cartDTOList
     */
    void decreaseStock(List<CartDTO> cartDTOList);


}
