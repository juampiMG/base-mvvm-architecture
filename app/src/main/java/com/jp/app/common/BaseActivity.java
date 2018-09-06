package com.jp.app.common;

import android.app.AlertDialog;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.jp.app.BR;
import com.jp.app.R;
import com.jp.app.common.view.IBaseFragmentCallback;
import com.jp.app.common.viewModel.BaseViewModel;
import com.jp.app.ui.sample.SampleActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * TBaseViewModel is the reference to the ViewModel, if the activity has not reference to any, add BaseActivityViewModel instead
 */

public abstract class BaseActivity<TViewDataBinding extends ViewDataBinding, TBaseViewModel extends BaseViewModel> extends AppCompatActivity implements HasSupportFragmentInjector, IBaseFragmentCallback {

    private static final int DEFAULT_NUM_COUNT_BACK = 1;

    protected int layoutId;

    protected Fragment currentFragment;

    public enum actionOnError {
        CLOSE, NOTHING
    }

    @Inject
    DispatchingAndroidInjector<Fragment> mFragmentInjector;


    protected TViewDataBinding mViewDataBinding;
    protected TBaseViewModel mViewModel;

    private Unbinder unbinder;
    private CompositeDisposable compositeDisposable;


    private Toast exitToast;
    private int defaultCountBackToExit;
    private int countBackToExit;

    private Handler handler = new Handler();
    private Runnable restartCountBackToExit = new Runnable() {
        public void run() {
            countBackToExit = defaultCountBackToExit;
        }
    };

    // =============== HasFragmentInjector =========================================================

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return mFragmentInjector;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidInjection.inject(this);
        layoutId = getLayoutId();
        setContentView(layoutId);
        unbinder = ButterKnife.bind(this);
        setUpBackPressValues();
        performDataBinding();

    }

    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
            removeAllDisposables();
        }
    }

    // =============== Load View and Binding =======================================================

    protected abstract int getLayoutId();

    public int getBindingVariable() {
        return BR.viewModel;
    }

    /**
     * If the Activity has not interactions with views just return BaseActivityViewModel, if not
     * return the reference to the new ViewModel.
     * No forget to add the new ViewModel to Dagger as is done with the BaseActivityViewModel
     */

    public abstract TBaseViewModel getViewModel();

    public TViewDataBinding getViewDataBinding() {
        return mViewDataBinding;
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    // =============== Manage BackPress ============================================================

    @Override
    public void onBackPressed() {
        manageBackPressed();
    }

    private void setUpBackPressValues() {
        if (defaultCountBackToExit == 0) {
            defaultCountBackToExit = DEFAULT_NUM_COUNT_BACK;
        }
        countBackToExit = defaultCountBackToExit;
    }

    protected void manageBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            super.onBackPressed();
        }
        if (this instanceof SampleActivity) {
            if (exitToast == null) {
                exitToast = Toast.makeText(this, getString(R.string.count_back_exit_message), Toast.LENGTH_SHORT);
            }
            if (countBackToExit > 0) {
                countBackToExit--;
                removeCallbacks();
                handler.postDelayed(restartCountBackToExit, 2000);
                exitToast.show();
                getFragmentManager().popBackStack();
            } else {
                exitToast.cancel();
                super.onBackPressed();
            }

        } else {
            super.onBackPressed();
        }


    }

    private void removeCallbacks() {
        if (handler != null) {
            handler.removeCallbacks(restartCountBackToExit);
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
        if (compositeDisposable == null || compositeDisposable.isDisposed())
            compositeDisposable = new CompositeDisposable();
        return compositeDisposable;
    }

    public void addDisposable(Disposable disposable) {
        if (disposable != null && compositeDisposable != null) {
            compositeDisposable.add(disposable);
        }
    }

    public void removeDisposable(Disposable disposable) {
        if (disposable != null) {
            if (!disposable.isDisposed()) {
                disposable.dispose();
            }
            if (compositeDisposable != null) {
                compositeDisposable.remove(disposable);
            }
        }
    }

    public void removeAllDisposables() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }

    public boolean hasDisposables() {
        if (compositeDisposable != null) {
            return compositeDisposable.size() > 0;
        } else {
            return false;
        }
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
