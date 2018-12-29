package com.third.enterprise.integration;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

    boolean uploadFile(MultipartFile file, String fileName);

    String getImgBase64Str(String fileName);
}
