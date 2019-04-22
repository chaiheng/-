package com.io.east.district.bean;

public class MyDepositBean {


    /**
     * code : 1
     * msg : 获取成功
     * data : {"deposit_money":0,"release_money":0,"total_money":0,"dilution_progress":0}
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
         * deposit_money : 0
         * release_money : 0
         * total_money : 0
         * dilution_progress : 0
         */

        private int deposit_money;
        private int release_money;
        private int total_money;
        private int dilution_progress;

        public int getDeposit_money() {
            return deposit_money;
        }

        public void setDeposit_money(int deposit_money) {
            this.deposit_money = deposit_money;
        }

        public int getRelease_money() {
            return release_money;
        }

        public void setRelease_money(int release_money) {
            this.release_money = release_money;
        }

        public int getTotal_money() {
            return total_money;
        }

        public void setTotal_money(int total_money) {
            this.total_money = total_money;
        }

        public int getDilution_progress() {
            return dilution_progress;
        }

        public void setDilution_progress(int dilution_progress) {
            this.dilution_progress = dilution_progress;
        }
    }
}
