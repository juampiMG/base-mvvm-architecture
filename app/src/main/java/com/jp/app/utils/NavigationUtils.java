package com.jp.app.utils;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jp.app.common.view.BaseFragment;
import com.jp.app.model.SampleView;


public class NavigationUtils {

    public static void navigateToFragment(Activity activity, FragmentManager supportFragmentManager, Fragment fragment, int contentFrame, boolean addToBackStack) {
        pushFragment(activity, fragment, supportFragmentManager, contentFrame, addToBackStack);
    }

    private static String getFragmentTag(Fragment fragment) {
        String tag;
        if (fragment instanceof BaseFragment) {
            tag = ((BaseFragment) fragment).getFragmentId();
        } else {
            tag = ((Object) fragment).getClass().getName();
        }
        return tag;
    }

    private static void pushFragment(Activity activity, Fragment fragment, FragmentManager supportFragmentManager, int contentFrame, boolean addToBackStack) {
        if (fragment == null) {
            return;
        }

        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        String tag = getFragmentTag(fragment);

        fragmentTransaction.replace(contentFrame, fragment, tag);

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(tag);
        }

        fragmentTransaction.commit();

        // Only calling executePendingTransactions() if no nested Fragment call
        if (contentFrame <= 0) {
            activity.getFragmentManager().executePendingTransactions();
        }

    }
}
