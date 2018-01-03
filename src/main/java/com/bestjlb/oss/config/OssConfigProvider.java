package com.bestjlb.oss.config;

/**
 * Created by yydx811 on 2018/1/3.
 */
public interface OssConfigProvider {

    void setOssConfig(OssConfig ossConfig);

    OssConfig getOssConfig();
}
