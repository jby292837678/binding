package com.binding.model.model.request;

public interface RecyclerStatus {
    int loadBottom = 0;
    int loadTop = 1;
    int init = 2;
    int resumeError = 3;
    int click = 4;
}