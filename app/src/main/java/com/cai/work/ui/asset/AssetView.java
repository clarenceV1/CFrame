package com.cai.work.ui.asset;

import com.cai.work.bean.Asset;
import com.cai.work.bean.Welfare;

import java.util.List;

public interface AssetView {

    void callBack(String message);

    void callBack(List<Asset> assetList);
}
