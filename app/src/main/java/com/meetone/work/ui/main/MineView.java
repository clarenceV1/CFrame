package com.meetone.work.ui.main;

import com.meetone.work.bean.MineModel;
import com.meetone.work.bean.User;

public interface MineView {

    void updataMineData(MineModel data);

    void toast(String message);

    void updateUserInfo(User user);
}
