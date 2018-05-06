package com.cai.work.protocol;

import com.cai.annotation.apt.ProtocolShadow;

@ProtocolShadow("AModule2App")
public interface IAModule2App {
    String testProtocol(int a);
}
