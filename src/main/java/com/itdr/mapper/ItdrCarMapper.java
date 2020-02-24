package com.itdr.mapper;

import com.itdr.pojo.ItdrCar;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItdrCarMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItdrCar record);

    int insertSelective(ItdrCar record);

    ItdrCar selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItdrCar record);

    int updateByPrimaryKey(ItdrCar record);

    List<ItdrCar> selectByUserID(Integer id);

    ItdrCar selectByUserIDAndProductId(@Param("userid") Integer userid, @Param("productid") Integer productid);
}