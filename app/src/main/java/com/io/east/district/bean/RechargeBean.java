package com.io.east.district.bean;

public class RechargeBean {


    /**
     * code : 1
     * msg : 提交成功
     * data : {"recharge_id":1,"is_already":1}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * recharge_id : 1
         * is_already : 1
         */

        private int recharge_id;
        private int is_already;

        public int getRecharge_id() {
            return recharge_id;
        }

        public void setRecharge_id(int recharge_id) {
            this.recharge_id = recharge_id;
        }

        public int getIs_already() {
            return is_already;
        }

        public void setIs_already(int is_already) {
            this.is_already = is_already;
        }
    }
}
