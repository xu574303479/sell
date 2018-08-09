package com.imooc.repository;

import com.imooc.BaseTest;
import com.imooc.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;

/**
 * 用途描述:
 *
 * @author xuhaibin
 * @version 1.0.0
 * @create ${YEAR}-${MONTH}-${DAY} ${TIME}
 */
public class OrderMasterRepositoryTest extends BaseTest {

    @Autowired
    private OrderMasterRepository repository;

    private final String OPENID = "110110";

    @Test
    public void saveTest () {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234567");
        orderMaster.setBuyerName("师兄");
        orderMaster.setBuyerPhone("123456789123");
        orderMaster.setBuyerAddress("幕课网");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(2.5));

        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);

    }

    @Test
    public void findByBuyerOpenid() throws Exception {
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<OrderMaster> result = repository.findByBuyerOpenid(OPENID, pageRequest);

        System.out.println(result.getTotalElements());

    }
}