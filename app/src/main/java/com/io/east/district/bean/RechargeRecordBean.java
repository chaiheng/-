package com.io.east.district.bean;

import java.util.List;

public class RechargeRecordBean {


    /**
     * code : 1
     * msg : 获取成功
     * data : [{"id":6,"money":"10000.00","amount":"3000.02","num":1,"status":1,"pay_status":1,"type":1,"create_time":1554024931,"create_time_text":"2019-03-31 17:35:31","create_time_short":"2019-03-31"},{"id":5,"money":"10000.00","amount":"0.00","num":1,"status":1,"pay_status":1,"type":1,"create_time":1554121621,"create_time_text":"2019-04-01 20:27:01","create_time_short":"2019-04-01"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 6
         * money : 10000.00
         * amount : 3000.02
         * num : 1
         * status : 1
         * pay_status : 1
         * type : 1
         * create_time : 1554024931
         * create_time_text : 2019-03-31 17:35:31
         * create_time_short : 2019-03-31
         */

        private int id;
        private String money;
        private String amount;
        private int num;
        private int status;
        private int pay_status;
        private int type;
        private int create_time;
        private String create_time_text;
        private String create_time_short;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getPay_status() {
            return pay_status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public String getCreate_time_text() {
            return create_time_text;
        }

        public void setCreate_time_text(String create_time_text) {
            this.create_time_text = create_time_text;
        }

        public String getCreate_time_short() {
            return create_time_short;
        }

        public void setCreate_time_short(String create_time_short) {
            this.create_time_short = create_time_short;
        }
    }
}