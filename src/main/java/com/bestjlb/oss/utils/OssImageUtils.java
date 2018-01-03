package com.bestjlb.oss.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.http.util.Asserts;

import java.util.Base64;

/**
 * Created by yydx811 on 2017/12/27.
 */
public class OssImageUtils extends OssMediaUtils {

    /**
     * 操作分类，图片
     */
    public static final String IMAGE = "image";

    /**
     * 自定义模式宽高缩放。
     *
     * @param model
     *          {@link Resize.ResizeModel}
     * @param width
     *          {@link Resize#WIDTH}
     * @param height
     *          {@link Resize#HEIGHT}
     * @param limit
     *          当目标缩略图大于原图时是否处理。0表示处理，其余不处理。
     * @param color
     *          {@link Resize#COLOR}
     * @return
     */
    public static String generateResize(Resize.ResizeModel model, int width, int height, int limit, String color) {
        Asserts.check(width !=0 || height != 0, "width and height can not both be 0!");
        StringBuilder builder = new StringBuilder(64);
        builder.append(OUTER_SEPARATOR).append(Resize.RESIZE);
        if (width > 0) {
            builder.append(INNER_SEPARATOR).append(Resize.WIDTH).append(PARAMETER_SEPARATOR).append(width);
        }
        if (height > 0) {
            builder.append(INNER_SEPARATOR).append(Resize.HEIGHT).append(PARAMETER_SEPARATOR).append(height);
        }
        if (model != null) {
            builder.append(INNER_SEPARATOR).append(Resize.MODEL).append(PARAMETER_SEPARATOR).append(model.model);
        }
        if (limit == 0) {
            builder.append(INNER_SEPARATOR).append(Resize.LIMIT).append(PARAMETER_SEPARATOR).append(limit);
        }
        if (StringUtils.isNotBlank(color)) {
            builder.append(INNER_SEPARATOR).append(Resize.COLOR).append(PARAMETER_SEPARATOR).append(color);
        }
        return builder.toString();
    }

    /**
     * 默认模式宽高缩放。
     *
     * @param width
     *          {@link Resize#WIDTH}
     * @param height
     *          {@link Resize#HEIGHT}
     * @return
     */
    public static String generateResize(int width, int height) {
        return generateResize(null, width, height, 0, null);
    }

    /**
     * 按比例缩放图片
     *
     * @param scale
     *          {@link Resize#SCALE}
     * @param limit
     *          当目标缩略图大于原图时是否处理。0表示处理，其余不处理。
     * @return
     */
    public static String generateResizeByScale(int scale, int limit) {
        StringBuilder builder = new StringBuilder(16);
        builder.append(OUTER_SEPARATOR).append(Resize.RESIZE).append(INNER_SEPARATOR)
                .append(Resize.SCALE).append(PARAMETER_SEPARATOR).append(scale);
        if (limit == 0) {
            builder.append(INNER_SEPARATOR).append(Resize.LIMIT).append(PARAMETER_SEPARATOR).append(limit);
        }
        return builder.toString();
    }

    /**
     * 生成内切圆参数
     *
     * @param radius
     *          {@link Circle#RADIUS}
     * @return
     */
    public static String generateCircle(int radius) {
        return OUTER_SEPARATOR + Circle.CIRCLE + INNER_SEPARATOR + Circle.RADIUS + PARAMETER_SEPARATOR + radius;
    }

    /**
     * 裁剪图片
     *
     * @param x
     *          {@link Crop#X_AXIS}
     * @param y
     *          {@link Crop#Y_AXIS}
     * @param width
     *          {@link Crop#WIDTH}
     * @param height
     *          {@link Crop#HEIGHT}
     * @param origin
     *          {@link Crop#ORIGIN}
     * @return
     */
    public static String generateCrop(int x, int y, int width, int height, Crop.CropOrigin origin) {
        StringBuilder builder = new StringBuilder(64);
        builder.append(OUTER_SEPARATOR).append(Crop.CROP).append(INNER_SEPARATOR);
        if (x != 0) {
            builder.append(Crop.X_AXIS).append(PARAMETER_SEPARATOR).append(x).append(INNER_SEPARATOR);
        }
        if (y != 0) {
            builder.append(Crop.Y_AXIS).append(PARAMETER_SEPARATOR).append(y).append(INNER_SEPARATOR);
        }
        if (width != 0) {
            builder.append(Crop.WIDTH).append(PARAMETER_SEPARATOR).append(width).append(INNER_SEPARATOR);
        }
        if (height != 0) {
            builder.append(Crop.HEIGHT).append(PARAMETER_SEPARATOR).append(height);
        }
        if (origin != null) {
            builder.append(INNER_SEPARATOR).append(Crop.ORIGIN).append(PARAMETER_SEPARATOR).append(origin.origin);
        }
        return builder.toString();
    }
    
