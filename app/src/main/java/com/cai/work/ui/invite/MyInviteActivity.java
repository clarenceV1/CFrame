package com.cai.work.ui.invite;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.R;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.InviteOne;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.MyInviteBinding;
import com.example.clarence.utillibrary.StringUtils;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@Route(path = "/AppModule/MyInviteActivity", name = "我的邀请")
public class MyInviteActivity extends AppBaseActivity<MyInviteBinding> implements MyInviteView {
    @Inject
    MyInvitePresenter presenter;
    @Autowired(name = "inviteOne")
    String inviteOneJson;
    @Autowired(name = "inviteTwo")
    String inviteTwoJson;

    List<InviteOne> oneInvite;
    List<InviteOne> twoInvite;
    int selectedTabType = 1;

    Map<String, WeakReference<Fragment>> fragmentMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public void initView() {
        initData();
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.invite_my_titile));
        mViewBinding.tvTabLeft.setText(StringUtils.buildString(getString(R.string.invite_one), "(", oneInvite.size(), ")"));
        mViewBinding.tvTabRight.setText(StringUtils.buildString(getString(R.string.invite_two), "(", twoInvite.size(), ")"));

        mViewBinding.rlTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTabType == 1) {
                    return;
                }
                selectedTabType = 1;
                switchTab();
            }
        });
        mViewBinding.rlTab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTabType == 2) {
                    return;
                }
                selectedTabType = 2;
                switchTab();
            }
        });
        switchTab();
    }

    private void initData() {
        if (!TextUtils.isEmpty(inviteOneJson)) {
            oneInvite = JSON.parseArray(inviteOneJson, InviteOne.class);
        } else {
            oneInvite = new ArrayList<>();
        }
        if (!TextUtils.isEmpty(inviteTwoJson)) {
            twoInvite = JSON.parseArray(inviteTwoJson, InviteOne.class);
        } else {
            twoInvite = new ArrayList<>();
        }
    }

    private void switchTab() {
        if (selectedTabType == 1) {
            mViewBinding.bottomLine1.setVisibility(View.VISIBLE);
            mViewBinding.bottomLine2.setVisibility(View.GONE);
            mViewBinding.tvTabLeft.setTextColor(getResources().getColor(R.color.home_forward_tab_color_selected));
            mViewBinding.tvTabRight.setTextColor(getResources().getColor(R.color.home_forward_tab_color));
        } else {
            mViewBinding.bottomLine1.setVisibility(View.GONE);
            mViewBinding.bottomLine2.setVisibility(View.VISIBLE);
            mViewBinding.tvTabLeft.setTextColor(getResources().getColor(R.color.home_forward_tab_color));
            mViewBinding.tvTabRight.setTextColor(getResources().getColor(R.color.home_forward_tab_color_selected));
        }
        Fragment fragment;
        WeakReference<Fragment> weakReference = fragmentMap.get("Left");
        if (weakReference != null) {
            fragment = weakReference.get();
        } else {
            if (selectedTabType == 1) { //LEFT
                fragment = getForwardFragment("left", oneInvite);
                fragmentMap.put("left", new WeakReference<>(fragment));
            } else {//RIGHT
                fragment = getForwardFragment("right", twoInvite);
                fragmentMap.put("right", new WeakReference<>(fragment));
            }
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    public <T> Fragment getForwardFragment(String type, List<T> data) {
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putSerializable("dataList", (Serializable) data);
        return Fragment.instantiate(this, MyInviteFragment.class.getName(), bundle);
    }

    @Override
    public int getLayoutId() {
        return R.layout.my_invite;
    }

}
