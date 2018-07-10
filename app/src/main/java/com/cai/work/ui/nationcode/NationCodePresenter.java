package com.cai.work.ui.nationcode;

import com.cai.framework.utils.LanguageLocalUtil;
import com.cai.work.base.AppBasePresenter;
import com.cai.work.bean.NationCodeModel;
import com.cai.work.bean.respond.NationCodeRespond;
import com.example.clarence.netlibrary.NetRespondCallBack;
import com.example.clarence.utillibrary.AssetUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NationCodePresenter extends AppBasePresenter<NationCodeView> {
    private ArrayList<String> tags;

    @Inject
    public NationCodePresenter() {
    }

    @Override
    public void onAttached() {

    }

    public void loadNationCode() {
        boolean isChinese = LanguageLocalUtil.isChinese();
        loadNationCodeOfCache(isChinese);
//        loadNationCodeOfNet(isChinese);
    }

    public void loadNationCodeOfCache(final boolean isChinese) {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<List<NationCodeModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<NationCodeModel>> e) {
                String countryCode = null;
                if (isChinese) {
                    countryCode = AssetUtils.getStringFromAsset(context, "country_code.json");
                } else {
                    countryCode = AssetUtils.getStringFromAsset(context, "country_code_en.json");
                }
                ArrayList<NationCodeModel> localData = new ArrayList<>();
                try {
                    localData = handleDatas(countryCode);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                e.onNext(localData);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<NationCodeModel>>() {
                    @Override
                    public void accept(List<NationCodeModel> nationCodeModels) throws Exception {
                        mView.callback(nationCodeModels, isChinese);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        mCompositeSubscription.add(disposable);
    }

    public void loadNationCodeOfNet(final boolean isChinese) {
        requestStore.get().loadNationCode()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetRespondCallBack<NationCodeRespond>() {
                    @Override
                    public void respondResult(Subscription subscription, NationCodeRespond respond) {
                        if (respond.getErrorcode() == 0) {
                            mView.callback(respond.getData(), isChinese);
                        } else {
                            mView.callback(respond.getMessage());
                        }
                    }

                    @Override
                    public void respondError(Throwable t) {
                        mView.callback(t.getMessage());
                    }
                });
    }

    private ArrayList<NationCodeModel> handleDatas(String countryCode) throws JSONException {
        JSONObject jsonObject = new JSONObject(countryCode);
        createTags();
        //
        ArrayList<NationCodeModel> nationCodeModels = new ArrayList<>();
        int size = tags.size();
        for (int i = 0; i < size; i++) {
            String tag = tags.get(i);
            JSONArray tagsJson = jsonObject.optJSONArray(tag);
            if (tagsJson != null) {
                int jSize = tagsJson.length();
                for (int j = 0; j < jSize; j++) {
                    JSONObject tagObj = tagsJson.getJSONObject(j);
                    NationCodeModel nationCodeModel = new NationCodeModel();
                    nationCodeModel.setContry(tagObj.optString("contry"));
                    nationCodeModel.setCode(tagObj.optString("code"));
                    nationCodeModel.setTag(tag);
                    nationCodeModels.add(nationCodeModel);
                }
            }
        }
        return nationCodeModels;
    }

    private void createTags() {
        if (tags == null) {
            tags = new ArrayList<>();
            tags.add("#");
            tags.add("A");
            tags.add("B");
            tags.add("C");
            tags.add("D");
            tags.add("E");
            tags.add("F");
            tags.add("G");
            tags.add("H");
            tags.add("I");
            tags.add("J");
            tags.add("K");
            tags.add("L");
            tags.add("M");
            tags.add("N");
            tags.add("O");
            tags.add("P");
            tags.add("Q");
            tags.add("R");
            tags.add("S");
            tags.add("T");
            tags.add("U");
            tags.add("V");
            tags.add("W");
            tags.add("X");
            tags.add("Y");
            tags.add("Z");
        }
    }
}
