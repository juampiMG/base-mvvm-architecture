package com.jp.app.common.viewModel;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel<N> extends ViewModel {

    @Inject
    Context mContext;

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


    public void setView(N navigator) {
        this.mWeakReference = new WeakReference<>(navigator);
    }

    public N getView() {
        return mWeakReference.get();
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
