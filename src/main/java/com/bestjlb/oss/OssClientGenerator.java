package com.bestjlb.oss;

import com.aliyun.oss.OSSClient;
import com.bestjlb.oss.config.DefaultOssConfigProvider;
import com.bestjlb.oss.config.OssConfig;
import com.bestjlb.oss.config.OssConfigProvider;

/**
 * Created by yydx811 on 2017/12/20.
 */
public class OssClientGenerator {

    private final String endpoint;

    private final String bucketName;

    private final OssConfigProvider provider;

    /**
     * 普通模式
     *
     * @param endpoint
     *          oss区域地址。
     * @param bucketName
     *          桶名。
     * @param accessKeyId
     * @param accessKeySecret
     */
    public OssClientGenerator(String endpoint, String bucketName, String accessKeyId, String accessKeySecret) {
        this(endpoint, bucketName, new OssConfig(accessKeyId, accessKeySecret));
    }

    /**
     * sts模式
     *
     * @param endpoint
     *          oss区域地址。
     * @param bucketName
     *          桶名。
     * @param accessKeyId
     * @param accessKeySecret
     * @param token
     * @param expires
     *          过期时间。
     */
    public OssClientGenerator(String endpoint, String bucketName, String accessKeyId, String accessKeySecret, String token, long expires) {
        this(endpoint, bucketName, new OssConfig(accessKeyId, accessKeySecret, token, expires));
    }

    public OssClientGenerator(String endpoint, String bucketName, OssConfig config) {
        this(endpoint, bucketName, new DefaultOssConfigProvider(config));
    }

    public OssClientGenerator(String endpoint, String bucketName, OssConfigProvider provider) {
        this.endpoint = endpoint;
        this.bucketName = bucketName;
        this.provider = provider;
    }

    public OSSClient getOssClient() {
        OSSClient client;
        if (provider.getOssConfig().isSTSModel()) {
            client = new OSSClient(endpoint, provider.getOssConfig().getAccessKeyId(), provider.getOssConfig().getAccessKeySecret(), provider.getOssConfig().getToken());
        } else {
            client = new OSSClient(endpoint, provider.getOssConfig().getAccessKeyId(), provider.getOssConfig().getAccessKeyId());
        }
        return client;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getBucketName() {
        return bucketName;
    }
}
