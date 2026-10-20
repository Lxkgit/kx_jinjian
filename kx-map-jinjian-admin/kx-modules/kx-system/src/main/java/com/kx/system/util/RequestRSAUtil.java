package com.kx.system.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kx.common.core.utils.StringUtils;
import com.kx.system.util.rsa.RSAUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Base64;

@Slf4j
public class RequestRSAUtil {




    public  static JSONObject requestAnalysis(HttpServletRequest request, JSONObject jsonObject,Boolean isPost,String privateKey){

        String gridDev = request.getHeader("gridDev");
        JSONObject result = new JSONObject();
        try {
            log.info("jsonObject : {}",jsonObject);
            if (StringUtils.isNotEmpty(gridDev) && "1".equals(gridDev)){
                log.info("移动端访问");
                //解密
                String content = jsonObject.getString("content");
                log.info("content:{}",content);

                log.info("privateKey : {}",privateKey);
                byte[] decodes = null;
                if (!isPost){
                    log.info("get");
                    String decode = URLDecoder.decode(content, "UTF-8");
                    decodes = Base64.getDecoder().decode(decode);
                }else {
                    log.info("post");
                    decodes = Base64.getDecoder().decode(content);
                }

                String jsonString = new String(RSAUtil.decryptByPrivateKey(decodes, privateKey));
                log.info("解密结果  {}", jsonString);
                result = JSONObject.parseObject(jsonString);
                return result;
            }else {
                log.info("pc端访问");
                return jsonObject;
            }
        } catch (UnsupportedEncodingException e) {
            log.info("解密失败");
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.info("解密失败");
            throw new RuntimeException(e);
        }

    }



    public static JSONObject encryption(JSONObject jsonObject ,String publicKey){




        try {
            String result = RSAUtil.encryptedDataOnJava(jsonObject.toJSONString(), publicKey);
            JSONObject resultJson = new JSONObject();
            resultJson.put("content",result);
            log.info("加密结果");
            log.info("{}",result);
            return resultJson;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
    public static JSONObject encryption(JSONArray jsonArray , String publicKey){

        try {
//            JSONArray jsonArray1 = new JSONArray();
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("name","李根");
//            jsonArray1.add(jsonObject);


            String result = RSAUtil.encryptedDataOnJava(jsonArray.toJSONString(), publicKey);
            JSONObject resultJson = new JSONObject();
            resultJson.put("content",result);
            log.info("result length: {}",result.length());
            log.info("加密结果");
            log.info("{}",result);
            return resultJson;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    //AES加密
    public static JSONObject encryptionAES(JSONArray jsonArray , String key){
        String encrypt = AESCBCUtils.encrypt(jsonArray.toJSONString(), key);
        if(StringUtils.isEmpty(encrypt)){
            log.info("加密失败");
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content",encrypt);
        return jsonObject;
    }


    public static JSONObject encryptionAES(JSONObject jsonObject , String key){
        String encrypt = AESCBCUtils.encrypt(jsonObject.toJSONString(), key);
        if(StringUtils.isEmpty(encrypt)){
            log.info("加密失败");
            return null;
        }
        JSONObject result = new JSONObject();
        result.put("content",encrypt);
        return result;
    }
    public static JSONObject encryptionAES(String s , String key){
        String encrypt = AESCBCUtils.encrypt(s, key);
        if(StringUtils.isEmpty(encrypt)){
            log.info("加密失败");
            return null;
        }
        JSONObject result = new JSONObject();
        result.put("content",encrypt);
        return result;
    }


    public  static JSONObject requestAnalysisAES(HttpServletRequest request, JSONObject jsonObject,Boolean isPost,String key){
        String gridDev = request.getHeader("gridDev");
        JSONObject result = new JSONObject();

        if (StringUtils.isNotEmpty(gridDev) && "1".equals(gridDev)){
            log.info("移动端访问");
            //解密
            String content = jsonObject.getString("content");
            log.info("content:{}",content);
            String jsonString = AESCBCUtils.decrypt(content, key);
            log.info("解密结果  {}", jsonString);
            result = JSONObject.parseObject(jsonString);
            return result;
        }
        log.info("pc端访问");
        return jsonObject;
    }


    public  static JSONArray requestAnalysisAES(HttpServletRequest request, JSONObject jsonObject,String key){
        String gridDev = request.getHeader("gridDev");
        JSONArray result = new JSONArray();
        if (StringUtils.isNotEmpty(gridDev) && "1".equals(gridDev)){
            log.info("移动端访问");
            //解密
            String content = jsonObject.getString("content");
            log.info("content:{}",content);
            String jsonString = AESCBCUtils.decrypt(content, key);
            log.info("解密结果  {}", jsonString);
            result = JSONArray.parseArray(jsonString);
            return result;
        }
        log.info("pc端访问");
        return result;
    }



    public  static JSONObject requestAnalysisAES( String content,String key){

        try {
            String decode = URLDecoder.decode(content, "UTF-8");
            String jsonString = AESCBCUtils.decrypt(decode, key);
            log.info("解密结果  {}", jsonString);
            return JSONObject.parseObject(jsonString);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }




    }

}
