package com.itdr.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.itdr.config.ConstCode;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/18 13:07
 */
@Getter
@Setter
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//序列化json时，时null的对象，key也会消失
public class ServerResponse<T> implements Serializable {
    private Integer status;
    private T data;
    private String msg;

    private ServerResponse(Integer status) {
        this.status = status;
    }

    private ServerResponse(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(Integer status, T data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    private ServerResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ServerResponse(String msg) {
        this.status = ConstCode.DEFAULT_FAIL;
        this.msg = msg;
    }


    public static <T> ServerResponse successRS() {
        return new ServerResponse(ConstCode.DEFAULT_SUCCESS);
    }

    public static <T> ServerResponse successRS(String msg) {
        return new ServerResponse(ConstCode.DEFAULT_SUCCESS,msg);
    }

    public static <T> ServerResponse successRS(T data) {
        return new ServerResponse(ConstCode.DEFAULT_SUCCESS,data);
    }

    public static <T> ServerResponse successRS(Integer status,T data) {
        return new ServerResponse(status,data);
    }


    //    成功的时候传入状态码、数据、信息
    public static <T> ServerResponse successRS(Integer status, T data, String msg) {
        return new ServerResponse(status,data, msg);
    }

    public static <T> ServerResponse defeatedRS(Integer errorCode, String errorMessage) {
        return new ServerResponse(errorCode, errorMessage);
    }

    public static <T> ServerResponse defeatedRS(String errorMessage) {
        return new ServerResponse(ConstCode.DEFAULT_FAIL,errorMessage);
    }

    @JsonIgnore
//让success不在json序列化结果之中
    public boolean isSuccess() {
        return this.status == ConstCode.DEFAULT_SUCCESS;
    }

}