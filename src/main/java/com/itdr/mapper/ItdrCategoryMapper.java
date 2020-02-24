package com.itdr.mapper;

import com.itdr.pojo.ItdrCar;
import com.itdr.pojo.ItdrCategory;

import java.util.List;

public interface ItdrCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItdrCategory record);

    int insertSelective(ItdrCategory record);

    ItdrCategory selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItdrCategory record);

    int updateByPrimaryKey(ItdrCategory record);

    List<ItdrCategory> selectByParentID(Integer pid);

    List<ItdrCar> selectByUserID(Integer id);
}