package com.itdr.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/19 13:18
 */
@Getter
@Setter
public class UserVO {
    private Integer id;

    private String username;

    private String email;

    private String phone;

    @JsonFormat(locale = "zh", timezone = "GMT-8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(locale = "zh", timezone = "GMT-8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
