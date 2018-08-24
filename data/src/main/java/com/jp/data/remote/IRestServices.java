package com.jp.data.remote;

import com.jp.data.entities.sample.ResultSampleEntity;

import io.reactivex.Single;

public interface IRestServices {

    Single<ResultSampleEntity> getSamples();

}
