package com.itdr.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zangye03@gmail.com
 * @date 2020/3/3 10:56
 */
@Getter
@Setter
public class OrderItemVO_TotalPrice {
    private List<OrderItemVO> orderItemVOList;
    private String imageHost;
    private Integer productTotalPrice;
}
