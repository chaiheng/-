package com.io.east.district.bean;

public class RegisterBean {


    /**
     * code : 1
     * msg : 注册成功
     * data : {"token":"2e4901ad86a051cf2ee3e53c636c5f2a3f8ec34df2f67e605e3426626aeee11e","mobile":"15168368215","avatar":"https://qudongdong.oss-cn-hongkong.aliyuncs.com/qdd/1554895365242_userimg.png","nickname":"用户_HIDU4LPo","invitation_code":"0008"}
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
         * token : 2e4901ad86a051cf2ee3e53c636c5f2a3f8ec34df2f67e605e3426626aeee11e
         * mobile : 15168368215
         * avatar : https://qudongdong.oss-cn-hongkong.aliyuncs.com/qdd/1554895365242_userimg.png
         * nickname : 用户_HIDU4LPo
         * invitation_code : 0008
         */

        private String token;
        private String mobile;
        private String avatar;
        private String nickname;
        private String invitation_code;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

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

        public String getInvitation_code() {
            return invitation_code;
        }

        public void setInvitation_code(String invitation_code) {
            this.invitation_code = invitation_code;
        }
    }
}
