package com.vampire.service.impl;

import com.vampire.dataobject.OrderDetail;
import com.vampire.dto.OrderDTO;
import com.vampire.enums.OrderStatusEnum;
import com.vampire.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String BUYER_OPENID = "abc123";

    private final String orderId = "1556594016663168622";

    @Test
    public void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("咕噜");
        orderDTO.setBuyerPhone("15694709615");
        orderDTO.setBuyerAddress("呼市");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        // 购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("2-2");
        o1.setProductQuantity(1);
        orderDetailList.add(o1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("2-1");
        o2.setProductQuantity(20);
        orderDetailList.add(o2);

        OrderDetail o3 = new OrderDetail();
        o3.setProductId("1-2");
        o3.setProductQuantity(2);
        orderDetailList.add(o3);

        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result = orderService.create(orderDTO);

        System.out.println(result);
        log.info("---------------------------------");
        log.info("【创建订单】result = ", result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = orderService.findOne(orderId);
        log.info("【查询单个】result = {}", orderDTO);
        Assert.assertNotNull(orderDTO);
    }

    @Test
    public void findList() {
        PageRequest request = new PageRequest(0, 5);
        Page<OrderDTO> list = orderService.findList(BUYER_OPENID, request);
        log.info("【查询列表】result = {}", list);
        log.info("content = ", list.getContent());
        Assert.assertNotEquals(0, list.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(orderId);
        OrderDTO cancel = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), cancel.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(orderId);
        OrderDTO finished = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), finished.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne(orderId);
//        OrderDTO paid = orderService.paid(orderDTO);
//        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), paid.getPayStatus());
    }
}