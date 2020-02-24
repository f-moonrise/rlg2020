package com.itdr.mapper;

import com.itdr.pojo.ItdrMybox;

public interface ItdrMyboxMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItdrMybox record);

    int insertSelective(ItdrMybox record);

    ItdrMybox selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItdrMybox record);

    int updateByPrimaryKey(ItdrMybox record);
}