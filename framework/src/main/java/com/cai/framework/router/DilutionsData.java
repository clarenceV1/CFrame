package com.cai.framework.router;

import android.content.Intent;
import android.net.Uri;

/**
 * Created by clarence on 2018/2/5.
 */

public class DilutionsData<T> {
    private Intent mIntent;
    private T what;
    private Uri mUri;
    private Object mResult;
    public Intent getIntent(){
        return mIntent;
    }

    public T getWhat(){
        return what;
    }

    public void setIntent(Intent intent){
        mIntent = intent;
    }

    public void setUri(Uri uri){
        mUri = uri;
    }

    public void setResult(Object result){
        mResult = result;
    }

    public Object getResult(){
        return mResult;
    }

    public Uri getUri(){
        return mUri;
    }

    public void setWhat(T what){
        this.what = what;
    }
}
