package com.itdr.service;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.ItdrUser;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/22 15:22
 */
public interface CartService {
    ServerResponse list(ItdrUser user);

    ServerResponse add(Integer productId, Integer count,Integer type, ItdrUser user);

    ServerResponse update(Integer productId, Integer count,Integer type, ItdrUser user);

    ServerResponse delete(Integer productId, ItdrUser user);

    ServerResponse deleteaLL(ItdrUser user);

    ServerResponse getCartProductCount(ItdrUser user);

    ServerResponse checked(Integer productId,Integer type, ItdrUser user);

    ServerResponse over(ItdrUser user);
}
