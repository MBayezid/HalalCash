package com.mb_lab.halal_cash.DataModels;

import androidx.annotation.NonNull;

public class UserRegistrationDataModel {
    private String user_id;
    private String user_id_type;
    private String code;
    private String password;
    private String confirm_password;

    public UserRegistrationDataModel(@NonNull String user_id, @NonNull String user_id_type, @NonNull String password, @NonNull String confirm_password) {
        this.user_id = user_id;
        this.user_id_type = user_id_type;
        this.password = password;
        this.confirm_password = confirm_password;
    }

    public UserRegistrationDataModel(@NonNull String user_id, @NonNull String user_id_type, @NonNull String code) {
        this.user_id = user_id;
        this.user_id_type = user_id_type;
        this.code = code;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUser_id_type() {
        return user_id_type;
    }

    public void setUser_id_type(String user_id_type) {
        this.user_id_type = user_id_type;
    }
}
