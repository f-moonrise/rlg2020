package com.itdr.service;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.ItdrUser;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/29 14:01
 */
public interface ItdrOrderService {
    ServerResponse create(ItdrUser user, Integer shoppingId);

}
