package com.jp.app.common.viewModel;

import android.arch.lifecycle.LiveData;

import com.jp.app.common.view.IBaseView;
import com.jp.app.model.ShowErrorMessage;

public interface IBaseViewModel {

    void setView(IBaseView view);

    LiveData<Boolean> getIsLoading();

    LiveData<ShowErrorMessage> showErrorMessage();

}
