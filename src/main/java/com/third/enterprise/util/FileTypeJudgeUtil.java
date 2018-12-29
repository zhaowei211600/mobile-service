package com.third.enterprise.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/***
 * @author zhaowei
 * 根据文件二进制流来判断文件类型
 */
public class FileTypeJudgeUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileTypeJudgeUtil.class);

    private FileTypeJudgeUtil() {
    }

    /**
     * 将文件头转换成16进制字符串
     *
     * @param src
     * @return 16进制字符串
     */
    private static String bytesToHexString(byte[] src) {

        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 得到文件头
     * @return 文件头
     * @throws IOException
     */
    private static String getFileContent(InputStream is) throws IOException {

        byte[] b = new byte[28];

        InputStream inputStream = null;

        try {
            is.read(b, 0, 28);
        } catch (IOException e) {
            logger.warn("获取文件流出现异常:{} {}", e.getMessage(), e.getClass().getSimpleName());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.warn(" 关闭文件流出现异常:{} {}", e.getMessage(), e.getClass().getSimpleName());
                }
            }
        }
        return bytesToHexString(b);
    }

    /**
     * 判断文件类型
     * @return 文件类型
     */
    public static FileTypeEnum getType(InputStream is) throws IOException {

        String fileHead = getFileContent(is);

        if (fileHead == null || fileHead.length() == 0) {
            return null;
        }

        fileHead = fileHead.toUpperCase();

        FileTypeEnum[] fileTypes = FileTypeEnum.values();

        for (FileTypeEnum type : fileTypes) {
            if (fileHead.startsWith(type.getValue())) {
                return type;
            }
        }

        return null;
    }

    public static Integer isFileType(FileTypeEnum value) {
        Integer type = 7;// 其他
        // 图片
        FileTypeEnum[] pics = { FileTypeEnum.JPEG, FileTypeEnum.PNG, FileTypeEnum.GIF};

        FileTypeEnum[] docs = {  FileTypeEnum.DOC, FileTypeEnum.DOCX, FileTypeEnum.PDF, FileTypeEnum.WPS};

        FileTypeEnum[] others = {};

        // 图片
        for (FileTypeEnum fileType : pics) {
            if (fileType.equals(value)) {
                type = 1;
            }
        }
        // 文档
        for (FileTypeEnum fileType : docs) {
            if (fileType.equals(value)) {
                type = 2;
            }
        }
        return type;
    }

}
