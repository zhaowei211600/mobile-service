package com.third.mobile.service;


import com.third.mobile.bean.CheckOrder;
import com.third.mobile.bean.request.CheckOrderListRequest;

import java.util.List;

public interface ICheckOrderService {

    CheckOrder selectById(Integer checkOrderId);

    boolean updateCheckOrder(CheckOrder checkOrder);

    boolean saveCheckOrder(CheckOrder checkOrder);

    boolean hasOpenCheckOrder(Integer orderId);

    List<CheckOrder> getCheckOrderList(CheckOrderListRequest request);
}
