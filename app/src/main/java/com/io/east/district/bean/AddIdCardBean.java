package com.io.east.district.bean;

public class AddIdCardBean {


    /**
     * code : 1
     * msg : 提交成功
     * data : {"verify_id":1}
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
         * verify_id : 1
         */

        private int verify_id;

        public int getVerify_id() {
            return verify_id;
        }

        public void setVerify_id(int verify_id) {
            this.verify_id = verify_id;
        }
    }
}
