package com.io.east.district.bean;

public class PerformanceBean {


    /**
     * code : 1
     * msg : 获取成功
     * data : {"total_sum":0,"month_sum":0,"month_wait_sum":0,"month_received_sum":0,"today_sum":0,"week_sum":0}
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
         * total_sum : 0
         * month_sum : 0
         * month_wait_sum : 0
         * month_received_sum : 0
         * today_sum : 0
         * week_sum : 0
         */

        private int total_sum;
        private int month_sum;
        private int month_wait_sum;
        private int month_received_sum;
        private int today_sum;
        private int week_sum;

        public int getTotal_sum() {
            return total_sum;
        }

        public void setTotal_sum(int total_sum) {
            this.total_sum = total_sum;
        }

        public int getMonth_sum() {
            return month_sum;
        }

        public void setMonth_sum(int month_sum) {
            this.month_sum = month_sum;
        }

        public int getMonth_wait_sum() {
            return month_wait_sum;
        }

        public void setMonth_wait_sum(int month_wait_sum) {
            this.month_wait_sum = month_wait_sum;
        }

        public int getMonth_received_sum() {
            return month_received_sum;
        }

        public void setMonth_received_sum(int month_received_sum) {
            this.month_received_sum = month_received_sum;
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
    }
}
