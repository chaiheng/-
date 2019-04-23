package com.io.east.district.bean;

public class LooKVerifyBean {


    /**
     * code : 1
     * msg : 获取成功
     * data : {"id":1,"user_id":2,"country":"中国","real_name":"张三","id_number":"3303261","img_front":"fdsd","img_reverse":"fdada","status":1,"remark":"","create_time":1553762588}
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
         * id : 1
         * user_id : 2
         * country : 中国
         * real_name : 张三
         * id_number : 3303261
         * img_front : fdsd
         * img_reverse : fdada
         * status : 1
         * remark :
         * create_time : 1553762588
         */

        private int id;
        private int user_id;
        private String country;
        private String real_name;
        private String id_number;
        private String img_front;
        private String img_reverse;
        private int status;
        private String remark;
        private int create_time;

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

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getId_number() {
            return id_number;
        }

        public void setId_number(String id_number) {
            this.id_number = id_number;
        }

        public String getImg_front() {
            return img_front;
        }

        public void setImg_front(String img_front) {
            this.img_front = img_front;
        }

        public String getImg_reverse() {
            return img_reverse;
        }

        public void setImg_reverse(String img_reverse) {
            this.img_reverse = img_reverse;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }
    }
}
