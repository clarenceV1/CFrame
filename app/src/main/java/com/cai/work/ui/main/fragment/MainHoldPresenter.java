package com.cai.work.ui.main.fragment;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBasePresenter;
import com.cai.work.bean.ForwardHold;
import com.cai.work.bean.SocketInfo;
import com.cai.work.bean.User;
import com.cai.work.bean.respond.CommonRespond;
import com.cai.work.bean.respond.ForwardAccountRespond;
import com.cai.work.bean.respond.StockAccountRespond;
import com.cai.work.bean.respond.StockHoldRespond;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
import com.cai.work.dao.HomeDataSqlDAO;
import com.cai.work.dao.UserDAO;
import com.cai.work.event.ForwardHoldEvent;
import com.cai.work.socket.SocketManager;
import com.example.clarence.utillibrary.StringUtils;
import com.koushikdutta.async.http.WebSocket;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainHoldPresenter extends GodBasePresenter<HoldView> {
    @Inject
    RequestStore requestStore;
    @Inject
    HomeDataSqlDAO homeDataSqlDAO;
    @Inject
    UserDAO userDAO;
    @Inject
    AccountDAO accountDAO;

    @Inject
    public MainHoldPresenter() {
    }

    @Override
    public void onAttached() {

    }


    public void getSocketInfo() {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestSocketInfo(token, new Consumer<SocketInfo>() {
            @Override
            public void accept(SocketInfo data) {
                mView.socketInfo(data);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast("socket数据获取错误", 1);
            }
        });
        mCompositeSubscription.add(disposable);
    }

    public void requestData(boolean isRealTrade, boolean isStock, boolean isHolder, int page, SocketInfo socketInfo) {
        SocketManager.closeSocket();
        if (isRealTrade) {//实盘
            if (isStock) { //股票
                if (isHolder) {
                    requestRealStockHold();
                } else {
                    requestRealStockAccounts();
                }
            } else { //期货
                if (isHolder) {
                    requestRealForwardHold(socketInfo);
                } else {
                    requestRealForwardAccounts(page);
                }
            }
        } else {
            if (isStock) {
                if (isHolder) {
                    requestFakeStockHold();
                } else {
                    requestFakeStockAccounts();
                }
            } else {
                if (isHolder) {
                    requestFakeForwardHold(socketInfo);
                } else {
                    requestFakeForwardAccounts(page);
                }
            }
        }
    }

    /**
     * 股票实盘持仓
     */
    public void requestRealStockHold() {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestRealStockHold(token, new Consumer<StockHoldRespond>() {
            @Override
            public void accept(StockHoldRespond data) {
                if (data.getCode() == 200 && data.getData() != null) {
                    mView.stockHold(data.getData());
                } else {
                    mView.toast(data.getResponseText(), 2);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast(" 股票实盘持仓数据获取错误", 2);
            }
        });
        mCompositeSubscription.add(disposable);
    }

    /**
     * 股票模拟持仓
     */
    public void requestFakeStockHold() {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestFakeStockHold(token, new Consumer<StockHoldRespond>() {
            @Override
            public void accept(StockHoldRespond data) {
                if (data.getCode() == 200 && data.getData() != null) {
                    mView.stockHold(data.getData());
                } else {
                    mView.toast(data.getResponseText(), 2);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast(" 股票实盘持仓数据获取错误", 2);
            }
        });
        mCompositeSubscription.add(disposable);
    }

    /**
     * 股票实盘结算
     */
    public void requestRealStockAccounts() {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestRealStockAccounts(token, new Consumer<StockAccountRespond>() {
            @Override
            public void accept(StockAccountRespond data) {
                if (data.getCode() == 200 && data.getData() != null) {
                    mView.stockAccount(data.getData());
                } else {
                    mView.toast(data.getResponseText(), 2);
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast(" 股票实盘持仓数据获取错误", 2);
            }
        });
        mCompositeSubscription.add(disposable);
    }

    /**
     * 股票模拟结算
     */
    public void requestFakeStockAccounts() {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestFakeStockAccounts(token, new Consumer<StockAccountRespond>() {
            @Override
            public void accept(StockAccountRespond data) {
                if (data.getCode() == 200 && data.getData() != null) {
                    mView.stockAccount(data.getData());
                } else {
                    mView.toast(data.getResponseText(), 2);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast(" 股票实盘持仓数据获取错误", 2);
            }
        });
        mCompositeSubscription.add(disposable);
    }

    /**
     * 期货实盘持仓
     */
    public void requestRealForwardHold(SocketInfo socketInfo) {
        if (socketInfo == null) {
            return;
        }
        //socket
        SocketManager.init(socketInfo.getSocket_host(), socketInfo.getSocket_port(), new WebSocket.StringCallback() {
            @Override
            public void onStringAvailable(String json) {
                //  Log.i("requestRealForwardHold", "实盘 ip:" + SocketManager.mIp + "====>" + json);
                json = StringUtils.replaceBlank(json);
                if (!TextUtils.isEmpty(json) && !"[]".equals(json)) {
                    List<ForwardHold> data = JSON.parseArray(json, ForwardHold.class);
                    EventBus.getDefault().post(new ForwardHoldEvent(data));
                }
            }
        });
        SocketManager.connect();
        int userId = userDAO.getData().getMemberId();
        SocketManager.sendSocket("hold|0|" + userId);

//        List<ForwardHold> data = SocketManager.getTestData();
//        EventBus.getDefault().post(new ForwardHoldEvent(data));
    }

    /**
     * 期货模拟持仓
     */
    public void requestFakeForwardHold(SocketInfo socketInfo) {
        if (socketInfo == null) {
            return;
        }
        //socket
        SocketManager.init(socketInfo.getSocket_host(), socketInfo.getSocket_port(), new WebSocket.StringCallback() {
            @Override
            public void onStringAvailable(String json) {
                // Log.i("requestRealForwardHold", " 模拟 ip:" + SocketManager.mIp + "====>" + json);
                json = StringUtils.replaceBlank(json);
                if (!TextUtils.isEmpty(json) && !"[]".equals(json)) {
                    List<ForwardHold> data = JSON.parseArray(json, ForwardHold.class);
                    EventBus.getDefault().post(new ForwardHoldEvent(data));
                }
            }
        });
        SocketManager.connect();
        int userId = userDAO.getData().getMemberId();
        SocketManager.sendSocket("hold|0|" + userId + "|mn");

//        List<ForwardHold> data = SocketManager.getTestData();
//        EventBus.getDefault().post(new ForwardHoldEvent(data));
    }

    /**
     * 期货实盘结算
     */
    public void requestRealForwardAccounts(int page) {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestRealForwardAccounts(page, token, new Consumer<ForwardAccountRespond>() {
            @Override
            public void accept(ForwardAccountRespond data) {
                if (data.getCode() == 200 && data.getData() != null) {
                    mView.forwardAccount(data.getData());
                } else {
                    mView.toast(data.getResponseText(), 2);
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast(" 股票实盘持仓数据获取错误", 2);
            }
        });
        mCompositeSubscription.add(disposable);
    }

    /**
     * 期货模拟结算
     */
    public void requestFakeForwardAccounts(int page) {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.requestFakeForwardAccounts(page, token, new Consumer<ForwardAccountRespond>() {
            @Override
            public void accept(ForwardAccountRespond data) {
                if (data.getCode() == 200 && data.getData() != null) {
                    mView.forwardAccount(data.getData());
                } else {
                    mView.toast(data.getResponseText(), 2);
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast(" 股票实盘持仓数据获取错误", 2);
            }
        });
        mCompositeSubscription.add(disposable);
    }

    public void postPingCang(boolean isRealTrade, String id, String code) {
        String token = accountDAO.getToken();
        if (isRealTrade) {
            realPingCang(token, id, code);
        } else {
            moniPingCang(token, id, code);
        }
    }

    private void realPingCang(String token, String id, String code) {
        Disposable disposable = requestStore.realPingCang(token, id, code, new Consumer<CommonRespond>() {
            @Override
            public void accept(CommonRespond data) {
                mView.toast(data.getResponseText(), 2);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast(throwable.getMessage(), 2);
            }
        });
        mCompositeSubscription.add(disposable);
    }

    private void moniPingCang(String token, String id, String code) {
        Disposable disposable = requestStore.moniPingCang(token, id, code, new Consumer<CommonRespond>() {
            @Override
            public void accept(CommonRespond data) {
                mView.toast(data.getResponseText(), 2);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast(throwable.getMessage(), 2);
            }
        });
        mCompositeSubscription.add(disposable);
    }


    /**
     * 股票实盘持仓
     */
    public void checkSell(String id, String sellWTPrice, String stockCode) {
        String token = accountDAO.getToken();
        Disposable disposable = requestStore.checkSell(token, id, sellWTPrice, stockCode, new Consumer<CommonRespond>() {
            @Override
            public void accept(CommonRespond data) {
                mView.toast(data.getResponseText(), 2);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast(throwable.getMessage(), 2);
            }
        });
        mCompositeSubscription.add(disposable);
    }

    public void postFanshou(boolean isRealTrade, String id, String code) {
        String token = accountDAO.getToken();
        if (isRealTrade) {
            realFanshou(token, id, code);
        } else {
            moniFanshou(token, id, code);
        }
    }

    private void moniFanshou(String token, String id, String code) {
        Disposable disposable = requestStore.moniFanshou(token, id, code, new Consumer<CommonRespond>() {
            @Override
            public void accept(CommonRespond data) {
                mView.toast(data.getResponseText(), 2);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast(throwable.getMessage(), 2);
            }
        });
        mCompositeSubscription.add(disposable);
    }

    private void realFanshou(String token, String id, String code) {
        Disposable disposable = requestStore.realFanshou(token, id, code, new Consumer<CommonRespond>() {
            @Override
            public void accept(CommonRespond data) {
                mView.toast(data.getResponseText(), 2);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                mView.toast(throwable.getMessage(), 2);
            }
        });
        mCompositeSubscription.add(disposable);
    }
}
