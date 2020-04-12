package com.vampire.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品(包含类目 )
 */
@Data
public class ProductVO {

    /** 类目名字. JsonProperty 表示返回给前段用 name */
    @JsonProperty("name")
    private String categoryName;

    /** 类目类型. */
    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}
