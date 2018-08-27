package com.jp.app.injector.module;


import com.jp.data.ServerMock;
import com.jp.data.repository.SampleRepository;
import com.jp.domain.repository.ISampleRepository;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Single;

import static org.mockito.Mockito.when;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    ISampleRepository sampleRepository(SampleRepository repository) {
        repository = Mockito.mock(SampleRepository.class);
        when(repository.getSamples()).thenReturn(Single.just(ServerMock.getListDomain()));
        return repository;
    }
}
