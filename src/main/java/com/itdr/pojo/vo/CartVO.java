package com.itdr.pojo.vo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/22 17:36
 */
@Getter
@Setter
public class CartVO {

    private List<CartProductVO> cartProductVOList;

    private Boolean allChecked;

    private Integer cartTotalPrice;

    @Override
    public String toString() {
        return "CartVO{" +
                "cartProductVOList=" + cartProductVOList +
                ", allChecked=" + allChecked +
                ", cartTotalPrice=" + cartTotalPrice +
                '}';
    }
}
