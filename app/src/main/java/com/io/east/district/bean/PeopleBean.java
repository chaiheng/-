package com.io.east.district.bean;

public class PeopleBean {

    /**
     * code : 1
     * msg : 获取成功
     * data : {"is_verify":0,"is_partner":0,"is_already":0,"recharge_id":0}
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
         * is_verify : 0
         * is_partner : 0
         * is_already : 0
         * recharge_id : 0
         */

        private int is_verify;
        private int is_partner;
        private int is_already;
        private int recharge_id;

        public int getIs_verify() {
            return is_verify;
        }

        public void setIs_verify(int is_verify) {
            this.is_verify = is_verify;
        }

        public int getIs_partner() {
            return is_partner;
        }

        public void setIs_partner(int is_partner) {
            this.is_partner = is_partner;
        }

        public int getIs_already() {
            return is_already;
        }

        public void setIs_already(int is_already) {
            this.is_already = is_already;
        }

        public int getRecharge_id() {
            return recharge_id;
        }

        public void setRecharge_id(int recharge_id) {
            this.recharge_id = recharge_id;
        }
    }
}
