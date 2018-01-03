package com.bestjlb.oss.config;

/**
 * Created by yydx811 on 2017/12/20.
 */
public class OssConfig {
    
    private volatile String accessKeyId;
    
    private volatile String accessKeySecret;

    private volatile String token;

    private int isSTSModel = 1;

    private volatile long expires;

    /**
     * 普通模式
     *
     * @param accessKeyId
     * @param accessKeySecret
     */
    public OssConfig(String accessKeyId, String accessKeySecret) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.isSTSModel = 2;
    }

    /**
     * STS模式
     *
     * @param accessKeyId
     * @param accessKeySecret
     * @param token
     * @param expires
     */
    public OssConfig(String accessKeyId, String accessKeySecret, String token, long expires) {
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.token = token;
        this.expires = expires;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public String getToken() {
        return token;
    }

    public int getIsSTSModel() {
        return isSTSModel;
    }

    public long getExpires() {
        return expires;
    }

    public boolean isSTSModel() {
        return this.isSTSModel == 1;
    }

    public boolean isExpired() {
        return this.expires <= System.currentTimeMillis();
    }
}
