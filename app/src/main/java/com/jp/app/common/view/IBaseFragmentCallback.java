package com.jp.app.common.view;

import com.jp.app.common.BaseActivity;

public interface IBaseFragmentCallback {

    void showLoading();

    void hideLoading();

    void showError(String title, String message, BaseActivity.actionOnError action);

    void showMessage(String title, String message);
}
