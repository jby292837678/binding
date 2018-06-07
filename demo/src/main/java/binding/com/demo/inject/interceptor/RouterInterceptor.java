package binding.com.demo.inject.interceptor;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

import timber.log.Timber;

/**
 * Created by apple on 2017/7/30.
 */
@Interceptor(priority = 100)
public class RouterInterceptor implements IInterceptor{
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {
        Timber.i("init router interceptor");
    }
}
