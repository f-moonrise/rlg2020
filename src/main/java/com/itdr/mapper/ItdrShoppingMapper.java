package com.itdr.mapper;

import com.itdr.pojo.ItdrShopping;

public interface ItdrShoppingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItdrShopping record);

    int insertSelective(ItdrShopping record);

    ItdrShopping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItdrShopping record);

    int updateByPrimaryKey(ItdrShopping record);
}