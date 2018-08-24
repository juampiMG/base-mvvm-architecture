package com.jp.app.injector.module;


import com.jp.data.remote.IRestServices;
import com.jp.data.remote.RestServices;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    IRestServices restServices() {
        return new RestServices();
    }
}
