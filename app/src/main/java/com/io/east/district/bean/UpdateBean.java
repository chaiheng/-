package com.io.east.district.bean;

public class UpdateBean {


    /**
     * code : 1
     * msg : 获取版本成功
     * data : {"oldversion":"1.1.1","newversion":"1.2.1","packagesize":"20M","content":"更新内容","downloadurl":"https://www.qdd.world/download.html","enforce":1}
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
         * oldversion : 1.1.1
         * newversion : 1.2.1
         * packagesize : 20M
         * content : 更新内容
         * downloadurl : https://www.qdd.world/download.html
         * enforce : 1
         */

        private String oldversion;
        private String newversion;
        private String packagesize;
        private String content;
        private String downloadurl;
        private int enforce;

        public String getOldversion() {
            return oldversion;
        }

        public void setOldversion(String oldversion) {
            this.oldversion = oldversion;
        }

        public String getNewversion() {
            return newversion;
        }

        public void setNewversion(String newversion) {
            this.newversion = newversion;
        }

        public String getPackagesize() {
            return packagesize;
        }

        public void setPackagesize(String packagesize) {
            this.packagesize = packagesize;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDownloadurl() {
            return downloadurl;
        }

        public void setDownloadurl(String downloadurl) {
            this.downloadurl = downloadurl;
        }

        public int getEnforce() {
            return enforce;
        }

        public void setEnforce(int enforce) {
            this.enforce = enforce;
        }
    }
}
