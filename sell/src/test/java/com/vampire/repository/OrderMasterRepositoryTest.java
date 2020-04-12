package com.vampire.repository;

import com.vampire.dataobject.OrderMaster;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private final String openid = "abc123";

    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("2");
        orderMaster.setBuyerName("咕噜咕噜");
        orderMaster.setBuyerPhone("15694709615");
        orderMaster.setBuyerAddress("呼和浩特市");
        orderMaster.setBuyerOpenid("abc123");
        orderMaster.setOrderAmount(new BigDecimal(599));
        OrderMaster result = repository.save(orderMaster);
        log.info(result.toString());
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid() {
        PageRequest request = new PageRequest(0, 1);
        Page<OrderMaster> orderMasterPage = repository.findByBuyerOpenid(openid, request);
        System.out.println("totalElements: " + orderMasterPage.getTotalElements());
        System.out.println("totalPages: " + orderMasterPage.getTotalPages());
        Assert.assertNotEquals(0, orderMasterPage.getContent().size());
    }
}