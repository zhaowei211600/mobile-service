package com.third.mobile.dao;


import com.third.mobile.bean.ProductAttachment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductAttachmentMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(ProductAttachment record);

    int insertSelective(ProductAttachment record);

    ProductAttachment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductAttachment record);

    int updateByPrimaryKey(ProductAttachment record);

    boolean enableAttachment(@Param("productId") Integer productId,
                             @Param("list") String[] fileList);

    ProductAttachment selectByFilePath(String filePath);

    List<ProductAttachment> listFile(Integer productId);
}