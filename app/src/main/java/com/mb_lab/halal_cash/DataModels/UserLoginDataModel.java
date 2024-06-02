package com.mb_lab.halal_cash.DataModels;

import androidx.annotation.NonNull;

public class UserLoginDataModel {
    private String user_id;
    private String user_id_type;
    private String password;

    public UserLoginDataModel(@NonNull String user_id, @NonNull String user_id_type, @NonNull String password) {
        this.user_id = user_id;
        this.user_id_type = user_id_type;
        this.password = password;

    }
    public UserLoginDataModel( @NonNull String user_id_type ,@NonNull String user_id) {
        this.user_id = user_id;
        this.user_id_type = user_id_type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id_type() {
        return user_id_type;
    }

    public void setUser_id_type(String user_id_type) {
        this.user_id_type = user_id_type;
    }
}
