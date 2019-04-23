package com.io.east.district.bean;

import java.util.List;

public class AssetsListBean {


    /**
     * code : 1
     * msg : 获取成功
     * data : {"myassets":{"money":0,"freeze_money":0,"total_assets":0},"data":[{"id":3,"user_id":2,"commission_id":1,"type":1,"money":"1.00","commission":"1.00","describe":"","status":1,"recorded_time":"2019-04-02","create_time":"2019-04-08","commission_user":{"id":1,"mobile":"13888888888"}},{"id":1,"user_id":2,"commission_id":1,"type":1,"money":"1.01","commission":"1.01","describe":"","status":1,"recorded_time":"2019-04-03","create_time":"2019-04-03","commission_user":{"id":1,"mobile":"13888888888"}}]}
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
         * myassets : {"money":0,"freeze_money":0,"total_assets":0}
         * data : [{"id":3,"user_id":2,"commission_id":1,"type":1,"money":"1.00","commission":"1.00","describe":"","status":1,"recorded_time":"2019-04-02","create_time":"2019-04-08","commission_user":{"id":1,"mobile":"13888888888"}},{"id":1,"user_id":2,"commission_id":1,"type":1,"money":"1.01","commission":"1.01","describe":"","status":1,"recorded_time":"2019-04-03","create_time":"2019-04-03","commission_user":{"id":1,"mobile":"13888888888"}}]
         */

        private MyassetsBean myassets;
        private List<DataBean> data;

        public MyassetsBean getMyassets() {
            return myassets;
        }

        public void setMyassets(MyassetsBean myassets) {
            this.myassets = myassets;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class MyassetsBean {
            /**
             * money : 0
             * freeze_money : 0
             * total_assets : 0
             */

            private int money;
            private int freeze_money;
            private int total_assets;

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
        }

        public static class DataBean {
            /**
             * id : 3
             * user_id : 2
             * commission_id : 1
             * type : 1
             * money : 1.00
             * commission : 1.00
             * describe :
             * status : 1
             * recorded_time : 2019-04-02
             * create_time : 2019-04-08
             * commission_user : {"id":1,"mobile":"13888888888"}
             */

            private int id;
            private int user_id;
            private int commission_id;
            private int type;
            private String money;
            private String commission;
            private String describe;
            private int status;
            private String recorded_time;
            private String create_time;
            private CommissionUserBean commission_user;

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

            public int getCommission_id() {
                return commission_id;
            }

            public void setCommission_id(int commission_id) {
                this.commission_id = commission_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getCommission() {
                return commission;
            }

            public void setCommission(String commission) {
                this.commission = commission;
            }

            public String getDescribe() {
                return describe;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getRecorded_time() {
                return recorded_time;
            }

            public void setRecorded_time(String recorded_time) {
                this.recorded_time = recorded_time;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public CommissionUserBean getCommission_user() {
                return commission_user;
            }

            public void setCommission_user(CommissionUserBean commission_user) {
                this.commission_user = commission_user;
            }

            public static class CommissionUserBean {
                /**
                 * id : 1
                 * mobile : 13888888888
                 */

                private int id;
                private String mobile;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }
            }
        }
    }
}
