package com.jp.app.common.viewModel;

import android.arch.lifecycle.LiveData;
import com.jp.app.model.ShowErrorMessage;

public interface IBaseViewModel {

    LiveData<Boolean> getIsLoading();

    LiveData<ShowErrorMessage> showErrorMessage();

}
