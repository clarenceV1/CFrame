package com.cai.work.ui.web;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSONObject;
import com.cai.framework.utils.LanguageLocalUtil;
import com.cai.framework.web.WebViewFragment;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.WebBinding;
import com.cai.work.event.LoginEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@Route(path = "/MeetOne/WebActivity", name = "web")
public class WebActivity extends AppBaseActivity<WebBinding> implements WebForRTB {

    @Autowired(name = "url")
    String url;
    @Autowired(name = "title")
    String title;
    @Autowired(name = "paramMap")
    String paramString;

    Map<String, String> paramMap;
    WebViewFragment webViewFragment;

    @Inject
    WebPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
        View rootView = findViewById(android.R.id.content);
        if (rootView != null) {
            rootView.setBackgroundResource(com.cai.framework.R.color.white_a);
        }
    }

    @Override
    public void initDagger() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    public void addPresenters(List observerList) {
        observerList.add(presenter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.web;
    }

    @Override
    public void initView() {
        initTitleBar();
        if (!TextUtils.isEmpty(paramString)) {
            paramMap = JSONObject.parseObject(paramString, HashMap.class);
        }
        initUrl();
        initFragment();
    }

    private void initTitleBar() {
        if (!TextUtils.isEmpty(title)) {
            mViewBinding.titleBar.setTitleText(title);
        }else{
            mViewBinding.titleBar.hideTitle();
        }
        mViewBinding.titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    private void initUrl() {
        String paramSt = "";
        if (paramMap != null && paramMap.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                stringBuilder.append(entry.getKey());
                stringBuilder.append("=");
                stringBuilder.append(entry.getValue());
                stringBuilder.append("&");
            }
            int lengh = stringBuilder.length();
            paramSt = stringBuilder.subSequence(0, lengh - 1).toString();
        }
        if (!TextUtils.isEmpty(paramSt)) {
            if (url.contains("?")) {
                url = url + "&" + paramSt;
            } else {
                url = url + "?" + paramSt;
            }
        }
        //
        if (url.contains(App.DOMAIN_NAME) || url.contains(App.H5_NAME) || url.contains(App.H5_CANDY)) {
            //
            if (url.contains("?")) {
                url = url + "&";
            } else {
                url = url + "?";
            }
            url = url + getMyParams();
        }
        Log.e("WebViewActivity", "url=" + url);
    }

    private String getMyParams() {
        StringBuilder sb = new StringBuilder();
        sb.append("auth");
        sb.append("=");
        sb.append(presenter.getToken());
        sb.append("&");
        sb.append("lang");
        sb.append("=");
        sb.append(LanguageLocalUtil.getSystemLanguage().toUpperCase());
        return sb.toString();
    }

    private void initFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(WebViewFragment.KEY_RUL, url);
        webViewFragment = (WebViewFragment) Fragment.instantiate(this, WebViewFragment.class.getName(), bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containLayout, webViewFragment);
        fragmentTransaction.commit();
    }


    private void goBack() {
        if (webViewFragment != null && webViewFragment.canGoBack()) {
            webViewFragment.goBack();
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            goBack();
            return true;
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginEvent(LoginEvent event) {
        if (webViewFragment != null) {
            webViewFragment.reload();
        }
    }
}
