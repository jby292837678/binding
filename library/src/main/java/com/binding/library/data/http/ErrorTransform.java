package com.binding.library.data.http;

import com.binding.library.data.entity.InfoEntity;

import java.util.ServiceConfigurationError;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * Created by Zane on 16/4/9.
 * 更优雅的转换Observable去统一处理错误
 */
public class ErrorTransform<T> implements ObservableTransformer<InfoEntity<T>, InfoEntity<T>> {
    private int code = 1;
    private String errorMessage = "";

    public ErrorTransform() {}

    @Override
    public ObservableSource<InfoEntity<T>> apply(Observable<InfoEntity<T>> upstream) {
        return upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> {
                    code = 99999;
                    if (throwable instanceof HttpException) {
                        HttpException response = (HttpException) throwable;
                        code = response.code();
                        switch (response.code()) {
                            case 401:errorMessage = "token无效" + response.message();
//                                App.getApp().logout();
//                                BaseUtil.navigation(Router.login);
                            break;
                            case 402:errorMessage = "数据库连接错误" + response.message();break;
                            case 403:errorMessage = "无记录" + response.message();break;
                            case 405:errorMessage = "token无效" + response.message();break;
                            case 400:errorMessage = "参数为空" + response.message();break;
                            default:errorMessage = "未知错误" + response.message();break;
                        }
                    } else if (throwable instanceof ServiceConfigurationError) {
                        errorMessage = "服务器错误";
                    } else {
                        errorMessage = "网络错误";
                        throwable.printStackTrace();
                    }
                    return Observable.create(subscriber -> {
                        InfoEntity<T> infoEntity = new InfoEntity<T>();
                        infoEntity.setError_code(code);
                        infoEntity.setMessage(errorMessage);
                        subscriber.onNext(infoEntity);
                    });
                });
    }

}
