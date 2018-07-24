package com.cai.work.socket;

import android.util.Log;

import com.cai.work.bean.ForwardHold;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SocketManager {

    private static WebSocket webSocket;
    public static String mIp = "ws://47.75.37.208:7709";
    private static String mPort = "7709";
    private static WebSocket.StringCallback callback;


    public static void init(String ip, String port, WebSocket.StringCallback callback) {
        StringBuilder builder = new StringBuilder();
        builder.append("ws://");
        builder.append(ip);
        builder.append(":");
        builder.append(port);
        mIp = builder.toString();
        mPort = port;
        SocketManager.callback = callback;
        closeSocket();
    }

    public static void connect() {
        Future<WebSocket> future = AsyncHttpClient.getDefaultInstance().websocket(
                mIp,// webSocket地址
                mPort,// 端口
                new AsyncHttpClient.WebSocketConnectCallback() {
                    @Override
                    public void onCompleted(Exception ex, WebSocket webSocket) {
                        if (ex != null) {
                            ex.printStackTrace();
                            return;
                        }
                        webSocket.setStringCallback(new WebSocket.StringCallback() {
                            public void onStringAvailable(String s) {
                                Log.i("SocketActivity", "onStringAvailable: " + s);
                                if (callback != null) {
                                    callback.onStringAvailable(s);
                                }
                            }
                        });
                        webSocket.setDataCallback(new DataCallback() {
                            public void onDataAvailable(DataEmitter emitter, ByteBufferList byteBufferList) {
                                Log.i("SocketActivity", "I got some bytes!");
                                // note that this data has been read
                                byteBufferList.recycle();
                            }
                        });
                    }
                });
        try {
            webSocket = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static WebSocket getWebSocket() {
        return webSocket;
    }

    /**
     * 发送
     *
     * @param conent hold|0|684;
     */
    public static void sendSocket(String conent) {
        WebSocket webSocket = getWebSocket();
        if (webSocket != null) {
            if (webSocket.isPaused()) {
                webSocket.resume();
            }
            webSocket.send(conent);
            webSocket.send(new byte[10]);
        }
    }

    public static void pauseSocket() {
        if (webSocket != null) {
            webSocket.pause();
        }
    }

    public static void resumeSocket() {
        if (webSocket != null && webSocket.isPaused()) {
            webSocket.resume();
        }
    }

    public static void closeSocket() {
        if (webSocket != null) {
            webSocket.close();
            webSocket = null;
        }
    }

    public static List<ForwardHold> getTestData() {
        List<ForwardHold> list = new ArrayList<>();
        ForwardHold f1 = new ForwardHold();
        f1.setId(148);
        f1.setOrderNo("QHJY1807061339191586");
        f1.setOpenData("2018-07-06");
        f1.setOpenTime("13:39:19");
        f1.setContractName("测试数据记的删");
        f1.setContractCode("i1809");
        f1.setOpenPrice("458.5");
        f1.setBuyWay("1");
        f1.setBuyWayText("买涨");
        f1.setBuyAmount("1");
        f1.setMk_price("458.5");
        f1.setYkMoney("0.00");
        f1.setBond("600");
        f1.setZsMoney("500");
        f1.setZyMoney("800");
        f1.setIsClosing("2");
        f1.setBtnText("平仓");
        list.add(f1);
        return list;
    }
//    [{
//        "id": "148",
//                "orderNo": "QHJY1807061339191586",
//                "openData": "2018-07-06",
//                "openTime": "13:39:19",
//                "contractName": "铁矿石",
//                "contractCode": "i1809",
//                "openPrice": "458.5",
//                "buyWay": "1",
//                "buyWayText": "买涨",
//                "buyAmount": "1",
//                "mk_price": "458.5",
//                "ykMoney": "0.00",
//                "bond": "600",
//                "zsMoney": "500",
//                "zyMoney": "800",
//                "isClosing": "2",
//                "btnText": "平仓"
//    }, {
//        "id": "147",
//                "orderNo": "QHJY1807061338506801",
//                "openData": "2018-07-06",
//                "openTime": "13:38:50",
//                "contractName": "国内原油",
//                "contractCode": "sc1809",
//                "openPrice": "496.3",
//                "buyWay": "1",
//                "buyWayText": "买涨",
//                "buyAmount": "1",
//                "mk_price": "496.4",
//                "ykMoney": "100.00",
//                "bond": "2000",
//                "zsMoney": "1700",
//                "zyMoney": "2500",
//                "isClosing": "2",
//                "btnText": "平仓"
//    }]
}
