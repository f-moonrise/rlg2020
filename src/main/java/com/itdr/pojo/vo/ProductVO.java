package com.itdr.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author zangye03@gmail.com
 * @date 2020/2/20 23:18
 */
@Getter
@Setter
public class ProductVO {

    private Integer id;

    private String pname;

    private Integer price;

    private Integer pnum;

    private String pimages;

    @JsonFormat(locale = "zh", timezone = "GMT-8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(locale = "zh", timezone = "GMT-8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private Integer categoryid;
}
