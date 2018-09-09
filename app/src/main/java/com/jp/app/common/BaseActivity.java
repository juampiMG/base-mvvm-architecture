package com.jp.app.common;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jp.app.R;
import com.jp.app.common.view.IBaseFragmentCallback;
import com.jp.app.ui.sample.SampleActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity implements HasSupportFragmentInjector, IBaseFragmentCallback {

    private static final int DEFAULT_NUM_COUNT_BACK = 1;

    protected int mLayoutId;

    protected Fragment mCurrentFragment;

    public enum actionOnError {
        CLOSE, NOTHING
    }

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentInjector;

    @Nullable
    @BindView(R.id.generic_loading)
    RelativeLayout mGenericLoading;

    private Unbinder mUnBinder;
    private CompositeDisposable mCompositeDisposable;
    private Handler mHandler = new Handler();
    private Toast mExitToast;

    private int mDefaultCountBackToExit;
    private int mCountBackToExit;

    // =============== HasFragmentInjector =========================================================

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentInjector;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLayoutId = getLayoutId();
        setContentView(mLayoutId);

        AndroidInjection.inject(this);
        mUnBinder = ButterKnife.bind(this);

        setUpBackPressValues();

    }

    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null) {
            mUnBinder.unbind();
            removeAllDisposables();
        }
    }

    // =============== Manage Views ================================================================

    protected abstract int getLayoutId();

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    // =============== Manage BackPress ============================================================

    @Override
    public void onBackPressed() {
        manageBackPressed();
    }

    private void setUpBackPressValues() {
        if (mDefaultCountBackToExit == 0) {
            mDefaultCountBackToExit = DEFAULT_NUM_COUNT_BACK;
        }
        mCountBackToExit = mDefaultCountBackToExit;
    }

    private Runnable mRestartCountBackToExit = new Runnable() {
        public void run() {
            mCountBackToExit = mDefaultCountBackToExit;
        }
    };

    protected void manageBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();
        }
        if (this instanceof SampleActivity) {
            if (mExitToast == null) {
                mExitToast = Toast.makeText(this, getString(R.string.count_back_exit_message), Toast.LENGTH_SHORT);
            }
            if (mCountBackToExit > 0) {
                mCountBackToExit--;
                removeCallbacks();
                mHandler.postDelayed(mRestartCountBackToExit, 2000);
                mExitToast.show();
                getFragmentManager().popBackStack();
            } else {
                mExitToast.cancel();
                super.onBackPressed();
            }

        } else {
            super.onBackPressed();
        }
    }

    private void removeCallbacks() {
        if (mHandler != null) {
            mHandler.removeCallbacks(mRestartCountBackToExit);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                manageBackPressed();
                break;
        }
        return true;
    }


    // =============== Manage Disposable ===========================================================

    public CompositeDisposable getCompositeDisposable() {
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed())
            mCompositeDisposable = new CompositeDisposable();
        return mCompositeDisposable;
    }

    public void addDisposable(Disposable disposable) {
        if (disposable != null && mCompositeDisposable != null) {
            mCompositeDisposable.add(disposable);
        }
    }

    public void removeDisposable(Disposable disposable) {
        if (disposable != null) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
            if (mCompositeDisposable != null) {
                mCompositeDisposable.remove(disposable);
            }
        }
    }

    public void removeAllDisposables() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    public boolean hasDisposables() {
        if (mCompositeDisposable != null) {
            return mCompositeDisposable.size() > 0;
        } else {
            return false;
        }
    }

    // =============== Generic Loading =============================================================

    public void showLoading() {
        if (mGenericLoading != null) mGenericLoading.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        if (mGenericLoading != null) mGenericLoading.setVisibility(View.GONE);
    }

    // =============== ShowDialogs =================================================================

    @Override
    public void showError(String title, String message, actionOnError action) {
        showErrorDialog(title, message, action);
    }

    @Override
    public void showMessage(String title, String message) {
        showMessageDialog(title, message);
    }

    private void showErrorDialog(String title, String message, final actionOnError action) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle(title);
        builder.setPositiveButton(R.string.accept, (dialog, which) -> {
            if (action == actionOnError.CLOSE) {
                finish();
            }
            dialog.dismiss();
        });

        builder.create().show();
    }

    private void showMessageDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle(title);
        builder.setPositiveButton(R.string.accept, (dialog, which) -> {
            dialog.dismiss();
        });

        builder.create().show();
    }
}
