package com.cai.framework.router;

/**
 * Created by clarence on 2018/2/5.
 */

public interface DilutionsGlobalListener {
    public boolean onCheckUri(String uri);

    public boolean onUnSupportUri(String uri);

    public boolean onIntercept(DilutionsData data);

    public boolean onCallBack(DilutionsData data);
}
