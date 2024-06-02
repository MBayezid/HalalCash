package com.mb_lab.halal_cash.DataModels;

import java.io.Serializable;
import java.util.List;

public class UserAdsResponse  implements Serializable {
  private List<Data> data;

  public List<Data> getData() {
    return this.data;
  }

  public void setData(List<Data> data) {
    this.data = data;
  }

  public static class Data implements Serializable {
    private String date;

    private String image;

    private String ad_type;

    private String unit_price_floor;

    private String unit_price_ceil;

    private String payable_with;

    private String visibility_status;

    private String order_limit_max;

    private String permission_status;

    private String user_price;

    private String delete_status;

    private String name;

    private String asset_type;

    private String price_type;

    private Integer id;

    private String advertise_total_amount;

    private String ads_unique_num;

    private String order_limit_min;

    public String getDate() {
      return this.date;
    }

    public void setDate(String date) {
      this.date = date;
    }

    public String getImage() {
      return this.image;
    }

    public void setImage(String image) {
      this.image = image;
    }

    public String getAd_type() {
      return this.ad_type;
    }

    public void setAd_type(String ad_type) {
      this.ad_type = ad_type;
    }

    public String getUnit_price_floor() {
      return this.unit_price_floor;
    }

    public void setUnit_price_floor(String unit_price_floor) {
      this.unit_price_floor = unit_price_floor;
    }

    public String getUnit_price_ceil() {
      return this.unit_price_ceil;
    }

    public void setUnit_price_ceil(String unit_price_ceil) {
      this.unit_price_ceil = unit_price_ceil;
    }

    public String getPayable_with() {
      return this.payable_with;
    }

    public void setPayable_with(String payable_with) {
      this.payable_with = payable_with;
    }

    public String getVisibility_status() {
      return this.visibility_status;
    }

    public void setVisibility_status(String visibility_status) {
      this.visibility_status = visibility_status;
    }

    public String getOrder_limit_max() {
      return this.order_limit_max;
    }

    public void setOrder_limit_max(String order_limit_max) {
      this.order_limit_max = order_limit_max;
    }

    public String getPermission_status() {
      return this.permission_status;
    }

    public void setPermission_status(String permission_status) {
      this.permission_status = permission_status;
    }

    public String getUser_price() {
      return this.user_price;
    }

    public void setUser_price(String user_price) {
      this.user_price = user_price;
    }

    public String getDelete_status() {
      return this.delete_status;
    }

    public void setDelete_status(String delete_status) {
      this.delete_status = delete_status;
    }

    public String getName() {
      return this.name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getAsset_type() {
      return this.asset_type;
    }

    public void setAsset_type(String asset_type) {
      this.asset_type = asset_type;
    }

    public String getPrice_type() {
      return this.price_type;
    }

    public void setPrice_type(String price_type) {
      this.price_type = price_type;
    }

    public Integer getId() {
      return this.id;
    }

    public void setId(Integer id) {
      this.id = id;
    }

    public String getAdvertise_total_amount() {
      return this.advertise_total_amount;
    }

    public void setAdvertise_total_amount(String advertise_total_amount) {
      this.advertise_total_amount = advertise_total_amount;
    }

    public String getAds_unique_num() {
      return this.ads_unique_num;
    }

    public void setAds_unique_num(String ads_unique_num) {
      this.ads_unique_num = ads_unique_num;
    }

    public String getOrder_limit_min() {
      return this.order_limit_min;
    }

    public void setOrder_limit_min(String order_limit_min) {
      this.order_limit_min = order_limit_min;
    }
  }
}