    /**
     * 生成索引切个参数
     *
     * @param width
     *          {@link IndexCrop#WIDTH}
     * @param height
     *          {@link IndexCrop#HEIGHT}
     * @param index
     *          {@link IndexCrop#INDEX}
     * @return
     */
    public static String generateIndexCrop(int width, int height, int index) {
        Asserts.check(width != 0 || height != 0, "one of width or height is required!");
        StringBuilder builder = new StringBuilder(32);
        builder.append(OUTER_SEPARATOR).append(IndexCrop.INDEX_CROP).append(INNER_SEPARATOR);
        if (width != 0) {
            builder.append(IndexCrop.WIDTH).append(PARAMETER_SEPARATOR).append(width);
        } else {
            builder.append(IndexCrop.HEIGHT).append(PARAMETER_SEPARATOR).append(height);
        }
        builder.append(INNER_SEPARATOR).append(IndexCrop.INDEX).append(PARAMETER_SEPARATOR).append(index);
        return builder.toString();
    }

    /**
     * 生成圆角矩形参数
     *
     * @param radius
     *          {@link RoundedCorners#RADIUS}
     * @return
     */
    public static String generateRoundedCorners(int radius) {
        return OUTER_SEPARATOR + RoundedCorners.ROUNDED_CORNERS + INNER_SEPARATOR + RoundedCorners.RADIUS + radius;
    }

    /**
     * 生成自适应方向参数
     *
     * @param auto
     *          {@link AutoOrient#AUTO_ORIENT}
     * @return
     */
    public static String generateAutoOrient(int auto) {
        return OUTER_SEPARATOR + AutoOrient.AUTO_ORIENT + INNER_SEPARATOR + auto;
    }

    /**
     * 生成旋转参数
     *
     * @param degrees
     *          顺时针旋转角度，0-360
     * @return
     */
    public static String generateRotate(int degrees) {
        return OUTER_SEPARATOR + Rotate.ROTATE + INNER_SEPARATOR + degrees;
    }

    /**
     * 生成图片模糊参数
     *
     * @param r
     *          模糊半径，1-50，越大越模糊
     * @param s
     *          正态分布的标准差，1-50，越大越模糊
     * @return
     */
    public static String generateBlur(int r, int s) {
        return OUTER_SEPARATOR
                + Blur.BLUR + INNER_SEPARATOR
                + Blur.R + PARAMETER_SEPARATOR + r + INNER_SEPARATOR
                + Blur.S + PARAMETER_SEPARATOR + s;
    }

    /**
     * 生成亮度参数
     *
     * @param bright
     *          亮度参数，0原亮度，-100到100，越大越亮
     * @return
     */
    public static String generateBright(int bright) {
        return OUTER_SEPARATOR + Bright.BRIGHT + INNER_SEPARATOR + bright;
    }

    /**
     * 生成对比度参数
     *
     * @param contrast
     *          对比度参数，0原对比度，-100到100，越大对比度越大
     * @return
     */
    public static String generateContrast(int contrast) {
        return OUTER_SEPARATOR + Contrast.CONTRAST + INNER_SEPARATOR + contrast;
    }

    /**
     * 生成锐化参数
     *
     * @param sharpen
     *          锐化值50-399，推荐100
     * @return
     */
    public static String generateSharpen(int sharpen) {
        return OUTER_SEPARATOR + Sharpen.SHARPEN + INNER_SEPARATOR + sharpen;
    }

