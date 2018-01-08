package work.cai.com.cwork;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import work.cai.com.lib.Router;

@Router("woderouter")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
