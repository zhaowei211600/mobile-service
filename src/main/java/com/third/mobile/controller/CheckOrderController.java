package com.third.mobile.controller;

import com.third.mobile.bean.CheckOrder;
import com.third.mobile.bean.Order;
import com.third.mobile.bean.request.CheckOrderListRequest;
import com.third.mobile.bean.response.UnifiedResult;
import com.third.mobile.bean.response.UnifiedResultBuilder;
import com.third.mobile.service.GenerateProductNumberService;
import com.third.mobile.service.ICheckOrderService;
import com.third.mobile.service.IOrderService;
import com.third.mobile.util.Constants;
import com.third.mobile.util.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/check/order/")
public class CheckOrderController {

    private static final Logger logger = LoggerFactory.getLogger(CheckOrderController.class);

    @Autowired
    private ICheckOrderService checkOrderService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private GenerateProductNumberService numberService;

    @PostMapping("/detail")
    public UnifiedResult checkDetail(Integer checkOrderId, Integer orderId){

        if(checkOrderId != null && checkOrderId != 0){
            CheckOrder checkOrder = checkOrderService.selectById(checkOrderId);
            if(checkOrder != null){
                return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE,
                        checkOrder);
            }
        }else if(orderId != null && orderId != 0){
            Order order = orderService.selectById(orderId);
            if(order != null){
                return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE,
                        order);
            }
        }

        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }


    @PostMapping("/list")
    public UnifiedResult checkOrderList(@RequestBody CheckOrderListRequest request){
        List<CheckOrder> checkOrderList = checkOrderService.getCheckOrderList(request);
        if(checkOrderList != null && checkOrderList.size() > 0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE,
                    checkOrderList,
                    Page.toPage(checkOrderList).getTotal());
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }

    @GetMapping("/number")
    public UnifiedResult getCheckOrderNumber(){
        String checkOrderNumber = numberService.generateCheckOrderNumber();
        if(!StringUtils.isEmpty(checkOrderNumber)){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_CODE,
                    Constants.SUCCESS_MESSAGE,
                    checkOrderNumber);
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }
}
