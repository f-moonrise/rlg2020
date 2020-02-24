package com.itdr.service;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.ItdrUser;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/22 15:22
 */
public interface CartService {
    ServerResponse list(ItdrUser user);

    ServerResponse add(Integer productId, Integer count, ItdrUser user);
}
