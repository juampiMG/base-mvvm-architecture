package com.jp.app.common.view;

import com.jp.app.common.BaseActivity;
import com.jp.app.common.viewModel.IBaseViewModel;

public interface IBaseView{

    void showLoading();

    void hideLoading();

    void showError(String title, String message, BaseActivity.actionOnError actionOnError);

}