    /**
     * 生成格式转换参数
     *
     * @param type
     *          转换类型，{@link Format.FormatType}
     * @return
     */
    public static String generateFormat(Format.FormatType type) {
        return OUTER_SEPARATOR + Format.FORMAT + INNER_SEPARATOR + type.type;
    }

    /**
     * 生成渐进显示参数
     *
     * @param interlace
     *          jpg显示的时候有两种，0自上而下，1先模糊逐渐清晰，默认0
     * @return
     */
    public static String generateInterlace(int interlace) {
        return OUTER_SEPARATOR + Interlace.INTERLACE + INNER_SEPARATOR + interlace;
    }

    /**
     * 生成质量参数
     *
     * @param type
     *          质量类型，只对jpg和webp生效，1绝对质量，其余相对质量。{@link Quality}
     * @param quality
     *          质量参数，1-100
     * @return
     */
    public static String generateQuality(int type, int quality) {
        return OUTER_SEPARATOR
                + Quality.QUALITY + INNER_SEPARATOR
                + (type == 1 ? Quality.RELATIVE_QUALITY : Quality.ABSOLUTE_QUALITY) + PARAMETER_SEPARATOR + quality;
    }

    /**
     * 添加水印
     *
     * @param image
     *          图片水印，例：panda.png?x-oss-process=image/resize,P_30
     * @param text
     *          文字水印，最大64字节，中文约20个左右。
     *          <p>注：原文档写的最大64字符，这里本人觉得不妥。https://help.aliyun.com/document_detail/44957.html?spm=5176.doc44693.6.973.jNWj6w
     * @param x
     *          {@link Watermark#X_AXIS}
     * @param y
     *          {@link Watermark#Y_AXIS}
     * @param offset
     *          {@link Watermark#V_OFFSET}
     * @param origin
     *          {@link Watermark#ORIGIN}
     * @param transparency
     *          {@link Watermark#TRANSPARENCY}，小于零使用默认值100。
     * @param type
     *          {@link Watermark#TYPE}，null使用默认字体。
     * @param color
     *          {@link Watermark#COLOR}，null或空字符使用默认000000，即黑色。
     * @param size
     *          {@link Watermark#SIZE}，小于等于0使用默认40。
     * @param shadow
     *          {@link Watermark#SHADOW}，0不使用阴影效果。
     * @param rotate
     *          {@link Watermark#ROTATE}，小于等于0不旋转。
     * @param fill
     *          {@link Watermark#FILL}，小于0使用默认值0。
     * @param order
     *          {@link Watermark#ORDER}，小于0使用默认值0。
     * @param align
     *          {@link Watermark#ALIGN}，小于0使用默认值0。
     * @param interval
     *          {@link Watermark#INTERVAL}，小于0使用默认值0。
     * @return
     */
    public static String generateWatermark(String image, String text,
                                    int x, int y, int offset, Watermark.WatermarkOrigin origin, int transparency,
                                    Watermark.WatermarkTextType type, String color, int size, int shadow, int rotate, int fill,
                                    int order, int align, int interval) {
        Asserts.check(StringUtils.isNotBlank(image) || StringUtils.isNotBlank(text), "image and text can not both be empty!");
        StringBuilder builder = new StringBuilder(256);
        builder.append(OUTER_SEPARATOR).append(Watermark.WATERMARK);
        if (StringUtils.isNotBlank(image)) {
            builder.append(INNER_SEPARATOR).append(Watermark.IMAGE).append(PARAMETER_SEPARATOR).append(new String(Base64.getUrlEncoder().encode(image.getBytes())));
        }
        if (StringUtils.isNotBlank(text)) {
            builder.append(INNER_SEPARATOR).append(Watermark.TEXT).append(PARAMETER_SEPARATOR).append(new String(Base64.getUrlEncoder().encode(text.getBytes())));
        }
        if (x != 10) {
            builder.append(INNER_SEPARATOR).append(Watermark.X_AXIS).append(PARAMETER_SEPARATOR).append(x);
        }
        if (y != 10) {
            builder.append(INNER_SEPARATOR).append(Watermark.Y_AXIS).append(PARAMETER_SEPARATOR).append(y);
        }
        if (offset != 0) {
            builder.append(INNER_SEPARATOR).append(Watermark.V_OFFSET).append(PARAMETER_SEPARATOR).append(offset);
        }
        if (origin != null) {
            builder.append(INNER_SEPARATOR).append(Watermark.ORIGIN).append(PARAMETER_SEPARATOR).append(origin.origin);
        }
        if (transparency >= 0) {
            builder.append(INNER_SEPARATOR).append(Watermark.TRANSPARENCY).append(PARAMETER_SEPARATOR).append(transparency);
        }
        if (type != null) {
            builder.append(INNER_SEPARATOR).append(Watermark.TYPE).append(PARAMETER_SEPARATOR).append(type.encodeType);
        }
        if (StringUtils.isNotBlank(color)) {
            builder.append(INNER_SEPARATOR).append(Watermark.COLOR).append(PARAMETER_SEPARATOR).append(color);
        }
        if (size > 0) {
            builder.append(INNER_SEPARATOR).append(Watermark.SIZE).append(PARAMETER_SEPARATOR).append(size);
        }
        if (shadow > 0) {
            builder.append(INNER_SEPARATOR).append(Watermark.SHADOW).append(PARAMETER_SEPARATOR).append(shadow);
        }
        if (rotate > 0) {
            builder.append(INNER_SEPARATOR).append(Watermark.ROTATE).append(PARAMETER_SEPARATOR).append(rotate % 360);
        }
        if (fill >= 0) {
            builder.append(INNER_SEPARATOR).append(Watermark.FILL).append(PARAMETER_SEPARATOR).append(fill);
        }
        if (order >= 0) {
            builder.append(INNER_SEPARATOR).append(Watermark.ORDER).append(PARAMETER_SEPARATOR).append(order);
        }
        if (align >= 0) {
            builder.append(INNER_SEPARATOR).append(Watermark.ALIGN).append(PARAMETER_SEPARATOR).append(align);
        }
        if (interval >= 0) {
            builder.append(INNER_SEPARATOR).append(Watermark.INTERVAL).append(PARAMETER_SEPARATOR).append(interval);
        }
        return builder.toString();
    }

