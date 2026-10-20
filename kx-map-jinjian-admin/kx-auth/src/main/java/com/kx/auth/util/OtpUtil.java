package com.kx.auth.util;

import com.kx.common.core.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
//import org.tinyradius.packet.AccessRequest;
//import org.tinyradius.packet.RadiusPacket;
//import org.tinyradius.util.RadiusClient;
//import org.tinyradius.util.RadiusException;

import java.io.IOException;
import java.net.SocketTimeoutException;

//@Component
//@RefreshScope
public class OtpUtil {
    //认证服务器地址
//    @Value("${OTPSERVER}")
//    private String otpServer;
//    //认证服务器密码
//    @Value("${SHAREPWD}")
//    private String sharePwd;
//
//    public void auth(String username,String password){
//        try {
//            RadiusClient rc = new RadiusClient(otpServer, sharePwd);
//
//            AccessRequest ar = new AccessRequest(username, password);
//
//            ar.setAuthProtocol(AccessRequest.AUTH_CHAP);
//            RadiusPacket response = rc.authenticate(ar);
//            if (response.getPacketType() != RadiusPacket.ACCESS_ACCEPT) {
//                throw new ServiceException("认证服务器返回信息,密码错误或账号被锁定");
//            }
//        }  catch(SocketTimeoutException ste) {
//            ste.printStackTrace();
//            throw new ServiceException("认证服务器连接失败，请联系系统管理员");
//        } catch (RadiusException e) {
//            e.printStackTrace();
//            throw new ServiceException("认证服务器返回信息,认证失败");
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new ServiceException("认证服务器返回信息,认证失败");
//        }
//    }


}
