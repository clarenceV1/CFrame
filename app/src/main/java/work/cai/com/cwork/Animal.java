package work.cai.com.cwork;

import android.util.Log;

/**
 * Created by clarence on 2018/1/10.
 */

public class Animal {
    private static final String TAG = "Animal";

    public void fly() {
        Log.e(TAG, this.toString() + "#fly");
    }
}
