package com.binding.model.rxjava;

import android.support.annotation.Nullable;

public interface LoadingAnimator {
    void loading(boolean start, @Nullable Throwable throwable);

}
