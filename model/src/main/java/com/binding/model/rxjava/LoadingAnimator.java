package com.binding.model.rxjava;

import androidx.annotation.Nullable;

public interface LoadingAnimator {
    void loading(boolean start, @Nullable Throwable throwable);

}
