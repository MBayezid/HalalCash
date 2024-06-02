package com.mb_lab.halal_cash.DataModels;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

public class HomeNotificationResponse implements Serializable {
  private List<Data> data;

  public List<Data> getData() {
    return this.data;
  }

  public void setData(List<Data> data) {
    this.data = data;
  }

  public static class Data implements Serializable {
    private String image;

    private String description;

    private String title;

    public String getImage() {
      return this.image;
    }

    public void setImage(String image) {
      this.image = image;
    }

    public String getDescription() {
      return this.description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public String getTitle() {
      return this.title;
    }

    public void setTitle(String title) {
      this.title = title;
    }
  }
}
