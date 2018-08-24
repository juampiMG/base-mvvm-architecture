package com.jp.data.entities.sample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultSampleEntity {

    @SerializedName("data")
    @Expose
    public List<SampleEntity> data = null;

}
