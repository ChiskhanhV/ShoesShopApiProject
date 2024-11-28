package com.example.api.config;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

public class MomoConfig {
	public static final  String accessKey = "M8brj9K6E22vXoDB";	
	public static final String serectkey = "nqQiVSgDMy809JoPF6OzP5OdBUB550Y4";
	public static final String orderType = "momo_wallet";
	public static final String requestType="captureWallet";
	public static final String payType="webApp";
	public static final String redirectUrl="http://localhost:8888/paywithmomo";
	public static final String ipnUrl="http://localhost:8888/paywithmomo";
	public static final String Url="https://test-payment.momo.vn/v2/gateway/api/create";
	public static final String IDMOMO="MOMO5RGX20191128";
	
	public static String encode(String key, String data) {
	    try {
	        byte[] keySect = "nqQiVSgDMy809JoPF6OzP5OdBUB550Y4".getBytes();
	        HmacUtils hm256 = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, keySect);
	        //hm256 object can be used again and again
	        String hmac = hm256.hmacHex(data);
	        return hmac;
	    } catch (Exception e) {
	        e.printStackTrace();
	    }	    
	    return null;
	}
}
