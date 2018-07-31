package com.imooc.repository;

import com.imooc.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 用途描述: 类目持久层
 *
 * @author xuhaibin
 * @version 1.0.0
 * @create 2018-06-19 23:18:00
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {


    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
