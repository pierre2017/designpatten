package com.study.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * RSA公钥/私钥/签名工具包
 * </p>
 * <p>
 * 罗纳德·李维斯特（Ron [R]ivest）、阿迪·萨莫尔（Adi [S]hamir）和伦纳德·阿德曼（Leonard [A]dleman）
 * </p>
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 * <p>
 * 苏扬
 * 2015-6-26
 * 1.0
 */
public class RSAUtils {

    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 53;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 64;

    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(512);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data       已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data      已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign      数字签名
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64.decodeBase64(sign));
    }

    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey    私钥(BASE64编码)
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密  
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param publicKey     公钥(BASE64编码)
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密  
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密  
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密  
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data       源数据
     * @param privateKey 私钥(BASE64编码)
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密  
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap 密钥对
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap 密钥对
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * DESCRIPTION:
     * suyang
     * 2015年12月30日
     * main 方法
     *
     * @param args
     */
    public static void main(String[] args) {

        //    	PaymentServiceImpl sss = new PaymentServiceImpl();
        //    	QueryLoanDetails queryLoanDetails = new QueryLoanDetails();
        //    	queryLoanDetails.setApplSeq("941846");
        //    	sss.queryLoanDetails(queryLoanDetails);

        //    	MeiChuangNoticeInfo ss = new MeiChuangNoticeInfo();
        //        ss.setTransNo("1111");
        //    	ss.setOrderNo("2222");
        //    	ss.setUserId("3333");
        //    	ss.setCreditNo("4444");	
        //    	ss.setApplystatus("27");
        //    	ss.setApplyMessage("通过");
        //    	ss.setName("kevin"); 
        //    	ss.setIdnumber("370682198909783612");
        //    	ss.setPhone("18660839714");	
        //    	ss.setOrdtotalloan("8000.00");
        //    	
        //    	PaymentServiceImpl psm = new PaymentServiceImpl();
        //    	String result = psm.mcCheckNotice(ss);
        //mcCheckNotice

        //XiMuTask xm = new XiMuTask();
        //xm.run();
        try {
            Map<String, Object> map = genKeyPair();
//            String publicKey = getPublicKey(map);
//            String privateKey = getPrivateKey(map);
            String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKJH9SKW/ZNJjll0ZKTsxdsPB+r+EjDS8XP/d2EmgncrR8xVbckp9iksuHM0ckw5bk84P+5YH2mIf8cDRoBSJykCAwEAAQ==";
            String privateKey = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAokf1Ipb9k0mOWXRkpOzF2w8H6v4SMNLxc/93YSaCdytHzFVtySn2KSy4czRyTDluTzg/7lgfaYh/xwNGgFInKQIDAQABAkEAiQL/8KhSh5w+1z/yCVzu37idEsZYTWiL+0fhXuDInhtLmU7Tmt6VQwn4rq7tmrYIb+Q15E6jtIbyHU3rszVP0QIhAPDC56TTK+knob2fzBPtN9+VSi8bBok5Y4wO84EWqot3AiEArI1snWhHOYspGbELAI3ieHj83Nag/3TrrkgUK8kISl8CIBcsa2dt+/gBHIxH6TixyIL4t585FrP2liJQ/hcau2eZAiAnJgsPh3opZxZTGuTpIkfQl3qfTB7I9qkGKJpS+NBltwIhAJobifVL7NAsWnyTq2jvT+boQttAGZGO87U/lRPXL1PS";
            //			String publicKey ="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMsAEWQepQLdo3mhgw3mLU00n/ldDd4knbq01bH89XnE5RITn+qZBAkHow5jgwH2T5ve2KJzkrHD6JbDDQtRO0UCAwEAAQ==";
            //			String privateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAywARZB6lAt2jeaGDDeYtTTSf+V0N3iSdurTVsfz1ecTlEhOf6pkECQejDmODAfZPm97YonOSscPolsMNC1E7RQIDAQABAkBdolFjQfNQ6BSgZMxDW/lnVC+54J8l6PPMC99JsukzWbjvCqCxMY2/9LIV7W1OpzTp0OcPH+8FdI+GTrQqaL59AiEA7NUfjdHVvqgjWBBLxvYmk9KSVol5eQPE2LL9h4OVMXsCIQDbbft7qRNcISk+oacS109IvS6FiR6QtCxt01ci/v3KPwIgAsWPw/ojtUKEf2BZcq+ozewV+FtNh6QKprqZiv94PIcCICVYeJ65TfQ2KKDiPl80ieflWs+bYLpcD9u5RnFMWMFdAiEAkhXiwm4ltINMTcj82/fl0BwujBb0jybXfVnKNHH43S0=";
            System.out.println("公钥----" + publicKey);
            System.out.println("私钥----" + privateKey);
            String abc = "fef4b1b6-d096-4dcd-9fd9-74ffdd938dc4";
            String sss = "";
            //String sss ="vHaXHgAm+ihC6fmdLp4Ynbdyy7GOo6OAv1bvjjgBFBov4/XrB40gEIscTIaf/Yh54gecQVcAZbVfVDOiFDGBkg==";
            abc = Base64.encodeBase64String(encryptByPrivateKey(abc.getBytes(), privateKey));
            System.out.println("加密后的数据：" + abc);
            sss = new String(decryptByPublicKey(Base64.decodeBase64(abc), publicKey));
            System.out.println("解密后数据：" + sss);

            String priString = "我是原始内容";
            String encodeBase64 = Base64.encodeBase64String(encryptByPublicKey(priString.getBytes(), publicKey));
            System.out.println("加密后的数据" + encodeBase64);
            String s = new String(decryptByPrivateKey(Base64.decodeBase64(encodeBase64), privateKey));
            System.out.println("解密后的数据" + s);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
