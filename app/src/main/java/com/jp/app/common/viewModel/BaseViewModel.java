package com.jp.app.common.viewModel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.jp.app.common.BaseActivity;
import com.jp.app.common.view.IBaseView;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel<TBaseView extends IBaseView> extends ViewModel implements IBaseViewModel {

    @Inject
    Context mContext;

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

    public void setView(IBaseView view) {
        this.mWeakReference = new WeakReference<>((TBaseView) view);
    }

    public TBaseView getView() {
        return mWeakReference.get();
    }

    public void showLoading () {
        if (getView() != null) {
            getView().showLoading();
        }
    }

    public void hideLoading () {
        if (getView() != null) {
            getView().hideLoading();
        }
    }

    public void showError (String title, String message, BaseActivity.actionOnError actionOnError) {
        if (getView() != null) {
            getView().showError(title, message, actionOnError);
        }
    }

    public Context getContext () {
        return mContext;
    }

    public void addDisposable(Disposable disposable) {
        if (disposable != null && mCompositeDisposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }

}
