package com.itdr.service.impl;

import com.itdr.common.ServerResponse;
import com.itdr.config.ConstCode;

import com.itdr.config.TokenCache;
import com.itdr.mapper.ItdrUserMapper;
import com.itdr.pojo.ItdrUser;
import com.itdr.service.UserService;
import com.itdr.utils.Filter;
import com.itdr.utils.MD5Util;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/18 20:01
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    ItdrUserMapper itdrUserMapper;

    @Override
    public ServerResponse login(String username, String password) {
        //参数判断
        if(StringUtils.isEmpty(username)){
            return ServerResponse.defeatedRS(
                    //获取错误的状态码和错误的信息
                    ConstCode.ItdrUserEnum.EMPTY_USERNAME.getCode(),
                    ConstCode.ItdrUserEnum.EMPTY_USERNAME.getDesc());
        }
        if(StringUtils.isEmpty(password)){
            return ServerResponse.defeatedRS(
                    //获取错误的状态码和错误的信息
                    ConstCode.ItdrUserEnum.EMPTY_PASSWORD.getCode(),
                    ConstCode.ItdrUserEnum.EMPTY_PASSWORD.getDesc());
        }

        //MD5加密
        String MD5Password = MD5Util.getMD5Code(password);

        //查询用户
        ItdrUser u = itdrUserMapper.selectByUserNameAndPassword(username,MD5Password);
        if(u == null){
            return ServerResponse.defeatedRS(
                    //获取错误的状态码和错误的信息
                    ConstCode.ItdrUserEnum.FAIL_LOGIN.getCode(),
                    ConstCode.ItdrUserEnum.FAIL_LOGIN.getDesc());
        }

        //返回成功数据
        return ServerResponse.successRS(u);
    }

    @SneakyThrows
    @Override
    public ServerResponse<ItdrUser> register(ItdrUser u) {

        //乱码过滤
        String username = Filter.MessyCode(u.getUsername());
        String password = Filter.MessyCode(u.getPassword());
        String  question = Filter.MessyCode(u.getQuestion());
        String answer = Filter.MessyCode(u.getAnswer());

        u.setUsername(username);
        u.setPassword(password);
        u.setQuestion(question);
        u.setAnswer(answer);

        //参数非空判断
        if(StringUtils.isEmpty(u.getUsername())){
            return ServerResponse.defeatedRS(
                    //获取错误的状态码和错误的信息
                    ConstCode.ItdrUserEnum.EMPTY_USERNAME.getCode(),
                    ConstCode.ItdrUserEnum.EMPTY_USERNAME.getDesc());
        }
        if(StringUtils.isEmpty(u.getPassword())){
            return ServerResponse.defeatedRS(
                    //获取错误的状态码和错误的信息
                    ConstCode.ItdrUserEnum.EMPTY_PASSWORD.getCode(),
                    ConstCode.ItdrUserEnum.EMPTY_PASSWORD.getDesc());
        }
        if(StringUtils.isEmpty(u.getQuestion())){
            return ServerResponse.defeatedRS(
                    //获取错误的状态码和错误的信息
                    ConstCode.ItdrUserEnum.EMPTY_QUESTION.getCode(),
                    ConstCode.ItdrUserEnum.EMPTY_QUESTION.getDesc());
        }
        if(StringUtils.isEmpty(u.getAnswer())){
            return ServerResponse.defeatedRS(
                    //获取错误的状态码和错误的信息
                    ConstCode.ItdrUserEnum.EMPTY_ANSWER.getCode(),
                    ConstCode.ItdrUserEnum.EMPTY_ANSWER.getDesc());
        }



        //查找用户是否存在
        ItdrUser i = itdrUserMapper.selectByUserName(u.getUsername());

        if(i != null){
            return ServerResponse.defeatedRS(
                    ConstCode.ItdrUserEnum.EXIST_USER.getCode(),
                    ConstCode.ItdrUserEnum.EXIST_USER.getDesc());
        }

        //MD5加密
        u.setPassword(MD5Util.getMD5Code(u.getPassword()));

        //注册用户信息
        int insert = itdrUserMapper.insert(u);
        //注册失败
        if(insert <= 0){
            return ServerResponse.defeatedRS(
                    ConstCode.ItdrUserEnum.FAIL_REGISTER.getCode(),
                    ConstCode.ItdrUserEnum.FAIL_REGISTER.getDesc());
        }

        //注册成功
        return ServerResponse.successRS(
                ConstCode.DEFAULT_SUCCESS,
                ConstCode.ItdrUserEnum.SUCCESS_USER.getDesc());
    }

    @Override
    public ServerResponse<ItdrUser> getall(ItdrUser u) {
        //参数非空判断
        if(StringUtils.isEmpty(u.getUsername())){
            return ServerResponse.defeatedRS(
                    //获取错误的状态码和错误的信息
                    ConstCode.ItdrUserEnum.EMPTY_USERNAME.getCode(),
                    ConstCode.ItdrUserEnum.EMPTY_USERNAME.getDesc());
        }
        if(StringUtils.isEmpty(u.getPassword())){
            return ServerResponse.defeatedRS(
                    //获取错误的状态码和错误的信息
                    ConstCode.ItdrUserEnum.EMPTY_PASSWORD.getCode(),
                    ConstCode.ItdrUserEnum.EMPTY_PASSWORD.getDesc());
        }

        //查询用户
        ItdrUser u1 = itdrUserMapper.selectByUserNameAndPassword(u.getUsername(),u.getPassword());
        if(u1 != null){
            return ServerResponse.successRS(u1);
        }

        return ServerResponse.defeatedRS(
                //获取错误的状态码和错误的信息
                ConstCode.ItdrUserEnum.FAIL_MSG.getCode(),
                ConstCode.ItdrUserEnum.FAIL_MSG.getDesc());
    }

    @Override
    public ServerResponse<ItdrUser> checkValid(String str, String type) {
        //参数非空判断
        if(StringUtils.isEmpty(str)){
            return ServerResponse.defeatedRS(
                    //获取错误的状态码和错误的信息
                    ConstCode.DEFAULT_FAIL,
                    ConstCode.ItdrUserEnum.EMPTY_USERNAMEOREMATL.getDesc());
        }
        if(StringUtils.isEmpty(type)){
            return ServerResponse.defeatedRS(
                    //获取错误的状态码和错误的信息
                    ConstCode.DEFAULT_FAIL,
                    ConstCode.ItdrUserEnum.EMPTY_PASSWORD.getDesc());
        }
        //查找用户或邮箱是否存在
        int i = itdrUserMapper.selectByUserNameOrEmail(str,type);
        if(i>0){
            return ServerResponse.defeatedRS(
                    ConstCode.ItdrUserEnum.EXIST_USERNAMEOREMATL.getCode(),
                    ConstCode.ItdrUserEnum.EXIST_USERNAMEOREMATL.getDesc());
        }
        return ServerResponse.successRS(
                ConstCode.ItdrUserEnum.SUCCESS_MSG.getDesc()
        );
    }

    @Override
    public ServerResponse<ItdrUser> updateInforamtion(String email, String phone, String question, String answer,ItdrUser user) throws UnsupportedEncodingException {
        //乱码过滤
        email = Filter.MessyCode(email);
        phone = Filter.MessyCode(phone);
        question = Filter.MessyCode(question);
        answer = Filter.MessyCode(answer);

        ItdrUser itdrUser = new ItdrUser();
        itdrUser.setId(user.getId());
        itdrUser.setEmail(email);
        itdrUser.setPhone(phone);
        itdrUser.setQuestion(question);
        itdrUser.setAnswer(answer);

        int i = itdrUserMapper.updateByPrimaryKeySelective(itdrUser);
        if(i <= 0){
            return ServerResponse.defeatedRS(
                    ConstCode.DEFAULT_FAIL,
                    ConstCode.ItdrUserEnum.EMPTY_PARAM2.getDesc()
            );
        }
        return ServerResponse.successRS(ConstCode.ItdrUserEnum.SUCCESS_USERMSG.getDesc());
    }

    @Override
    public ServerResponse<ItdrUser> forgetGetQuestion(String username) {
        //参数判断
        if(StringUtils.isEmpty(username)){
            return ServerResponse.defeatedRS(
                    //获取错误的状态码和错误的信息
                    ConstCode.ItdrUserEnum.EMPTY_USERNAME.getCode(),
                    ConstCode.ItdrUserEnum.EMPTY_USERNAME.getDesc());
        }

        //该用户是否存在(在前端可以通过ajax方式判断)
        ItdrUser u = itdrUserMapper.selectByUserName(username);
        if(u == null){
            return ServerResponse.defeatedRS(
                    ConstCode.ItdrUserEnum.INEXISTENCE_USER.getCode(),
                    ConstCode.ItdrUserEnum.INEXISTENCE_USER.getDesc()
            );
        }

        //获取用户密保问题
        String question = u.getQuestion();
        if(StringUtils.isEmpty(question)){
            return ServerResponse.defeatedRS(
                    //获取错误的状态码和错误的信息
                    ConstCode.ItdrUserEnum.NO_QUESTION.getCode(),
                    ConstCode.ItdrUserEnum.NO_QUESTION.getDesc());
        }
        return ServerResponse.successRS(ConstCode.DEFAULT_SUCCESS,question);
    }

    @Override
    public ServerResponse<ItdrUser> forgetCheckAnswer(String username, String question, String answer) throws UnsupportedEncodingException {
        //乱码过滤
        username = Filter.MessyCode(username);
        question = Filter.MessyCode(question);
        answer = Filter.MessyCode(answer);

        //参数判断
        if(StringUtils.isEmpty(username)){
            return ServerResponse.defeatedRS(
                    //获取错误的状态码和错误的信息
                    ConstCode.ItdrUserEnum.EMPTY_USERNAME.getCode(),
                    ConstCode.ItdrUserEnum.EMPTY_USERNAME.getDesc());
        }
        if(StringUtils.isEmpty(question)){
            return ServerResponse.defeatedRS(
                    //获取错误的状态码和错误的信息
                    ConstCode.ItdrUserEnum.EMPTY_QUESTION.getCode(),
                    ConstCode.ItdrUserEnum.EMPTY_QUESTION.getDesc());
        }
        if(StringUtils.isEmpty(answer)){
            return ServerResponse.defeatedRS(
                    //获取错误的状态码和错误的信息
                    ConstCode.ItdrUserEnum.EMPTY_ANSWER.getCode(),
                    ConstCode.ItdrUserEnum.EMPTY_ANSWER.getDesc());
        }
        //判断答案是否正确
        int i = itdrUserMapper.selectByUserNameAndQuestionAndAnswer(username,question,answer);
        if(i <= 0){
            return ServerResponse.defeatedRS(
                    ConstCode.ItdrUserEnum.ERROR_ANSWER.getCode(),
                    ConstCode.ItdrUserEnum.ERROR_ANSWER.getDesc()
            );
        }
        //返回随机令牌
        String token = UUID.randomUUID().toString();
        //把令牌放入缓存中，这里使用的是Google的guava缓存，后期会使用redis()
        TokenCache.set("token_" + username,token);
        return ServerResponse.successRS(ConstCode.DEFAULT_SUCCESS,token);
    }

    @Override
    public ServerResponse<ItdrUser> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        //参数非空判断
        if(StringUtils.isEmpty(username)){
            return ServerResponse.defeatedRS(
                    //获取错误的状态码和错误的信息
                    ConstCode.ItdrUserEnum.EMPTY_USERNAME.getCode(),
                    ConstCode.ItdrUserEnum.EMPTY_USERNAME.getDesc());
        }
        if(StringUtils.isEmpty(passwordNew)){
            return ServerResponse.defeatedRS(
                    //获取错误的状态码和错误的信息
                    ConstCode.ItdrUserEnum.EMPTY_PASSWORD.getCode(),
                    ConstCode.ItdrUserEnum.EMPTY_PASSWORD.getDesc());
        }
        if(StringUtils.isEmpty(forgetToken)){
            return ServerResponse.defeatedRS(
                    //获取错误的状态码和错误的信息
                    ConstCode.ItdrUserEnum.UNLAWFULNESS_TOKEN.getCode(),
                    ConstCode.ItdrUserEnum.UNLAWFULNESS_TOKEN.getDesc());
        }

        //判断缓存中的token
        String token = TokenCache.get("token_" + username);
        if(token == null || token.equals("")){
            return ServerResponse.defeatedRS(
                    ConstCode.ItdrUserEnum.LOSE_EFFICACY.getCode(),
                    ConstCode.ItdrUserEnum.LOSE_EFFICACY.getDesc()
            );
        }
        if(!token.equals(forgetToken)){
            return ServerResponse.defeatedRS(
                    ConstCode.ItdrUserEnum.UNLAWFULNESS_TOKEN.getCode(),
                    ConstCode.ItdrUserEnum.UNLAWFULNESS_TOKEN.getDesc()
            );
        }

        //MD5加密
        String MD5Password = MD5Util.getMD5Code(passwordNew);

        //重置密码
        int i = itdrUserMapper.updateByUserNameAndPassWordNew(username,MD5Password);
        if(i <= 0){
            return ServerResponse.defeatedRS(
                    ConstCode.ItdrUserEnum.DEFACTED_PASSWORDNEW.getCode(),
                    ConstCode.ItdrUserEnum.DEFACTED_PASSWORDNEW.getDesc()
            );
        }
        return ServerResponse.successRS(
                ConstCode.ItdrUserEnum.SUCCESS_PASSWORDNEW.getCode(),
                ConstCode.ItdrUserEnum.SUCCESS_PASSWORDNEW.getDesc()
        );
    }

    @Override
    public ServerResponse<ItdrUser> resetPassword(ItdrUser itdrUser, String passwordOld, String passwordNew) {
        //参数非空判断
        if(StringUtils.isEmpty(passwordOld)){
            return ServerResponse.defeatedRS(
                    //获取错误的状态码和错误的信息
                    ConstCode.ItdrUserEnum.EMPTY_PASSWORD.getCode(),
                    ConstCode.ItdrUserEnum.EMPTY_PASSWORD.getDesc());
        }
        if(StringUtils.isEmpty(passwordNew)){
            return ServerResponse.defeatedRS(
                    //获取错误的状态码和错误的信息
                    ConstCode.ItdrUserEnum.EMPTY_PASSWORD.getCode(),
                    ConstCode.ItdrUserEnum.EMPTY_PASSWORD.getDesc());
        }

        //MD5加密
        String MD5PasswordOld = MD5Util.getMD5Code(passwordOld);
        String MD5PasswordNew = MD5Util.getMD5Code(passwordNew);

        //更新密码
        int i = itdrUserMapper.updateByUserNameAndPassWordOldAndPassWordNew(itdrUser.getUsername(),MD5PasswordOld,MD5PasswordNew);
        if(i <= 0){
            return ServerResponse.defeatedRS(
                    ConstCode.ItdrUserEnum.DEFACTED_PASSWORDNEW.getCode(),
                    ConstCode.ItdrUserEnum.DEFACTED_PASSWORDNEW.getDesc()
            );
        }
        return ServerResponse.successRS(
                ConstCode.ItdrUserEnum.SUCCESS_PASSWORDNEW.getCode(),
                ConstCode.ItdrUserEnum.SUCCESS_PASSWORDNEW.getDesc()
        );
    }


}
