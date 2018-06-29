package com.cai.work.ui.main;

import android.text.TextUtils;

import com.cai.work.base.App;
import com.cai.work.base.AppBasePresenter;
import com.cai.work.bean.AppUpdate;
import com.example.clarence.utillibrary.PackageUtils;

import java.util.Observable;

import javax.inject.Inject;

/**
 * Created by clarence on 2018/1/12.
 */
public class MainPresenter extends AppBasePresenter<MainView> {

    @Inject
    public MainPresenter() {

    }

    @Override
    public void onAttached() {
        App.getAppComponent().inject(this);
    }

    public AppUpdate isNewVersion() {
        AppUpdate update = dataStore.get().getAppUpdate();
        if (update != null) {
            String nowVersionName = PackageUtils.getVersionName(context);
            String lastestVersionName = update.getLatest_version();
            if (isNewVersion(nowVersionName, lastestVersionName)) {
                return update;
            }
        }
        return null;
    }

    /**
     * 比较是否是新版本
     *
     * @param nowVersion 3.1.1 3.1.1
     * @param netVersion 3.1.2 3.1.1.2
     * @return
     */
    public boolean isNewVersion(String nowVersion, String netVersion) {
        try {
            String nowVersionTemp = nowVersion;
            if (!TextUtils.isEmpty(nowVersionTemp) && nowVersionTemp.indexOf("-") != -1) {
                nowVersionTemp = nowVersionTemp.substring(0, nowVersionTemp.indexOf("-"));
            }
            nowVersionTemp = nowVersionTemp.replace(".", "");
            String netVersionTemp = netVersion;
            netVersionTemp = netVersionTemp.replace(".", "");
            int nowCount = nowVersionTemp.length();
            int netCount = netVersionTemp.length();
            if (nowCount > netCount) {
                for (int i = 0; i < nowCount - netCount; ++i) {
                    netVersionTemp = netVersionTemp + "0";
                }
            } else if (nowCount < netCount) {
                for (int i = 0; i < netCount - nowCount; ++i) {
                    nowVersionTemp = nowVersionTemp + "0";
                }
            }
            boolean update = Integer.parseInt(netVersionTemp) > Integer.parseInt(nowVersionTemp);
            return update;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
}
