package com.itdr.service.impl;

import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.mapper.ItdrCarMapper;
import com.itdr.mapper.ItdrOrderMapper;
import com.itdr.pojo.ItdrCar;
import com.itdr.pojo.ItdrUser;
import com.itdr.service.ItdrOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/29 14:02
 */
@Service
public class ItdrOrderServiceImpl implements ItdrOrderService {

    @Autowired
    ItdrOrderMapper orderMapper;
    @Autowired
    ItdrCarMapper cartMapper;

    @Override
    public ServerResponse create(ItdrUser user, Integer shoppingId) {
        //参数非空判断
        if(shoppingId == null || shoppingId < 0){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,
                    ConstCode.UNLAWFULENSS_PARAM);
        }

        //创建
        return null;
    }


}
