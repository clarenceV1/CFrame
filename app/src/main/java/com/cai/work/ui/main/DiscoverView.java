package com.cai.work.ui.main;

import com.cai.work.bean.Discover;

import java.util.List;

public interface DiscoverView {
    void callBack(List<Discover> discoverList);

    void callBack(String message);
}
