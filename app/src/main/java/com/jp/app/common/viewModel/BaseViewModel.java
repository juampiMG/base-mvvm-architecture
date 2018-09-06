package com.jp.app.common.viewModel;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.ObservableBoolean;

import com.jp.app.common.BaseActivity;
import com.jp.app.common.view.IBaseView;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel<TBaseView extends IBaseView> extends ViewModel implements IBaseViewModel {

    @Inject
    Application mApplication;

    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);

    private CompositeDisposable mCompositeDisposable;

    private WeakReference<TBaseView> mWeakReference;

    public BaseViewModel() {
        this.mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public Context getContext () {
        return mApplication.getApplicationContext();
    }

    public void setView(IBaseView view) {
        this.mWeakReference = new WeakReference<>((TBaseView) view);
    }

    public TBaseView getView() {
        return mWeakReference.get();
    }

    public ObservableBoolean getIsLoading () {
        return mIsLoading;
    }

    public void setIsLoading(boolean visibility) {
        mIsLoading.set(visibility);
    }


    public void addDisposable(Disposable disposable) {
        if (disposable != null && mCompositeDisposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }

    protected void showError (String title, String message, BaseActivity.actionOnError actionOnError){
        if (getView() != null) {
            getView().showError(title, message, actionOnError);
        }
    }
}
