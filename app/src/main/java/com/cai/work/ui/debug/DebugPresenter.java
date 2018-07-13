package com.cai.work.ui.debug;

import com.cai.work.base.AppBasePresenter;
import com.example.clarence.netlibrary.NetRespondCallBack;

import org.reactivestreams.Subscription;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.ResponseBody;

public class DebugPresenter extends AppBasePresenter<DebugView> {
    @Inject
    public DebugPresenter() {
    }

    @Override
    public void onAttached() {

    }

//    public void loadCandyDetail(int token_id) {
//        Map<String, String> params = new HashMap<>();
//        params.put("token_id", token_id + "");
//        requestStore.get().loadCandyDetail(params)
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new NetRespondCallBack<ResponseBody>() {
//                    @Override
//                    public void respondResult(Subscription subscription, ResponseBody responseBody) {
//
//                    }
//
//                    @Override
//                    public void respondError(Throwable t) {
////                        mView.callback(t.getMessage(), 1);
//                    }
//                });
//    }
}
