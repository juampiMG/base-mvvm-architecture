package com.jp.app.ui.sample.view;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jp.app.BR;
import com.jp.app.R;
import com.jp.app.common.view.BaseFragment;
import com.jp.app.common.view.IBaseFragmentCallback;
import com.jp.app.common.viewModel.BaseViewModel;
import com.jp.app.databinding.SampleFragmentBinding;
import com.jp.app.model.SampleView;
import com.jp.app.ui.sample.adapter.SampleAdapter;
import com.jp.app.ui.sample.viewModel.SampleViewModel;

import butterknife.BindView;


public class SampleFragment extends BaseFragment<SampleFragmentBinding, SampleFragment.FragmentCallback> implements ISampleView {

    public static final int LAYOUT_ID = R.layout.sample_fragment;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    SampleViewModel mSampleViewModel;

    private LinearLayoutManager mGridLayoutManager;
    private SampleAdapter mAdapter;

    public static SampleFragment newInstance(Bundle bundle) {
        SampleFragment fragment = new SampleFragment();
        bundle = bundle == null ? new Bundle() : bundle;
        fragment.setArguments(bundle);
        return fragment;
    }


    public interface FragmentCallback extends IBaseFragmentCallback {
        void loadSampleInfo(SampleView Sample);
    }

    @Override
    public BaseViewModel<ISampleView> getViewModel() {
        mSampleViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SampleViewModel.class);
        return mSampleViewModel;
    }

    @Override
    public int getLayoutId() {
        return LAYOUT_ID;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public void onViewLoaded(Bundle savedInstanceState, View view) {
        super.onViewLoaded(savedInstanceState, view);
        setUpRecyclerView();
        subscribeToLiveData();
        callGetSamples();
    }

    private void callGetSamples() {
        mSampleViewModel.callGetSamples();
    }

    private void setUpRecyclerView() {
        mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mViewDataBinding.recyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mAdapter = new SampleAdapter(mSampleViewModel);
        mViewDataBinding.recyclerView.setAdapter(mAdapter);
    }


    @Override
    public void loadSampleInfo(SampleView Sample) {
        if (mCallback != null) {
            mCallback.loadSampleInfo(Sample);
        }
    }

    public SampleAdapter getAdapter() {
        return mAdapter;
    }

    private void subscribeToLiveData() {
        mSampleViewModel.getSamples().observe(this, samples -> mSampleViewModel.addSamples(samples));
    }
}

