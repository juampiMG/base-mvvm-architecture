package com.jp.app.common.viewModel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.jp.app.common.BaseActivity;
import com.jp.app.common.view.IBaseView;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel<N extends IBaseView> extends ViewModel implements IBaseViewModel {

    @Inject
    Application mApplication;

    private CompositeDisposable mCompositeDisposable;

    private WeakReference<N> mWeakReference;

    public BaseViewModel() {
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public void setView(IBaseView view) {
        this.mWeakReference = new WeakReference<>((N) view);
    }

    public N getView() {
        return mWeakReference.get();
    }

    public Context getContext () {
        return mApplication.getApplicationContext();
    }

    public void addDisposable(Disposable disposable) {
        if (disposable != null && mCompositeDisposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }

    protected void showLoading () {
        if (getView() != null) {
            getView().showLoading();
        }
    }

    protected void hideLoading () {
        if (getView() != null) {
            getView().hideLoading();
        }
    }

    protected void showError (String title, String message, BaseActivity.actionOnError actionOnError){
        if (getView() != null) {
            getView().showError(title, message, actionOnError);
        }
    }
}
