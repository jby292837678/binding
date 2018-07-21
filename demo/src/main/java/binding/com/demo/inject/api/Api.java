package binding.com.demo.inject.api;

import binding.com.demo.base.util.InfoEntity;


import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by arvin on 2017/11/28.
 */

public interface Api {
    String host = "";
//
//    @POST("paladin1/Passport/dologin")
//    Flowable<InfoEntity<UserEntity>> login();
    @GET("https://www.baidu.com")
    Observable<InfoEntity<String>> baidu();

}
