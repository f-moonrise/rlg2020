package com.itdr.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/22 15:43
 */
@Setter
@Getter
public class CartProductVO {
    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer quantity;

    private Integer pnum;

    private String pname;

    private Integer price;

    private Integer sumprice;

    private String pimages;

    private Integer status;

    private Integer checked;

    private String limitQuantity;

    @JsonFormat(locale = "zh", timezone = "GMT-8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(locale = "zh", timezone = "GMT-8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Override
    public String toString() {
        return "CartProductVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", pnum=" + pnum +
                ", pname='" + pname + '\'' +
                ", price=" + price +
                ", sumprice=" + sumprice +
                ", pimages='" + pimages + '\'' +
                ", status=" + status +
                ", checked=" + checked +
                ", limitQuantity='" + limitQuantity + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
