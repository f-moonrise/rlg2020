package com.itdr.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ItdrProduct {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname == null ? null : pname.trim();
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPnum() {
        return pnum;
    }

    public void setPnum(Integer pnum) {
        this.pnum = pnum;
    }

    public String getPimages() {
        return pimages;
    }

    public void setPimages(String pimages) {
        this.pimages = pimages == null ? null : pimages.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
    }

    @Override
    public String toString() {
        return "ItdrProduct{" +
                "id=" + id +
                ", pname='" + pname + '\'' +
                ", price=" + price +
                ", pnum=" + pnum +
                ", pimages='" + pimages + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", categoryid=" + categoryid +
                '}';
    }
}