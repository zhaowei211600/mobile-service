package com.third.mobile.dao;

import com.third.mobile.bean.Attachment;
import com.third.mobile.bean.request.AttachmentListRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AttachmentMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Attachment record);

    int insertSelective(Attachment record);

    Attachment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Attachment record);

    int updateByPrimaryKey(Attachment record);

    List<Attachment> listAttachment(AttachmentListRequest request);

    int updateAttachment(@Param("checkOrderNumber") String checkOrderNumber,
                         @Param("checkOrderId") Integer checkOrderId);

}