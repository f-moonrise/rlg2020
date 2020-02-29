package com.itdr.mapper;

import com.itdr.pojo.ItdrPayinfo;

public interface ItdrPayinfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItdrPayinfo record);

    int insertSelective(ItdrPayinfo record);

    ItdrPayinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItdrPayinfo record);

    int updateByPrimaryKey(ItdrPayinfo record);
}