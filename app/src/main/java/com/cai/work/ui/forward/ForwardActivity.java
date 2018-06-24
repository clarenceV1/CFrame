package com.cai.work.ui.forward;

import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.widget.spiner.SpinerPopWindow;
import com.cai.work.R;
import com.cai.work.base.AppBaseActivity;
import com.cai.work.bean.Forward;
import com.cai.work.bean.ForwardRecord;
import com.cai.work.dagger.component.DaggerAppComponent;
import com.cai.work.databinding.ForwardBinding;
import com.example.clarence.utillibrary.DimensUtils;
import com.example.clarence.utillibrary.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@Route(path = "/AppModule/ForwardActivity", name = "站内公告")
public class ForwardActivity extends AppBaseActivity<ForwardBinding> implements ForwardView {

    @Inject
    ForwardPresenter presenter;
    ForwardAdapter adapter;
    SpinerPopWindow spinerPopWindow;
    int spinerPopwindowHeight;

    @Override
    public void initDagger() {
        DaggerAppComponent.create().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.forward;
    }

    @Override
    public void initView() {
        spinerPopwindowHeight = DimensUtils.dip2px(this, 300);
        mViewBinding.ivGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.tvRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/AppModule/WebActivity").withCharSequence("url", "http://m.hellceshi.com/tpl/app/futures_rule.html").withCharSequence("title", "期货规则").navigation();
            }
        });
        mViewBinding.spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSpinWindow();
            }
        });
        adapter = new ForwardAdapter(this);
        spinerPopWindow = new SpinerPopWindow(this);
        spinerPopWindow.setAdatper(adapter);
        spinerPopWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Forward forward = (Forward) adapter.getItem(position);
                mViewBinding.tvTitle.setText(forward.getName());
                spinerPopWindow.dismiss();
            }
        });
        presenter.requestRecord();
        presenter.requestContracts();
    }

    //设置PopWindow
    private void showSpinWindow() {
        int spinerWith = mViewBinding.spinner.getWidth();
        //设置mSpinerPopWindow显示的宽度
        spinerPopWindow.setWidth(spinerWith);
        spinerPopWindow.setHeight(spinerPopwindowHeight);
        //设置显示的位置在哪个控件的下方
        spinerPopWindow.showAsDropDown(mViewBinding.spinner);
    }


    @Override
    public void toast(String msg, int type) {
        ToastUtils.showShort(msg);
    }

    @Override
    public void callBack(ForwardRecord forwardRecord) {

    }

    @Override
    public void callBack(List<Forward> forwardList) {
        adapter.update(forwardList);
        mViewBinding.tvTitle.setText(forwardList.get(0).getName());
    }
}
