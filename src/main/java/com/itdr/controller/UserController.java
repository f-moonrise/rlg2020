package com.itdr.controller;

import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;
import com.itdr.pojo.ItdrUser;
import com.itdr.pojo.vo.UserVO;
import com.itdr.service.UserService;
import com.itdr.utils.ObjectToVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/18 15:53
 */
@Controller
@ResponseBody
@RequestMapping("/portal/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("login.do")
    public ServerResponse<ItdrUser> login(String username, String password, HttpSession session){
        ServerResponse sr = userService.login(username,password);
        //当登录成功在session中保存用户数据
        if(sr.isSuccess()){
            session.setAttribute("user",sr.getData());
        }
        return sr;
    }

    /**
     * 注册
     * @param u
     * @return
     */
    @RequestMapping("register.do")
    public ServerResponse<ItdrUser> register(ItdrUser u){
        return userService.register(u);
    }

    /**
     * 获得登录用户信息
     * @param session
     * @return
     */
    @RequestMapping("get_user_info.do")
    public ServerResponse<ItdrUser> getUserInfo(HttpSession session){
        ItdrUser itdrUser = (ItdrUser) session.getAttribute("user");
        UserVO userVO = ObjectToVOUtil.ItdrUserToUserVO(itdrUser);
        return ServerResponse.successRS(userVO);
    }

    /**
     * 获取当前登录用户详细信息
     * @param session
     * @return
     */
    @RequestMapping("get_inforamtion.do")
    public ServerResponse<ItdrUser> getInforamtion(HttpSession session){
        //判断用户是否登录
        ItdrUser itdrUser = (ItdrUser) session.getAttribute("user");
        if(itdrUser == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.ItdrUserEnum.NO_LOGIN.getDesc());
        }
        return ServerResponse.successRS(itdrUser);
    }

    /**
     * 登录状态更新个人信息
     * @param email
     * @param phone
     * @param question
     * @param answer
     * @param session
     * @return
     */
    @RequestMapping("update_inforamtion.do")
    public ServerResponse<ItdrUser> updateInforamtion(String email,String phone,String question,String answer,HttpSession session) throws UnsupportedEncodingException {
        //判断用户是否登录
        ItdrUser itdrUser = (ItdrUser) session.getAttribute("user");

        if(itdrUser == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.ItdrUserEnum.NO_LOGIN.getDesc());
        }
        return userService.updateInforamtion(email,phone,question,answer,itdrUser);
    }

    /**
     * 检查用户是否有效
     * @param str
     * @param type
     * @return
     */
    @RequestMapping("check_valid.do")
    public ServerResponse<ItdrUser> checkValid(String str,String type){
        return userService.checkValid(str,type);
    }

    /**
     * 用户退出
     * @param session
     * @return
     */
    @RequestMapping("logout.do")
    public ServerResponse<ItdrUser> logout(HttpSession session){
        session.removeAttribute("user");
        return ServerResponse.successRS(ConstCode.ItdrUserEnum.LOGOUT.getDesc());
    }

    /**
     * 忘记密码
     * @param username
     * @return
     */
    @RequestMapping("forget_get_question.do")
    public ServerResponse<ItdrUser> forgetGetQuestion(String username){
        return userService.forgetGetQuestion(username);
    }

    /**
     * 提交问题答案
     * @param username
     * @param question
     * @param answer
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("forget_check_answer.do")
    public ServerResponse<ItdrUser> forgetCheckAnswer(String username, String question, String answer) throws UnsupportedEncodingException {
        return userService.forgetCheckAnswer(username,question,answer);
    }

    /**
     * 忘记密码，重置密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @param session
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("forget_reset_password.do")
    public ServerResponse<ItdrUser> forgetResetPassword(String username, String passwordNew, String forgetToken,HttpSession session) throws UnsupportedEncodingException {
        ServerResponse<ItdrUser> userServerResponse = userService.forgetResetPassword(username,passwordNew,forgetToken);
        if(userServerResponse.isSuccess()){
            session.removeAttribute("user");
        }
        return userServerResponse;
    }

    /**
     * 根据旧密码改新密码
     * @param passwordOld
     * @param passwordNew
     * @param session
     * @return
     */
    @RequestMapping("reset_password.do")
    public ServerResponse<ItdrUser> resetPassword(String passwordOld, String passwordNew,HttpSession session){
        //判断用户是否登录
        ItdrUser itdrUser = (ItdrUser) session.getAttribute("user");
        if(itdrUser == null){
            return ServerResponse.defeatedRS(ConstCode.DEFAULT_FAIL,ConstCode.ItdrUserEnum.NO_LOGIN.getDesc());
        }
        return userService.resetPassword(itdrUser,passwordOld,passwordNew);
    }

}
