package com.itdr.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.mapper.*;
import com.itdr.pojo.*;
import com.itdr.pojo.vo.*;
import com.itdr.service.CartService;
import com.itdr.service.ItdrOrderService;
import com.itdr.utils.BigDecimalUtil;
import com.itdr.utils.ObjectToVOUtil;
import com.itdr.utils.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/29 14:02
 */
@Service
public class ItdrOrderServiceImpl implements ItdrOrderService {

    @Autowired
    ItdrOrderMapper orderMapper;
    @Autowired
    ItdrCarMapper cartMapper;
    @Autowired
    CartService cartService;
    @Autowired
    ItdrProductMapper productMapper;
    @Autowired
    ItdrOrderItemMapper orderItemMapper;
    @Autowired
    ItdrShoppingMapper shoppingMapper;

    //订单编号生成规则
    private Long getNo() {
        long round = Math.round(Math.random() * 100);
        long l = System.currentTimeMillis() + round;
        return l;
    }

    @Override
    public ServerResponse create(ItdrUser user, Integer shoppingId) {
        //参数非空判断
        if (shoppingId == null || shoppingId < 0) {
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,
                    ConstCode.UNLAWFULENSS_PARAM);
        }

        //判断当前用户购物车中有没有数据
        ServerResponse over = cartService.over(user);
        //购物车中商品没有被选中的数据
        if (!over.isSuccess()) {
            return ServerResponse.defeatedRS(
                    ConstCode.CartEnum.NOSELECT_PRODUCT.getCode(),
                    ConstCode.CartEnum.NOSELECT_PRODUCT.getDesc()
            );
        }

        //根据用户名获取购物车信息
        List<ItdrCar> carList = cartMapper.selectByUserID(user.getId());
        CartVO cartVO = ((CartServiceImpl) cartService).getCartVO(carList);

        //创建一个订单
        Long no = getNo();
        ItdrOrder o = new ItdrOrder();
        o.setUserid(user.getId());
        o.setOrderNo(Math.toIntExact(no));
        o.setShoppingId(shoppingId);
        o.setPayment(cartVO.getCartTotalPrice());
        o.setPaymentType(1);
        o.setPostage(0);
        o.setStatus(10);
        //把创建的订单对象存储到数据库中
        int i = orderMapper.insert(o);
        if (i <= 0) {
            return ServerResponse.defeatedRS("订单创建失败");
        }

        //创建订单详情
        List<OrderItemVO> itemVOList = new ArrayList<OrderItemVO>();
        for (ItdrCar cart : carList) {
            ItdrOrderItem orderItem = new ItdrOrderItem();
            orderItem.setUserid(user.getId());
            orderItem.setOrderNo(o.getOrderNo());
            if (cart.getChecked() == 1) {
                ItdrProduct product = productMapper.selectByPrimaryKey(cart.getProductid());
//                if (product.getPnum() > 0 && cart.getQuantity() <= product.getStock())
                if (product.getPnum() > 0) {
                    orderItem.setProductid(cart.getProductid());
                    orderItem.setProductName(product.getPname());
                    orderItem.setProductImage(product.getPimages());
                    orderItem.setCurrentunitprice(product.getPrice());
                    orderItem.setQuantity(cart.getQuantity());
                    orderItem.setTotalprice(BigDecimalUtil.mul(product.getPrice(), cart.getQuantity()));
                }
            }
            //把创建的订单详情对象存储到数据库中
            int i2 = orderItemMapper.insert(orderItem);
            if (i2 <= 0) {
                return ServerResponse.defeatedRS("订单详情创建失败");
            }
            OrderItemVO orderItemVO = ObjectToVOUtil.orderItemToOrderItemVo(orderItem);
            itemVOList.add(orderItemVO);
        }


        //清空购物车数据
        int i3 = cartMapper.deleteByUserId(user.getId());

