package com.third.enterprise.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class LocalFileUtil {

    private static final Logger logger = LoggerFactory.getLogger(LocalFileUtil.class);

    public static boolean saveFile(InputStream inputStream,String filePath, String fileName) {

        OutputStream os = null;
        try {
            byte[] bs = new byte[1024];
            int len;
            File tempFile = new File(filePath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            return true;
        } catch (IOException e) {
            logger.error("文件保存出错：{}", ErrorUtil.getErrorStackInfo(e));
        } catch (Exception e) {
            logger.error("文件保存出错：{}", ErrorUtil.getErrorStackInfo(e));
        } finally {
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                logger.error("文件流关闭异常：{}", ErrorUtil.getErrorStackInfo(e));
            }
        }
        return false;
    }

    public static byte[] getFileBytes(String filePath,String fileName) throws IOException {
        byte[] buffer = null;
        File file = new File(filePath + fileName);
        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;
        try {
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            logger.error("文件找不到：{}", ErrorUtil.getErrorStackInfo(e));
        } catch (IOException e) {
            logger.error("文件读取异常：{}", ErrorUtil.getErrorStackInfo(e));
            if(fis != null){
                fis.close();
            }
            if(bos != null){
                bos.close();
            }
        }
        return buffer;
    }
}
