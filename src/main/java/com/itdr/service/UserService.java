package com.itdr.service;

import com.itdr.common.ServerResponse;
import com.itdr.pojo.ItdrUser;

import java.io.UnsupportedEncodingException;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/18 20:00
 */
public interface UserService {
    ServerResponse login(String username, String password);

    ServerResponse<ItdrUser> register(ItdrUser u);

    ServerResponse<ItdrUser> getall(ItdrUser u);

    ServerResponse<ItdrUser> checkValid(String str, String type);

    ServerResponse<ItdrUser> updateInforamtion(String email, String phone, String question, String answer,ItdrUser itdrUser) throws UnsupportedEncodingException;

    ServerResponse<ItdrUser> forgetGetQuestion(String username);

    ServerResponse<ItdrUser> forgetCheckAnswer(String username, String question, String answer) throws UnsupportedEncodingException;

    ServerResponse<ItdrUser> forgetResetPassword(String username, String passwordNew, String forgetToken);

    ServerResponse<ItdrUser> resetPassword(ItdrUser itdrUser,String passwordOld, String passwordNew);
}
