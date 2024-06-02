package com.mb_lab.halal_cash.DataModels;

import java.io.Serializable;

public class GetReceiverInformationResponse implements Serializable {
    private Data data;

    private String message;

    private Boolean status;

    public Data getData() {
        return this.data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public static class Data implements Serializable {
        private String phone;

        private String name;

        private Integer id;

        private String pay_id;

        private String email;

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

        public Integer getId() {
            return this.id;
        }

        public void setId(Integer id) {
            this.id = id;
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
