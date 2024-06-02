package com.mb_lab.halal_cash.DataModels;

import java.io.Serializable;

public class GetGoldPriceResponse implements Serializable {
    private String date;

    private String price;

    private String highest_price;

    private Boolean status;

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHighest_price() {
        return this.highest_price;
    }

    public void setHighest_price(String highest_price) {
        this.highest_price = highest_price;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
