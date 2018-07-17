package com.meetone.work.ui.asset;

import com.meetone.work.bean.Asset;

import java.util.List;

public interface AssetView {

    void callBack(String message);

    void callBack(List<Asset> assetList);
}
