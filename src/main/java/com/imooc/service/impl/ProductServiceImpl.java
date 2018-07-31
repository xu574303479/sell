package com.imooc.service.impl;

import com.imooc.dataobject.ProductInfo;
import com.imooc.enums.ProductStatusEnum;
import com.imooc.repository.ProductInfoRepository;
import com.imooc.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用途描述: 商品列表service
 *
 * @author xuhaibin
 * @version 1.0.0
 * @create ${YEAR}-${MONTH}-${DAY} ${TIME}
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) throws Exception {
        return repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() throws Exception {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) throws Exception {
        return repository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) throws Exception {
        return repository.save(productInfo);
    }
}
