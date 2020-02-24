package com.itdr.controller;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.ItdrCategory;
import com.itdr.pojo.ItdrProduct;
import com.itdr.pojo.vo.ProductVO;
import com.itdr.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/20 12:04
 */
@Controller
@ResponseBody
@RequestMapping("/portal/product/")
public class ProductController {

    @Autowired
    ProductService productService;

    /**
     * 获取商品直接分类
     * @param pid
     * @return
     */
    @RequestMapping("basecategory.do")
    public ServerResponse<ItdrCategory> baseCategory(Integer pid){
        return productService.baseCategory(pid);
    }

    /**
     * 获得商品详细信息
     * @param productId
     * @return
     */
    @RequestMapping("detail.do")
    public ServerResponse<ProductVO> detail(Integer productId){
        return productService.detail(productId);
    }

    /**
     * 商品名称模糊查询
     * @param keyword
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("list.do")
    public ServerResponse<ProductVO> list(String keyword,
          @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
          @RequestParam(value = "pageSize",required = false,defaultValue = "5") Integer pageSize,
          @RequestParam(value = "orderBy",required = false,defaultValue = "") String orderBy
    )throws UnsupportedEncodingException {
        return productService.list(keyword,pageNum,pageSize,orderBy);
    }
}
