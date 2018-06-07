package com.cai.work.bean;

import java.util.List;

public class HomeType4Data {
    private String item1Name;
    private String item2Name;
    private List<HomeType4DataItem1> item1List;
    private List<HomeType4DataItem2> item2List;

    public String getItem1Name() {
        return item1Name;
    }

    public void setItem1Name(String item1Name) {
        this.item1Name = item1Name;
    }

    public String getItem2Name() {
        return item2Name;
    }

    public void setItem2Name(String item2Name) {
        this.item2Name = item2Name;
    }

    public List<HomeType4DataItem1> getItem1List() {
        return item1List;
    }

    public void setItem1List(List<HomeType4DataItem1> item1List) {
        this.item1List = item1List;
    }

    public List<HomeType4DataItem2> getItem2List() {
        return item2List;
    }

    public void setItem2List(List<HomeType4DataItem2> item2List) {
        this.item2List = item2List;
    }
}
