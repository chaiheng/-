package com.io.east.district.bean;

public class AssetsBean {


    /**
     * code : 1
     * msg : 获取成功
     * data : {"mobile":"29832892","avatar":"","nickname":"123","money":0,"freeze_money":0,"total_assets":0,"identity":1}
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
         * mobile : 29832892
         * avatar :
         * nickname : 123
         * money : 0
         * freeze_money : 0
         * total_assets : 0
         * identity : 1
         */

        private String mobile;
        private String avatar;
        private String nickname;
        private int money;
        private int freeze_money;
        private int total_assets;
        private int identity;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public int getFreeze_money() {
            return freeze_money;
        }

        public void setFreeze_money(int freeze_money) {
            this.freeze_money = freeze_money;
        }

        public int getTotal_assets() {
            return total_assets;
        }

        public void setTotal_assets(int total_assets) {
            this.total_assets = total_assets;
        }

        public int getIdentity() {
            return identity;
        }

        public void setIdentity(int identity) {
            this.identity = identity;
        }
    }
}
