package top.codeease.smartcoder.config;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * @author by: ly
 * @ClassName: OkHttpClientSingleton
 * @Description: OkHttp模块的单例实现
 * @Date: 2024/5/15 上午9:52
 */
public class OkHttpClientSingleton {
    private static OkHttpClient instance;

    private OkHttpClientSingleton() {
        // 私有构造函数，防止外部实例化
    }

    public static OkHttpClient getInstance() {
        if (instance == null) {
            synchronized (OkHttpClientSingleton.class) {
                if (instance == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();

                    // 设置连接超时
                    builder.connectTimeout(60, TimeUnit.SECONDS);
                    // 设置读取超时
                    builder.readTimeout(60, TimeUnit.SECONDS);
                    // 设置写入超时
                    builder.writeTimeout(60, TimeUnit.SECONDS);

                    // 构建 OkHttpClient 实例
                    instance = builder.build();
                }
            }
        }
        return instance;
    }
}