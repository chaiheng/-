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

    /**
     * 登录
     */
    String login = "api/user/login";
    /**
     * 重置密码
     */
    String PasswordReset = "api/user/passwordReset";
    /**
     * 用户退出
     */
    String logout = "api/user/logout";
    /**
     * 小组管理
     */

    String groupManage = "api/people/groupManage";
    /**
     * 获取保证金
     */
    String mydeposit = "api/mydeposit";
    /**
     * 我的业绩
     */
    String performance = "api/people/performance";
    /**
     * 用户是否认证
     */
    String verification = "api/verification";
    /**
     * 获取充值记录
     */
    String rechargeRecord = "api/recharge";


    /**
     * 充值
     */
    String recharge = "api/recharge";
    /**
     * 取消充值  和查看充值
     */
    String cancelRecharge = "api/recharge/";

    /**
     * 我的
     */
    String myassets = "api/myassets";

    /**
     * 资产列表
     */

    String assetflow = "api/assetflow";

    /**
     * 查看资料
     */
    String read = "api/user/read";

    /**
     * 修改资料
     */

    String modified = "api/user";
    /**
     * 公告
     */
    String notice = "api/notice";

    /**
     * 查看 身份认证 状态或添加 身份认证
     */

    String verify = "api/verify";

    /**
     * 修改身份证
     */
    String ChangeVerify = "api/verify/:id";


}
