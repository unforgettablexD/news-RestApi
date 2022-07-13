package com.example.myapplicationscrap.Listeners;

import com.example.myapplicationscrap.Models.NewsHeadline;

import java.util.List;

public interface OnFetchDataListener<T> {
    void onFetchData(List<NewsHeadline> data,  String message);
    void onError(String message);

}