    /**
     * 添加文字水印
     *
     * @param text
     *          文字水印，最大64字节，中文约20个左右。
     *          <p>注：原文档写的最大64字符，这里本人觉得不妥。https://help.aliyun.com/document_detail/44957.html?spm=5176.doc44693.6.973.jNWj6w
     * @param x
     *          {@link Watermark#X_AXIS}
     * @param y
     *          {@link Watermark#Y_AXIS}
     * @param offset
     *          {@link Watermark#V_OFFSET}
     * @param origin
     *          {@link Watermark#ORIGIN}
     * @param transparency
     *          {@link Watermark#TRANSPARENCY}，小于零使用默认值100。
     * @param type
     *          {@link Watermark#TYPE}，null使用默认字体。
     * @param color
     *          {@link Watermark#COLOR}，null或空字符使用默认000000，即黑色。
     * @param size
     *          {@link Watermark#SIZE}，小于等于0使用默认40。
     * @param shadow
     *          {@link Watermark#SHADOW}，0不使用阴影效果。
     * @param rotate
     *          {@link Watermark#ROTATE}，小于等于0不旋转。
     * @param fill
     *          {@link Watermark#FILL}，小于0使用默认值0。
     * @return
     */
    public static String generateWatermark(String text,
                                    int x, int y, int offset, Watermark.WatermarkOrigin origin, int transparency,
                                    Watermark.WatermarkTextType type, String color, int size, int shadow, int rotate, int fill) {
        return generateWatermark(null, text, x, y, offset, origin, transparency, type, color, size, shadow, rotate, fill, -1, -1, -1);
    }

    /**
     * 添加文字水印，黑色，默认大小，不旋转，右下角，30%透明，不铺满，无阴影
     *
     * @param text
     *          文字水印，最大64字节，中文约20个左右。
     *          <p>注：原文档写的最大64字符，这里本人觉得不妥。https://help.aliyun.com/document_detail/44957.html?spm=5176.doc44693.6.973.jNWj6w
     * @return
     */
    public static String generateWatermark(String text) {
        return generateWatermark(null, text, 10, 10, 0, null, 30, null, null, 0, 0, 0, -1, -1, -1, -1);
    }

