package com.third.mobile.service.impl;

import com.github.pagehelper.PageHelper;
import com.third.mobile.bean.Product;
import com.third.mobile.bean.request.ProductListRequest;
import com.third.mobile.bean.response.ProductStatResponse;
import com.third.mobile.dao.OrderMapper;
import com.third.mobile.dao.ProductMapper;
import com.third.mobile.service.GenerateProductNumberService;
import com.third.mobile.service.IProductService;
import com.third.mobile.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("productService")
public class ProductServiceImpl implements IProductService{

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Resource
    private GenerateProductNumberService generateProductNumberService;

    @Resource
    private ProductMapper productMapper;

    @Resource
    private OrderMapper orderMapper;

    @Override
    public List<Product> listProduct(ProductListRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        return productMapper.listProduct(request);
    }

    @Override
    public List<Product> listCheckProduct(ProductListRequest request) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        return productMapper.listCheckProduct(request);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveProduct(Product product) {
        product.setProductId(generateProductNumberService.generateProductNumberNumber());
        product.setStatus("1");
        product.setPublishStatus("1");
        if(productMapper.insertSelective(product) > 0){
            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateProduct(Product product) {
        if(productMapper.updateByPrimaryKeySelective(product) > 0){
            return true;
        }
        return false;
    }

    @Override
    public Product findByProductId(Integer productId) {
        return productMapper.selectByProductId(productId);
    }

    @Override
    public boolean updateStatus(Integer productId, String status) {
        if(productMapper.updateProductStatus(productId, status) > 0){
            return true;
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean chooseUser(Integer productId, Integer userId, Integer orderId) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Product product = productMapper.selectByProductId(productId);
        if(product != null){
            product.setOrderId(orderId);
            product.setOwnerId(userId);
            product.setStatus(Constants.ProductState.ON_DOING);
            product.setContractTime(format.format(new Date()));
            if(productMapper.updateByPrimaryKeySelective(product) > 0){
                if(orderMapper.updateOrderStatus(orderId , Constants.OrderState.CHECKED) > 0){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean revokeProduct(Integer productId) {
        if(productMapper.updateProductStatus(productId, Constants.PublishState.REVOKE) > 0){
            return true;
        }
        return false;
    }

    @Override
    public ProductStatResponse statProduct() {
        return productMapper.statProduct();
    }

    /**
     * 结项申请
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean applyProduct(Product product) {
        if(productMapper.updateByPrimaryKeySelective(product) > 0){
            if(orderMapper.updateOrderStatus(product.getOrderId(), Constants.OrderState.WAIT_CHECK) > 0){
                return true;
            }
        }
        return false;
    }
}
