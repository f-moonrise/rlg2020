package com.itdr.mapper;

import com.itdr.pojo.ItdrUser;
import org.apache.ibatis.annotations.Param;

public interface ItdrUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ItdrUser record);

    int insertSelective(ItdrUser record);

    ItdrUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ItdrUser record);

    int updateByPrimaryKey(ItdrUser record);

    ItdrUser selectByUserNameAndPassword(String username, String password);

    ItdrUser selectByUserName(String username);

    int selectByUserNameOrEmail(@Param("str") String str, @Param("type") String type);

    int selectByUserNameAndQuestionAndAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    int updateByUserNameAndPassWordNew(@Param("username") String username, @Param("passwordNew") String passwordNew);

    int updateByUserNameAndPassWordOldAndPassWordNew(@Param("username") String username, @Param("passwordOld") String passwordOld, @Param("passwordNew") String passwordNew);
}