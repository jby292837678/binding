package com.binding.library.util;//package com.binding.library.util;
//
//import android.content.Context;
//
//import com.alibaba.sdk.android.oss.ClientConfiguration;
//import com.alibaba.sdk.android.oss.OSS;
//import com.alibaba.sdk.android.oss.OSSClient;
//import com.alibaba.sdk.android.oss.common.OSSLog;
//import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
//import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
//import com.alibaba.sdk.android.oss.model.PutObjectRequest;
//import com.alibaba.sdk.android.oss.model.PutObjectResult;
//
//import java.io.File;
//
//import io.reactivex.Observable;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.schedulers.Schedulers;
//
///**
// * Created by pc on 2017/8/19.
// */
//
//public class AliyunOssUtil {
//    private static final String TAG = "AliyunOssUtil";
//    public static final String testBucket = "ideabuy";
//    private static final String accessKey = "LTAIXijUalhN321o";
//    private static final String screctKey = "d0ouwDjnRV7CpG5M1EptbAIhnYJQlD";
//    private static final String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
//    //    public static final String srcFileDir = Environment.getExternalStorageDirectory() + AppConfig.DEFAULT_IMG_CACHE_PATH + "/";
//    private static OSS oss;
//    private static AliyunOssUtil instance = new AliyunOssUtil();
//
//    public static AliyunOssUtil getInstance() {
//        return instance;
//    }
//
//    public static void init(Context context) {
//        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(accessKey, screctKey, "<StsToken.SecurityToken>");
//        ClientConfiguration conf = new ClientConfiguration();
//        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
//        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
//        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
//        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
//        OSSLog.enableLog();
////        oss = new OSSClient(context, endpoint, credentialProvider, conf);
//    }
//
//    //oss, testBucket, uploadObject, uploadFilePath
//    public static void upload(final File file) {
//        if (file == null || !file.exists() || !file.isFile()) return;
//        Observable<File> observable = Observable.create(emitter -> {
//            emitter.onNext(file);
//            PutObjectRequest put = new PutObjectRequest(testBucket, "", file.getAbsolutePath());
//            PutObjectResult putResult = oss.putObject(put);
////                putResult.getETag()
//        });
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(o -> {
//
//                }, throwable -> {
//
//                });
//
//    }
//}
