package com.jp.app.injector.module;


import com.jp.data.remote.IRestServices;
import com.jp.data.remote.RestServices;
import com.jp.data.repository.SampleRepository;
import com.jp.domain.repository.ISampleRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class NetworkModule {

    @Binds
    @Singleton
    abstract IRestServices restServices(RestServices restServices);
}
