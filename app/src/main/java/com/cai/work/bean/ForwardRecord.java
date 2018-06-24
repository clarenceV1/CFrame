package com.cai.work.bean;

public class ForwardRecord {
    //    {
//        "records": {
//        "isTrade": "1",
//                "buyDate": "09:00-10:15,10:30-11:30,13:30-14:50,21:00-次日02:20",
//                "attributeType": "1",
//                "type": "1",
//                "contractCode": "ag1812",
//                "subMarketCode": "",
//                "contractName": "沪银",
//                "ruleName": null
//    },
//        "socket_host": "47.75.37.208",
//            "socket_port": "7709"
//    }
    private Record records;
    private String socket_host;
    private String socket_port;

    public Record getRecords() {
        return records;
    }

    public void setRecords(Record records) {
        this.records = records;
    }

    public String getSocket_host() {
        return socket_host;
    }

    public void setSocket_host(String socket_host) {
        this.socket_host = socket_host;
    }

    public String getSocket_port() {
        return socket_port;
    }

    public void setSocket_port(String socket_port) {
        this.socket_port = socket_port;
    }
}
