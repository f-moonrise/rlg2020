package com.itdr.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author zangye03@gmail.com
 * @date 2020/3/1 10:32
 */
@Getter
@Setter
public class OrderVO {
    private Integer orderNo;

    private Integer shippingId;

    private Integer payment;

    private Integer paymentType;

    private Integer postage;

    private Integer status;

    private Date paymentTime;

    private Date sendTime;

    private Date endTime;

    private Date closeTime;

    private Date createTime;

    private List<OrderItemVO> orderItemVoList;

    private ShoppingVO shippingVO;

    //图片服务器地址
    private String imageHost;
}
