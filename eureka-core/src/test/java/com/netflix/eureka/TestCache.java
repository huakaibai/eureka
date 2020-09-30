package com.netflix.eureka;

import com.google.common.cache.*;
import com.netflix.eureka.registry.Key;
import com.netflix.eureka.registry.ResponseCacheImpl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author zhibin.wang
 * @desc 测试缓存失效
 **/
public class TestCache {

    private final static LoadingCache<String,String > readWriteCacheMap = CacheBuilder.newBuilder().initialCapacity(1000)
            .expireAfterWrite(180, TimeUnit.SECONDS)
            .removalListener(new RemovalListener<String, String>() {
                @Override
                public void onRemoval(RemovalNotification<String, String> notification) {

                }
            })
            // 初始化时加载缓存,在缓存失效后会自动重新加载缓存？
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    System.out.println("数据初始化:"+key);

                    return "123445";
                }
            });

    public static void main(String[] args) throws ExecutionException {
        String s = readWriteCacheMap.get("1111");
        System.out.println(s);
    }
}
