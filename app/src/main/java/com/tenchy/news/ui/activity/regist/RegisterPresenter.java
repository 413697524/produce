package com.tenchy.news.ui.activity.regist;


import com.tenchy.enginelibrary.utils.GsonUtils;
import com.tenchy.news.NewsResult;

import io.reactivex.Observable;


/**
 *
 * P层主要负责 数据逻辑
 *
 * Created by Administrator on 2017/7/28.
 */

public class RegisterPresenter implements RegisterContract.Presenter{

    //View
    private RegisterContract.View view;
    public RegisterPresenter(RegisterContract.View view) {
        this.view = view;
    }

    private RegisterContract.Model model;

    @Override
    public void destory() {

        view = null;
    }

    @Override
    public NewsResult register() {
        view.showLoadingDialog();
        RegisterModel model = new RegisterModel();
        NewsResult result = model.register();
        view.closeLoadingDialog();
        String contents = GsonUtils.getInstance().toJson(result);
        System.out.println("--------------"+contents );
        view.setContent(contents);
        return result;
    }
}