    /**
     * 添加图片水印
     *
     * @param image
     *          图片水印，例：panda.png?x-oss-process=image/resize,P_30
     * @param x
     *          {@link Watermark#X_AXIS}
     * @param y
     *          {@link Watermark#Y_AXIS}
     * @param offset
     *          {@link Watermark#V_OFFSET}
     * @param origin
     *          {@link Watermark#ORIGIN}
     * @param transparency
     *          {@link Watermark#TRANSPARENCY}，小于零使用默认值100。
     * @return
     */
    public static String generateWatermark(String image,
                                    int x, int y, int offset, Watermark.WatermarkOrigin origin, int transparency) {
        return generateWatermark(image, null, x, y, offset, origin, transparency, null, null, 0, 0, 0, -1, -1, -1, -1);
    }

    /**
     * 添加图片水印，右下角，默认位置
     *
     * @param image
     *          图片水印，例：panda.png?x-oss-process=image/resize,P_30
     * @param transparency
     *          {@link Watermark#TRANSPARENCY}，小于零使用默认值100。
     * @return
     */
    public static String generateWatermark(String image, int transparency) {
        return generateWatermark(image, null, 10, 10, 0, null, transparency, null, null, 0, 0, 0, -1, -1, -1, -1);
    }

    /**
     * 生成请求主色调参数
     *
     * @return
     */
    public static String generateAverageHue() {
        return OUTER_SEPARATOR + AverageHue.AVERAGE_HUE;
    }

    /**
     * 生成请求图片信息参数
     *
     * @return
     */
    public static String generateInfo() {
        return OUTER_SEPARATOR + Info.INFO;
    }

    /**
     * 缩放操作
     */
    public static class Resize {

        /**
         * 例：/resize,m_pad,w_100,h_100,limit_0,color_FF0000
         */
        public static final String RESIZE = "resize";

        /**
         * 缩放模式，默认lfit
         */
        public static final String MODEL = "m";

        /**
         * 宽，1-4096
         */
        public static final String WIDTH = "w";

        /**
         * 高，1-4096
         */
        public static final String HEIGHT = "h";

        /**
         * 当目标缩略图大于原图时是否处理。1表示不处理；0表示处理，默认1。
         */
        public static final String LIMIT = "limit";

        /**
         * 当缩放模式选择为pad（缩略填充）时，可以选择填充的颜色(默认是白色)参数的填写方式：采用16进制颜色码表示，如00FF00（绿色）。
         */
        public static final String COLOR = "color";

        /**
         * 按百分比缩放，1-1000
         */
        public static final String SCALE = "p";

        public enum ResizeModel {

            /**
             * 固定宽高，强制缩放
             */
            FIXED("fixed"),

            /**
             * 固定宽高，缩略填充
             */
            PAD("pad"),

            /**
             * 等比缩放，在给定宽高矩形框内生成最大图
             */
            LARGE_FIT("lfit"),

            /**
             * 等比缩放，在给定宽高矩形框外生成最小图
             */
            MIN_FIT("mfit"),

            /**
             * 等比缩放，在给定宽高矩形框外生成最小图进行居中裁剪
             */
            FILL("fill"),

            ;

            private String model;

            ResizeModel(String model) {
                this.model = model;
            }

            public String getModel() {
                return model;
            }
        }
    }

    /**
     * 内切圆
     */
    public static class Circle {

        /**
         * 例：/circle,r_50
         */
        public static final String CIRCLE = "circle";

        /**
         * 半径，不能超过原图的最小边的一半。如果超过，则圆的大小仍然是原圆的最大内切圆。保存为png、webp、bmp图片空白处为透明。
         */
        public static final String RADIUS = "r";
    }

    /**
     * 剪裁
     */
    public static class Crop {

        /**
         * 例：/crop,x_10,y_10,w_200,h_200,g_se
         */
        public static final String CROP = "crop";

        /**
         * 宽，指定裁剪宽度，0-图片最大宽
         */
        public static final String WIDTH = "w";

        /**
         * 高，指定裁剪高度，0-图片最大高
         */
        public static final String HEIGHT = "h";

