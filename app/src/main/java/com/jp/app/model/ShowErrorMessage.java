package com.jp.app.model;


import com.jp.app.common.BaseActivity;

import org.parceler.Parcel;

import lombok.Data;

@Parcel
@Data
public class ShowErrorMessage {

    public ShowErrorMessage() {}

    String title;
    String message;
    BaseActivity.actionOnError actionOnError;
}
