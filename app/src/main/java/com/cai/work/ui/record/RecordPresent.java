package com.cai.work.ui.record;

import com.cai.work.base.AppBasePresenter;
import com.cai.work.bean.RecordDataModel;
import com.cai.work.bean.respond.RecordRespond;
import com.example.clarence.netlibrary.NetRespondCallBack;

import org.reactivestreams.Subscription;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class RecordPresent extends AppBasePresenter<RecordView> {
    @Inject
    public RecordPresent() {
    }

    @Override
    public void onAttached() {

    }


    public void loadRecord(final int lastid, int token) {
        Map<String, String> params = new HashMap<>();
        params.put("lastid", lastid + "");
        params.put("token_id", token + "");
        requestStore.get().loadRecord(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetRespondCallBack<RecordRespond>() {
                    @Override
                    public void respondResult(Subscription subscription, RecordRespond respond) {
                        if (respond.getErrorcode() == 0) {
                            mView.callBack(respond.getData(),lastid);
                        } else {
                            mView.callBack(new RecordDataModel(),lastid);
                            mView.callBack(respond.getMessage());
                        }
                    }

                    @Override
                    public void respondError(Throwable t) {
                        mView.callBack(t.getMessage());
                        mView.callBack(new RecordDataModel(), lastid);
                    }
                });
    }
}
