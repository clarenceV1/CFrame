package com.cai.work.ui.rank;

import android.os.Bundle;
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
import com.cai.work.bean.home.HomeRangeData;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.RankBinding;
import com.cai.work.ui.main.fragment.HomeRangeAdapter;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/RankActivity", name = "排行榜")
public class RankActivity extends AppBaseActivity<RankBinding> implements RankView {
    @Inject
    RankPresenter presenter;
    HomeRangeAdapter adapter;
    @Autowired(name = "dataList")
    String dataJson;

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
        mViewBinding.commonHeadView.tvTitle.setText(getString(R.string.rank_title));
        mViewBinding.commonHeadView.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        List<HomeRangeData> data = null;
        if(!TextUtils.isEmpty(dataJson)){
            data = JSON.parseArray(dataJson,HomeRangeData.class);
        }
        adapter = new HomeRangeAdapter(this, data);
        mViewBinding.listView.setAdapter(adapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.rank;
    }
}
