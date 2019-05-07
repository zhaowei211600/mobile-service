package com.third.mobile.service;

import com.third.mobile.bean.Attachment;
import com.third.mobile.bean.request.AttachmentListRequest;

import java.util.List;

public interface IAttachmentService {

    boolean saveAttachment(Attachment attachment);

    Attachment selectAttachment(Integer attachmentId);

    boolean updateAttachment(Attachment attachment);

    List<Attachment> listAttachment(AttachmentListRequest request);
}
