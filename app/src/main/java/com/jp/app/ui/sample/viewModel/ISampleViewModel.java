package com.jp.app.ui.sample.viewModel;

import android.arch.lifecycle.MutableLiveData;

import com.jp.app.common.viewModel.IBaseViewModel;
import com.jp.app.model.SampleView;

import java.util.List;

public interface ISampleViewModel extends IBaseViewModel {

    MutableLiveData<List<SampleView>> getSamples();

    void addSamples(List<SampleView> samples);

    void callGetSamples();
}
