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

    @RequestMapping("list.do")
    public ServerResponse list(HttpSession session){
        ItdrUser user= (ItdrUser) session.getAttribute("user");
        if(user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.ItdrUserEnum.NO_LOGIN.getDesc());
        }
        return cartService.list(user);
    }

    @RequestMapping("add.do")
    public ServerResponse add(Integer productId,
                              @RequestParam(value = "count", required = false,defaultValue = "1") Integer count,
                              HttpSession session){
        System.out.println(productId+"aaa"+count);
        ItdrUser user= (ItdrUser) session.getAttribute("user");
        if(user == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.UNLAWFULENSS_PARAM);
        }

        return cartService.add(productId,count,user);
    }
}
