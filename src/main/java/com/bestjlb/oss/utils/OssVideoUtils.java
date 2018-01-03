package com.bestjlb.oss.utils;

/**
 * Created by yydx811 on 2017/12/28.
 */
public class OssVideoUtils extends OssMediaUtils {

    /**
     * 操作分类，视频
     */
    public static final String VIDEO = "video";

    /**
     * 生成视频截帧参数
     *
     * @param time
     *          {@link Snapshot#TIME}
     * @param width
     *          {@link Snapshot#WIDTH}
     * @param height
     *          {@link Snapshot#HEIGHT}
     * @param model
     *          截帧模式，1时间点关键帧，其余普通模式截取时间点帧。
     * @param type
     *          {@link Snapshot.SnapshotFormatType}
     * @return
     */
    public static String generateSnapshot(long time, int width, int height, int model, Snapshot.SnapshotFormatType type) {
        StringBuilder builder = new StringBuilder(64);
        builder.append(OUTER_SEPARATOR).append(Snapshot.SNAPSHOT).append(INNER_SEPARATOR)
                .append(Snapshot.TIME).append(PARAMETER_SEPARATOR).append(time);
        if (width > 0) {
            builder.append(INNER_SEPARATOR).append(Snapshot.WIDTH).append(PARAMETER_SEPARATOR).append(width);
        }
        if (height > 0) {
            builder.append(INNER_SEPARATOR).append(Snapshot.HEIGHT).append(PARAMETER_SEPARATOR).append(height);
        }
        if (model == 1) {
            builder.append(INNER_SEPARATOR).append(Snapshot.MODEL).append(PARAMETER_SEPARATOR).append(Snapshot.FAST);
        }
        if (type != null) {
            builder.append(INNER_SEPARATOR).append(Snapshot.FORMAT).append(PARAMETER_SEPARATOR).append(type.type);
        }
        return builder.toString();
    }

    /**
     * 生成视频截帧参数，图片格式jpg，原视频宽高，当前时间截图
     *
     * @param time
     *          {@link Snapshot#TIME}
     * @return
     */
    public static String generateSnapshot(long time) {
        return generateSnapshot(time, 0, 0, 0, null);
    }

    /**
     * 生成视频截帧参数，图片格式jpg，当前时间截图
     *
     * @param time
     *          {@link Snapshot#TIME}
     * @param width
     *          {@link Snapshot#WIDTH}
     * @param height
     *          {@link Snapshot#HEIGHT}
     * @return
     */
    public static String generateSnapshot(long time, int width, int height) {
        return generateSnapshot(time, width, height, 0, null);
    }

    /**
     * 视频截帧
     */
    public static class Snapshot {

        /**
         * /snapshot,t_3000,f_jpg,w_0,h_600,m_fast
         */
        public static final String SNAPSHOT = "snapshot";

        /**
         * 截图时间，毫秒，0-视频时间。
         */
        public static final String TIME = "t";

        /**
         * 宽，0为自动计算，0-视频宽
         */
        public static final String WIDTH = "w";

        /**
         * 高，0为自动计算，若宽高都为0，则输出原视频宽高，0-视频高
         */
        public static final String HEIGHT = "h";

        /**
         * 模式，fast时间点关键帧，默认普通模式，截取时间点。
         */
        public static final String MODEL = "m";

        /**
         * 输入格式，jpg，png
         */
        public static final String FORMAT = "f";

        /**
         * 视频截帧图片类型
         */
        public enum SnapshotFormatType {

            JPG("jpg"),

            PNG("png"),

            ;

            private String type;

            SnapshotFormatType(String type) {
                this.type = type;
            }

            public String getType() {
                return type;
            }
        }

        public static final String FAST = "fast";
    }
}
