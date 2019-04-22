package com.io.east.district.bean;

public class PartnerBean {


    /**
     * code : 1
     * msg : 获取成功
     * data : {"deposit_money":2000,"release_money":9000,"dilution_progress":82,"renewal_partner":1,"today_sum":0,"week_sum":0,"month_sum":0,"group_count":5,"total_sum":0,"beat":0,"is_verify":0}
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
         * deposit_money : 2000
         * release_money : 9000
         * dilution_progress : 82
         * renewal_partner : 1
         * today_sum : 0
         * week_sum : 0
         * month_sum : 0
         * group_count : 5
         * total_sum : 0
         * beat : 0
         * is_verify : 0
         */

        private int deposit_money;
        private int release_money;
        private int dilution_progress;
        private int renewal_partner;
        private int today_sum;
        private int week_sum;
        private int month_sum;
        private int group_count;
        private int total_sum;
        private int beat;
        private int is_verify;

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

        public int getDilution_progress() {
            return dilution_progress;
        }

        public void setDilution_progress(int dilution_progress) {
            this.dilution_progress = dilution_progress;
        }

        public int getRenewal_partner() {
            return renewal_partner;
        }

        public void setRenewal_partner(int renewal_partner) {
            this.renewal_partner = renewal_partner;
        }

        public int getToday_sum() {
            return today_sum;
        }

        public void setToday_sum(int today_sum) {
            this.today_sum = today_sum;
        }

        public int getWeek_sum() {
            return week_sum;
        }

        public void setWeek_sum(int week_sum) {
            this.week_sum = week_sum;
        }

        public int getMonth_sum() {
            return month_sum;
        }

        public void setMonth_sum(int month_sum) {
            this.month_sum = month_sum;
        }

        public int getGroup_count() {
            return group_count;
        }

        public void setGroup_count(int group_count) {
            this.group_count = group_count;
        }

        public int getTotal_sum() {
            return total_sum;
        }

        public void setTotal_sum(int total_sum) {
            this.total_sum = total_sum;
        }

        public int getBeat() {
            return beat;
        }

        public void setBeat(int beat) {
            this.beat = beat;
        }

        public int getIs_verify() {
            return is_verify;
        }

        public void setIs_verify(int is_verify) {
            this.is_verify = is_verify;
        }
    }
}
