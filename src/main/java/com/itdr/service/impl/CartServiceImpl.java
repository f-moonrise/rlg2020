package com.itdr.service.impl;

import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.mapper.ItdrCarMapper;
import com.itdr.mapper.ItdrProductMapper;
import com.itdr.pojo.ItdrCar;
import com.itdr.pojo.ItdrProduct;
import com.itdr.pojo.ItdrUser;
import com.itdr.pojo.vo.CartProductVO;
import com.itdr.pojo.vo.CartVO;
import com.itdr.service.CartService;
import com.itdr.utils.BigDecimalUtil;
import com.itdr.utils.ObjectToVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/22 15:22
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    ItdrCarMapper carMapper;
    @Autowired
    ItdrProductMapper productMapper;

    //获取CartVO对象
    private CartVO getCartVO(List<ItdrCar> cartList){
        //获取购物车对应的商品信息]
        List<CartProductVO> cartProductVOList = new ArrayList<CartProductVO>();


        boolean bol = true;
        BigDecimal cartTotalPrice = new BigDecimal("0");
        for(ItdrCar cart : cartList){
            ItdrProduct product = productMapper.selectByPrimaryKey(cart.getProductid());

            //把商品和购物车信息进行数据封装
            if(product != null){

                CartProductVO cartProductVO = ObjectToVOUtil.cartAndProductToVO(cart, product);
                cartProductVOList.add(cartProductVO);


                //计算购物车总价,只计算选中的商品
                if(cartProductVO.getChecked() == 1 && cartProductVO.getLimitQuantity() == "LIMIT_NUM_SUCCESS"){
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVO.getSumprice().doubleValue());
                }
            }
            //判断购物车是否全选
            if (cart.getChecked() == 0){
                bol = false;
            }
        }

        //返回数据
        CartVO cartVO = ObjectToVOUtil.toCartVO(cartProductVOList,bol,cartTotalPrice);
        return cartVO;
    }
    //获取用户购物车列表
    private ServerResponse<List<ItdrCar>> getCarList(ItdrUser user){
        //查询该用户的购物车信息
        List<ItdrCar> cartList = carMapper.selectByUserID(user.getId());

        //用户购物车是否有数据
        if (cartList.size() == 0){
            return ServerResponse.defeatedRS(
                    ConstCode.CartEnum.EMPTY_CART.getCode(),
                    ConstCode.CartEnum.EMPTY_CART.getDesc()
            );
        }
        return ServerResponse.successRS(cartList);
    }

    @Override
    public ServerResponse list(ItdrUser user) {
        //查询该用户的购物车信息
        ServerResponse<List<ItdrCar>> carList = getCarList(user);
        if (!carList.isSuccess()){
            return carList;
        }

        CartVO cartVO = getCartVO(carList.getData());
        return ServerResponse.successRS(cartVO);
    }

    @Override
    public ServerResponse add(Integer productId, Integer count, ItdrUser user) {
        //参数合法判断
        if(productId == null || productId < 0){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.UNLAWFULENSS_PARAM);
        }

        //要添加的商品是否在售
        ItdrProduct product = productMapper.selectByPrimaryKey(productId);
        if(product == null || product.getPnum() < 0){
            return ServerResponse.defeatedRS(
                    ConstCode.ItdrCategory.INEXISTENCE_PRODUCT.getCode(),
                    ConstCode.ItdrCategory.INEXISTENCE_PRODUCT.getDesc()
            );
        }
        //添加商品库存有没有超出
        if(count > product.getPnum()){
            return ServerResponse.defeatedRS(
              ConstCode.ItdrCategory.BEYOND_STOCK.getCode(),
              ConstCode.ItdrCategory.BEYOND_STOCK.getDesc()
            );
        }
        //向购物车中增加或更新一条数据
        ItdrCar c = new ItdrCar();
        c.setUserid(user.getId());
        c.setProductid(productId);
        c.setPnum(count);

        ItdrCar cart = carMapper.selectByUserIDAndProductId(user.getId(),productId);
        if(cart == null){
            int insert = carMapper.insert(c);
            if(insert <= 0){
                return ServerResponse.defeatedRS(
                        ConstCode.ItdrCategory.FAIL_ADD.getCode(),
                        ConstCode.ItdrCategory.FAIL_ADD.getDesc());
            }
        }else {
            cart.setPnum(count);
            int i = carMapper.updateByPrimaryKey(cart);
            if(i <= 0){
                return ServerResponse.defeatedRS(
                        ConstCode.ItdrCategory.FAIL_ADD.getCode(),
                        ConstCode.ItdrCategory.FAIL_ADD.getDesc());
            }
        }

        //返回封装好多
        //用户购物车是否有数据
        ServerResponse<List<ItdrCar>> carList = getCarList(user);
        if (!carList.isSuccess()){
            return carList;
        }

            CartVO cartVO = getCartVO(carList.getData());
            System.out.println(cartVO+"cartvo");
            return ServerResponse.successRS(cartVO);

    }
}
