package com.lmpay.starter.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {
    @Value("${federal.trustStore}")
    public String trustStore;

    @Value("${federal.trustStorePassword}")
    public String trustStorePassword;

    @Value("${federal.keyStore}")
    public String keyStore;

    @Value("${federal.keyStorePassword}")
    public String keyStorePassword;

    @Value("${federal.pkgs}")
    public String protocolHandlerPackages;

    @Value("${federal.keyStoreType}")
    public String keyStoreType;

    @Value("${federal.client.secret}")
    public String clientSecret;

    @Value("${federal.client.id}")
    public String clientId;

    @Value("${hellopaisa.auth.username}")
    public String username;

    @Value("${hellopaisa.auth.password}")
    public String password;

    @Value("${hellopaisa.userId}")
    public String dcmUserId;

    @Value("${hellopaisa.routingCode}")
    public String routingCode;

    @Value("${federal.user.id}")
    public String userId;

    @Value("${federal.user.password}")
    public String userPassword;

    @Value("${federal.sendercd}")
    public String sendercd;

    @Value("${federal.resp.url}")
    public String respUrl;

    @Value("${federal.intra.fund.transfer}")
    public String intraUrl;

    @Value("${federal.vostro.balance}")
    public String balanceUrl;

    @Value("${federal.transaction.enquiry}")
    public String transactionUrl;

    @Value("${federal.imps.fund.transfer}")
    public String impsUrl;

    @Value("${federal.neft.fund.transfer}")
    public String neftUrl;

    @Value("${federal.upi.val.address}")
    public String upiValAddress;

    @Value("${federal.upi.req.pay}")
    public String upiReqPay;

    @Value("${federal.beneficiary.name.enquiry}")
    public String nameEnquiry;

    @Value("${hellopaisa.check.agent.balance}")
    public String agentBalanceUrl;

    @Value("${hellopaisa.check.status}")
    public String statusUrl;

    @Value("${hellopaisa.post.transaction}")
    public String postTransactionUrl;

    @Value("${hellopaisa.agent.banks}")
    public String agentBanks;

    @Value("${hellopaisa.agent.rates}")
    public String agentRates;

}
