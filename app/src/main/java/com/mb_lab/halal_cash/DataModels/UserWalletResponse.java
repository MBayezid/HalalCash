package com.mb_lab.halal_cash.DataModels;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.String;

public class UserWalletResponse implements Serializable {
  private Wallet wallet;

  private Boolean status;

  public Wallet getWallet() {
    return this.wallet;
  }

  public void setWallet(Wallet wallet) {
    this.wallet = wallet;
  }

  public Boolean getStatus() {
    return this.status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public static class Wallet implements Serializable {
    private String gold;

    private String bdt;

    private String platinium;

    private String palladium;

    private String silver;

    public String getGold() {
      return this.gold;
    }

    public void setGold(String gold) {
      this.gold = gold;
    }

    public String getBdt() {
      return this.bdt;
    }

    public void setBdt(String bdt) {
      this.bdt = bdt;
    }

    public String getPlatinium() {
      return this.platinium;
    }

    public void setPlatinium(String platinium) {
      this.platinium = platinium;
    }

    public String getPalladium() {
      return this.palladium;
    }

    public void setPalladium(String palladium) {
      this.palladium = palladium;
    }

    public String getSilver() {
      return this.silver;
    }

    public void setSilver(String silver) {
      this.silver = silver;
    }
  }
}
