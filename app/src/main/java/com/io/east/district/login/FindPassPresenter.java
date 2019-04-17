package com.io.east.district.login;

import com.io.east.district.base.BaseObserver;
import com.io.east.district.base.BasePresenter;
import com.io.east.district.bean.FindPassBean;

/**
 * 作者： ch
 * 时间： 2018/3/21 0021-下午 4:13
 * 描述：
 * 来源：
 */


public class FindPassPresenter extends BasePresenter<FindPassView> {
    public FindPassPresenter(FindPassView baseView) {
        super(baseView);
    }


    /**
     * 发送验证码
     *
     * @param phone
     */
    public void SendMsg(String phone) {
        addDisposable(apiServer.SendMsg(phone), new BaseObserver<String>(baseView) {
            @Override
            public void onSuccess(String o) {
                baseView.onSendSucc();

            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);

            }
        });
    }

    /**
     * 找回密码
     */
    public void findpass(FindPassBean mp) {

        addDisposable(apiServer.findpass(mp), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onFindSucc();

            }

            @Override
            public void onError(String msg) {
                baseView.showError(msg);

            }
        });
    }


}
