package com.third.mobile.controller;

import com.github.pagehelper.PageHelper;
import com.third.mobile.bean.Order;
import com.third.mobile.bean.Product;
import com.third.mobile.bean.User;
import com.third.mobile.bean.request.OrderRequest;
import com.third.mobile.bean.response.UnifiedResult;
import com.third.mobile.bean.response.UnifiedResultBuilder;
import com.third.mobile.service.IOrderService;
import com.third.mobile.service.IProductService;
import com.third.mobile.service.IUserService;
import com.third.mobile.util.Constants;
import com.third.mobile.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IProductService productService;

    @RequestMapping("/product")
    public UnifiedResult<List<Order>> findOrderByProductId(OrderRequest request){
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        List<Order> orderList = orderService.listOrder(request);
        if(orderList != null && orderList.size() > 0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, orderList, Page.toPage(orderList).getTotal());
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @RequestMapping("/user")
    public UnifiedResult userOrder(OrderRequest request,
                                   @RequestAttribute("username")String phone){

        User user = userService.findByPhone(phone);
        if(user != null){
            request.setUserId(user.getId());
            List<Order> orderList = orderService.listOrder(request);
            if(orderList != null && orderList.size() > 0){
                return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, orderList,
                        Page.toPage(orderList).getTotal());
            }
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @RequestMapping("/list")
    public UnifiedResult listOrder(OrderRequest request){

        List<Order> orderList = orderService.listOrder(request);
        if(orderList != null && orderList.size() > 0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, orderList,
                    Page.toPage(orderList).getTotal());
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @RequestMapping("/count")
    public UnifiedResult countOrder(Integer productId){

        Integer count = orderService.countOrder(productId);
        if(count > 0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, count);
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    /**
     * 接单
     */
    @RequestMapping("/confirm")
    public UnifiedResult confirmOrder(@RequestAttribute("username")String phone,
                                      Integer productId,
                                      String amount){

        Product product = productService.findByProductId(productId);
        User user = userService.findByPhone(phone);
        if(user != null && product != null){
            Order order = orderService.findOrder(productId, user.getId());
            if(order != null){
                return UnifiedResultBuilder.errorResult(Constants.ORDER_EXIST_ERROR_CODE,
                        Constants.ORDER_EXIST_FAILED_ERROR_MESSAGE);
            }

            BigDecimal expectCost = new BigDecimal(amount);
            order = new Order();
            order.setProductId(productId);
            order.setUserId(user.getId());
            order.setStatus(Constants.OrderState.WAIT_CONFIRM);
            order.setExpectCost(expectCost);
            if(orderService.saveOrder(order)){
                return UnifiedResultBuilder.defaultSuccessResult();
            }
        }
        return UnifiedResultBuilder.errorResult(Constants.CALL_SERVICE_ERROR_CODE,
                Constants.CALL_SERVICE_ERROR_MESSAGE);
    }

    @RequestMapping("/stat")
    public UnifiedResult statOrder(@RequestAttribute("username")String phone){
        User user = userService.findByPhone(phone);
        if(user != null) {
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE, orderService.statOrder(user.getId()));
        }
        return UnifiedResultBuilder.errorResult(Constants.CALL_SERVICE_ERROR_CODE,
                Constants.CALL_SERVICE_ERROR_MESSAGE);
    }
}
