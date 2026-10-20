package com.kx.system.util.rsa;

import com.alibaba.fastjson.JSONObject;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.util.io.StreamUtil;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.URLEncoder;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

@Slf4j
public class RSAUtil {
    /**
     * SHA256 加密长度：64字节数量
     */
    private final static String SHA256 = "SHA-256";
    private static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 245;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 256;

    /**
     * RSA 位数 如果采用2048 上面最大加密和最大解密则须填写:  245 256
     */
    private static final int INITIALIZE_LENGTH = 2048;

    //生成报文
    public static String getParam(JSONObject content , String privateKey) throws Exception{

        String sign = getSign(content,privateKey);
        log.info("sign : {}",sign);
        content.put("sign",sign);
        Set<String> jsonKeySet = content.keySet();

        Map<String,Object> mapData = new HashMap<>();
        for (String jsonKey : jsonKeySet) {
            String value = content.getString(jsonKey);
            mapData.put(jsonKey,value);
        }
        //根据key值的ascii排序
        String sort = sort(mapData);
        String encode = URLEncoder.encode(sort, "UTF-8");
        log.info(" 报文： {}",encode);
        return encode;
    }


//    public static Map<String,Object> getParam2(JSONObject content,String privateKey,String apiName,String timestamp,String nonce) throws Exception{
//        //标准报文
//        JSONObject data = new JSONObject();
//        /** 开发者ID*/
//        data.put("dlpId","pb000020037277");
//        /** 应用ID */
//        data.put("appId","TB9J2023072800034368");
//        /** 产品ID */
//        data.put("prodId","TB9J2022102531814777");
//        /** API方法名 */
//        data.put("method",apiName);
//        /** 报文格式,仅支持JSON */
//        data.put("format","JSON");
//        /** 请求使用的编码格式仅支持utf-8 */
//        data.put("charset","utf-8");
//        /** 商户生成签名字符串所使用的签名算法类型,目前支RSA2(RSAWITHSHA256)*/
//        data.put("signType","RSA2");
//        /** 请求时间戳*/
//        data.put("timestamp",timestamp);
//        /** 调用的接口版本，默认为：1.0.0 */
//        data.put("version","1.0.0");
//        /** 请求的唯一标识 */
//        data.put("nonce",nonce);
//        /** 请求 报文*/
//        String jsonString = content.toJSONString();
//        String bizContentEncode = URLEncoder.encode(jsonString, "UTF-8");
//
//        data.put("bizContent",bizContentEncode);
//
////        data.put("bizContent",content);
//        JSONObject data2 = JSONObject.parseObject(data.toJSONString());
//        String sign = getSign(data2, privateKey);
//        log.info("sign : {}",sign);
//        data.put("sign",sign);
//        Set<String> jsonKeySet = data.keySet();
//
//        Map<String,Object> mapData = new HashMap<>();
//        for (String jsonKey : jsonKeySet) {
//            String value = data.getString(jsonKey);
//            mapData.put(jsonKey,value);
//        }
//
//        return mapData;
//    }



    public static String getSign(JSONObject data,String key) throws Exception{
//        String bizContent = data.getJSONObject("bizContent").toJSONString();
//        String bizContentEncode = URLEncoder.encode(bizContent, "UTF-8");
//        data.put("bizContent",bizContentEncode);
        Set<String> jsonKeySet = data.keySet();
        Map<String,Object> mapData = new HashMap<>();
        for (String jsonKey : jsonKeySet) {
            String value = data.getString(jsonKey);
            if(StringUtils.isEmpty(value)){
                continue;
            }

            mapData.put(jsonKey,value);
        }
        //根据key值的ascii排序
        String sort = sort(mapData);
        log.info("签名生成   参数排序结果1：{}",sort);
        //SHA256摘要值
        String digest = encryptDigest(sort);
        log.info("签名生成   SHA256摘要值：{}",digest);

        PrivateKey privateKey = getPrivateKey(key);

        Signature Sign = Signature.getInstance(SIGNATURE_ALGORITHM);
        Sign.initSign(privateKey);
        Sign.update(digest.getBytes());
        byte[] signed = Sign.sign();



        return Base64.getEncoder().encodeToString(signed);

    }






    /**
     * 消息摘要算法，是单向、不可逆的
     *
     * @param input     消息类型

     * @return 十六进制字符串
     */
    public static String encryptDigest(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(SHA256);
            // 消息数字摘要
            byte[] digest = messageDigest.digest(input.getBytes());
            return toHex(digest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加密失败！");
        }
    }


