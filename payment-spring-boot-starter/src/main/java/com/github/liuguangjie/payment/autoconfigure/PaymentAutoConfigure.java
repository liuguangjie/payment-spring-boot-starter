package com.github.liuguangjie.payment.autoconfigure;

import com.egzosn.pay.ali.api.AliPayConfigStorage;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.wx.api.WxPayConfigStorage;
import com.egzosn.pay.wx.api.WxPayService;
import com.github.liuguangjie.payment.alipay.service.AliPayService;
import com.github.liuguangjie.payment.properties.PaymentProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 支付自动配置类
 */
@Configuration
@EnableConfigurationProperties(PaymentProperties.class)
public class PaymentAutoConfigure {



    private final PaymentProperties paymentProperties;


    public PaymentAutoConfigure(PaymentProperties paymentProperties) {
        this.paymentProperties = paymentProperties;
    }

    @Bean
    @ConditionalOnMissingBean(HttpConfigStorage.class)
    @ConditionalOnClass(HttpConfigStorage.class)
    public HttpConfigStorage httpConfigStorage() {
        HttpConfigStorage httpConfigStorage = new HttpConfigStorage();
        httpConfigStorage.setCharset("utf-8");
        //请求连接池配置
        //最大连接数
        httpConfigStorage.setMaxTotal(20);
        //默认的每个路由的最大连接数
        httpConfigStorage.setDefaultMaxPerRoute(10);
        return httpConfigStorage;
    }


    // 微信生成预处理订单service
    @Bean
    @ConditionalOnMissingBean(WxPayService.class)
    @ConditionalOnClass(WxPayService.class)
    public WxPayService wxPayService(HttpConfigStorage httpConfigStorage) {
        PaymentProperties.WechatProperties wechatProperties =
                paymentProperties.getWechat();
        WxPayConfigStorage wxPayConfigStorage = new WxPayConfigStorage();
        wxPayConfigStorage.setAppid(wechatProperties.getAppId());
        wxPayConfigStorage.setMchId(wechatProperties.getMchId());
        wxPayConfigStorage.setSecretKey(wechatProperties.getSecretKey());
        wxPayConfigStorage.setSignType(wechatProperties.getSignType());
        wxPayConfigStorage.setInputCharset(wechatProperties.getInputCharset());
        wxPayConfigStorage.setNotifyUrl(wechatProperties.getHttpCallback());

        WxPayService wxPayService = new WxPayService(wxPayConfigStorage);
        wxPayService.setRequestTemplateConfigStorage(httpConfigStorage);

        return wxPayService;
    }


    // 支付宝App支付服务端service 应用场景 webapp移动端获取预处理订单
    @Bean
    @ConditionalOnMissingBean(AliPayService.class)
    public AliPayService aliPayService(HttpConfigStorage httpConfigStorage) {
        PaymentProperties.AlipayProperties alipayProperties =
                paymentProperties.getAlipay();
        AliPayConfigStorage aliPayConfigStorage = new AliPayConfigStorage();
        aliPayConfigStorage.setAppId(alipayProperties.getAppId());
        aliPayConfigStorage.setKeyPrivate(alipayProperties.getPrivateKey());
        aliPayConfigStorage.setKeyPublic(alipayProperties.getPublicKey());
        aliPayConfigStorage.setInputCharset(
                alipayProperties.getInputCharset() == null ? "utf-8" :
                alipayProperties.getInputCharset());

        aliPayConfigStorage.setSignType(
                alipayProperties.getSignType() == null ? "RSA2" :
                alipayProperties.getSignType());

        aliPayConfigStorage.setNotifyUrl(alipayProperties.getHttpCallback());

        return new AliPayService(aliPayConfigStorage, httpConfigStorage);
    }


    // 支付宝App支付客户端service 应用场景 原生app支付获取签名


}
