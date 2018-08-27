package com.jp.app.ui.sample.viewModel;

import com.jp.app.ui.BaseTest;
import com.jp.app.ui.sample.SampleActivity;
import com.jp.app.ui.sample.view.ISampleView;
import com.jp.app.ui.sample.view.SampleFragment;
import com.jp.data.ServerMock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.android.controller.ActivityController;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.verify;

public class SampleViewModelTest extends BaseTest {


    SampleViewModel mViewModel;

    ActivityController<SampleActivity> mActivityController;

    SampleActivity mActivity;

    SampleFragment mFragment;

    @Before
    public void setup() {
        mActivityController = Robolectric.buildActivity(SampleActivity.class);
        mActivityController.create().start().resume();
        mActivity = mActivityController.get();
        mFragment = (SampleFragment) mActivity.getCurrentFragment();
        mViewModel = (SampleViewModel) mFragment.getViewModel();
    }


    @Override
    public void controlViews() {
        assertNotNull(mActivity);
        assertNotNull(mFragment);
        assertNotNull (mViewModel);
    }

    @Test
    public void checkLoadSample () {
//        assertEquals(mViewModel.mSampleViewMutableList.getValue().get(0), ServerMock.getSampleView());
        assertEquals(mViewModel.mSampleViewMutableList.getValue().size(), 20);
    }
}
