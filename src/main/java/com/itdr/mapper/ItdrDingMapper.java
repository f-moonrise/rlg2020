package com.itdr.mapper;

import com.itdr.pojo.ItdrDing;

public interface ItdrDingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItdrDing record);

    int insertSelective(ItdrDing record);

    ItdrDing selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItdrDing record);

    int updateByPrimaryKey(ItdrDing record);
}