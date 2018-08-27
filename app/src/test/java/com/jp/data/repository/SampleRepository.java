package com.jp.data.repository;

import com.jp.data.ServerMock;
import com.jp.data.entities.mapper.SampleEntityMapper;
import com.jp.data.remote.IRestServices;
import com.jp.domain.model.SampleDomain;
import com.jp.domain.repository.ISampleRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class SampleRepository implements ISampleRepository {
    @Inject
    SampleEntityMapper mSampleEntityMapper;

    IRestServices mRestServices;

    @Inject
    public SampleRepository(IRestServices restServices){
        mRestServices = restServices;
    }


    @Override
    public Single<List<SampleDomain>> getSamples() {
        return Single.just(ServerMock.getListDomain());
    }

}
