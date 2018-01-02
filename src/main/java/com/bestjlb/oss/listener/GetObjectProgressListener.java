package com.bestjlb.oss.listener;

import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by yydx811 on 2017/12/27.
 */
public class GetObjectProgressListener implements ProgressListener{

    private static Log log = LogFactory.getLog(GetObjectProgressListener.class);

    private long bytesRead = 0;

    private long totalBytes = -1;

    private boolean succeed = false;

    @Override
    public void progressChanged(ProgressEvent progressEvent) {
        long bytes = progressEvent.getBytes();
        ProgressEventType eventType = progressEvent.getEventType();
        switch (eventType) {
            case TRANSFER_STARTED_EVENT:
                log.info("start to download......");
                break;

            case RESPONSE_CONTENT_LENGTH_EVENT:
                this.totalBytes = bytes;
                log.info(this.totalBytes + " bytes in total will be downloaded to a local file");
                break;

            case RESPONSE_BYTE_TRANSFER_EVENT:
                this.bytesRead += bytes;
                if (this.totalBytes != -1) {
                    int percent = (int)(this.bytesRead * 100.0 / this.totalBytes);
                    log.info(bytes + " bytes have been read at this time, download progress: " +
                            percent + "%(" + this.bytesRead + "/" + this.totalBytes + ")");
                } else {
                    log.info(bytes + " bytes have been read at this time, download ratio: unknown" +
                            "(" + this.bytesRead + "/...)");
                }
                break;

            case TRANSFER_COMPLETED_EVENT:
                this.succeed = true;
                log.info("succeed to download, " + this.bytesRead + " bytes have been transferred in total");
                break;

            case TRANSFER_FAILED_EVENT:
                log.error("failed to download, " + this.bytesRead + " bytes have been transferred");
                break;

            default:
                break;
        }
    }

    public boolean isSucceed() {
        return succeed;
    }
}
