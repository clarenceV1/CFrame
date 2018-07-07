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

    @Inject
    public UserDAO() {
        userBox = boxStore.boxFor(User.class);
    }

    public void update(long userId, String avatar, int nationCode, String nickname, String phone) {
        User user = userBox.query().equal(User_.user_id, userId).build().findFirst();
        if (user != null) {
            user.setAvatar(avatar);
            user.setNation_code(nationCode);
            user.setNickname(nickname);
            user.setPhone(phone);
            userBox.put(user);
        }
    }

    public String getToken() {
        User user = userBox.query().build().findFirst();
        if (user != null) {
            return user.getToken();
        }
        return null;
    }

    public boolean isLogin() {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return false;
        }
        return true;
    }

    public User getUserInfo() {
        User user = userBox.query().build().findFirst();
        return user;
    }

    public void save(User user) {
        if (user != null) {
            userBox.removeAll();
            userBox.put(user);
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
