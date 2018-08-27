
package com.jp.app.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jp.app.model.SampleView;
import com.jp.app.ui.sample.adapter.SampleAdapter;

import java.util.List;

public final class BindingUtils {

    @BindingAdapter({"sample_adapter"})
    public static void addSamples(RecyclerView recyclerView, List<SampleView> sampleViews) {
        SampleAdapter adapter = (SampleAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addSamples(sampleViews);
        }
    }


    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(url).into(imageView);
    }
}
