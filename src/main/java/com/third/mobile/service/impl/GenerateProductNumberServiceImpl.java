package com.third.mobile.service.impl;

import com.third.mobile.service.GenerateProductNumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class GenerateProductNumberServiceImpl implements GenerateProductNumberService{

    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateProductNumberServiceImpl.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String PRODUCT_NUMBER_PREFIX = "PN";

    private static final String PRODUCT_NUMBER_REDIS_KEY = "product_number:";

    private static final String CHECK_ORDER_NUMBER_PREFIX = "CN";

    private static final String CHECK_ORDER_NUMBER_REDIS_KEY = "check_order_number:";

    @Override
    public String generateProductNumberNumber() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current = simpleDateFormat.format(new Date());
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        Long serialNumber = valueOperations.increment(PRODUCT_NUMBER_REDIS_KEY, 1);
        String assetNumber = String.format("%08d", serialNumber);
        StringBuffer sb = new StringBuffer();
        sb.append(PRODUCT_NUMBER_PREFIX).append(current).append(assetNumber);
        LOGGER.info("正在生成资产编号：{}", sb.toString());
        return sb.toString();
    }

    @Override
    public String generateCheckOrderNumber() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String current = simpleDateFormat.format(new Date());
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        Long serialNumber = valueOperations.increment(CHECK_ORDER_NUMBER_PREFIX, 1);
        String assetNumber = String.format("%08d", serialNumber);
        StringBuffer sb = new StringBuffer();
        sb.append(CHECK_ORDER_NUMBER_REDIS_KEY).append(current).append(assetNumber);
        LOGGER.info("正在生成验收编号编号：{}", sb.toString());
        return sb.toString();
    }
}
