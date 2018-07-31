package com.imooc.service.impl;

import com.imooc.dataobject.ProductCategory;
import com.imooc.repository.ProductCategoryRepository;
import com.imooc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 用途描述: 类目Service
 *
 * @author xuhaibin
 * @version 1.0.0
 * @create 2018-06-19 23:18:00
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository repository;


    @Override
    public ProductCategory findOne(Integer categoryId) throws Exception {
        return repository.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() throws Exception {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> category) throws Exception {
        return repository.findByCategoryTypeIn(category);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) throws Exception {
        return repository.save(productCategory);
    }
}
