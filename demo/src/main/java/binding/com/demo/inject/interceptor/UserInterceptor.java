package binding.com.demo.inject.interceptor;

import android.content.Context;

import binding.com.demo.inject.qualifier.context.AppContext;
import com.binding.model.util.BaseUtil;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * project：cutv_ningbo
 * description：
 * create developer： admin
 * create time：10:01
 * modify developer：  admin
 * modify time：10:01
 * modify remark：
 *
 * @version 2.0
 */

public class UserInterceptor implements Interceptor {
    private Context context;

    @Inject
    public UserInterceptor(@AppContext Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        boolean isConnection = BaseUtil.isNetworkConnected(context);
//        Timber.i("token:%1s",DemoApplication.getUser().getToken());
        if (isConnection) {
            request = request.newBuilder()
                    .removeHeader("Pragma")
//                    .header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7)
                    .addHeader("User-Agent", "android")
//                    .addHeader("Authorization", "Bearer " + DemoApplication.getUser().getToken())
                    .build();
        } else {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .addHeader("User-Agent", "android")
                    .build();
        }
        Response response = chain.proceed(request);
        if(response.code() == 401){
            return response.newBuilder().code(401).message("").build();
        }
        return response;
    }
}
