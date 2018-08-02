package com.imooc.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @Author xuhaibin
 * @ClassName: LoggerTest
 * @Description:日志测试类
 * @Version 1.0.0
 * @Date 2018/8/1 23:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoggerTest {

    private final static Logger logger = LoggerFactory.getLogger(LoggerTest.class);


    @Test
    public void test() {
        logger.debug("===========================debug");
        logger.info("===========================info");
        try {
            int a = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("=================" + e.getMessage(), e);
        }

    }

    @Test
    public void test1() {
        LogUtils.logInfo("===========================INFO ~");
        LogUtils.logDebug("===========================DEBUG ~");
        try {
            int a = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.logError("===========================ERROR~", e);
        }

        LogUtils.logWarn("===========================WARN ~");
    }




}
