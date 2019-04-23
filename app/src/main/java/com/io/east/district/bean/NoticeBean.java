package com.io.east.district.bean;

import java.util.List;

public class NoticeBean {


    /**
     * code : 1
     * msg : 获取成功
     * data : [{"id":2,"title":"2","content":"222","createtime":"2019-03-28 16:43"},{"id":1,"title":"标题","content":"内容。。。","createtime":"2019-03-28 16:43"}]
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
         * id : 2
         * title : 2
         * content : 222
         * createtime : 2019-03-28 16:43
         */

        private int id;
        private String title;
        private String content;
        private String createtime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }
    }
}
