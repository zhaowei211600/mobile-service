package com.third.mobile.controller;

import com.third.mobile.bean.response.UnifiedResult;
import com.third.mobile.service.ICheckOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/check")
public class CheckOrderController {

    private static final Logger logger = LoggerFactory.getLogger(CheckOrderController.class);

    @Autowired
    private ICheckOrderService checkOrderService;

    /*@PostMapping("/detail")
    public UnifiedResult checkDetail(Integer id){

        CheckOrder checkOrder = checkOrderService.selectById(id);
        if(checkOrder != null){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE,
                    checkOrder);
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }


    @PostMapping("/list")
    public UnifiedResult settleList(CheckListRequest request){
        List<CheckOrder> checkOrderList = checkOrderService.settleList(request);
        if(checkOrderList != null && checkOrderList.size() > 0){
            return UnifiedResultBuilder.successResult(Constants.SUCCESS_MESSAGE,
                    checkOrderList,
                    Page.toPage(checkOrderList).getTotal());
        }
        return UnifiedResultBuilder.errorResult(Constants.EMPTY_DATA_ERROR_CODE,
                Constants.EMPTY_DATA_ERROR_MESSAGE);
    }*/

}