        /**
         * 起点横坐标
         */
        public static final String X_AXIS = "x";

        /**
         * 起点纵坐标
         */
        public static final String Y_AXIS = "y";

        /**
         * <p>设置坐标原点位置，原点位于每个区域的左上角。
         * <p>https://help.aliyun.com/document_detail/44693.html?spm=5176.doc44696.6.958.9lxNaU
         * <pre>
         *  _______________________
         * |  nw   | north |  ne   |
         * |_______|_______|_______|
         * |  west | center|  east |
         * |_______|_______|_______|
         * |  sw   | south |  se   |
         * |_______|_______|_______|
         * </pre>
         * 注：然而并不所有原点在左上角，经本人测试发现，各个位置原点并不相同。<br>
         * nw在左上角，north在上边中点，ne在右上角，west在左边中点，center在中心中点，east在右边中点，sw在左下角，south在下边中点，se在右下角。<br>
         * 备注日期：2017-12-28
         */
        public static final String ORIGIN = "g";

        /**
         * 裁剪原点位置
         */
        public enum CropOrigin {

            /**
             * 坐标原点位置，左上
             */
            NORTH_WEST("nw"),

            /**
             * 坐标原点位置，上
             */
            NORTH("north"),

            /**
             * 坐标原点位置，右上
             */
            NORTH_EAST("ne"),

            /**
             * 坐标原点位置，左
             */
            WEST("west"),

            /**
             * 坐标原点位置，中
             */
            CENTER("center"),

            /**
             * 坐标原点位置，右
             */
            EAST("east"),

            /**
             * 坐标原点位置，左下
             */
            SOUTH_WEST("sw"),

            /**
             * 坐标原点位置，下
             */
            SOUTH("south"),

            /**
             * 坐标原点位置，右下
             */
            SOUTH_EAST("se"),

            ;

            private String origin;

            CropOrigin(String origin) {
                this.origin = origin;
            }

            public String getOrigin() {
                return origin;
            }
        }
    }

    /**
     * 索引切割
     */
    public static class IndexCrop {

        /**
         * 例：/indexcrop,x_50,i_2
         */
        public static final String INDEX_CROP = "indexcrop";

        /**
         * 进行水平切割，每块图片的长度。x 参数与 y 参数只能任选其一，1-图片最大宽
         */
        public static final String WIDTH = "x";

        /**
         * 进行垂直切割，每块图片的长度。x 参数与 y 参数只能任选其一，1-图片最大宽
         */
        public static final String HEIGHT = "y";

        /**
         * 切割后第几块，0第一块，超过最大块数返回原图
         */
        public static final String INDEX = "i";
    }

    /**
     * 圆角矩形
     */
    public static class RoundedCorners {

        /**
         * 例：/rounded-corners,r_10
         */
        public static final String ROUNDED_CORNERS = "rounded-corners";

        /**
         * 将图片切出圆角，指定圆角的半径，不能超过原图的最小边的一半。保存为png、webp、bmp图片空白处为透明。
         */
        public static final String RADIUS = "r";
    }

    /**
     * 自适应旋转
     */
    public static class AutoOrient {

        /**
         * 进行自动旋转，0：表示按原图默认方向，不进行自动旋转。1：先进行图片进行旋转，然后再进行缩略<br>
         * 例：/auto-orient,1
         */
        public static final String AUTO_ORIENT = "auto-orient";
    }

    /**
     * 顺时针旋转
     */
    public static class Rotate {

        /**
         * 图片按顺时针旋转，例：/rotate,90
         */
        public static final String ROTATE = "rotate";
    }

    /**
     * 模糊效果
     */
    public static class Blur {

        /**
         * 例：/blur,r_1,s_2
         */
        public static final String BLUR = "blur";

        /**
         * 模糊半径，1-50
         */
        public static final String R = "r";

        /**
         * 正态分布的标准差，1-50
         */
        public static final String S = "s";
    }

    /**
     * 亮度调整
     */
    public static class Bright {

        /**
         * 亮度调整，0原亮度，-100到100，例：/bright,10
         */
        public static final String BRIGHT = "bright";
    }

    /**
     * 对比度调整
     */
    public static class Contrast {

