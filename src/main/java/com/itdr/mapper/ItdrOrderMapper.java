package com.itdr.mapper;

import com.itdr.pojo.ItdrOrder;
import org.apache.ibatis.annotations.Param;

public interface ItdrOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItdrOrder record);

    int insertSelective(ItdrOrder record);

    ItdrOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItdrOrder record);

    int updateByPrimaryKey(ItdrOrder record);

    ItdrOrder selectByOrderNoAndUserId(@Param("orderNo") Long orderNo, @Param("userid") Integer userid);

    ItdrOrder selectByOrderNo(Long orderNo);
}