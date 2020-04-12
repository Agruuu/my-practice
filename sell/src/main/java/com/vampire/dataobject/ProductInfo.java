package com.vampire.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class  ProductInfo {

    @Id
    private String productId;

    /** 商品名字. */
    private String productName;

    /** 商品单价. */
    private BigDecimal productPrice;

    /** 商品库存. */
    private Integer productStock;

    /** 商品简介. */
    private String productDescription;

    /** 商品图片. */
    private String productIcon;

    /** 商品状态.0正常 1下架 */
    private Integer productStatus;

    /** 类目编号. */
    private Integer categoryType;
}
