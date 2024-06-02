package com.mb_lab.halal_cash.DataModels;

import java.io.Serializable;

public class UserPaySendConfirmationResponse implements Serializable {
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
        private String receiver_pay_id;

        private String note;

        private String amount;

        private Integer sender_pay_id;

        private String asset_type;

        private Integer transaction_pin;

        private String trans_id;

        private String transaction_type;

        public String getReceiver_pay_id() {
            return this.receiver_pay_id;
        }

        public void setReceiver_pay_id(String receiver_pay_id) {
            this.receiver_pay_id = receiver_pay_id;
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

        public Integer getSender_pay_id() {
            return this.sender_pay_id;
        }

        public void setSender_pay_id(Integer sender_pay_id) {
            this.sender_pay_id = sender_pay_id;
        }

        public String getAsset_type() {
            return this.asset_type;
        }

        public void setAsset_type(String asset_type) {
            this.asset_type = asset_type;
        }

        public Integer getTransaction_pin() {
            return this.transaction_pin;
        }

        public void setTransaction_pin(Integer transaction_pin) {
            this.transaction_pin = transaction_pin;
        }

        public String getTrans_id() {
            return this.trans_id;
        }

        public void setTrans_id(String trans_id) {
            this.trans_id = trans_id;
        }

        public String getTransaction_type() {
            return this.transaction_type;
        }

        public void setTransaction_type(String transaction_type) {
            this.transaction_type = transaction_type;
        }
    }
}
