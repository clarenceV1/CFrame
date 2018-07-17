package com.meetone.work.ui.person;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.meetone.work.R;
import com.meetone.work.databinding.PersonBinding;
import com.example.clarence.imageloaderlibrary.ILoadImage;
import com.example.clarence.imageloaderlibrary.ILoadImageParams;
import com.example.clarence.imageloaderlibrary.ImageForGlideParams;
import com.example.clarence.utillibrary.ToastUtils;
import com.meetone.work.base.App;
import com.meetone.work.base.AppBaseActivity;
import com.meetone.work.bean.User;
import com.meetone.work.dialog.BaseDialog;
import com.meetone.work.dialog.EditInputDialog;
import com.meetone.work.dialog.TipDialog;
import com.meetone.work.selectimg.ISListConfig;
import com.meetone.work.selectimg.ISNav;

import java.util.List;

import javax.inject.Inject;

@Route(path = "/MeetOne/PersonActivity", name = "个人信息页面")
public class PersonActivity extends AppBaseActivity<PersonBinding> implements PersonView, View.OnClickListener {
    private static final int SELECT_HEAD = 2;
    private static final int REQUEST_LIST_CODE = 0;
    private static final int REQUEST_CAMERA_CODE = 1;

    @Inject
    PersonPresenter presenter;
    @Inject
    ILoadImage iLoadImage;

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public void initView() {
        mViewBinding.titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mViewBinding.titleBar.setTitleText(getString(R.string.person));

        mViewBinding.llNickname.setOnClickListener(this);
        mViewBinding.imgHead.setOnClickListener(this);
        mViewBinding.tvCommit.setOnClickListener(this);

        presenter.getUserInfo();
    }

    @Override
    public int getLayoutId() {
        return R.layout.person;
    }

    @Override
    public void callBack(User user) {
        ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                .url(user.getAvatar())
                .error(R.drawable.default_avatar)
                .placeholder(R.drawable.default_avatar)
                .build();
        imageParams.setImageView(mViewBinding.imgHead);
        iLoadImage.loadImage(this, imageParams);
        mViewBinding.tvNickname.setText(user.getNickname());
        mViewBinding.tvPhone.setText(user.getPhone());
    }

    @Override
    public void callBack(String s) {
        ToastUtils.showShort(s);
    }

    @Override
    public void loginout() {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llNickname:
                EditInputDialog editInputDialog = new EditInputDialog(this, getString(R.string.edit_nickname_title), getString(R.string.edit_nickname_hit));
                editInputDialog.setOnOnEditDialogClickListener(new EditInputDialog.OnEditDialogClickListener() {
                    @Override
                    public void onOkClick(String oldStr, String newStr) {
                        try {
                            String trim = newStr.trim();
                            if (TextUtils.isEmpty(trim)) {
                                ToastUtils.showShort(R.string.nick_name_empty);
                            } else if (!oldStr.equals(trim)) {
                                presenter.upUserNickName(trim);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancelClick() {

                    }
                });
                editInputDialog.show();
                break;
            case R.id.imgHead:
                selectPhoto();
                break;
            case R.id.tvCommit:
                TipDialog tipDialog = new TipDialog(this, getString(R.string.user_loginout_tip_title), getString(R.string.user_loginout_tip_content), new BaseDialog.OnDialogClickListener() {
                    @Override
                    public void onOkClick() {
                        presenter.loginOut();
                    }

                    @Override
                    public void onCancelClick() {

                    }
                });
                tipDialog.setOkBtnText(getString(R.string.user_login_out));
                tipDialog.show();
                break;
        }
    }

    private void selectPhoto() {
        ISListConfig config = new ISListConfig.Builder()
                // 是否多选
                .multiSelect(false)
                .btnText("Confirm")
                // 确定按钮背景色
                //.btnBgColor(Color.parseColor(""))
                // 确定按钮文字颜色
                .btnTextColor(Color.WHITE)
                // 使用沉浸式状态栏
                .statusBarColor(getResources().getColor(R.color.title_color))
                // 返回图标ResId
                .backResId(R.drawable.arrow_back)
                .title("Images")
                .titleColor(getResources().getColor(R.color.blue_8D48FF))
                .titleBgColor(getResources().getColor(R.color.white_a))
                .allImagesText("All Images")
                .needCrop(true)
                .cropSize(1, 1, 200, 200)
                // 第一个是否显示相机
                .needCamera(true)
                // 最大选择图片数量
                .maxNum(9)
                .build();

        ISNav.getInstance().init(iLoadImage);
        ISNav.getInstance().toListActivity(this, config, SELECT_HEAD);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_HEAD && resultCode == RESULT_OK && data != null) {//头像选择返回
            List<String> pathList = data.getStringArrayListExtra("result");
            if (pathList != null && pathList.size() > 0) {
                presenter.upUserHead(pathList.get(0));
            }
            // 测试Fresco
            // draweeView.setImageURI(Uri.parse("file://"+pathList.get(0)));
            for (String path : pathList) {
                Log.e("onActivityResult", "path=" + path);
            }
        } else if (requestCode == REQUEST_CAMERA_CODE && resultCode == RESULT_OK && data != null) {
            String path = data.getStringExtra("result");
            //mMyController.upUserHead(path);
            Log.e("onActivityResult", "path=" + path);
        }
    }
}
