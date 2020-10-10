package com.itdr.service;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.ItdrUser;

import java.util.Map;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/26 18:43
 */
public interface AliPayService {
    ServerResponse pay(ItdrUser user, Integer orderNo);

    ServerResponse alipayCallback(Map<String, String> params);

    ServerResponse queryOrderPayStatus(ItdrUser user, Integer orderNo);
}
