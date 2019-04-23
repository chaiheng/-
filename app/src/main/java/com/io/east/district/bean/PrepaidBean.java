package com.io.east.district.bean;

public class PrepaidBean {


    /**
     * code : 1
     * msg : 获取成功
     * data : {"id":6,"user_id":2,"trans_sn":"2019040151100485","money":"10000.00","amount":"3000.02","num":1,"status":1,"pay_status":1,"pay_time":1554706646,"type":1,"create_time":1554024931,"is_verify":0,"is_partner":1,"countdown":200,"total_money":10000,"gift":1000,"user_bta":{"user_id":2,"bta_id":2,"address":"0xface4f43660b70b855a557706d2b3e5c3004f49b"},"create_time_text":"2019-03-31 17:35:31","create_time_short":"2019-03-31"}
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
         * id : 6
         * user_id : 2
         * trans_sn : 2019040151100485
         * money : 10000.00
         * amount : 3000.02
         * num : 1
         * status : 1
         * pay_status : 1
         * pay_time : 1554706646
         * type : 1
         * create_time : 1554024931
         * is_verify : 0
         * is_partner : 1
         * countdown : 200
         * total_money : 10000
         * gift : 1000
         * user_bta : {"user_id":2,"bta_id":2,"address":"0xface4f43660b70b855a557706d2b3e5c3004f49b"}
         * create_time_text : 2019-03-31 17:35:31
         * create_time_short : 2019-03-31
         */

        private int id;
        private int user_id;
        private String trans_sn;
        private String money;
        private String amount;
        private int num;
        private int status;
        private int pay_status;
        private int pay_time;
        private int type;
        private int create_time;
        private int is_verify;
        private int is_partner;
        private int countdown;
        private int total_money;
        private int gift;
        private UserBtaBean user_bta;
        private String create_time_text;
        private String create_time_short;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getTrans_sn() {
            return trans_sn;
        }

        public void setTrans_sn(String trans_sn) {
            this.trans_sn = trans_sn;
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

        public int getPay_time() {
            return pay_time;
        }

        public void setPay_time(int pay_time) {
            this.pay_time = pay_time;
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

        public int getCountdown() {
            return countdown;
        }

        public void setCountdown(int countdown) {
            this.countdown = countdown;
        }

        public int getTotal_money() {
            return total_money;
        }

        public void setTotal_money(int total_money) {
            this.total_money = total_money;
        }

        public int getGift() {
            return gift;
        }

        public void setGift(int gift) {
            this.gift = gift;
        }

        public UserBtaBean getUser_bta() {
            return user_bta;
        }

        public void setUser_bta(UserBtaBean user_bta) {
            this.user_bta = user_bta;
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

        public static class UserBtaBean {
            /**
             * user_id : 2
             * bta_id : 2
             * address : 0xface4f43660b70b855a557706d2b3e5c3004f49b
             */

            private int user_id;
            private int bta_id;
            private String address;

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getBta_id() {
                return bta_id;
            }

            public void setBta_id(int bta_id) {
                this.bta_id = bta_id;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }
    }
}