        /**
         * 对比度调整，0原对比度，-100到100，例：/contrast,10
         */
        public static final String CONTRAST = "contrast";
    }

    /**
     * 锐化
     */
    public static class Sharpen {

        /**
         * 锐化处理，50-399，推荐100，例：/sharpen,100
         */
        public static final String SHARPEN = "sharpen";
    }

    /**
     * 格式转换
     */
    public static class Format {

        /**
         * 例：/format,png
         */
        public static final String FORMAT = "format";

        public enum FormatType {

            /**
             * jpg格式
             */
            JPEG("jpg"),

            /**
             * png格式
             */
            PNG("png"),

            /**
             * webp格式
             */
            WEBP("webp"),

            /**
             * bmp格式
             */
            BMP("bmp"),

            /**
             * gif格式
             */
            GIF("gif"),

            /**
             * tiff格式
             */
            TIFF("tiff"),

            ;

            FormatType(String type) {
                this.type = type;
            }

            private String type;

            public String getType() {
                return type;
            }
        }

    }

    /**
     * 渐进显示
     */
    public static class Interlace {

        /**
         * jpg显示的时候有两种，0自上而下，1先模糊逐渐清晰，默认0。例：/interlace,1
         */
        public static final String INTERLACE = "interlace";
    }

    /**
     * 质量变换
     */
    public static class Quality {

        /**
         * 例：/quality,Q_90
         */
        public static final String QUALITY = "quality";

        /**
         * 相对质量，如果原图质量为80%，压缩质量为90%，则得到72%的质量图。只对jpg生效，webp为绝对质量。
         */
        public static final String RELATIVE_QUALITY = "q";

        /**
         * 绝对质量，如果原图质量为80%，压缩质量为90%，则得到80%的质量图。只对jpg，webp生效。
         */
        public static final String ABSOLUTE_QUALITY = "Q";
    }

    /**
     * 图片水印
     */
    public static class Watermark {

        /**
         * 例：/watermark,image_cGFuZGEucG5nP3gtb3NzLXByb2Nlc3M9aW1hZ2UvcmVzaXplLFBfMjU,type_d3F5LXplbmhlaQ,size_30,text_SGVsbG8g5Zu-54mH5pyN5YqhIQ,color_FFFFFF,shadow_50,order_0,align_2,interval_10,t_100,g_se,x_10,y_10
         */
        public static final String WATERMARK = "watermark";

        /**
         * 透明度，默认100，越小越透明
         */
        public static final String TRANSPARENCY = "t";

        /**
         * <p>设置坐标原点位置，nw在左上角，north在上边中点，ne在右上角，west在左边中点，center在中心中点，east在右边中点，sw在左下角，south在下边中点，se在右下角。<br>
         * <p>https://help.aliyun.com/document_detail/44957.html?spm=5176.doc64555.6.973.lXaP3w
         * <pre>
         *  _______________________
         * |  nw   | north |  ne   |
         * |_______|_______|_______|
         * |  west | center|  east |
         * |_______|_______|_______|
         * |  sw   | south |  se   |
         * |_______|_______|_______|
         * </pre>
         */
        public static final String ORIGIN = "g";

        /**
         * 水平边距, 就是距离图片边缘的水平距离(px)，仅对左上，左中，左下， 右上，右中，右下生效，0-4096，默认10
         */
        public static final String X_AXIS = "x";

        /**
         * 垂直边距, 就是距离图片边缘的垂直距离(px)， 仅对左上，中上，右上，左下，中下，右下生效，0-4096，默认10
         */
        public static final String Y_AXIS = "y";

        /**
         * 中线垂直偏移(px)，当水印位置在左中，中部，右中时，可以指定水印位置根据中线往上或者往下偏移。-1000~1000，默认0
         */
        public static final String V_OFFSET = "voffset";

        /**
         * 水印图片，base64编码，例：panda.png?x-oss-process=image/resize,P_30，image_cGFuZGEucG5nP3gtb3NzLXByb2Nlc3M9aW1hZ2UvcmVzaXplLFBfMzA
         */
        public static final String IMAGE = "image";

        /**
         * 文字水印，base64编码。
         */
        public static final String TEXT = "text";

