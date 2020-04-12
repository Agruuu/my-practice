package com.vampire.repository;

import com.vampire.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void findOneTest() {
        ProductCategory productCategory = repository.findById(1).orElse(null);
        System.out.println(productCategory.toString());
    }

    @Test
    @Transactional //在service层是如果有错误就会回滚，而在test里面则完全回滚(比如我们测试完不需要测试数据在数据库里, 测试成功后会回滚)，两种区别
    public void save() {
        ProductCategory productCategory = new ProductCategory("测试类目1", 1);
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);
//        Assert.assertNotEquals(null, result);

    }

    @Test
    public void update() {
        ProductCategory productCategory = repository.findById(1).orElse(null);
        productCategory.setCategoryName("测试类目2");
        productCategory.setCategoryType(1);
        repository.save(productCategory);
    }

    @Test
    public void findByCategoryTypeInTest() {
        List<ProductCategory> byCategoryTypeIn = repository.findByCategoryTypeIn(Arrays.asList(1, 2, 3, 4, 5));
        Assert.assertNotEquals(0, byCategoryTypeIn.size() );
    }
}