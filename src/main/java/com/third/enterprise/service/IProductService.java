package com.third.enterprise.service;

import com.third.enterprise.bean.Product;
import com.third.enterprise.bean.request.ProductListRequest;
import com.third.enterprise.bean.response.ProductStatResponse;

import java.util.List;

public interface IProductService {

    List<Product> listPublishProduct(ProductListRequest request);

    List<Product> listCheckProduct(ProductListRequest request);

    boolean saveProduct(Product product);

    boolean updateProduct(Product product);

    Product findByProductId(Integer productId);

    boolean updateStatus(Integer productId, String status);

    boolean chooseUser(Integer productId, Integer userId, Integer orderId);

    boolean revokeProduct(Integer productId);

    ProductStatResponse statProduct();

}
