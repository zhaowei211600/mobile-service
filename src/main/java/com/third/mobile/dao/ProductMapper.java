package com.third.mobile.dao;

import com.third.mobile.bean.Product;
import com.third.mobile.bean.request.ProductListRequest;
import com.third.mobile.bean.response.ProductStatResponse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByProductId(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    List<Product> listProduct(ProductListRequest request);

    List<Product> listCheckProduct(ProductListRequest request);

    Integer updateProductStatus(@Param("productId") Integer productId,
                                @Param("status") String status);

    ProductStatResponse statProduct();
}