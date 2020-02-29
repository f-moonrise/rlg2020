package com.itdr.mapper;

import com.itdr.pojo.ItdrOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItdrOrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItdrOrderItem record);

    int insertSelective(ItdrOrderItem record);

    ItdrOrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItdrOrderItem record);

    int updateByPrimaryKey(ItdrOrderItem record);

    List<ItdrOrderItem> selectByOrderNoAndUserId(@Param("orderNo") Long orderNo, @Param("userid") Integer userid);
}