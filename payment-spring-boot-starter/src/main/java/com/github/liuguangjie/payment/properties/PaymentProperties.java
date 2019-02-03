package com.github.liuguangjie.payment.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
// 支付属性配置
@ConfigurationProperties(prefix = "spring.payment")
public class PaymentProperties {



    private AlipayProperties alipay;

    private WechatProperties wechat;

    private UnionpayProperties unionpay;



    public AlipayProperties getAlipay() {
        return alipay;
    }

    public void setAlipay(AlipayProperties alipay) {
        this.alipay = alipay;
    }

    public WechatProperties getWechat() {
        return wechat;
    }

    public void setWechat(WechatProperties wechat) {
        this.wechat = wechat;
    }

    public UnionpayProperties getUnionpay() {
        return unionpay;
    }

    public void setUnionpay(UnionpayProperties unionpay) {
        this.unionpay = unionpay;
    }
    // 公共属性赋值
    private static class CommonsProperties {
        // 支付成功回调地址
        private String httpCallback;

        // 签名类型
        private String signType;

        // 字符类型
        private String inputCharset;

        // 应用id
        private String appId;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getInputCharset() {
            return inputCharset;
        }

        public void setInputCharset(String inputCharset) {
            this.inputCharset = inputCharset;
        }

        public String getSignType() {
            return signType;
        }

        public void setSignType(String signType) {
            this.signType = signType;
        }

        public String getHttpCallback() {
            return httpCallback;
        }

        public void setHttpCallback(String httpCallback) {
            this.httpCallback = httpCallback;
        }
    }
    // 蚂蚁金服 支付配置属性
    public static class AlipayProperties extends CommonsProperties {

        // 公钥
        private String publicKey;

        /**
         * pkcs8格式 私钥
         */
        private String privateKey;

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }
    }
    // 微信支付配置属性
    public static class WechatProperties extends CommonsProperties {
        /**
         *  微信支付分配的商户号 合作者id
         */
        private String mchId;
        //微信支付的安全密钥
        private String secretKey;

        public String getMchId() {
            return mchId;
        }

        public void setMchId(String mchId) {
            this.mchId = mchId;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }
    }

    //银联支付配置属性
    public static class UnionpayProperties extends CommonsProperties {}

}
