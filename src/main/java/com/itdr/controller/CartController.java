package com.itdr.controller;

import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.pojo.ItdrUser;
import com.itdr.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/22 11:13
 */
@Controller
@ResponseBody
@RequestMapping("/portal/cart/")
public class CartController {

    @Autowired
    CartService cartService;

    /**
     * 购物车列表
     * @param session
     * @return
     */
    @RequestMapping("list.do")
    public ServerResponse list(HttpSession session){
        ItdrUser user= (ItdrUser) session.getAttribute("user");
        if(user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.ItdrUserEnum.NO_LOGIN.getDesc());
        }
        return cartService.list(user);
    }

    /**
     * 购物车增加或更新商品
     * @param productId
     * @param count
     * @param session
     * @return
     */
    @RequestMapping("update.do")
    public ServerResponse update(Integer productId,
                              @RequestParam(value = "count", required = false,defaultValue = "1") Integer count,
                                 @RequestParam(value = "type", required = false,defaultValue = "0") Integer type,
                                 HttpSession session){
//        System.out.println(productId+"aaa"+count);
        ItdrUser user= (ItdrUser) session.getAttribute("user");
        if(user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.UNLAWFULENSS_PARAM);
        }

        return cartService.update(productId,count,type,user);
    }

    /**
     * 购物车更新商品数量
     * @param productId
     * @param count
     * @param type
     * @param session
     * @return
     */
    @RequestMapping("add.do")
    public ServerResponse add(Integer productId,
                                 @RequestParam(value = "count", required = false,defaultValue = "1") Integer count,
                                 @RequestParam(value = "type", required = false,defaultValue = "0") Integer type,
                                 HttpSession session){
//        System.out.println(productId+"aaa"+count);
        ItdrUser user= (ItdrUser) session.getAttribute("user");
        if(user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.UNLAWFULENSS_PARAM);
        }

        return cartService.add(productId,count,type,user);
    }

    /**
     *  删除一条信息
     * @param productId
     * @param session
     * @return
     */
    @RequestMapping("delete.do")
    public ServerResponse delete(Integer productId, HttpSession session){
//        System.out.println(productId+"aaa"+count);
        ItdrUser user= (ItdrUser) session.getAttribute("user");
        if(user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.UNLAWFULENSS_PARAM);
        }
        return cartService.delete(productId,user);
    }

    /**
     * 删除选中多条数据
     * @param session
     * @return
     */
    @RequestMapping("deleteALL.do")
    public ServerResponse delete(HttpSession session){
        ItdrUser user= (ItdrUser) session.getAttribute("user");
        if(user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.UNLAWFULENSS_PARAM);
        }
        return cartService.deleteaLL(user);
    }

    /**
     * 获取购物车商品数量
     * @param session
     * @return
     */
    @RequestMapping("get_cart_product_count.do")
    public ServerResponse getCartProductCount(HttpSession session){
        ItdrUser user= (ItdrUser) session.getAttribute("user");
        if(user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.UNLAWFULENSS_PARAM);
        }
        return cartService.getCartProductCount(user);
    }

    @RequestMapping("checked.do")
    public ServerResponse checked(Integer productId,
                                  @RequestParam(value = "type", required = false,defaultValue = "0") Integer type,
                                  HttpSession session){
        ItdrUser user= (ItdrUser) session.getAttribute("user");
        if(user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.UNLAWFULENSS_PARAM);
        }
        System.out.println(productId);
        System.out.println(type);
        return cartService.checked(productId,type,user);
    }

    /**
     * 购物车去结算
     * @param session
     * @return
     */
    @RequestMapping("over.do")
    public ServerResponse over(HttpSession session){
        ItdrUser user = (ItdrUser) session.getAttribute("user");
        if (user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,
                    ConstCode.ItdrUserEnum.NO_LOGIN.getDesc());
        }
        return cartService.over(user);
    }
}
