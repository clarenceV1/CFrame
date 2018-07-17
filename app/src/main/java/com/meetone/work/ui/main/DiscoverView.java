package com.meetone.work.ui.main;

import com.meetone.work.bean.Discover;

import java.util.List;

public interface DiscoverView {
    void callBack(List<Discover> discoverList);

    void callBack(String message);
}
