package com.cai.work.qinius;

import android.text.TextUtils;
import android.util.Log;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * Created by davy on 2018/3/8.
 */

@Singleton
public class QiNiuController {
    private UploadManager uploadManager;

    @Inject
    public QiNiuController() {
        uploadManager = new UploadManager();
    }

    /**
     * data =
     * String key =
     * String token =
     *
     * @param filePath          <File对象、或 文件路径、或 字节数组>
     * @param key               <指定七牛服务上的文件名，或 null>;
     * @param token             < 从服务端SDK获取>;
     * @param completionHandler
     */
    public void uploadManager(String filePath, String key, String token, final UpCompletionHandler completionHandler) {
        Log.e("uploadManager", "token=" + token);
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        //token="MqF35-H32j1PH8igh-am7aEkduP511g-5-F7j47Z:Vowr8OWBqbHXbn9MDyw-1knZuN8=:eyJzY29wZSI6InB1YmxpYyIsImRlYWRsaW5lIjoxNTIwOTI1NTAxLCJzYXZlS2V5IjoicWluaXVfY2xvdWRfc3RvcmFnZV8xNTIwOTIxOTAxIiwibWltZUxpbWl0IjoiaW1hZ2VcLyoiLCJkZWxldGVBZnRlckRheXMiOjF9";
        uploadManager.put(filePath, key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                completionHandler.complete(key, info, response);
            }
        }, null);
    }
}
