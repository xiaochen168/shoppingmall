package com.czn.shoppingmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class TokenCache {

    private static Logger logger = LoggerFactory.getLogger(TokenCache.class);

    public static final String TOKEN_PREFIX = "token_";

    // 构建token缓存
    private static LoadingCache<String,String> tokenCache = CacheBuilder.newBuilder().initialCapacity(1000)
                                   .maximumSize(10000)
                                   .expireAfterAccess(12, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    return "null";
                }
            });
    public static void setToken(String tokenKey, String tokenValue) {
        tokenCache.put(tokenKey, tokenValue);
    }

    public static String getToken(String tokenKey) {
        String tokenValue = null;
        try {
            tokenValue = tokenCache.get(tokenKey);
        } catch (ExecutionException e) {
            logger.info("tokenCache get error ", e);
        }
        if ("null".equals(tokenValue)) {
           return null;
        }
        return tokenValue;
    }
}
