package com.imooc.dto;

import lombok.Data;

/**
 * @Author xuhaibin
 * @ClassName: CartDTO
 * @Description:购物车
 * @Version 1.0.0
 * @Date 2018-08-10 00:57:38
 */
@Data
public class CartDTO {

    /**
     * 商品Id.
     */
    private String productId;

    /**
     * 数量.
     */
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
