package com.cai.work;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.cai.work.bean.Account;
import com.cai.work.ui.UnitTest.TestActivityForA;
import com.cai.work.ui.UnitTest.TestActivityForB;
import com.cai.work.ui.UnitTest.TestFragment;
import com.cai.work.ui.UnitTest.TestReceiver;
import com.cai.work.ui.UnitTest.TestService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.android.controller.ServiceController;
import org.robolectric.shadows.ShadowActivity;
import org.robolectric.shadows.ShadowAlertDialog;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.shadows.ShadowToast;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.shadow.api.Shadow.extract;


public class ExampleUnitTest extends BaseRobolectricTestCase {

    @Mock
    List mocklit;

    TestActivityForB testActivityForB;
    Button buttonA, btnGoB;

    private final String action = "com.cai.work.Receiver";

    @Before
    public void setup() {
        //输出日志
        ShadowLog.stream = System.out;
        MockitoAnnotations.initMocks(this);
        testActivityForB = Robolectric.setupActivity(TestActivityForB.class);
        buttonA = (Button) testActivityForB.findViewById(R.id.btnGoA);
        btnGoB = (Button) testActivityForB.findViewById(R.id.btnGoB);
    }
    @Test
    public void testShadowShadow(){
        Account person = new Account();
        //实际上调用的是ShadowPerson的方法
        Log.d("test", String.valueOf(person.getName()));
        Log.d("test", String.valueOf(person.getPassword()));

        //获取Person对象对应的Shadow对象
//        ShadowAccount shadowPerson = extract(person);
//        assertEquals("xiaocai", shadowPerson.getName());
//        assertEquals("123", shadowPerson.getPassword());
    }

    @Test
    public void testActivity() {
        assertNotNull(testActivityForB);
        assertEquals(testActivityForB.getName(), "TestActivityForB");
    }

    @Test
    public void testJump() {
        // 触发按钮点击
        buttonA.performClick();
        // 获取对应的Shadow类
        ShadowActivity shadowActivity = Shadows.shadowOf(testActivityForB);
        // 借助Shadow类获取启动下一Activity的Intent
        Intent nextIntent = shadowActivity.getNextStartedActivity();
        // 校验Intent的正确性
        assertEquals(nextIntent.getComponent().getClassName(), TestActivityForA.class.getName());
    }

    @Test
    public void testToast() {
        buttonA.performClick();
        Toast toast = ShadowToast.getLatestToast();
        // 判断Toast已经弹出
        assertNotNull(toast);
        // 获取Shadow类进行验证
        ShadowToast shadowToast = Shadows.shadowOf(toast);
        assertEquals(Toast.LENGTH_LONG, shadowToast.getDuration());
        assertEquals("Hello UT!", ShadowToast.getTextOfLatestToast());
    }

    @Test
    public void testDialog() {
        btnGoB.performClick();
        AlertDialog dialog = ShadowAlertDialog.getLatestAlertDialog();
        // 判断Dialog已经弹出
        assertNotNull(dialog);
        // 获取Shadow类进行验证
        ShadowAlertDialog shadowDialog = Shadows.shadowOf(dialog);
        assertEquals("Hello UT！", shadowDialog.getMessage());
    }

    @Test
    public void testFragment() {
        TestFragment sampleFragment = new TestFragment();
        //添加Fragment到Activity中，会触发Fragment的onCreateView()
        SupportFragmentTestUtil.startFragment(sampleFragment);
        assertNotNull(sampleFragment.getView());
    }

    @Test
    public void testLifecycle() {
        // 创建Activity控制器
        ActivityController<TestActivityForB> activityController = Robolectric.buildActivity(TestActivityForB.class);
        TestActivityForB activity = activityController.get();
        assertNull(activity.getLifecycleState());

        // 调用Activity的performCreate方法
        activityController.create();
        assertEquals("onCreate", activity.getLifecycleState());

        // 调用Activity的performStart方法
        activityController.start();
        assertEquals("onStart", activity.getLifecycleState());

        // 调用Activity的performResume方法
        activityController.resume();
        assertEquals("onResume", activity.getLifecycleState());

        // 调用Activity的performPause方法
        activityController.pause();
        assertEquals("onPause", activity.getLifecycleState());

        // 调用Activity的performStop方法
        activityController.stop();
        assertEquals("onStop", activity.getLifecycleState());
        // 调用Activity的performRestart方法
        activityController.restart();
        // 注意此处应该是onStart，因为performRestart不仅会调用restart，还会调用onStart
        assertEquals("onStart", activity.getLifecycleState());

        // 调用Activity的performDestroy方法
        activityController.destroy();
        assertEquals("onDestroy", activity.getLifecycleState());
    }

    @Test
    public void testMockito() {
        mocklit.add("one");
        mocklit.clear();
        Mockito.verify(mocklit).add("one");
        Mockito.verify(mocklit).clear();
    }

    /**
     * 验证广播是否注册成功
     *
     * @throws Exception
     */
    @Test
    public void testRegister() {
        ShadowApplication shadowApplication = ShadowApplication.getInstance();
        Intent intent = new Intent(action);
        // 验证是否注册了相应的Receiver
        assertTrue(shadowApplication.hasReceiverForIntent(intent));
    }

    @Test
    public void testReceive() {
        //发送广播
        Intent intent = new Intent(action);
        intent.putExtra(TestReceiver.NAME, "AndroidUT");
        TestReceiver myReceiver = new TestReceiver();
        myReceiver.onReceive(RuntimeEnvironment.application, intent);
        //验证广播的处理逻辑是否正确
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(RuntimeEnvironment.application);
        assertEquals("AndroidUT", preferences.getString(TestReceiver.NAME, ""));
    }

    /**
     * 控制Service生命周期进行验证
     *
     * @throws Exception
     */
    @Test
    public void testServiceLifecycle()  {
        ServiceController<TestService> serviceController = Robolectric.buildService(TestService.class);
        TestService testService = serviceController.get();
        serviceController.create();
        serviceController.startCommand(0, 0);
        serviceController.bind();
        serviceController.unbind();
        serviceController.destroy();
    }

    @Test
    public void testStub() {
        LinkedList mockedList = Mockito.mock(LinkedList.class);
        Mockito.when(mockedList.get(0)).thenReturn("first");
        System.out.println(mockedList.get(0));
    }

}