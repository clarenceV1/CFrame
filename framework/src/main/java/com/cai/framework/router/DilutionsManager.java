package com.cai.framework.router;

/**
 * Created by clarence on 2018/2/5.
 */
public interface DilutionsManager<T> {

    public void apply() throws Exception;

    public DilutionsBuilder getDilutionsBuilder();

    public ProtocolClazzORMethod getProtocolClazzORMethod();

    public int getProtocolType();
}
