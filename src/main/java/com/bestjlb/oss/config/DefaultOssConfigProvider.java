package com.bestjlb.oss.config;

/**
 * Created by yydx811 on 2018/1/3.
 */
public class DefaultOssConfigProvider implements OssConfigProvider {

    private volatile OssConfig config;

    public DefaultOssConfigProvider(OssConfig config) {
        setOssConfig(config);
    }

    @Override
    public synchronized void setOssConfig(OssConfig ossConfig) {
        if (config.isSTSModel() && config.isExpired()) {
            return;
        }
        this.config = ossConfig;
    }

    @Override
    public OssConfig getOssConfig() {
        return config;
    }
}
