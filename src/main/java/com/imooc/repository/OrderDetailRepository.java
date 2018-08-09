package com.imooc.repository;

import com.imooc.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 廖师兄
 * 2017-06-11 17:28
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    /**
     * 根据订单id查询订单详情---一对多
     *
     * @param orderId
     * @return
     */
    List<OrderDetail> findByOrderId(String orderId);
}
