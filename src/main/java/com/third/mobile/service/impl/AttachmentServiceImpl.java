package com.third.mobile.service.impl;

import com.github.pagehelper.PageHelper;
import com.third.mobile.bean.Attachment;
import com.third.mobile.bean.request.AttachmentListRequest;
import com.third.mobile.dao.AttachmentMapper;
import com.third.mobile.service.IAttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttachmentServiceImpl implements IAttachmentService{

    private static final Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Override
    public boolean saveAttachment(Attachment attachment) {

        if(attachmentMapper.insertSelective(attachment) > 0){
            return true;
        }
        return false;
    }

    @Override
    public Attachment selectAttachment(Integer attachmentId) {
        return attachmentMapper.selectByPrimaryKey(attachmentId);
    }

    @Override
    public boolean updateAttachment(Attachment attachment) {
        if(attachmentMapper.updateByPrimaryKeySelective(attachment) > 0){
            return true;
        }
        return false;
    }

    @Override
    public List<Attachment> listAttachment(AttachmentListRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        return attachmentMapper.listAttachment(request);
    }
}
