package com.bestjlb.oss.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSErrorCode;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yydx811 on 2017/12/20.
 */
public class OssBucketUtils {

    /**
     * 列举全部桶
     *
     * @param client
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static List<Bucket> listBuckets(OSSClient client) throws OSSException, ClientException {
        return client.listBuckets();
    }

    /**
     * 按条件分页列举桶
     *
     * @param client
     * @param request
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static BucketList listBuckets(OSSClient client, ListBucketsRequest request) throws OSSException, ClientException {
        return client.listBuckets(request);
    }

    /**
     * 按条件分页列举桶
     *
     * @param client
     * @param prefix
     *          桶前缀，可以为null
     * @param marker
     *          从那个桶名开始，可以为null
     * @param maxKeys
     *          每页数量，0-1000，可以为null，默认100
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static BucketList listBuckets(OSSClient client, String prefix, String marker, Integer maxKeys) throws OSSException, ClientException {
        return listBuckets(client, new ListBucketsRequest(prefix, marker, maxKeys));
    }

    /**
     * 按条件列举全部桶
     *
     * @param client
     * @param prefix
     *          桶前缀，可以为null
     * @param marker
     *          从那个桶名开始，可以为null
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static List<Bucket> listAllBuckets(OSSClient client, String prefix, String marker) throws OSSException, ClientException {
        List<Bucket> result = new ArrayList<>();
        BucketList list;
        do {
            list = listBuckets(client, prefix, marker, 1000);
            result.addAll(list.getBucketList());
            marker = list.getNextMarker();
        } while (list.isTruncated());
        return result;
    }

    /**
     * 桶名是否存在
     *
     * @param client
     * @param bucketName
     *          桶名
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static boolean doesBucketExist(OSSClient client, String bucketName) throws OSSException, ClientException {
        return doesBucketExist(client, new GenericRequest(bucketName));
    }

    /**
     * 桶名是否存在
     *
     * @param client
     * @param request
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static boolean doesBucketExist(OSSClient client, GenericRequest request) throws OSSException, ClientException {
        return client.doesBucketExist(request);
    }

    /**
     * 创建桶
     *
     * @param client
     * @param bucketName
     *          桶名
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static Bucket createBucket(OSSClient client, String bucketName) throws OSSException, ClientException {
        return createBucket(client, new CreateBucketRequest(bucketName));
    }

    /**
     * 创建桶
     *
     * @param client
     * @param request
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static Bucket createBucket(OSSClient client, CreateBucketRequest request) throws OSSException, ClientException {
        return client.createBucket(request);
    }

    /**
     * 获取桶所在区域
     *
     * @param client
     * @param bucketName
     *          桶名
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static String getBucketLocation(OSSClient client, String bucketName) throws OSSException, ClientException {
        return getBucketLocation(client, new GenericRequest(bucketName));
    }

    /**
     * 获取桶所在区域
     *
     * @param client
     * @param request
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static String getBucketLocation(OSSClient client, GenericRequest request) throws OSSException, ClientException {
        return client.getBucketLocation(request);
    }

    /**
     * 设置桶的acl规则
     *
     * @param client
     * @param request
     * @throws OSSException
     * @throws ClientException
     */
    public static void setBucketAcl(OSSClient client, SetBucketAclRequest request) throws OSSException, ClientException {
        client.setBucketAcl(request);
    }

    /**
     * 设置桶的acl规则
     *
     * @param client
     * @param bucketName
     * @param cannedAcl
     *          acl规则 {@link CannedAccessControlList}
     * @throws OSSException
     * @throws ClientException
     */
    public static void setBucketAcl(OSSClient client, String bucketName, CannedAccessControlList cannedAcl) throws OSSException, ClientException {
        setBucketAcl(client, new SetBucketAclRequest(bucketName, cannedAcl));
    }

    /**
     * 获取桶的acl规则
     *
     * @param client
     * @param request
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static AccessControlList getBucketAcl(OSSClient client, GenericRequest request) throws OSSException, ClientException {
        return client.getBucketAcl(request);
    }

    /**
     * 获取桶的acl规则
     *
     * @param client
     * @param bucketName
     *          桶名
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static AccessControlList getBucketAcl(OSSClient client, String bucketName) throws OSSException, ClientException {
        return getBucketAcl(client, new GenericRequest(bucketName));
    }

    /**
     * 设置桶的跨域规则
     *
     * @param client
     * @param bucketName
     *          桶名
     * @param allowedOrigin
     *          允许来源
     * @param allowedMethod
     *          允许方法
     * @param allowedHeader
     *          允许头部
     * @param exposeHeader
     *          可以使XMLHttpRequest.getResponseHeader()拿到除了Cache-Control、Content-Language、Content-Type、Expires、Last-Modified、Pragma以外的其他头部，可选。
     * @param maxAge
     *          预检请求的有效期，秒。
     * @throws OSSException
     * @throws ClientException
     */
    public static void setBucketCORS(OSSClient client, String bucketName, List<String> allowedOrigin, List<String> allowedMethod, List<String> allowedHeader, List<String> exposeHeader, Integer maxAge)
            throws OSSException, ClientException {
        SetBucketCORSRequest request = new SetBucketCORSRequest(bucketName);

        SetBucketCORSRequest.CORSRule rule = new SetBucketCORSRequest.CORSRule();
        rule.setAllowedOrigins(allowedOrigin);
        rule.setAllowedMethods(allowedMethod);
        rule.setAllowedHeaders(allowedHeader);
        rule.setExposeHeaders(exposeHeader);
        rule.setMaxAgeSeconds(maxAge);

        request.addCorsRule(rule);
        setBucketCORS(client, request);
    }

