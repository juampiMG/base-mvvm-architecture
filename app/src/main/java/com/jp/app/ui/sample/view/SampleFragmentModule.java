package com.jp.app.ui.sample.view;


import android.arch.lifecycle.ViewModelProvider;
import android.support.v4.app.Fragment;

import com.jp.app.common.ViewModelProviderFactory;
import com.jp.app.injector.scope.PerFragment;
import com.jp.app.ui.sample.viewModel.SampleViewModel;
import com.jp.app.ui.sample.viewModel.SampleViewModelModule;

import dagger.Binds;
import dagger.Module;


@Module(includes = {
        SampleViewModelModule.class
})
public abstract class SampleFragmentModule {

    @Binds
    @PerFragment
    abstract Fragment fragment(SampleFragment fragment);

    @Binds
    @PerFragment
    abstract ISampleView view(SampleFragment fragment);

    @Binds
    @PerFragment
    abstract ViewModelProvider.Factory provideSampleViewModel (ViewModelProviderFactory<SampleViewModel> sampleViewModel);


}
