package com.jp.app.ui.sample.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.jp.app.common.BaseActivity;
import com.jp.app.common.BaseSingleObserver;
import com.jp.app.common.viewModel.BaseViewModel;
import com.jp.app.model.SampleView;
import com.jp.app.model.mapper.SampleViewMapper;
import com.jp.app.ui.sample.adapter.SampleAdapter;
import com.jp.app.ui.sample.view.ISampleView;
import com.jp.domain.interactor.IGetSampleUseCase;
import com.jp.domain.model.SampleDomain;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SampleViewModel extends BaseViewModel<ISampleView> implements ISampleViewModel, SampleAdapter.SampleAdapterCallBack {

    @Inject
    IGetSampleUseCase mGetSampleUseCase;
    @Inject
    SampleViewMapper mSampleViewMapper;

    protected final MutableLiveData<List<SampleView>> mSampleViewMutableList;

    public final ObservableList<SampleView> mSampleViewObservableArrayList = new ObservableArrayList<>();

    List<SampleDomain> mSampleDomain;


    @Inject
    public SampleViewModel() {
        super();
        mSampleViewMutableList = new MutableLiveData<>();
    }

    @Override
    public void sampleClicked(int adapterPosition) {
        getView().loadSampleInfo(mSampleViewObservableArrayList.get(adapterPosition));
    }

    public MutableLiveData<List<SampleView>> getSamples() {
        return mSampleViewMutableList;
    }

    @Override
    public void addSamples(List<SampleView> samples) {
        mSampleViewObservableArrayList.clear();
        mSampleViewObservableArrayList.addAll(samples);
    }

    @Override
    public void callGetSamples() {
        setIsLoading(true);
        mGetSampleUseCase.execute().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSingleObserver<List<SampleDomain>>(this) {

                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onSuccess(List<SampleDomain> Sample) {
                        setIsLoading(false);
                        if (Sample != null) {
                            mSampleDomain = Sample;
                            mSampleViewMutableList.setValue(mSampleViewMapper.transform(Sample));
                        }
                    }

                    @Override
                    protected void onError(int code, String title, String description) {
                        setIsLoading(false);
                        showError(title, description, BaseActivity.actionOnError.CLOSE);
                    }
                });
    }
}
