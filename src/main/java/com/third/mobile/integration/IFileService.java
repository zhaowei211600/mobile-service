package com.third.mobile.integration;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

public interface IFileService {

    boolean uploadFile(MultipartFile file, String fileName);

    String getImgBase64Str(String fileName);

    ByteArrayInputStream download(String fileName);

}
