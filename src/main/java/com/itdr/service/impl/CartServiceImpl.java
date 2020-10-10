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
    protected CartVO getCartVO(List<ItdrCar> cartList){
        //获取购物车对应的商品信息]
        List<CartProductVO> cartProductVOList = new ArrayList<CartProductVO>();


        boolean bol = true;
        Integer cartTotalPrice = 0;
        for(ItdrCar cart : cartList){
            ItdrProduct product = productMapper.selectByPrimaryKey(cart.getProductid());

            //把商品和购物车信息进行数据封装
            if(product != null){

                CartProductVO cartProductVO = ObjectToVOUtil.cartAndProductToVO(cart, product);
                cartProductVOList.add(cartProductVO);


                //计算购物车总价,只计算选中的商品
                if(cartProductVO.getChecked() == 1 && cartProductVO.getLimitQuantity() == "LIMIT_NUM_SUCCESS"){
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice,cartProductVO.getSumprice());
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
    //要添加的商品是否在售
    private ServerResponse<ItdrProduct> online(Integer productId){
        ItdrProduct product = productMapper.selectByPrimaryKey(productId);
        if(product == null || product.getPnum() < 0){
            return ServerResponse.defeatedRS(
                    ConstCode.ItdrCategory.INEXISTENCE_PRODUCT.getCode(),
                    ConstCode.ItdrCategory.INEXISTENCE_PRODUCT.getDesc()
            );
        }
        return ServerResponse.successRS(product);
    }
    //返回成功后封装好的对象
    private ServerResponse getSuccess(ItdrUser user){
        ServerResponse<List<ItdrCar>> carList = getCarList(user);
        if(!carList.isSuccess()){
            return carList;
        }
        CartVO cartVO = getCartVO(carList.getData());

        return ServerResponse.successRS(cartVO);
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
    public ServerResponse add(Integer productId, Integer count, Integer type,ItdrUser user) {
        //参数合法判断
        if(productId == null || productId < 0){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.UNLAWFULENSS_PARAM);
        }
//        System.out.println("aaaaaaaaaaaaaaaaaaaaaaa");
//        System.out.println(productId+count+type);
        //要添加的商品是否在售
        ItdrProduct product = productMapper.selectByPrimaryKey(productId);
        if(product == null || product.getPnum() < 0){
//            System.out.println("bbbbbbbbbbbbbbbbb");
            return ServerResponse.defeatedRS(
                    ConstCode.ItdrCategory.INEXISTENCE_PRODUCT.getCode(),
                    ConstCode.ItdrCategory.INEXISTENCE_PRODUCT.getDesc()
            );
        }

//        ServerResponse online = online(productId);
//        if(online(productId).isSuccess()){
//            return online;
//        }
//        if(count > online.getData().getPnum()){
//            return ServerResponse.defeatedRS(
//                    ConstCode.ItdrCategory.BEYOND_STOCK.getCode(),
//                    ConstCode.ItdrCategory.BEYOND_STOCK.getDesc()
//            );
//        }
//        System.out.println("ccccccccccccccccccccc");
//        添加商品库存有没有超出
        if(count > product.getPnum()){
//            System.out.println("dddddddddddddddddddddd");
            return ServerResponse.defeatedRS(
              ConstCode.ItdrCategory.BEYOND_STOCK.getCode(),
              ConstCode.ItdrCategory.BEYOND_STOCK.getDesc()
            );
        }
//        System.out.println("eeeeeeeeeeeeeeeeee");
        //向购物车中增加或更新一条数据
        ItdrCar c = new ItdrCar();
        c.setUserid(user.getId());
        c.setProductid(productId);
        c.setPnum(count);

//        System.out.println("fffffffffffffffff");
        //查询要添加的数据是否存在
        ItdrCar cart = carMapper.selectByUserIDAndProductId(user.getId(),productId);
//        System.out.println("gggggggggggggggggggggg");
        if(cart == null){
            int insert = carMapper.insert(c);
            if(insert <= 0){
                return ServerResponse.defeatedRS(
                        ConstCode.ItdrCategory.FAIL_ADD.getCode(),
                        ConstCode.ItdrCategory.FAIL_ADD.getDesc());
            }
        }else {
            //根据type判断要执行的更新方法
            if(type == ConstCode.CartEnum.CART_TYPE.getCode()){
                cart.setPnum(count+cart.getPnum());
            }else if(type == 1){
                cart.setPnum(count);
            }
            //更新数据库中的数据
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
//            System.out.println(cartVO+"cartvo");
            return ServerResponse.successRS(cartVO);

    }

    @Override
    public ServerResponse update(Integer productId, Integer count,Integer type, ItdrUser user) {
        //参数合法判断
        if(productId == null || productId < 0){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.UNLAWFULENSS_PARAM);
        }
        if(count == null || count <= 0){
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

        //查询要更新的数据
        ItdrCar cart = carMapper.selectByUserIDAndProductId(user.getId(),productId);
        //根据type判断要执行的更新方法
        if(type == ConstCode.CartEnum.CART_TYPE.getCode()){
                cart.setPnum(count+cart.getPnum());
            }else if(type == 1){
                cart.setPnum(count);
            }
            //更新数据库中的数据
            int i = carMapper.updateByPrimaryKey(cart);
            if(i <= 0){
                return ServerResponse.defeatedRS(
                        ConstCode.ItdrCategory.FAIL_ADD.getCode(),
                        ConstCode.ItdrCategory.FAIL_ADD.getDesc());
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

    @Override
    public ServerResponse delete(Integer productId, ItdrUser user) {
        //参数合法判断
        if(productId == null || productId < 0){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.UNLAWFULENSS_PARAM);
        }
        //移除购物车中对应的商品
        int i = carMapper.deleteByUserIDAndProductId(user.getId(),productId);
        if(i <= 0){
            return ServerResponse.defeatedRS(ConstCode.CartEnum.FAIL_ADD.getCode(),
                    ConstCode.CartEnum.FAIL_ADD.getDesc());
        }
        return getSuccess(user);
    }

    @Override
    public ServerResponse deleteaLL(ItdrUser user) {
        //移除购物车中对应的商品
        int i = carMapper.deleteByUserIDAndChecked(user.getId());
        if(i <= 0){
            return ServerResponse.defeatedRS(ConstCode.CartEnum.FAIL_ADD.getCode(),
                    ConstCode.CartEnum.FAIL_ADD.getDesc());
        }
        return getSuccess(user);
    }

    @Override
    public ServerResponse getCartProductCount(ItdrUser user) {
        List<ItdrCar> cartList = carMapper.selectByUserID(user.getId());
        Integer num = 0;
        for(ItdrCar cart : cartList){
            num += cart.getPnum();
        }
        return ServerResponse.successRS(num);
    }

    @Override
    public ServerResponse checked(Integer productId,Integer type, ItdrUser user) {
        if(productId != null && productId < 0){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.UNLAWFULENSS_PARAM);
        }
        if(type == null || type < 0){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.UNLAWFULENSS_PARAM);
        }
        //选中或取消选中一个商品
        System.out.println(user.getId());
        System.out.println(productId);
        System.out.println(type);
        int i = carMapper.updateByUserIdOrProductId(user.getId(),productId,type);
        if(i <= 0){
            return ServerResponse.defeatedRS(
                    ConstCode.DEFAULT_FAIL,ConstCode.CartEnum.FAIL_STATE.getDesc());
        }

        return list(user);
    }

    @Override
    public ServerResponse over(ItdrUser user) {
        //判断当前用户购物车中有没有数据
        List<ItdrCar> cartList = carMapper.selectByUserID(user.getId());
        if(cartList.size() == 0){
            return ServerResponse.defeatedRS(
                    ConstCode.CartEnum.EMPTY_CART.getCode(),
                    ConstCode.CartEnum.EMPTY_CART.getDesc());
        }

        //购物车中商品没有被选中
        boolean bol = false;
        for (ItdrCar cart : cartList) {
            if(cart.getChecked()== 1){
                bol=true;
            }
        }
        if(!bol){
            return ServerResponse.defeatedRS(
                    ConstCode.CartEnum.NOSELECT_PRODUCT.getCode(),
                    ConstCode.CartEnum.NOSELECT_PRODUCT.getDesc());
        }
        return ServerResponse.successRS(true);
    }


}
