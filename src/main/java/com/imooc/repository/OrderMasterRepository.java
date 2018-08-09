package com.imooc.repository;

import com.imooc.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author xuhaibin
 * @ClassName: OrderMasterRepository
 * @Description:买家订单
 * @Version 1.0.0
 * @Date 2018-08-09 23:11:51
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    /**
     * 根据买家openid查询订单列表---分页
     *
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);


}
