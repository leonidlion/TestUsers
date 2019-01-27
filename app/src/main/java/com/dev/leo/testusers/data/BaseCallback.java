package com.dev.leo.testusers.data;

public interface BaseCallback<T> {
    void onSuccess(T data);
    void onError(int code, String message);
    void onFailure(Throwable throwable);
}
