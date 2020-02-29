package com.itdr.pojo;

import java.util.Date;

public class ItdrOrderItem {
    private Integer id;

    private Integer userid;

    private Integer orderNo;

    private Integer productid;

    private String productName;

    private String productImage;

    private Integer currentunitprice;

    private Integer quantity;

    private Integer totalprice;

    private Date createTime;

    private Date updateTime;

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

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    @Override
    public String toString() {
        return "ItdrOrderItem{" +
                "id=" + id +
                ", userid=" + userid +
                ", orderNo=" + orderNo +
                ", productid=" + productid +
                ", productName='" + productName + '\'' +
                ", productImage='" + productImage + '\'' +
                ", currentunitprice=" + currentunitprice +
                ", quantity=" + quantity +
                ", totalprice=" + totalprice +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage == null ? null : productImage.trim();
    }

    public Integer getCurrentunitprice() {
        return currentunitprice;
    }

    public void setCurrentunitprice(Integer currentunitprice) {
        this.currentunitprice = currentunitprice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Integer totalprice) {
        this.totalprice = totalprice;
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
}