package com.meetone.work.ui.debug;

import com.meetone.work.base.AppBasePresenter;

import javax.inject.Inject;

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
