package com.itdr.utils;

import com.itdr.pojo.ItdrCar;
import com.itdr.pojo.ItdrProduct;
import com.itdr.pojo.ItdrUser;
import com.itdr.pojo.vo.CartProductVO;
import com.itdr.pojo.vo.CartVO;
import com.itdr.pojo.vo.ProductVO;
import com.itdr.pojo.vo.UserVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/19 13:22
 */
public class ObjectToVOUtil {

    /**
     * 用户类分装
     * @param u
     * @return
     */
    public static UserVO ItdrUserToUserVO(ItdrUser u){
        UserVO uv = new UserVO();
        uv.setId(u.getId());
        uv.setUsername(u.getUsername());
        uv.setEmail(u.getEmail());
        uv.setPhone(u.getPhone());
        uv.setCreateTime(u.getCreateTime());
        uv.setUpdateTime(u.getUpdateTime());
        return uv;
    }

    /**
     * 商品类封装
     * @param p
     * @return
     */
    public static ProductVO ProductToUserVO(ItdrProduct p){
        ProductVO pv = new ProductVO();
        pv.setId(p.getId());
        pv.setPname(p.getPname());
        pv.setPnum(p.getPnum());
        pv.setCategoryid(p.getCategoryid());
        pv.setPrice(p.getPrice());
        pv.setCreateTime(p.getCreateTime());
        pv.setUpdateTime(p.getUpdateTime());

        pv.setPimages(PropertiesUtil.getValue("ImageHost"));
        return pv;
    }

    /**
     * 购物车和商品信息分装
     * @param c
     * @param p
     * @return
     */
    public static CartProductVO cartAndProductToVO(ItdrCar c, ItdrProduct p){
        CartProductVO cvo = new CartProductVO();
        //一条购物信息总价
        BigDecimal tp ;
        if(c.getPnum()!=0 && p.getPrice()!=0){
            tp = BigDecimalUtil.mul(c.getPnum(),p.getPrice());
            cvo.setSumprice(tp);
        }

        cvo.setId(c.getId());
        cvo.setUserId(c.getUserid());
        cvo.setProductId(p.getId());
        cvo.setPrice(p.getPrice());

        cvo.setPname(p.getPname());
        cvo.setPimages(p.getPimages());
        cvo.setPnum(c.getPnum());
//        cvo.setStatus(p.get);
        //是否被选中
        cvo.setChecked(c.getChecked());
        cvo.setQuantity(c.getQuantity());
        cvo.setStatus(c.getStatus());
        cvo.setCreateTime(c.getCreatetime());
        cvo.setUpdateTime(c.getUpdatetime());
        //商品是否超过库存
        String limitQuantity = "LIMIT_NUM_SUCCESS";
        if (c.getPnum() > p.getPnum()){
            limitQuantity = "LIMIT_NUM_FAIL";
        }
        cvo.setLimitQuantity(limitQuantity);
        return cvo;
    }

    public static CartVO toCartVO(List<CartProductVO> li,boolean bol,BigDecimal cartTotalPrice){
        CartVO cvo = new CartVO();
        //购物车中商品数据
        cvo.setCartProductVOList(li);
        //购物车商品是否全选
        cvo.setAllChecked(bol);
        //购物车总价
        cvo.setCartTotalPrice(cartTotalPrice);
        return cvo;
    }
}
