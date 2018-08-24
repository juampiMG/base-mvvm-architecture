package com.jp.app.common;

import android.content.Context;

import com.jp.app.R;
import com.jp.app.common.viewModel.BaseViewModel;

import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;

public abstract class BaseSingleObserver<T> implements SingleObserver<T> {

    private Context mContext;

    public BaseSingleObserver(Context context) {
        this.mContext = context;
    }

    public BaseSingleObserver(BaseViewModel viewModel) {
        this(viewModel.getContext());
    }

    @Override
    public void onError(@NonNull Throwable e) {
        handleError(e);
    }

    protected abstract void onError(int code, String title, String description);

    protected void handleError(Throwable throwable) {
        int code = 0;
        String title = mContext.getString(R.string.oh_hell);
        String message = mContext.getString(R.string.default_error);

        onError(code, title, message);
    }

}
