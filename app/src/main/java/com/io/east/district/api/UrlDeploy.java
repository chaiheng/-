package com.io.east.district.api;

public interface UrlDeploy {

    //     正式地址
//    String BaseUrl = "http://www.qdd.com/ ";
//       测试地址
    String BaseUrl = "http://test.qdd.world  ";
    /**
     * 获取人脉
     */
    String people = "api/people";
    /**
     * 注册
     */
    String register = "api/user/register";
    /**
     * 手机验证码
     */

    String verification_code = "api/verification_code/send";

    /**  登录
     * */
    String login ="api/user/login";
    /**  重置密码
     * */
    String PasswordReset ="api/user/passwordReset";
    /**   用户退出
     * */
    String logout ="api/user/logout";
    /**  小组管理
     * */

    String groupManage ="api/people/groupManage";
    /**  获取保证金
     * */
    String mydeposit ="api/mydeposit";
    /**   我的业绩
     * */
    String performance ="api/people/performance";
    /**  用户是否认证
     * */
    String verification ="api/verification";
/**
 *   获取充值记录
 * */
    String rechargeRecord  ="api/recharge";


    /**  充值
     *
     * */
    String recharge  ="api/recharge";
}
