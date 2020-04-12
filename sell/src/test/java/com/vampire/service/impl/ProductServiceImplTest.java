package com.vampire.service.impl;

import com.vampire.dataobject.ProductInfo;
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
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productService.findOne("test1");
        Assert.assertNotNull(productInfo);
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = productService.findUpAll();
        Assert.assertNotEquals(0, productInfoList.size());
    }

    @Test
    public void findAll() {
        PageRequest request = new PageRequest(0, 5);
        Page<ProductInfo> page = productService.findAll(request);
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        page.forEach((k) -> {
            System.out.print(k.getProductName() + ",");
        });
        System.out.println();
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("test2");
        productInfo.setProductName("商品测试2");
        productInfo.setProductPrice(new BigDecimal(5.8));
        productInfo.setProductStock(16);
        productInfo.setProductStatus(0);
        productInfo.setProductDescription("商品测试介绍2");
        productInfo.setProductIcon("http://xxx.jpg");
        productInfo.setCategoryType(1);
        ProductInfo result = productService.save(productInfo);
        System.out.println(result);
        Assert.assertNotNull(result);
    }
}