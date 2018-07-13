package com.cai.work.ui.about;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.utils.LanguageLocalUtil;
import com.cai.work.BuildConfig;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.common.Constant;
import com.cai.work.databinding.AboutBinding;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route(path = "/MeetOne/AboutActivity", name = "关于我们")
public class AboutActivity extends AppBaseActivity<AboutBinding> implements AboutView {


    @Override
    public void initDagger() {

    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {

    }

    @Override
    public void initView() {
        mViewBinding.titleBar.setTitleText(getString(R.string.about));
        mViewBinding.titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.llAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = Constant.H5_NAME + "/en/help.html";
                if (LanguageLocalUtil.isChinese()) {
                    url = Constant.H5_NAME + "/help.html";
                }
                Map<String, String> params = new HashMap<>();
                params.put("timestamp", System.currentTimeMillis() + "");
                ARouter.getInstance().build("/MeetOne/WebActivity")
                        .withCharSequence("title", getString(R.string.about))
                        .withCharSequence("url", url)
                        .withCharSequence("paramMap", JSON.toJSONString(params))
                        .navigation();
            }
        });
        mViewBinding.llDisclaimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<>();
                params.put("timestamp", System.currentTimeMillis() + "");
                ARouter.getInstance().build("/MeetOne/WebActivity")
                        .withCharSequence("title", getString(R.string.about_disclaimer))
                        .withCharSequence("url", Constant.H5_NAME + "/statement.html")
                        .withCharSequence("paramMap", JSON.toJSONString(params))
                        .navigation();
            }
        });
        showDebug();
    }

    private void showDebug() {
        if (BuildConfig.DEBUG) {
            findViewById(R.id.llDebug).setVisibility(View.VISIBLE);
            findViewById(R.id.llDebug).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().build("/MeetOne/DebugActivity").navigation();
                }
            });
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.about;
    }
}
