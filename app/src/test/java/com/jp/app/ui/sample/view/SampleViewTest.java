package com.jp.app.ui.sample.view;

import android.app.Dialog;

import com.jp.app.ui.BaseTest;
import com.jp.app.ui.sample.SampleActivity;
import com.jp.app.ui.sample.adapter.SampleAdapter;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowDialog;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

public class SampleViewTest extends BaseTest {

    SampleActivity mActivity;

    SampleFragment mFragment;

    @Before
    public void setup() {
        mActivity = Robolectric.setupActivity(SampleActivity.class);

        mFragment = (SampleFragment) mActivity.getCurrentFragment();

        startFragment (mFragment);
    }


    @Override
    public void controlViews() {
        assertNotNull(mActivity);
        assertNotNull(mFragment);
    }

    @Test
    public void checkRecyclerViewNotNull() {
        assertNotNull(mFragment.mRecyclerView);
    }

    @Test
    public void checkAdapter() {
        assertNotNull(mFragment.getAdapter());
    }

    @Test
    public void checkAdapterLoadAllData() {
        assertEquals(3, mFragment.getAdapter().getItemCount());
    }


    @Test
    public void checkFirstRowViewsData() {

        SampleAdapter.ItemViewHolder holder = (SampleAdapter.ItemViewHolder) mFragment.getAdapter().onCreateViewHolder(mFragment.mRecyclerView, 0);
        mFragment.getAdapter().onBindViewHolder(holder, 0);

        assertNotNull(holder);
        assertEquals("Sample test", holder.title.getText().toString());

    }

    @Test
    public void checkOnClickFirstRowData() {

        mFragment.mRecyclerView.getChildAt(0).performClick();
        Dialog dialog = ShadowDialog.getLatestDialog();
        assertEquals("The dialog should be displayed", dialog.isShowing(),true);

    }

}