        /**
         * 文字字体，base64编码，默认wqy-zenhei，编码后的值：d3F5LXplbmhlaQ。
         */
        public static final String TYPE = "type";

        /**
         * 文字颜色，16进制颜色000000-FFFFFF，默认000000
         */
        public static final String COLOR = "color";

        /**
         * 文字大小(px)，1-1000默认40
         */
        public static final String SIZE = "size";

        /**
         * 文字阴影透明度，1-100
         */
        public static final String SHADOW = "shadow";

        /**
         * 文字顺时针旋转，0-360
         */
        public static final String ROTATE = "rotate";

        /**
         * 文字水印铺满效果，0无效果，1铺满
         */
        public static final String FILL = "fill";

        /**
         * 图片和文字先后顺序，0图前文后，1文前图后，默认0。
         */
        public static final String ORDER = "order";

        /**
         * 图片文字对齐方式，0上对齐，1中对齐，2下对齐，默认0。
         */
        public static final String ALIGN = "align";

        /**
         * 图片文字间距，0-1000，px。
         */
        public static final String INTERVAL = "interval";

        /**
         * 水印原点位置
         */
        public enum WatermarkOrigin {

            /**
             * 坐标原点位置，左上
             */
            NORTH_WEST("nw"),

            /**
             * 坐标原点位置，上
             */
            NORTH("north"),

            /**
             * 坐标原点位置，右上
             */
            NORTH_EAST("ne"),

            /**
             * 坐标原点位置，左
             */
            WEST("west"),

            /**
             * 坐标原点位置，中
             */
            CENTER("center"),

            /**
             * 坐标原点位置，右
             */
            EAST("east"),

            /**
             * 坐标原点位置，左下
             */
            SOUTH_WEST("sw"),

            /**
             * 坐标原点位置，下
             */
            SOUTH("south"),

            /**
             * 坐标原点位置，右下
             */
            SOUTH_EAST("se"),

            ;

            private String origin;

            WatermarkOrigin(String origin) {
                this.origin = origin;
            }

            public String getOrigin() {
                return origin;
            }
        }

        /**
         * 水印字体类型
         */
        public enum WatermarkTextType {

            /**
             * wqy-zenhei，文泉驿正黑
             */
            WQY_ZENHEI("wqy-zenhei", "d3F5LXplbmhlaQ"),

            /**
             * wqy-microhei，文泉微米黑
             */
            WQY_MICROHEI("wqy-microhei", "d3F5LW1pY3JvaGVp"),

            /**
             * fangzhengshusong，方正书宋
             */
            FANGZHENG_SHUSONG("fangzhengshusong", "ZmFuZ3poZW5nc2h1c29uZw"),

            /**
             * fangzhengkaiti，方正楷体
             */
            FANGZHENG_KAITI("fangzhengkaiti", "ZmFuZ3poZW5na2FpdGk"),

            /**
             * fangzhengheiti，方正黑体
             */
            FANGZHENG_HEITI("fangzhengheiti", "ZmFuZ3poZW5naGVpdGk"),

            /**
             * fangzhengfangsong，方正仿宋
             */
            FANGZHENG_FANGSONG("fangzhengfangsong", "ZmFuZ3poZW5nZmFuZ3Nvbmc"),

            /**
             * droidsansfallback，方正书宋
             */
            DROID_SANS_FALLBACK("droidsansfallback", "ZHJvaWRzYW5zZmFsbGJhY2s"),

            ;

            private String type;

            private String encodeType;

            WatermarkTextType(String type, String encodeType) {
                this.type = type;
                this.encodeType = encodeType;
            }

            public String getType() {
                return type;
            }

            public String getEncodeType() {
                return encodeType;
            }
        }
    }

    /**
     * 图片主色调
     */
    public static class AverageHue {

        /**
         * 例：/average-hue，返回：{"RGB": "0xff0000"}
         */
        public static final String AVERAGE_HUE = "average-hue";
    }

    /**
     * 图片信息
     */
    public static class Info {

        /**
         * 例：/info，返回：https://help.aliyun.com/document_detail/44975.html?spm=5176.doc44976.6.976.UDxHUP
         */
        public static final String INFO = "info";
    }
}
