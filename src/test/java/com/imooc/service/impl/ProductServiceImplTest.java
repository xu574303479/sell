package com.imooc.service.impl;

import com.imooc.dataobject.ProductInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.TextUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() throws Exception {
        ProductInfo productInfo = productService.findOne("123456");
        Assert.assertEquals("123456", productInfo.getProductId());
    }

    @Test
    public void findUpAll() throws Exception {
        List<ProductInfo> productInfoList = productService.findUpAll();
        Assert.assertNotEquals(0, productInfoList.size());
    }

    @Test
    public void findAll() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 4, Sort.Direction.DESC, new String[]{"productPrice", "productStatus"});
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);
        Assert.assertNotEquals(0, productInfoPage.getSize());
    }

    @Test
    public void save() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("219171");
        productInfo.setProductName("皮蛋粥11");
        productInfo.setProductPrice(new BigDecimal(6.6));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很好吃的");
        productInfo.setProductIcon("https://gss2.bdstatic.com/-fo3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D730/sign=65267135dd58ccbf1bbcb73929dabcd4/fd039245d688d43f516c4975711ed21b0ff43b74.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);

        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);
    }



    public static void main(String[] args) {
        Assert.assertEquals("123456", "122");
    }
}