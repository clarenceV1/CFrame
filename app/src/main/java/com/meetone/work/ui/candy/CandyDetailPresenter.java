package com.meetone.work.ui.candy;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cai.framework.http.NetRespondCallBack;
import com.meetone.work.base.AppBasePresenter;
import com.meetone.work.bean.CandyDetailModel;
import com.meetone.work.bean.respond.Respond;

import org.reactivestreams.Subscription;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.ResponseBody;

public class CandyDetailPresenter extends AppBasePresenter<CandyDetailView> {
    @Inject
    public CandyDetailPresenter() {
    }

    @Override
    public void onAttached() {

    }

    public boolean isLogin() {
        return userDAO.get().isLogin();
    }

    public void loadCandyDetail(int token_id) {
        Map<String, String> params = new HashMap<>();
        params.put("token_id", token_id + "");
        requestStore.get().loadCandyDetail(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetRespondCallBack<ResponseBody>() {
                    @Override
                    public void respondResult(Subscription subscription, ResponseBody responseBody) {
                        try {
                            String json = responseBody.string();
                            if (!TextUtils.isEmpty(json)) {
                                Respond respond = JSONObject.parseObject(json, Respond.class);
                                if (respond != null) {
                                    if (respond.getErrorcode() == 0) {
                                        String data = respond.getData();
                                        JSONObject dataObjcet = JSON.parseObject(data);
                                        String pro = dataObjcet.getString("pro");
                                        CandyDetailModel candyDetailModel = JSON.parseObject(pro, CandyDetailModel.class);
                                        String candy_total = dataObjcet.getJSONObject("user").getString("candy_total");
                                        candyDetailModel.setCandy_total(candy_total);
                                        mView.callback(candyDetailModel);
                                    } else {
                                        mView.callback(respond.getMessage(), 1);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            mView.callback("出现异常", 1);
                        }
                    }

                    @Override
                    public void respondError(Throwable t) {
                        mView.callback(t.getMessage(), 1);
                    }
                });
    }
}
