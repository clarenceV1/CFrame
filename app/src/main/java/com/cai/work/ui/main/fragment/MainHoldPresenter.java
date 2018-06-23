package com.cai.work.ui.main.fragment;

import com.cai.framework.base.GodBasePresenter;
import com.cai.work.bean.SocketInfo;
import com.cai.work.bean.User;
import com.cai.work.bean.respond.ForwardAccountRespond;
import com.cai.work.bean.respond.StockAccountRespond;
import com.cai.work.bean.respond.StockHoldRespond;
import com.cai.work.common.RequestStore;
import com.cai.work.dao.AccountDAO;
import com.cai.work.dao.HomeDataSqlDAO;
import com.cai.work.dao.UserDAO;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

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

    public void requestData(boolean isRealTrade, boolean isStock, boolean isHolder,int page) {
        if (isRealTrade) {//实盘
            if (isStock) { //股票
                if (isHolder) {
                    requestRealStockHold();
                } else {
                    requestRealStockAccounts();
                }
            } else { //期货
                if (isHolder) {
                    requestRealForwardHold();
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
                    requestFakeForwardHold();
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
    public void requestRealForwardHold() {
        //socket
    }

    /**
     * 期货模拟持仓
     */
    public void requestFakeForwardHold() {
        //socket
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

    public void creatSocket(String host, int port) {
        try {

            System.out.println("准备连接");
            Socket socket = new Socket(host, port);
            System.out.println("连接上了");

            InputStream inputStream = socket.getInputStream();
            byte buffer[] = new byte[1024 * 4];
            int temp = 0;
            String res = null;
            //从inputstream中读取客户端所发送的数据
            System.out.println("接收到服务器的信息是：");

            while ((temp = inputStream.read(buffer)) != -1) {
                System.out.println(new String(buffer, 0, temp));
                res += new String(buffer, 0, temp);
            }

            System.out.println("已经结束接收信息……");

            socket.close();
            inputStream.close();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUserId() {
        User user = userDAO.getData();
        if (user != null) {
            return user.getMemberId() + "";
        }
        return null;
    }
}