    /**
     * 设置桶的跨域规则
     *
     * @param client
     * @param request
     * @throws OSSException
     * @throws ClientException
     */
    public static void setBucketCORS(OSSClient client, SetBucketCORSRequest request) throws OSSException, ClientException {
        client.setBucketCORS(request);
    }

    /**
     * 获取桶跨域规则
     *
     * @param client
     * @param request
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static List<SetBucketCORSRequest.CORSRule> getBucketCORSRules(OSSClient client, GenericRequest request) throws OSSException, ClientException {
        return client.getBucketCORSRules(request);
    }

    /**
     * 获取桶跨域规则
     *
     * @param client
     * @param bucketName
     *          桶名
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static List<SetBucketCORSRequest.CORSRule> getBucketCORSRules(OSSClient client, String bucketName) throws OSSException, ClientException {
        return getBucketCORSRules(client, new GenericRequest(bucketName));
    }

    /**
     * 删除桶跨域规则
     *
     * @param client
     * @param request
     * @throws OSSException
     * @throws ClientException
     */
    public static void deleteBucketCORSRules(OSSClient client, GenericRequest request) throws OSSException, ClientException {
        client.deleteBucketCORSRules(request);
    }

    /**
     * 删除桶跨域规则
     *
     * @param client
     * @param bucketName
     *          桶名
     * @throws OSSException
     * @throws ClientException
     */
    public static void deleteBucketCORSRules(OSSClient client, String bucketName) throws OSSException, ClientException {
        deleteBucketCORSRules(client, new GenericRequest(bucketName));
    }

    public static void setBucketLifecycle() throws OSSException, ClientException {
        throw new ClientException("current version doesn't implement!", OSSErrorCode.NOT_IMPLEMENTED, "no request id!");
    }

    public static List<LifecycleRule> getBucketLifecycle() throws OSSException, ClientException {
        throw new ClientException("current version doesn't implement!", OSSErrorCode.NOT_IMPLEMENTED, "no request id!");
    }

    public static void deleteBucketLifecycle() throws OSSException, ClientException {
        throw new ClientException("current version doesn't implement!", OSSErrorCode.NOT_IMPLEMENTED, "no request id!");
    }

    public static void setBucketLogging() throws OSSException, ClientException {
        throw new ClientException("current version doesn't implement!", OSSErrorCode.NOT_IMPLEMENTED, "no request id!");
    }

    public static BucketLoggingResult getBucketLogging() throws OSSException, ClientException {
        throw new ClientException("current version doesn't implement!", OSSErrorCode.NOT_IMPLEMENTED, "no request id!");
    }

    public static void deleteBucketLogging() throws OSSException, ClientException {
        throw new ClientException("current version doesn't implement!", OSSErrorCode.NOT_IMPLEMENTED, "no request id!");
    }

    public static void setBucketReferer() throws OSSException, ClientException {
        throw new ClientException("current version doesn't implement!", OSSErrorCode.NOT_IMPLEMENTED, "no request id!");
    }

    public static List<String> getBucketReferer() throws OSSException, ClientException {
        throw new ClientException("current version doesn't implement!", OSSErrorCode.NOT_IMPLEMENTED, "no request id!");
    }

    public static void deleteBucketReferer() throws OSSException, ClientException {
        throw new ClientException("current version doesn't implement!", OSSErrorCode.NOT_IMPLEMENTED, "no request id!");
    }

    public static void setBucketWebsite() throws OSSException, ClientException {
        throw new ClientException("current version doesn't implement!", OSSErrorCode.NOT_IMPLEMENTED, "no request id!");
    }

    public static BucketWebsiteResult getBucketWebsite() throws OSSException, ClientException {
        throw new ClientException("current version doesn't implement!", OSSErrorCode.NOT_IMPLEMENTED, "no request id!");
    }

    public static void deleteBucketWebsite() throws OSSException, ClientException {
        throw new ClientException("current version doesn't implement!", OSSErrorCode.NOT_IMPLEMENTED, "no request id!");
    }

    /**
     * 删除桶
     *
     * @param client
     * @param request
     * @throws OSSException
     * @throws ClientException
     */
    public static void deleteBucket(OSSClient client, GenericRequest request) throws OSSException, ClientException {
        client.deleteBucket(request);
    }

    /**
     * 删除桶
     *
     * @param client
     * @param bucketName
     *          桶名
     * @throws OSSException
     * @throws ClientException
     */
    public static void deleteBucket(OSSClient client, String bucketName) throws OSSException, ClientException {
        deleteBucket(client, new GenericRequest(bucketName));
    }
}
