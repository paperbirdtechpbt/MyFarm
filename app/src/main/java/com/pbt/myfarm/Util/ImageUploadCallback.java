package com.pbt.myfarm.Util;

public interface ImageUploadCallback {
    void onProgressUpdate(int percentage);
    void onError(String message);
    void onSuccess(String message);
}
