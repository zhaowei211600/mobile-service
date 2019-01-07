package com.third.mobile.service;

import com.third.mobile.bean.Product;
import com.third.mobile.bean.request.ProductListRequest;
import com.third.mobile.bean.response.ProductStatResponse;

import java.util.List;

public interface IProductService {

    List<Product> listProduct(ProductListRequest request);

    List<Product> listCheckProduct(ProductListRequest request);

    boolean saveProduct(Product product);

    boolean updateProduct(Product product);

    Product findByProductId(Integer productId);

    boolean updateStatus(Integer productId, String status);

    boolean chooseUser(Integer productId, Integer userId, Integer orderId);

    boolean revokeProduct(Integer productId);

    ProductStatResponse statProduct();

    boolean applyProduct(Product product);

}