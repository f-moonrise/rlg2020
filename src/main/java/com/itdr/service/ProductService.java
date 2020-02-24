package com.itdr.service;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.ItdrCategory;
import com.itdr.pojo.ItdrProduct;
import com.itdr.pojo.vo.ProductVO;

import java.io.UnsupportedEncodingException;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/20 18:23
 */
public interface ProductService {
    ServerResponse<ItdrCategory> baseCategory(Integer pid);

    ServerResponse<ProductVO> detail(Integer productId);

    ServerResponse<ProductVO> list(String keyword) throws UnsupportedEncodingException;

    ServerResponse<ProductVO> list(String keyword, Integer pageNum, Integer pageSize, String orderBy) throws UnsupportedEncodingException;
}
