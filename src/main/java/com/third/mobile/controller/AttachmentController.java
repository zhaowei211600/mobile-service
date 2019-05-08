package com.third.mobile.controller;


import com.third.mobile.bean.Attachment;
import com.third.mobile.bean.Requirement;
import com.third.mobile.bean.User;
import com.third.mobile.bean.request.AttachmentListRequest;
import com.third.mobile.bean.request.RequireCommitRequest;
import com.third.mobile.bean.response.UnifiedResult;
import com.third.mobile.bean.response.UnifiedResultBuilder;
import com.third.mobile.integration.IFileService;
import com.third.mobile.service.IAttachmentService;
import com.third.mobile.service.IRequirementService;
import com.third.mobile.service.IUserService;
import com.third.mobile.util.Constants;
import com.third.mobile.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/attachment/order")
public class AttachmentController {

    private static final Logger logger = LoggerFactory.getLogger(AttachmentController.class);

    @Autowired
    private IAttachmentService attachmentService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IFileService fileService;

    @GetMapping("/disable")
    public UnifiedResult disableAttachment(Integer attachmentId){

        Attachment attachment = attachmentService.selectAttachment(attachmentId);
        if(attachment != null){
            attachment.setStatus("2");
            if(attachmentService.updateAttachment(attachment)){
                return UnifiedResultBuilder.defaultSuccessResult();
            }
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @PostMapping("/list")
    public UnifiedResult listAttachment(@RequestBody AttachmentListRequest request,
                                        @RequestAttribute("username")String phone){

        User user =  userService.findByPhone(phone);
        if(user != null){
            request.setUserId(user.getId());
            List<Attachment> attachmentList = attachmentService.listAttachment(request);
            if(attachmentList != null && attachmentList.size() > 0){
                return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE,
                        attachmentList,
                        Page.toPage(attachmentList).getTotal());
            }
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @RequestMapping("/upload")
    public UnifiedResult uploadAttachment(@RequestParam("file") MultipartFile file,
                                          @RequestParam("orderId") int orderId) throws IOException {

        logger.info("文件上传大小：{}", file.getSize());
        String fileSuffix = "";

        //未匹配出实际的格式
        String originalName = file.getOriginalFilename();
        if(!StringUtils.isEmpty(originalName)){
            fileSuffix = originalName.substring(originalName.lastIndexOf(".") + 1);
        }
        HashMap<String,Object> fileAttr = new HashMap<>(2);
        String fileName = Constants.generateFileName(fileSuffix);
        if(fileService.uploadFile(file, fileName)){
            fileAttr.put("fileName", fileName);
            fileAttr.put("originalName", originalName);
            Attachment attachment = new Attachment();
            attachment.setOrderId(orderId);
            attachment.setFileName(originalName);
            attachment.setFilePath(fileName);
            attachment.setStatus("1");
            if(attachmentService.saveAttachment(attachment)){
                fileAttr.put("attachmentId",attachment.getId());
                return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE,
                        fileAttr);
            }
        }
        return UnifiedResultBuilder.errorResult(Constants.FILE_HANDLE_ERROR_CODE,
                Constants.FILE_HANDLE_ERROR_MESSAGE);
    }
}
