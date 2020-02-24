package com.itdr.mapper;

import com.itdr.pojo.ItdrProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItdrProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItdrProduct record);

    int insertSelective(ItdrProduct record);

    ItdrProduct selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItdrProduct record);

    int updateByPrimaryKey(ItdrProduct record);

    List<ItdrProduct> selectByName(String keyWord);

    List<ItdrProduct> selectByNameAndOrderBy(@Param("keyWord") String keyWord,
                                             @Param("filed") String filed
                                            );
}