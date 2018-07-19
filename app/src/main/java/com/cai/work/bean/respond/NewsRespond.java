package com.cai.work.bean.respond;

import com.cai.work.bean.NewsList;

public class NewsRespond extends BaseRespond {
    private NewsList data;

    public NewsList getData() {
        return data;
    }

    public void setData(NewsList data) {
        this.data = data;
    }
}
