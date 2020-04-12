package com.vampire.repository;

import com.vampire.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    /**
     * 查询订单下面的订单详情列表
     * @param orderId
     * @return
     */
    List<OrderDetail> findByOrderId(String orderId);
}
