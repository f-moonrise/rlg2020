package com.itdr.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ItdrCar {
    private Integer id;

    private Integer userid;

    private Integer productid;

    private Integer pnum;

    private String pimages;

    private Integer sumprice;

    private Integer status;

    @JsonFormat(locale = "zh", timezone = "GMT-8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    @JsonFormat(locale = "zh", timezone = "GMT-8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatetime;

    private Integer quantity;

    private Integer checked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
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

    public Integer getSumprice() {
        return sumprice;
    }

    public void setSumprice(Integer sumprice) {
        this.sumprice = sumprice;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "ItdrCar{" +
                "id=" + id +
                ", userid=" + userid +
                ", productid=" + productid +
                ", pnum=" + pnum +
                ", pimages='" + pimages + '\'' +
                ", sumprice=" + sumprice +
                ", status=" + status +
                ", createtime=" + createtime +
                ", updatetime=" + updatetime +
                ", quantity=" + quantity +
                ", checked=" + checked +
                '}';
    }
}