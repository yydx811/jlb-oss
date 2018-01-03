package com.bestjlb.oss.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.internal.OSSConstants;
import com.aliyun.oss.model.*;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.Asserts;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yydx811 on 2017/12/20.
 */
public class OssObjectUtils {

    private static final ExecutorService UPLOAD_EXECUTOR = Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors() * 2);

    private static final ExecutorService DOWNLOAD_EXECUTOR = Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors() * 2);

    private static final long DEFAULT_PART_SIZE = OSSConstants.KB * 1024L;

    /**
     * 按条件分页列举文件
     *
     * @param client
     * @param request
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static ObjectListing listObjects(OSSClient client, ListObjectsRequest request) throws OSSException, ClientException {
        return client.listObjects(request);
    }

    /**
     * 按条件分页列举文件
     *
     * @param client
     * @param bucketName
     *          桶名，不能为空
     * @param prefix
     *          桶前缀，可以为null
     * @param marker
     *          从那个文件开始，可以为null
     * @param maxKeys
     *          每页数量，0-1000，可以为null，默认100
     * @param delimiter
     *          分割符，目录名，/为根目录，显示当前目录下的文件夹和文件，https://help.aliyun.com/document_detail/31860.html?spm=5176.doc31978.6.600.dJrF1U
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static ObjectListing listObjects(OSSClient client, String bucketName, String prefix, String marker, String delimiter, Integer maxKeys) throws OSSException, ClientException {
        return listObjects(client, new ListObjectsRequest(bucketName, prefix, marker, delimiter, maxKeys));
    }

    /**
     * 按条件列举全部文件
     *
     * @param client
     * @param bucketName
     *          桶名，不能为空
     * @param prefix
     *          桶前缀，不能为空
     * @param marker
     *          从那个文件开始，可以为null
     * @param delimiter
     *          分割符，目录名，/为根目录，显示当前目录下的文件夹和文件，https://help.aliyun.com/document_detail/31860.html?spm=5176.doc31978.6.600.dJrF1U
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static List<OSSObjectSummary> listAllObjects(OSSClient client, String bucketName, String prefix, String marker, String delimiter) throws OSSException, ClientException {
        Asserts.check(StringUtils.isBlank(prefix), "");
        List<OSSObjectSummary> result = new ArrayList<>();
        ObjectListing list;
        do {
            list = listObjects(client, bucketName, prefix, marker, delimiter, 1000);
            result.addAll(list.getObjectSummaries());
            marker = list.getNextMarker();
        } while (list.isTruncated());
        return result;
    }

    /**
     * 流式上传
     *
     * @param client
     * @param bucketName
     *          桶名
     * @param key
     *          上传路径
     * @param input
     *          文件流
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static PutObjectResult putObject(OSSClient client, String bucketName, String key, InputStream input) throws OSSException, ClientException {
        return putObject(client, bucketName, key, input, null);
    }

    /**
     * 流式上传
     *
     * @param client
     * @param bucketName
     *          桶名
     * @param key
     *          上传路径
     * @param input
     *          文件流
     * @param metadata
     *          常用http header，比如Content-MD5、Content-Type 、 Content-Length、Content-Disposition 、Content-Encoding 、 Expires、x-oss-server-side-encryption等
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static PutObjectResult putObject(OSSClient client, String bucketName, String key, InputStream input, ObjectMetadata metadata) throws OSSException, ClientException {
        return putObject(client, new PutObjectRequest(bucketName, key, input, metadata));
    }

    /**
     * 文件上传
     *
     * @param client
     * @param bucketName
     *          桶名
     * @param key
     *          上传路径
     * @param file
     *          文件
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static PutObjectResult putObject(OSSClient client, String bucketName, String key, File file) throws OSSException, ClientException {
        return putObject(client, bucketName, key, file, null);
    }

    /**
     * 文件上传
     *
     * @param client
     * @param bucketName
     *          桶名
     * @param key
     *          上传路径
     * @param file
     *          文件
     * @param metadata
     *          常用http header，比如Content-MD5、Content-Type 、 Content-Length、Content-Disposition 、Content-Encoding 、 Expires、x-oss-server-side-encryption等
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static PutObjectResult putObject(OSSClient client, String bucketName, String key, File file, ObjectMetadata metadata) throws OSSException, ClientException {
        return putObject(client, new PutObjectRequest(bucketName, key, file, metadata));
    }

    /**
     * 上传
     *
     * @param client
     * @param request
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static PutObjectResult putObject(OSSClient client, PutObjectRequest request) throws OSSException, ClientException {
        return client.putObject(request);
    }

    /**
     * 判断文件是否存在
     *
     * @param client
     * @param bucketName
     *          桶名
     * @param key
     *          上传路径
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static boolean doesObjectExist(OSSClient client, String bucketName, String key) throws OSSException, ClientException {
        return doesObjectExist(client, new GenericRequest(bucketName, key));
    }

    /**
     * 判断文件是否存在
     *
     * @param client
     * @param request
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static boolean doesObjectExist(OSSClient client, GenericRequest request) throws OSSException, ClientException {
        return client.doesObjectExist(request);
    }

    /**
     * 分片上传
     *
     * @param client
     * @param bucketName
     *          桶名
     * @param key
     *          上传路径
     * @param file
     *          上传文件
     * @param partSize
     *          每片大小
     * @return
     */
    public static CompleteMultipartUploadResult multipartUpload(OSSClient client, String bucketName, String key, File file, long partSize) {
        Asserts.check(partSize > 0, "part size can't be less than 1!");
        long length = file.length();
        int partCount = (int) (file.length() / partSize);
        if (length % partSize != 0) {
            ++partCount;
        }
        if (partCount > 10000) {
            throw new IllegalArgumentException("total parts count should not exceed 10000!");
        }

        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
        InitiateMultipartUploadResult result = client.initiateMultipartUpload(request);
        final String uploadId = result.getUploadId();

        List<PartETag> partETags = new ArrayList<>(partCount);
        final CountDownLatch countDown = new CountDownLatch(partCount);
        for (int i = 0; i < partCount; ++i) {
            long startPos = i * partSize;
            long curPartSize = (i + 1 == partCount) ? (length - startPos) : partSize;
            int partNum = i + 1;
            UPLOAD_EXECUTOR.execute(() -> {
                InputStream is = null;
                try {
                    is = new FileInputStream(file);
                    is.skip(startPos);

                    UploadPartRequest uploadPartRequest = new UploadPartRequest();
                    uploadPartRequest.setBucketName(bucketName);
                    uploadPartRequest.setKey(key);
                    uploadPartRequest.setUploadId(uploadId);
                    uploadPartRequest.setInputStream(is);
                    uploadPartRequest.setPartSize(curPartSize);
                    uploadPartRequest.setPartNumber(partNum);
                    UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);

                    synchronized (partETags) {
                        partETags.add(uploadPartResult.getPartETag());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException ignore) {
                        }
                    }
                    countDown.countDown();
                }
            });
        }

        try {
            countDown.await();
        } catch (InterruptedException ignore) {
        }
        if (partETags.size() != partCount) {
            throw new IllegalStateException("upload multipart fail due to some parts are not finished yet!");
        }
        Collections.sort(partETags, (p1, p2) -> p1.getPartNumber() - p2.getPartNumber());

        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(bucketName, key, uploadId, partETags);
        return client.completeMultipartUpload(completeMultipartUploadRequest);
    }

    /**
     * 按条件列出分片
     *
     * @param client
     * @param bucketName
     *          桶名
     * @param key
     *          上传路径
     * @param uploadId
     *          上传id
     * @param maxParts
     *          每页数量，0-1000，可以为null，默认1000
     * @param partNum
     *          从第几片开始，默认null
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static PartListing listParts(OSSClient client, String bucketName, String key, String uploadId, int maxParts, int partNum) throws OSSException, ClientException {
        ListPartsRequest request = new ListPartsRequest(bucketName, key, uploadId);
        request.setMaxParts(maxParts);
        request.setPartNumberMarker(partNum);
        return listParts(client, request);
    }

    /**
     * 按条件列出分片
     *
     * @param client
     * @param request
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static PartListing listParts(OSSClient client, ListPartsRequest request) throws OSSException, ClientException {
        return client.listParts(request);
    }

    /**
     * 按条件列出所有分片
     *
     * @param client
     * @param bucketName
     *          桶名
     * @param key
     *          上传路径
     * @param uploadId
     *          上传id
     * @param partNum
     *          从第几片开始，默认null
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static List<PartSummary> listAllParts(OSSClient client, String bucketName, String key, String uploadId, int partNum) throws OSSException, ClientException {
        List<PartSummary> result = new ArrayList<>();
        PartListing list;
        do {
            list = listParts(client, bucketName, key, uploadId, partNum, 1000);
            result.addAll(list.getParts());
            partNum = list.getNextPartNumberMarker();
        } while (list.isTruncated());
        return result;
    }

    /**
     * 断点续传
     *
     * @param client
     * @param bucketName
     *          桶名
     * @param key
     *          上传路径
     * @param uploadFile
     *          上传文件
     * @return
     * @throws Throwable
     */
    public static UploadFileResult checkpointUpload(OSSClient client, String bucketName, String key, String uploadFile) throws Throwable {
        return checkpointUpload(client, new UploadFileRequest(bucketName, key, uploadFile, DEFAULT_PART_SIZE, 5, true));
    }

    /**
     * 断点续传
     *
     * @param client
     * @param request
     * @return
     * @throws Throwable
     */
    public static UploadFileResult checkpointUpload(OSSClient client, UploadFileRequest request) throws Throwable {
        return client.uploadFile(request);
    }

    /**
     * 设置文件权限
     *
     * @param client
     * @param bucketName
     *          桶名
     * @param key
     *          上传路径
     * @param acl
     *          acl规则 {@link CannedAccessControlList}
     * @throws OSSException
     * @throws ClientException
     */
    public static void setObjectAcl(OSSClient client, String bucketName, String key, CannedAccessControlList acl) throws OSSException, ClientException {
        setObjectAcl(client, new SetObjectAclRequest(bucketName, key, acl));
    }

    /**
     * 设置文件权限
     *
     * @param client
     * @param request
     * @throws OSSException
     * @throws ClientException
     */
    public static void setObjectAcl(OSSClient client, SetObjectAclRequest request) throws OSSException, ClientException {
        client.setObjectAcl(request);
    }

    /**
     * 获取文件acl权限
     *
     * @param client
     * @param request
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static ObjectAcl getObjectAcl(OSSClient client, GenericRequest request) throws OSSException, ClientException {
        return client.getObjectAcl(request);
    }

    /**
     * 获取文件acl权限
     *
     * @param client
     * @param bucketName
     *          桶名
     * @param key
     *          上传路径
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static ObjectAcl getObjectAcl(OSSClient client, String bucketName, String key) throws OSSException, ClientException {
        return getObjectAcl(client, new GenericRequest(bucketName, key));
    }

    /**
     * 下载文件
     *
     * @param client
     * @param bucketName
     *          桶名
     * @param key
     *          上传路径
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static OSSObject getObject(OSSClient client, String bucketName, String key) throws OSSException, ClientException {
        return getObject(client, new GetObjectRequest(bucketName, key));
    }

    /**
     * 下载文件
     *
     * @param client
     * @param request
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static OSSObject getObject(OSSClient client, GetObjectRequest request) throws OSSException, ClientException {
        return client.getObject(request);
    }

    /**
     * 下载文件
     *
     * @param client
     * @param request
     * @param localFile
     *          下载到本地文件
     * @return {@link ObjectMetadata}
     * @throws OSSException
     * @throws ClientException
     */
    public static ObjectMetadata getObject(OSSClient client, GetObjectRequest request, File localFile) throws OSSException, ClientException {
        return client.getObject(request, localFile);
    }

    /**
     * 获取文件信息
     *
     * @param client
     * @param bucketName
     *          桶名
     * @param key
     *          上传路径
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static ObjectMetadata getObjectMetadata(OSSClient client, String bucketName, String key) throws OSSException, ClientException {
        return getObjectMetadata(client, new GetObjectRequest(bucketName, key));
    }

    /**
     * 获取文件信息
     *
     * @param client
     * @param request
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static ObjectMetadata getObjectMetadata(OSSClient client, GetObjectRequest request) throws OSSException, ClientException {
        return client.getObjectMetadata(request);
    }

    /**
     * 分块下载
     *
     * @param client
     * @param bucketName
     *          桶名
     * @param key
     *          上传路径
     * @param localFilePath
     *          下载到本地文件路径
     * @throws OSSException
     * @throws ClientException
     * @throws IOException
     */
    public static void multipartDownload(OSSClient client, String bucketName, String key, String localFilePath) throws OSSException, ClientException, IOException {
        multipartDownload(client, new GetObjectRequest(bucketName, key), localFilePath);
    }

    /**
     * 分块下载
     *
     * @param client
     * @param request
     * @param localFilePath
     *          下载到本地文件路径
     * @throws OSSException
     * @throws ClientException
     * @throws IOException
     */
    public static void multipartDownload(OSSClient client, GetObjectRequest request, String localFilePath) throws OSSException, ClientException, IOException {
        ObjectMetadata metadata = getObjectMetadata(client, request);
        long size = metadata.getContentLength();
        RandomAccessFile raf = new RandomAccessFile(localFilePath, "rw");
        raf.setLength(size);
        raf.close();
        int partCount = (int) (size / DEFAULT_PART_SIZE);
        if (size % DEFAULT_PART_SIZE != 0) {
            ++partCount;
        }

        final AtomicInteger completedBlocks = new AtomicInteger(0);
        final CountDownLatch countDown = new CountDownLatch(partCount);
        for (int i = 0; i < partCount; ++i) {
            long startPos = i * DEFAULT_PART_SIZE;
            long endPos = (i + 1 == partCount) ? size : (i + 1) * DEFAULT_PART_SIZE;
            DOWNLOAD_EXECUTOR.execute(() -> {
                RandomAccessFile file = null;
                try {
                    file = new RandomAccessFile(localFilePath, "rw");
                    file.seek(startPos);

                    OSSObject object = getObject(client, request.withRange(startPos, endPos - 1));
                    InputStream objectContent = object.getObjectContent();
                    try {
                        byte[] buf = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = objectContent.read(buf)) != -1) {
                            file.write(buf, 0, bytesRead);
                        }
                        completedBlocks.incrementAndGet();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        objectContent.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (file != null) {
                        try {
                            file.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    countDown.countDown();
                }
            });
        }

        try {
            countDown.await();
        } catch (InterruptedException ignore) {
        }
        if (completedBlocks.intValue() != partCount) {
            throw new IllegalStateException("download multipart fail due to some parts are not finished yet!");
        }
    }

    /**
     * 断点下载
     *
     * @param client
     * @param bucketName
     *          桶名
     * @param key
     *          上传路径
     * @param localFile
     *          上传文件
     * @return
     * @throws Throwable
     */
    public static DownloadFileResult checkpointDownload(OSSClient client, String bucketName, String key, String localFile) throws Throwable {
        return checkpointDownload(client, new DownloadFileRequest(bucketName, key, localFile, DEFAULT_PART_SIZE, 5, true));
    }

    /**
     * 断点下载
     *
     * @param client
     * @param request
     * @return
     * @throws Throwable
     */
    public static DownloadFileResult checkpointDownload(OSSClient client, DownloadFileRequest request) throws Throwable {
        return client.downloadFile(request);
    }

    /**
     * 删除多个对象
     *
     * @param client
     * @param bucketName
     *          桶名
     * @param keys
     *          上传路径
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static DeleteObjectsResult deleteObjects(OSSClient client, String bucketName, String... keys) throws OSSException, ClientException {
        Asserts.check(keys == null || keys.length == 0, "object keys can't be empty!");
        return deleteObjects(client, bucketName, Arrays.asList(keys));
    }

    /**
     * 删除多个对象
     *
     * @param client
     * @param bucketName
     *          桶名
     * @param keys
     *          上传路径列表
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static DeleteObjectsResult deleteObjects(OSSClient client, String bucketName, List<String> keys) throws OSSException, ClientException {
        return deleteObjects(client, new DeleteObjectsRequest(bucketName).withKeys(keys));
    }

    /**
     * 删除多个对象
     *
     * @param client
     * @param request
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static DeleteObjectsResult deleteObjects(OSSClient client, DeleteObjectsRequest request) throws OSSException, ClientException {
        return client.deleteObjects(request);
    }

    /**
     * 删除一个对象
     *
     * @param client
     * @param bucketName
     *          桶名
     * @param key
     *          上传路径
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static void deleteObjects(OSSClient client, String bucketName, String key) throws OSSException, ClientException {
        deleteObjects(client, new GenericRequest(bucketName, key));
    }

    /**
     * 删除一个对象
     *
     * @param client
     * @param request
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static void deleteObjects(OSSClient client, GenericRequest request) throws OSSException, ClientException {
        client.deleteObject(request);
    }

    /**
     * 创建文件夹
     *
     * @param client
     * @param bucketName
     *          桶名
     * @param folderName
     *          文件夹名，多级a/b/
     * @return
     * @throws OSSException
     * @throws ClientException
     */
    public static PutObjectResult createFolder(OSSClient client, String bucketName, String folderName) throws OSSException, ClientException {
        return putObject(client, bucketName, folderName.endsWith("/") ? folderName : folderName + "/", new ByteArrayInputStream(new byte[0]), null);
    }
}
