package com.cai.work.ui.presenter;

import android.widget.Toast;

import com.cai.annotation.apt.InstanceFactory;
import com.cai.framework.base.CBasePresenter;
import com.cai.framework.http.Api;
import com.cai.framework.utils.LogUtils;
import com.cai.work.ApiService;
import com.cai.work.bean.User;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by clarence on 2018/1/12.
 */
@InstanceFactory
public class LoginPresenter extends CBasePresenter<LoginView> {
    @Override
    public void onAttached() {
        mView.setLoginContent("我是登录页面");
    }

    public void login() {
        Disposable disposable = Api.getInstance().request().create(ApiService.class).login().subscribe(new Consumer<User>() {

            @Override
            public void accept(User user) throws Exception {

            }
        }, new Consumer<Throwable>() {

            @Override
            public void accept(Throwable throwable) throws Exception {
                LogUtils.d("请求失败");
            }
        });
        mCompositeSubscription.add(disposable);
    }
}
