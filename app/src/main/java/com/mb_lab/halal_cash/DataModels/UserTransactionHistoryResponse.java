package com.mb_lab.halal_cash.DataModels;

import java.io.Serializable;
import java.util.List;

public class UserTransactionHistoryResponse  implements Serializable {
  private List<Data> data;

  public List<Data> getData() {
    return this.data;
  }

  public void setData(List<Data> data) {
    this.data = data;
  }

  public static class Data implements Serializable {
    private String date;

    private String note;

    private String amount;

    private String pin;

    private String asset_type;

    private String name;

    private String trans_id;

    private String payId;

    private String userType;

    private String transaction_type;

    private String status;

    public String getDate() {
      return this.date;
    }

    public void setDate(String date) {
      this.date = date;
    }

    public String getNote() {
      return this.note;
    }

    public void setNote(String note) {
      this.note = note;
    }

    public String getAmount() {
      return this.amount;
    }

    public void setAmount(String amount) {
      this.amount = amount;
    }

    public String getPin() {
      return this.pin;
    }

    public void setPin(String pin) {
      this.pin = pin;
    }

    public String getAsset_type() {
      return this.asset_type;
    }

    public void setAsset_type(String asset_type) {
      this.asset_type = asset_type;
    }

    public String getName() {
      return this.name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getTrans_id() {
      return this.trans_id;
    }

    public void setTrans_id(String trans_id) {
      this.trans_id = trans_id;
    }

    public String getPayId() {
      return this.payId;
    }

    public void setPayId(String payId) {
      this.payId = payId;
    }

    public String getUserType() {
      return this.userType;
    }

    public void setUserType(String userType) {
      this.userType = userType;
    }

    public String getTransaction_type() {
      return this.transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
      this.transaction_type = transaction_type;
    }

    public String getStatus() {
      return this.status;
    }

    public void setStatus(String status) {
      this.status = status;
    }
  }
}
