package com.itdr.utils;

import java.math.BigDecimal;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/22 18:43
 */
public class BigDecimalUtil {

    /**
     * 加法运算
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal add(Double d1, Double d2){
        BigDecimal bigDecimal = new BigDecimal(Double.toString(d1));
        BigDecimal bigDecimal1 = new BigDecimal(Double.toString(d2));
        return bigDecimal.add(bigDecimal1);
    }

    /**
     * 减法运算
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal sub(Integer d1,Integer d2){
        BigDecimal bigDecimal = new BigDecimal(Integer.toString(d1));
        BigDecimal bigDecimal1 = new BigDecimal(Integer.toString(d2));
        return bigDecimal.subtract(bigDecimal1);
    }

    /**
     * 乘法运算
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal mul(Integer d1,Integer d2){
        BigDecimal bigDecimal = new BigDecimal(Integer.toString(d1));
        BigDecimal bigDecimal1 = new BigDecimal(Integer.toString(d2));
        return bigDecimal.multiply(bigDecimal1);
    }

    /**
     * 除法运算
     * @param d1
     * @param d2
     * @return
     */
    public static BigDecimal div(Integer d1,Integer d2){
        BigDecimal bigDecimal = new BigDecimal(Integer.toString(d1));
        BigDecimal bigDecimal1 = new BigDecimal(Integer.toString(d2));
        return bigDecimal.divide(bigDecimal1,2,BigDecimal.ROUND_HALF_UP);
    }
}
