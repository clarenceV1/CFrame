package com.meetone.work.ui.nationcode;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.cai.framework.utils.LanguageLocalUtil;
import com.example.clarence.netlibrary.NetRespondCallBack;
import com.example.clarence.utillibrary.AssetUtils;
import com.meetone.work.base.AppBasePresenter;
import com.meetone.work.bean.NationCodeModel;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

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
        loadNationCodeOfNet(isChinese);
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
                List<NationCodeModel> localData = new ArrayList<>();
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
                .map(new Function<ResponseBody, Map<String, Object>>() {
                    @Override
                    public Map<String, Object> apply(ResponseBody responseBody) throws Exception {
                        Map<String, Object> map = new HashMap<>();
                        try {
                            String result = responseBody.string();
                            if (result != null) {
                                JSONObject jsonObject = JSON.parseObject(result);
                                if (jsonObject != null) {
                                    if (jsonObject.getInteger("errorcode") == 0) {
                                        String dataJson = jsonObject.getString("data");
                                        List<NationCodeModel> datas = handleDatas(dataJson);
                                        map.put("data", datas);
                                    } else {
                                        String error = jsonObject.getString("message");
                                        if (!TextUtils.isEmpty(error)) {
                                            map.put("error", error);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return map;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetRespondCallBack<Map<String, Object>>() {
                    @Override
                    public void respondResult(Subscription subscription, Map<String, Object> map) {
                        if (map.get("data") != null) {
                            List<NationCodeModel> datas = (List<NationCodeModel>) map.get("data");
                            mView.callback(datas, isChinese);
                        } else if (map.get("error") != null) {
                            mView.callback(map.get("error").toString());
                        }
                    }

                    @Override
                    public void respondError(Throwable t) {
                        mView.callback(t.getMessage());
                    }
                });
    }

    private List<NationCodeModel> handleDatas(String countryCode) throws JSONException {
        JSONObject jsonObject = JSON.parseObject(countryCode);
        createTags();
        //
        ArrayList<NationCodeModel> nationCodeModels = new ArrayList<>();
        int size = tags.size();
        for (int i = 0; i < size; i++) {
            String tag = tags.get(i);
            JSONArray tagsJson = jsonObject.getJSONArray(tag);
            if (tagsJson != null) {
                int jSize = tagsJson.size();
                for (int j = 0; j < jSize; j++) {
                    JSONObject tagObj = tagsJson.getJSONObject(j);
                    NationCodeModel nationCodeModel = new NationCodeModel();
                    nationCodeModel.setContry(tagObj.getString("contry"));
                    nationCodeModel.setCode(tagObj.getString("code"));
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
