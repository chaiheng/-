package com.h.cheng.chain100.base;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * 作者： ch
 * 描述：
 * 来源：
 */

public abstract class BaseObserver<T> extends DisposableObserver<T> {

    protected BaseView view;
    /**
     * 解析数据失败
     */
    public static final int PARSE_ERROR = 1001;
    /**
     * 网络问题
     */
    public static final int BAD_NETWORK = 1002;
    /**
     * 连接错误
     */
    public static final int CONNECT_ERROR = 1003;
    /**
     * 连接超时
     */
    public static final int CONNECT_TIMEOUT = 1004;

    private boolean isShowDialog;


    public BaseObserver(BaseView view) {
        this.view = view;
    }

    public BaseObserver(BaseView view, boolean isShowDialog) {
        this.view = view;
        this.isShowDialog = isShowDialog;
    }

    @Override
    protected void onStart() {
        if (view != null && isShowDialog) {
            view.showLoading();
        }
    }

    @Override
    public void onNext(T o) {
        try {
            BaseModel model = (BaseModel) o;
            if (model.getErrcode().equals("200")||model.getErrcode().equals("0000")) {
                onSuccess((T) model.getResult());
            } else if (model.getErrmsg() != null) {
                onError(model.getErrmsg());
            } else {
                if (view != null) {
                    view.onErrorCode(model);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            onError(e.toString());
        }


    }

    @Override
    public void onError(Throwable e) {
        if (view != null && isShowDialog) {
            view.hideLoading();
        }
        if (e instanceof HttpException) {
            //   HTTP错误
            onException(BAD_NETWORK, e);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            //   连接错误
            onException(CONNECT_ERROR, e);
        } else if (e instanceof InterruptedIOException) {
            //  连接超时
            onException(CONNECT_TIMEOUT, e);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //  解析错误
            onException(PARSE_ERROR, e);
        } else {
            if (e != null) {
                onError(e.toString());
            } else {
                onError("未知错误");
            }
        }

    }

    private void onException(int unknownError, Throwable e) {
        switch (unknownError) {
            case CONNECT_ERROR:
                onError("请检查网络是否连接");
                break;

            case CONNECT_TIMEOUT:
                onError("请检查网络是否连接");
                break;

            case BAD_NETWORK:
                onError("请检查网络是否连接");
                break;

            case PARSE_ERROR:
                onError("解析数据失败," + e.getCause() + e.getMessage());
                break;

            default:
                break;
        }
    }


    @Override
    public void onComplete() {
        if (view != null && isShowDialog) {
            view.hideLoading();
        }

    }


    public abstract void onSuccess(T o);

    public abstract void onError(String msg);


}
