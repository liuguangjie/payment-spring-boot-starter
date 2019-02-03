package com.github.liuguangjie.test;

import com.egzosn.pay.common.bean.PayOrder;
import com.egzosn.pay.wx.api.WxPayService;
import com.egzosn.pay.wx.bean.WxTransactionType;
import com.github.liuguangjie.main.SpringBootMain;
import com.github.liuguangjie.payment.alipay.service.AliPayService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootMain.class)
public class PaymentTest {

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    private AliPayService aliPayService;

    // 微信预处理订单
    @Test
    public void testWxOrder(){
        Map<String, Object> map = wxPayService.orderInfo(createPayOrder());
        // 打印
        map.forEach((k, v) -> {
            System.out.println(k + ": " + v);
        });
    }

    // 蚂蚁金服预处理订单
    @Test
    public void testAliOrder(){
        Map<String, Object> map = aliPayService.orderInfo(createPayOrder());
        Assert.assertTrue(map.containsKey("orderInfo"));
        // 打印
        map.forEach((k, v) -> {
            System.out.println(k + ": " + v);
        });

    }


    public PayOrder createPayOrder() {
        // request params
        String userId = "5356"; // request.getParameter("userId");
        String amount = "0.01"; // request.getParameter("amount");
        // 订单号
        String orderId = userId + System.nanoTime() / 1000 + "";
        PayOrder payOrder = new PayOrder();
        payOrder.setTransactionType(WxTransactionType.APP);
        payOrder.setSubject("测试订单Subject_" + orderId);
        payOrder.setBody("测试订单Body_" + orderId);
        payOrder.setOutTradeNo(orderId);
        payOrder.setPrice(new BigDecimal(amount));

        return payOrder;
    }
}
