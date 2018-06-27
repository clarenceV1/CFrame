package com.cai.work.ui.main.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.cai.annotation.aspect.Permission;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.base.App;
import com.cai.work.base.AppBaseFragment;
import com.cai.work.R;
import com.cai.work.bean.Service;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.MainServiceFragmentBinding;

import java.util.List;

import javax.inject.Inject;

/**
 * 咨询
 */
public class MainServiceFragment extends AppBaseFragment<MainServiceFragmentBinding> implements MainServiceView {

    private String phone ;//= "13779926287";
    private String qq ;//= "1990988012";

    @Inject
    MainServicePresenter presenter;
    Service service;

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_service_fragment;
    }

    @Override
    public void initView(View view) {
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.service_title));
        mViewBinding.commonHeadView.ivGoBack.setVisibility(View.GONE);
        mViewBinding.rlOnlineServiece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //第二种方式：可以跳转到添加好友，如果qq号是好友了，直接聊天
                    String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq;//uin是发送过去的qq号码
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mViewBinding.rlTelephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone();
            }
        });
        presenter.requestData();
    }

    @Permission(value = Manifest.permission.CALL_PHONE)
    private void callPhone() {
        //用intent启动拨打电话
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        startActivity(intent);
    }

    @Override
    public void refreshData(List<Service> data) {
        if (data != null && data.size() > 0) {
            service = data.get(0);
            phone = service.getTelephone();
            qq = service.getQq();
            mViewBinding.tvPhone.setText(phone);
        }
    }

    @Override
    public void toast(String msg) {

    }
}
