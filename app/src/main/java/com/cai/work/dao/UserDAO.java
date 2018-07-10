package com.cai.work.dao;

import android.text.TextUtils;

import com.cai.work.bean.User;
import com.cai.work.bean.User_;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.objectbox.Box;

@Singleton
public class UserDAO extends BaseDAO {
    Box<User> userBox;

    private static User mUser;

    @Inject
    public UserDAO() {
        userBox = boxStore.boxFor(User.class);
        swtichUser();
    }

    public User getUser() {
        return mUser;
    }

    public void swtichUser() {
        mUser = userBox.query().build().findFirst();
    }

    public void update(long userId, String avatar, int nationCode, String nickname, String phone) {
        if (mUser != null && mUser.getUser_id() == userId) {
            mUser.setAvatar(avatar);
            mUser.setNation_code(nationCode);
            mUser.setNickname(nickname);
            mUser.setPhone(phone);
            userBox.put(mUser);
        }
    }

    public void loginOut() {
        userBox.removeAll();
        mUser = null;
    }

    public String getToken() {
        if (mUser != null) {
            return mUser.getToken();
        }
        return null;
    }

    public boolean isLogin() {
        if (mUser == null) {
            return false;
        }
        return true;
    }

    public void save(User user) {
        if (user != null) {
            userBox.removeAll();
            userBox.put(user);
            swtichUser();
        }
    }

//
//    public Disposable getBalance(Consumer onNext, Consumer<Throwable> onError) {
//        Box<User> box = boxStore.boxFor(User.class);
//        Query<User> query = box.query().build();
//        Disposable subscribe = RxQuery.observable(query).subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread()).map(new Function<List<User>, User>() {
//                    @Override
//                    public User apply(List<User> users) {
//                        if (users != null && users.size() > 0) {
//                            return users.get(0);
//                        }
//                        return new User();
//                    }
//                }).subscribe(onNext, onError);
//        return subscribe;
//    }
}
