package com.jp.data.remote;

import com.jp.data.Constants;
import com.jp.data.entities.sample.ResultSampleEntity;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class RestServices implements IRestServices {


    @Inject
    public RestServices() {
    }

    @Override
    public Single<ResultSampleEntity> getSamples() {
        return Rx2AndroidNetworking.get(Constants.BASE_URL_SAMPLES)
                .build()
                .getObjectSingle(ResultSampleEntity.class);
    }

}
