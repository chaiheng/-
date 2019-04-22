package com.io.east.district.bean;

import java.util.List;

public class GroupManageBean {


    /**
     * code : 1
     * msg : 获取成功
     * data : {"mygroup":{"avatar":"https://qudongdong.oss-cn-hongkong.aliyuncs.com/qdd/1554895365242_userimg.png","nickname":"admin","identity":2,"total_sum":3.09,"total_group":5,"total_partner":3},"data":[{"invitation_id":2,"user_id":6,"level":1,"group_count":1,"partner_count":1,"total_amount_sum":0.05,"user":{"id":6,"nickname":"13588909621","total_amount":"2.00","is_partner":1}},{"invitation_id":2,"user_id":3,"level":1,"group_count":3,"partner_count":2,"total_amount_sum":0.04,"user":{"id":3,"nickname":"13588909618","total_amount":"0.00","is_partner":0}},{"invitation_id":2,"user_id":25,"level":1,"group_count":0,"partner_count":0,"total_amount_sum":0,"user":{"id":25,"nickname":"兔斯基贝","total_amount":"1.00","is_partner":1}}]}
     */

    private int code;
    private String msg;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * mygroup : {"avatar":"https://qudongdong.oss-cn-hongkong.aliyuncs.com/qdd/1554895365242_userimg.png","nickname":"admin","identity":2,"total_sum":3.09,"total_group":5,"total_partner":3}
         * data : [{"invitation_id":2,"user_id":6,"level":1,"group_count":1,"partner_count":1,"total_amount_sum":0.05,"user":{"id":6,"nickname":"13588909621","total_amount":"2.00","is_partner":1}},{"invitation_id":2,"user_id":3,"level":1,"group_count":3,"partner_count":2,"total_amount_sum":0.04,"user":{"id":3,"nickname":"13588909618","total_amount":"0.00","is_partner":0}},{"invitation_id":2,"user_id":25,"level":1,"group_count":0,"partner_count":0,"total_amount_sum":0,"user":{"id":25,"nickname":"兔斯基贝","total_amount":"1.00","is_partner":1}}]
         */

        private MygroupBean mygroup;
        private List<DataBean> data;

        public MygroupBean getMygroup() {
            return mygroup;
        }

        public void setMygroup(MygroupBean mygroup) {
            this.mygroup = mygroup;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class MygroupBean {
            /**
             * avatar : https://qudongdong.oss-cn-hongkong.aliyuncs.com/qdd/1554895365242_userimg.png
             * nickname : admin
             * identity : 2
             * total_sum : 3.09
             * total_group : 5
             * total_partner : 3
             */

            private String avatar;
            private String nickname;
            private int identity;
            private double total_sum;
            private int total_group;
            private int total_partner;

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

            public int getIdentity() {
                return identity;
            }

            public void setIdentity(int identity) {
                this.identity = identity;
            }

            public double getTotal_sum() {
                return total_sum;
            }

            public void setTotal_sum(double total_sum) {
                this.total_sum = total_sum;
            }

            public int getTotal_group() {
                return total_group;
            }

            public void setTotal_group(int total_group) {
                this.total_group = total_group;
            }

            public int getTotal_partner() {
                return total_partner;
            }

            public void setTotal_partner(int total_partner) {
                this.total_partner = total_partner;
            }
        }

        public static class DataBean {
            /**
             * invitation_id : 2
             * user_id : 6
             * level : 1
             * group_count : 1
             * partner_count : 1
             * total_amount_sum : 0.05
             * user : {"id":6,"nickname":"13588909621","total_amount":"2.00","is_partner":1}
             */

            private int invitation_id;
            private int user_id;
            private int level;
            private int group_count;
            private int partner_count;
            private double total_amount_sum;
            private UserBean user;

            public int getInvitation_id() {
                return invitation_id;
            }

            public void setInvitation_id(int invitation_id) {
                this.invitation_id = invitation_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getGroup_count() {
                return group_count;
            }

            public void setGroup_count(int group_count) {
                this.group_count = group_count;
            }

            public int getPartner_count() {
                return partner_count;
            }

            public void setPartner_count(int partner_count) {
                this.partner_count = partner_count;
            }

            public double getTotal_amount_sum() {
                return total_amount_sum;
            }

            public void setTotal_amount_sum(double total_amount_sum) {
                this.total_amount_sum = total_amount_sum;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public static class UserBean {
                /**
                 * id : 6
                 * nickname : 13588909621
                 * total_amount : 2.00
                 * is_partner : 1
                 */

                private int id;
                private String nickname;
                private String total_amount;
                private int is_partner;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public String getTotal_amount() {
                    return total_amount;
                }

                public void setTotal_amount(String total_amount) {
                    this.total_amount = total_amount;
                }

                public int getIs_partner() {
                    return is_partner;
                }

                public void setIs_partner(int is_partner) {
                    this.is_partner = is_partner;
                }
            }
        }
    }
}
