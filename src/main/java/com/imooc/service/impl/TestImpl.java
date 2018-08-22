package com.imooc.service.impl;

import com.imooc.dataobject.ProductCategory;
import com.imooc.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author xuhaibin
 * @ClassName: TestImpl
 * @Description:
 * @Version 1.0.0
 * @Date 2018-08-23 02:19:02
 */
@Service("testImpl")
public class TestImpl implements CategoryService {



    @Override
    public ProductCategory findOne(Integer categoryId) {
        return null;
    }

    @Override
    public List<ProductCategory> findAll() {
        return null;
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> category) {
        return null;
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return null;
    }
}
