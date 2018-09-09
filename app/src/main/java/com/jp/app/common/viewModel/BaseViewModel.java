package com.jp.app.common.viewModel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
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

    private final MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();

    private CompositeDisposable mCompositeDisposable;

    private WeakReference<TBaseView> mWeakReference;

    public BaseViewModel() {
        this.mCompositeDisposable = new CompositeDisposable();
    }

    public Context getContext () {
        return mApplication.getApplicationContext();
    }

    // =============== IBaseViewModel ==============================================================

    @Override
    public void setView(IBaseView view) {
        this.mWeakReference = new WeakReference<>((TBaseView) view);
    }

    @Override
    public LiveData<Boolean> getIsLoading () {
        return mIsLoading;
    }

    // =============== CompositeDispoable ==========================================================

    @Override
    protected void onCleared() {
        mCompositeDisposable.dispose();
        super.onCleared();
    }

    public void addDisposable(Disposable disposable) {
        if (disposable != null && mCompositeDisposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }

    public TBaseView getView() {
        return mWeakReference.get();
    }

    public void setIsLoading(boolean visibility) {
        mIsLoading.setValue(visibility);
    }

    public void showError (String title, String message, BaseActivity.actionOnError actionOnError) {
        if (getView() != null) {
            getView().showError(title, message, actionOnError);
        }
    }

}
