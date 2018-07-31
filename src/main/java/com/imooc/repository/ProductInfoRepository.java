package com.imooc.repository;


import com.imooc.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 用途描述: 商品詳情持久层
 *
 * @author xuhaibin
 * @version 1.0.0
 * @create 2018-06-19 23:18:00
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    /**
     * 通过商品状态查询商品列表
     *
     * @param productStatus 状态, 0正常1下架
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer productStatus) throws Exception;


}
