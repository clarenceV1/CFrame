package com.meetone.work.ui.nationcode;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.meetone.work.R;
import com.meetone.work.databinding.NationCodeBinding;
import com.example.clarence.utillibrary.ToastUtils;
import com.meetone.work.base.App;
import com.meetone.work.base.AppBaseActivity;
import com.meetone.work.bean.NationCodeModel;
import com.meetone.work.widget.WaveSideBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@Route(path = "/MeetOne/NationCodeActivity", name = "国家编号")
public class NationCodeActivity extends AppBaseActivity<NationCodeBinding> implements NationCodeView {

    SortAdapter mAdapter;
    NationCodeListAdapter searchAdatper;
    List<NationCodeModel> nationCodeModels;
    public boolean isChinese;//是否是中文
    LinearLayoutManager manager;
    TitleItemDecoration mDecoration;

    @Inject
    NationCodePresenter presenter;

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
        mViewBinding.titleBar.setTitleText(getString(R.string.choose_country));
        mViewBinding.titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mViewBinding.recyclerView.setLayoutManager(manager);

        //设置右侧SideBar触摸监听
        mViewBinding.sideBar.setOnTouchLetterChangeListener(new WaveSideBar.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(letter.charAt(0));
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }
        });

        mViewBinding.edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                }
                return false;
            }
        });
        mViewBinding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showSearchResult(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        searchAdatper = new NationCodeListAdapter(this, nationCodeModels);
        mViewBinding.searchListView.setAdapter(searchAdatper);
        mViewBinding.searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NationCodeModel data = searchAdatper.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("code", data);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        mViewBinding.searchContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //none
            }
        });
        mAdapter = new SortAdapter(this);
        mAdapter.setOnItemClickListener(new SortAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NationCodeModel data = mAdapter.getItem(position);
                Intent intent = new Intent();
                intent.putExtra("code", data);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        mViewBinding.recyclerView.setAdapter(mAdapter);
        mDecoration = new TitleItemDecoration(this);
        //如果add两个，那么按照先后顺序，依次渲染。
        mViewBinding.recyclerView.addItemDecoration(mDecoration);
        mViewBinding.recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        presenter.loadNationCode();
    }

    private void showSearchResult(String searchKey) {
        if (nationCodeModels == null) {
            return;
        }
        List<NationCodeModel> result = new ArrayList<>();
        for (int i = 0; i < nationCodeModels.size(); i++) {
            NationCodeModel temp = nationCodeModels.get(i);
            if (isChinese) {
                if (temp.getContry().contains(searchKey)) {
                    result.add(temp);
                }
            } else {
                if (temp.getContry().toLowerCase().startsWith(searchKey.toLowerCase())) {
                    result.add(temp);
                }
            }
        }
        if (result.size() > 0) {
            List<NationCodeModel> chinaList = new ArrayList<>();
            for (NationCodeModel nationCodeModel : result) {
                if (nationCodeModel.getCode().equals("86")) {
                    chinaList.add(nationCodeModel);
                }
            }
            if (chinaList.size() > 1) {//中文有2个中国文案
                result.remove(chinaList.get(0));
            }
            mViewBinding.searchContainer.setVisibility(View.VISIBLE);
            searchAdatper.update(result);
        } else {
            mViewBinding.searchContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.nation_code;
    }

    @Override
    public void callback(List<NationCodeModel> data, boolean isChinese) {
        this.isChinese = isChinese;
        nationCodeModels = data;
        mAdapter.updateList(nationCodeModels);
        mDecoration.update(nationCodeModels);
    }

    @Override
    public void callback(String message) {
        ToastUtils.showShort(message);
    }
}
