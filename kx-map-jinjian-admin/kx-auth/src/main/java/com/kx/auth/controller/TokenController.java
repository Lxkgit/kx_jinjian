package com.kx.auth.controller;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Consts;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.kx.auth.form.LoginBody;
import com.kx.auth.form.RegisterBody;
import com.kx.auth.service.SysLoginService;
import com.kx.common.core.domain.R;
import com.kx.common.core.utils.JwtUtils;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.security.auth.AuthUtil;
import com.kx.common.security.service.TokenService;
import com.kx.common.security.utils.SecurityUtils;
import com.kx.system.api.model.LoginUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * token 控制
 *
 * @author kangxu
 */
@RestController
public class TokenController
{
    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysLoginService sysLoginService;

    /**
     * 移动端检验token请求的url
     */
    private static final String token_url = "/uusafe/mos/thirdaccess/rest/user/v1/checkToken";

    /**
     * 移动端标识
     */
    private static final String mbs_org_code = "czcb";
    private static final String mbs_appkey = "mobileKey";
    private static final String mbs_secretkey = "mobileSSoKey";
    private static final String cykj_url = "https://mbs.app.cz:9074";

    @PostMapping("login")
    public R<?> login(@RequestBody LoginBody form)
    {
        // 用户登录
        LoginUser userInfo = sysLoginService.login(form.getUsername(), form.getPassword());
        // 获取登录token
        return R.ok(tokenService.createToken(userInfo));
    }


    /**
     * 1.根据稠银空间的token通过加盐加密获取执掌易token
     * 2.根据执掌易token获取调用行方执掌易接口 获取客户经理信息
     * 3.根据客户经理信息获取网格化tonken返给前端
     *
     * @param map 包含稠银空间的token
     * @return 包含客户经理的工号跟手机号
     */
    @PostMapping("mobileLogin")
    public R<?> checkToken(@RequestBody Map<String, Object> map) {
        if (!map.containsKey("cykjToken") || map.get("cykjToken") == null || map.get("cykjToken").equals("")){
            return R.fail("token不能为空！" );
        }
        String url = cykj_url + token_url;//https://10.129.152.77:9074/uusafe/mos/thirdaccess/rest/user/v1/checkToken
        String cykjToken = (String) map.get("cykjToken");
        String sign = encode(mbs_appkey + mbs_org_code + cykjToken + mbs_secretkey);
        String[] arr = {"phone"};
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orgCode", mbs_org_code);
        jsonObject.put("token", cykjToken);
        jsonObject.put("appkey", mbs_appkey);
        jsonObject.put("sign", sign);
        jsonObject.put("extendAttr", arr);//后端写死传phone，默认返回手机号
        Map<String, Object> tokenMap = connectMBS(url, jsonObject);
        //        log.info("根据token获取客户经理信息返回结果，{}", tokenMap);
        if (!"0".equals(tokenMap.get("code"))) {
            return R.fail("token解析失败");
        }
        Map<String, Object> dataMap = (Map<String, Object>) tokenMap.get("data");
        String userName = (String) dataMap.get("loginName"); //loginName如果是系统对应的工号即username字段即无需改动；如果不是对接行内对接执掌易返回数据得到用户工号再请求获取token
        LoginUser userInfo = sysLoginService.loginByUserName(userName);
        return R.ok(tokenService.createToken(userInfo));
    }

    /**
     * 加盐加密-
     * @param context
     * @return
     */
    public static String encode(String context){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(context.getBytes());
            byte[] encryContext = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < encryContext.length; offset++) {
                i = encryContext[offset];
                if (i<0) i+=256;
                if (i<16) buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
    }


    private static Map<String, Object> connectMBS(String url, JSONObject jsonObject){
        try {
            SSLContext sslContexts = SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) {
                    return true;
                }
            }).build();
            CloseableHttpClient httpClient = HttpClients.custom().setSSLContext(sslContexts)
                    .setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(300 * 1000)
                    .setSocketTimeout(300 * 1000).build();
            HttpPost post = new HttpPost(url);
            post.setConfig(requestConfig);
            post.setHeader("Content-Type", "application/json;charset=utf-8");
//            log.info("发送MBS链接：" + url +"，传参：" + jsonObject);
            StringEntity stringEntity = new StringEntity(jsonObject.toJSONString(), Consts.UTF_8);
            post.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(post);
            String result = EntityUtils.toString(response.getEntity());
//            log.info("MBS返回结果：" + result);
            if (result == null){
                HashMap<String, Object> resultMap = new HashMap<>();
                resultMap.put("code","10004");
                resultMap.put("msg","MBS移动平台返回为null！");
                return resultMap;
            }
            Map<String, Object> Map = JSONObject.parseObject(result, Map.class);
            if (Map.get("code").equals(0)){
                Map.put("code","0");
                Map.put("data",Map.get("data"));
                return  Map;
            }
            Map.put("code","10007");
            Map.put("msg","获取失败");
            Map.put("data",Map.get("data"));
            return Map;
        } catch (Exception e){
//            log.info("发送MBS失败:" + e);
            e.printStackTrace();
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("code","10007");
            resultMap.put("msg","获取失败");
            return resultMap;
        }
    }


    /**
     * 免登--后端登录
     *
     * @param loginBody
     * @return 结果
     */
    @PostMapping("loginByUserName")
    public R<?> loginByUserName(@RequestBody LoginBody loginBody)
    {
        // 用户登录
        LoginUser userInfo = sysLoginService.loginByUserName(loginBody.getUsername());
        // 获取登录token
        return R.ok(tokenService.createToken(userInfo));
    }

    @DeleteMapping("logout")
    public R<?> logout(HttpServletRequest request)
    {
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token))
        {
            String username = JwtUtils.getUserName(token);
            // 删除用户缓存记录
            AuthUtil.logoutByToken(token);
            // 记录用户退出日志
            sysLoginService.logout(username);
        }
        return R.ok();
    }

    @PostMapping("refresh")
    public R<?> refresh(HttpServletRequest request)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser))
        {
            // 刷新令牌有效期
            tokenService.refreshToken(loginUser);
            return R.ok();
        }
        return R.ok();
    }

    @PostMapping("register")
    public R<?> register(@RequestBody RegisterBody registerBody)
    {
        // 用户注册
        sysLoginService.register(registerBody.getUsername(), registerBody.getPassword());
        return R.ok();
    }
}