        //返回成功数据
        ItdrShopping shopping = shoppingMapper.selectByPrimaryKey(shoppingId);
        if (shopping == null) {
            return ServerResponse.defeatedRS("地址不存在");
        }
        ShoppingVO shoppingVO = ObjectToVOUtil.shippingToShippingVO(shopping);
        OrderVO orderVO = getOrderVO(o, shoppingId, itemVOList, shoppingVO);
        return ServerResponse.successRS(orderVO);
    }

    @Override
    public ServerResponse getOrderCartProduct(ItdrUser user, Integer orderNo) {
        OrderItemVO_TotalPrice orderItemVOTotalPriceList = new OrderItemVO_TotalPrice();

        // 如果订单编号不为空，根据用户id和订单编号获取对应的订单详情信息
        if (orderNo != null) {
            List<ItdrOrderItem> orderItemList = orderItemMapper.selectByOrderNoAndUserId(orderNo, user.getId());
            List<OrderItemVO> orderItemVO = this.getOrderItemVO(orderItemList);
            ItdrOrder order = orderMapper.selectByOrderNo(orderNo);
            if (order == null) {
                return ServerResponse.defeatedRS
                        (ConstCode.OrderEnum.INEXISTENCE_ORDER.getCode(), ConstCode.OrderEnum.INEXISTENCE_ORDER.getDesc());
            }

            orderItemVOTotalPriceList.setOrderItemVOList(orderItemVO);
            orderItemVOTotalPriceList.setImageHost(PropertiesUtil.getValue("ImageHost"));
            orderItemVOTotalPriceList.setProductTotalPrice(order.getPayment());
        } else {
            // 没有订单id，根据用户id获取订单详情
            // 获取购物车中选中的商品
            List<ItdrCar> cartList = cartMapper.selectByUserID(user.getId());
            if (cartList.size() == 0) {
                return ServerResponse.defeatedRS
                        (ConstCode.OrderEnum.INEXISTENCE_ORDER.getCode(), ConstCode.OrderEnum.INEXISTENCE_ORDER.getDesc());
            }

            // 存储订单选中的商品数据
            List<ItdrProduct> productList = new ArrayList<>();
            // 计算总价
            Integer payment = 0;
            for (ItdrCar cart : cartList) {
                // 判断商品是否失效
                Integer productId = cart.getProductid();
                // 根据商品id获取商品数据
                ItdrProduct product = productMapper.selectByPrimaryKey(productId);
                // 判断商品是否有效
                if (product == null) {
                    return ServerResponse.defeatedRS
                            (ConstCode.OrderEnum.FAIL_ADD.getCode(), ConstCode.OrderEnum.FAIL_ADD.getDesc());
                }
                // 判断商品是否下架
                if (product.getPnum() < 0) {
                    return ServerResponse.defeatedRS
                            (ConstCode.OrderEnum.FAIL_ADD.getCode(), ConstCode.OrderEnum.FAIL_ADD.getDesc());
                }

                // 根据购物车购物数量和商品单价计算一条购物车信息的总价
                Integer mul = BigDecimalUtil.mul(product.getPrice(), cart.getQuantity());
                // 把所有购物车信息总价相加，得出订单总价
                payment = BigDecimalUtil.add(payment, mul);
                // 存入商品集合
                productList.add(product);
            }

            List<ItdrOrderItem> orderItem = this.getOrderItem(user.getId(), productList, cartList);
            List<OrderItemVO> orderItemVO = this.getOrderItemVO(orderItem);

            orderItemVOTotalPriceList.setOrderItemVOList(orderItemVO);
            orderItemVOTotalPriceList.setImageHost(PropertiesUtil.getValue("ImageHost"));
            orderItemVOTotalPriceList.setProductTotalPrice(payment);
        }


        return ServerResponse.successRS(orderItemVOTotalPriceList);
    }

    private OrderVO getOrderVO(ItdrOrder o, Integer shoppingId, List<OrderItemVO> list,
                               ShoppingVO shoppingVO) {
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderNo(o.getOrderNo());
        orderVO.setShippingId(shoppingId);
        orderVO.setPayment(o.getPayment());
        orderVO.setPaymentType(o.getPaymentType());
        orderVO.setPostage(o.getPostage());
        orderVO.setStatus(o.getStatus());
        orderVO.setOrderItemVoList(list);
        orderVO.setShippingVO(shoppingVO);
        orderVO.setImageHost(PropertiesUtil.getValue("ImageHost"));
        return orderVO;
    }

    // 根据订单详情集合获取OrderItemVO集合
    private List<OrderItemVO> getOrderItemVO(List<ItdrOrderItem> itemList) {
        List<OrderItemVO> itemVOList = new ArrayList<>();
        for (ItdrOrderItem orderItem : itemList) {
            OrderItemVO orderItemVO = ObjectToVOUtil.orderItemToOrderItemVo(orderItem);
            itemVOList.add(orderItemVO);
        }
        return itemVOList;
    }

    // 创建一个订单详情对象
    private List<ItdrOrderItem> getOrderItem(Integer uid, List<ItdrProduct> productList, List<ItdrCar> cartList) {
        List<ItdrOrderItem> orderItemList = new ArrayList<>();
        for (ItdrCar cart : cartList) {
            ItdrOrderItem o = new ItdrOrderItem();
            o.setQuantity(cart.getQuantity());
            for (ItdrProduct product : productList) {
                if (product.getId().equals(cart.getProductid())) {
                    o.setUserid(uid);
                    o.setProductid(cart.getProductid());
                    o.setProductName(product.getPname());
                    o.setProductImage(product.getPimages());
                    o.setCurrentunitprice(product.getPrice());
                    o.setTotalprice(BigDecimalUtil.mul(product.getPrice(), cart.getQuantity()));

                    orderItemList.add(o);
                }
            }
        }
        return orderItemList;
    }


    // 获取用户订单列表
    @Override
    public ServerResponse getOrderList(Integer uid, Integer pageNum, Integer pageSize) {
        List<OrderVO> orderVOList = new ArrayList<>();

        // 获取用户的所有订单
        // 分页处理放在查询语句之上
        PageHelper.startPage(pageNum, pageSize);
        List<ItdrOrder> orderList = orderMapper.selectByUid(uid);

        // 循环创建OrderVO对象
        for (ItdrOrder o : orderList) {
            List<ItdrOrderItem> orderItemList = orderItemMapper.selectByOrderNoAndUserId(o.getOrderNo(), uid);
            // 判断是否有订单
            if (orderItemList.size() == 0) {
                return ServerResponse.defeatedRS
                        (ConstCode.OrderEnum.FAIL_ADD.getCode(), ConstCode.OrderEnum.FAIL_ADD.getDesc());
            }

            List<OrderItemVO> orderItemVOList = this.getOrderItemVO(orderItemList);
            ItdrShopping shipping = shoppingMapper.selectByPrimaryKey(o.getShoppingId());
            ShoppingVO shippingVO = ObjectToVOUtil.shippingToShippingVO(shipping);

            // 封装OrderVO对象，添加至集合中
            OrderVO orderVO = getOrderVO(o, o.getShoppingId(), orderItemVOList, shippingVO);

            orderVOList.add(orderVO);
        }

        PageInfo pageInfo = new PageInfo(orderList);
        // 用setList改变集合中内容为orderVOList
        pageInfo.setList(orderVOList);

        return ServerResponse.successRS(pageInfo);
    }

}
