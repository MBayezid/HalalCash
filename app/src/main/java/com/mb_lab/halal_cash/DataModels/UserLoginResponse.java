package com.mb_lab.halal_cash.DataModels;

import java.io.Serializable;

public class UserLoginResponse   implements Serializable {
    private String message;

    private User user;

    private String token;

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class User implements Serializable {
        private String image;

        private String account_type;

        private String phone;

        private String name;

        private Boolean verified;

        private Integer id;

        private Boolean is_authenticated;

        private String pay_id;

        private String email;

        public String getImage() {
            return this.image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAccount_type() {
            return this.account_type;
        }

        public void setAccount_type(String account_type) {
            this.account_type = account_type;
        }

        public String getPhone() {
            return this.phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getVerified() {
            return this.verified;
        }

        public void setVerified(Boolean verified) {
            this.verified = verified;
        }

        public Integer getId() {
            return this.id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Boolean getIs_authenticated() {
            return this.is_authenticated;
        }

        public void setIs_authenticated(Boolean is_authenticated) {
            this.is_authenticated = is_authenticated;
        }

        public String getPay_id() {
            return this.pay_id;
        }

        public void setPay_id(String pay_id) {
            this.pay_id = pay_id;
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
