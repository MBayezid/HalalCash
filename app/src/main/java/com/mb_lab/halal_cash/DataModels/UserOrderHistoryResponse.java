package com.mb_lab.halal_cash.DataModels;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

public class UserOrderHistoryResponse implements Serializable {
  private List<Data> data;

  public List<Data> getData() {
    return this.data;
  }

  public void setData(List<Data> data) {
    this.data = data;
  }

  public static class Data implements Serializable {
    private String date;

    private String buyer_email;

    private String seller_email;

    private String receivable_asset_type;

    private String method;

    private String ad_type;

    private String buyer_image;

    private String buyer_name;

    private String receivable_amount;

    private String seller_image;

    private String user_price;

    private String seller_name;

    private String price_type;

    private String ad_trans_id;

    private String payable_amount;

    private String advertise_total_amount;

    private String ads_unique_num;

    private String payable_asset_type;

    public String getDate() {
      return this.date;
    }

    public void setDate(String date) {
      this.date = date;
    }

    public String getBuyer_email() {
      return this.buyer_email;
    }

    public void setBuyer_email(String buyer_email) {
      this.buyer_email = buyer_email;
    }

    public String getSeller_email() {
      return this.seller_email;
    }

    public void setSeller_email(String seller_email) {
      this.seller_email = seller_email;
    }

    public String getReceivable_asset_type() {
      return this.receivable_asset_type;
    }

    public void setReceivable_asset_type(String receivable_asset_type) {
      this.receivable_asset_type = receivable_asset_type;
    }

    public String getMethod() {
      return this.method;
    }

    public void setMethod(String method) {
      this.method = method;
    }

    public String getAd_type() {
      return this.ad_type;
    }

    public void setAd_type(String ad_type) {
      this.ad_type = ad_type;
    }

    public String getBuyer_image() {
      return this.buyer_image;
    }

    public void setBuyer_image(String buyer_image) {
      this.buyer_image = buyer_image;
    }

    public String getBuyer_name() {
      return this.buyer_name;
    }

    public void setBuyer_name(String buyer_name) {
      this.buyer_name = buyer_name;
    }

    public String getReceivable_amount() {
      return this.receivable_amount;
    }

    public void setReceivable_amount(String receivable_amount) {
      this.receivable_amount = receivable_amount;
    }

    public String getSeller_image() {
      return this.seller_image;
    }

    public void setSeller_image(String seller_image) {
      this.seller_image = seller_image;
    }

    public String getUser_price() {
      return this.user_price;
    }

    public void setUser_price(String user_price) {
      this.user_price = user_price;
    }

    public String getSeller_name() {
      return this.seller_name;
    }

    public void setSeller_name(String seller_name) {
      this.seller_name = seller_name;
    }

    public String getPrice_type() {
      return this.price_type;
    }

    public void setPrice_type(String price_type) {
      this.price_type = price_type;
    }

    public String getAd_trans_id() {
      return this.ad_trans_id;
    }

    public void setAd_trans_id(String ad_trans_id) {
      this.ad_trans_id = ad_trans_id;
    }

    public String getPayable_amount() {
      return this.payable_amount;
    }

    public void setPayable_amount(String payable_amount) {
      this.payable_amount = payable_amount;
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

    public String getPayable_asset_type() {
      return this.payable_asset_type;
    }

    public void setPayable_asset_type(String payable_asset_type) {
      this.payable_asset_type = payable_asset_type;
    }
  }
}