    /**
     * 排序
     * 根据key值的 ascii升序排列 在拼接成字符串
     *
     * @param map
     * @return
     */
    private static String sort(Map<String,Object> map){

        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);
        int index = 0;
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object value = map.get(key);
            content.append((index==0?"":"&")+key+"="+value);
            index++;
        }
        return content.toString();

    }

    /**
     * 验签
     * @return
     */

    public static Boolean verificationSign(JSONObject jsonObject,String publicKey) throws Exception {

        if (!jsonObject.containsKey("sign")) {
            log.info("sign为空");
            return false;
        }
        //sign
        String sign = jsonObject.getString("sign");

        jsonObject.remove("sign");
        Set<String> jsonKeySet = jsonObject.keySet();
        Map<String, Object> mapData = new HashMap<>();
        for (String jsonKey : jsonKeySet) {
            String value = jsonObject.getString(jsonKey);
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            mapData.put(jsonKey, value);
        }

        String sort = sort(mapData);
        String digest = encryptDigest(sort);
        //验签
        return rsa256CheckContent(digest, sign, publicKey, "UTF-8");
    }

    private static String toHex(byte[] digest) {
        // 创建对象用来拼接
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            // 转成 16进制
            String s = Integer.toHexString(b & 0xff);
            if (s.length() == 1) {
                // 如果生成的字符只有一个，前面补0
                s = "0" + s;
            }
            sb.append(s);
        }
        return sb.toString();
    }
    /**
     * 解码PrivateKey
     * @param key
     * @return
     */
    public static PrivateKey getPrivateKey(String key) {
        try {
            byte[] byteKey = Base64.getDecoder().decode(key);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 公钥验签
     * @param content 内容
     * @param sign 私钥签名
     * @param publicKey 公钥
     * @param charset 编码
     * @return
     * @throws Exception
     */
    public static boolean rsa256CheckContent(String content, String sign, String publicKey,
                                             String charset) throws Exception {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initVerify(pubKey);
            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }
            return signature.verify(org.apache.commons.codec.binary.Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            throw new Exception("无效签名;RSAcontent = " + content + "; charset = " + charset, e);
        }
    }

    public static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        StringWriter writer = new StringWriter();
        StreamUtil.io(new InputStreamReader(ins), writer);
        byte[] encodedKey = writer.toString().getBytes();
        encodedKey = org.apache.commons.codec.binary.Base64.decodeBase64(encodedKey);
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    public static String getAsciiJsonString(JSONObject json){
        //将key值单独封装成List
        List<String> keys = new ArrayList<String>(((Map<String, Object>) json).keySet());
        //排序
        Collections.sort(keys);
        //使用LinkedHashMap记录插入顺序
        LinkedHashMap<String, Object> linkmap=new LinkedHashMap<String, Object>();
        //按照key值顺序插入对应的value
        for (String key : keys) {
            if (StringUtils.isNotEmpty(key)) {
                linkmap.put(key, ((Map<String, Object>) json).get(key));
            }
        }
        //将LinkedHashMap转换为JSONObject
        JSONObject jObject = new JSONObject(true);
        jObject.putAll(linkmap);
        //返回JSON字符串
        return jObject.toString();
    }


    public static String genHMAC(String data, String key) throws Exception{
        byte[] result = null;

        //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        SecretKeySpec signinKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
        //生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        //用给定密钥初始化 Mac 对象
        mac.init(signinKey);
        //完成 Mac 操作
        byte[] rawHmac = mac.doFinal(data.getBytes());
        result = org.apache.commons.codec.binary.Base64.encodeBase64(rawHmac);
        if (null != result) {
            return new String(result);
        } else {
            return null;
        }
    }


    /**
     * 私钥解密
     * @param encryptedData 已加密数据
     * @param privateKey
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
        byte[] keyBytes = org.apache.commons.codec.binary.Base64.decodeBase64(privateKey);
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



    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        byte[] keyBytes = org.apache.commons.codec.binary.Base64.decodeBase64(publicKey);
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
     * java端公钥加密
     */
    public static String encryptedDataOnJava(String data, String PUBLICKEY) {
        try {
            data = org.apache.commons.codec.binary.Base64.encodeBase64String(encryptByPublicKey(data.getBytes(), PUBLICKEY));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }

}
