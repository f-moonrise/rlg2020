package com.itdr.controller;

import com.github.pagehelper.Constant;
import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.pojo.ItdrOrder;
import com.itdr.pojo.ItdrUser;
import com.itdr.service.ItdrOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/29 13:41
 */
@Controller
@ResponseBody
@RequestMapping("/portal/order/")
public class OrderContorller {

    @Autowired
    ItdrOrderService orderService;

    /**
     * 创建订单
     * @param session
     * @param shoppingId
     * @return
     */
    @RequestMapping("create.do")
    public ServerResponse create(HttpSession session, Integer shoppingId){
        ItdrUser user = (ItdrUser) session.getAttribute("user");
        if (user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,
                    ConstCode.ItdrUserEnum.NO_LOGIN.getDesc());
        }
       return orderService.create(user,shoppingId);
    }

    /**
     * 获取订单详情
     * @param session
     * @param orderNum
     * @return
     */
    @RequestMapping("get_order_cart_product.do")
    public ServerResponse getOrderCartProduct(HttpSession session,
                                              @RequestParam(value = "orderNum",required=false) Integer orderNum){
        ItdrUser user = (ItdrUser) session.getAttribute("user");
        if (user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,
                    ConstCode.ItdrUserEnum.NO_LOGIN.getDesc());
        }
        return orderService.getOrderCartProduct(user,orderNum);
    }

    public ServerResponse getOrderList(HttpSession session,
                                       @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize,
                                       @RequestParam(value = "pageNum",required = false,defaultValue = "1")Integer pageNum
                                       ){
        ItdrUser user = (ItdrUser) session.getAttribute("user");
        if (user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,
                    ConstCode.ItdrUserEnum.NO_LOGIN.getDesc());
        }
        return orderService.getOrderList(user.getId(),pageNum,pageSize);
    }
}
