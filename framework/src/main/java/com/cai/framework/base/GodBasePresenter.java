package com.cai.framework.base;


import io.reactivex.disposables.CompositeDisposable;

public abstract class GodBasePresenter<V> {
    protected V mView;
    protected CompositeDisposable mCompositeSubscription = new CompositeDisposable();

    public void setView(V v) {
        this.mView = v;
        this.onAttached();
    }

    public abstract void onAttached();

    public void onDetached() {
        mCompositeSubscription.dispose();
    }
}
