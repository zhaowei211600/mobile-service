package com.third.enterprise.controller;


import com.third.enterprise.bean.response.UnifiedResult;
import com.third.enterprise.bean.response.UnifiedResultBuilder;
import com.third.enterprise.integration.IFileService;
import com.third.enterprise.util.Constants;
import com.third.enterprise.util.ErrorUtil;
import com.third.enterprise.util.FileTypeEnum;
import com.third.enterprise.util.FileTypeJudgeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

@RestController
@RequestMapping("/user/file")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private IFileService fileService;

    private static final String UPLOAD_FILE_PREFIX = "user_";

    private static final String[] ALL_FILE_TYPE = {"png","jpeg","jpg"};


    @RequestMapping("/upload")
    public UnifiedResult fileUpload(@RequestParam("file") MultipartFile file){

        logger.info("文件上传大小：{}", file.getSize());
        String fileSuffix = "";
        try {
            FileTypeEnum fileTypeEnum = FileTypeJudgeUtil.getType(file.getInputStream());
            if(fileTypeEnum != null){
                fileSuffix = fileTypeEnum.toString();
            }
        } catch (IOException e) {
            logger.error("获取上传文件的后缀出错:{}", ErrorUtil.getErrorStackInfo(e));
        }
        if(!StringUtils.isEmpty(fileSuffix)){
            fileSuffix = fileSuffix.toLowerCase();
        }else {
            //未匹配出实际的格式
            String originalName = file.getOriginalFilename();
            if(!StringUtils.isEmpty(originalName)){
                fileSuffix = originalName.substring(originalName.lastIndexOf(".") + 1);
            }
        }

        if(Arrays.asList(ALL_FILE_TYPE).contains(fileSuffix)){
            if(fileService.uploadFile(file, generateFileName(fileSuffix))){
                return UnifiedResultBuilder.defaultSuccessResult();
            }
        }
        logger.info("上传文件后缀不合法:{}", file.getOriginalFilename());
        return UnifiedResultBuilder.errorResult(Constants.FILE_HANDLE_ERROR_CODE,
                Constants.FILE_HANDLE_ERROR_MESSAGE);
    }

    @RequestMapping("/download")
    public UnifiedResult downloadFile(String fileName){
        String fileBase64Str = fileService.getImgBase64Str(fileName);
        if(!StringUtils.isEmpty(fileBase64Str)){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_CODE,Constants.SUCCESS_MESSAGE, fileBase64Str);
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE, Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    private static String generateFileName(String fileSuffix) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = dateFormat.format(new Date());
        Random rad = new Random();
        String random = ""+rad.nextInt(10)+rad.nextInt(10);
        return UPLOAD_FILE_PREFIX  + time + random + "." +fileSuffix;
    }

}
