package com.meetone.work.bean;

public class AppUpdate {
    /*    	"download_url": "http://more.one/download.html",
                    "is_force": 0,
                    "sha": "7fa8282ad93047a4d6fe6111c93b308a",
                    "is_remind": 1,
                    "latest_version": "2.1.3.3"*/
    private String download_url;
    private int is_force;
    private String sha;
    private int is_remind;
    private String latest_version;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public int getIs_force() {
        return is_force;
    }

    public void setIs_force(int is_force) {
        this.is_force = is_force;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public int getIs_remind() {
        return is_remind;
    }

    public void setIs_remind(int is_remind) {
        this.is_remind = is_remind;
    }

    public String getLatest_version() {
        return latest_version;
    }

    public void setLatest_version(String latest_version) {
        this.latest_version = latest_version;
    }
}
