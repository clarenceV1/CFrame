package com.cai.work.base;


/**
 * @ 实例化工厂 此类由apt自动生成 */
public final class InstanceFactory {

  public static Object create(Class mClass) throws IllegalAccessException, InstantiationException {
     switch (mClass.getSimpleName()) {
//      case "HomePresenter": return  new HomePresenter();
//      case "UserPresenter": return  new UserPresenter();
//      case "AdvisePresenter": return  new AdvisePresenter();
//      case "ArticlePresenter": return  new ArticlePresenter();
//      case "LoginPresenter": return  new LoginPresenter();
      default: return mClass.newInstance();
    }
  }
}
