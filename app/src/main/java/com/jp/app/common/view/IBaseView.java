package com.jp.app.common.view;

import com.jp.app.common.BaseActivity;

public interface IBaseView{

    void showError(String title, String message, BaseActivity.actionOnError actionOnError);

}
