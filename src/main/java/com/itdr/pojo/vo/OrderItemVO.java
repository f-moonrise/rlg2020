package com.itdr.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author zangye03@gmail.com
 * @date 2020/3/1 10:34
 */
@Getter
@Setter
public class OrderItemVO {
    private Integer orderNo;

    private Integer productId;

    private String productName;

    private String productImage;

    private Integer currentUnitPrice;

    private Integer quantity;

    private Integer totalPrice;

    private Date createTime;
}
