package com.mb_lab.halal_cash.DataModels;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

public class GetDepositAgentListResponse implements Serializable {
  private List<Data> data;

  public List<Data> getData() {
    return this.data;
  }

  public void setData(List<Data> data) {
    this.data = data;
  }

  public static class Data implements Serializable {
    private String number;

    private String name;

    private String payment_method;

    public String getNumber() {
      return this.number;
    }

    public void setNumber(String number) {
      this.number = number;
    }

    public String getName() {
      return this.name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getPayment_method() {
      return this.payment_method;
    }

    public void setPayment_method(String payment_method) {
      this.payment_method = payment_method;
    }
  }
}
