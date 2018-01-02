package com.bestjlb.oss.config;

/**
 * Created by yydx811 on 2017/12/20.
 */
public class OssConfig {
    
    private String endpoint;
    
    private String accessKeyId;
    
    private String accessKeySecret;
    
    private String bucketName;
    
    private String token;

    private int isSTSModel = 1;

    private long expires;

    public synchronized void refresh(String accessKeyId, String accessKeySecret, String token, long expires) {
        if (isSTSModel() && isExpired()) {
            this.accessKeyId = accessKeyId;
            this.accessKeySecret = accessKeySecret;
            this.token = token;
            this.expires = expires;
        }
    }

    /**
     * 普通模式
     *
     * @param endpoint
     * @param accessKeyId
     * @param accessKeySecret
     * @param bucketName
     */
    public OssConfig(String endpoint, String accessKeyId, String accessKeySecret, String bucketName) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketName = bucketName;
        this.isSTSModel = 2;
    }

    /**
     * STS模式
     *
     * @param endpoint
     * @param accessKeyId
     * @param accessKeySecret
     * @param bucketName
     * @param token
     * @param expires
     */
    public OssConfig(String endpoint, String accessKeyId, String accessKeySecret, String bucketName, String token, long expires) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.bucketName = bucketName;
        this.token = token;
        this.expires = expires;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public String getBucketName() {
        return bucketName;
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
