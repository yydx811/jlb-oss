package com.bestjlb.oss;

import com.aliyun.oss.OSSClient;
import com.bestjlb.oss.config.OssConfig;

/**
 * Created by yydx811 on 2017/12/20.
 */
public class OssClientGenerator {

    private final OssConfig config;

    public OssClientGenerator(OssConfig config) {
        this.config = config;
    }

    public OSSClient getOssClient() {
        OSSClient client;
        if (config.isSTSModel()) {
            client = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret(), config.getToken());
        } else {
            client = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeyId());
        }
        return client;
    }

    public OssConfig getConfig() {
        return config;
    }
}
