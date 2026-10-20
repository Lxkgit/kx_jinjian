package com.kx.system.util;

import com.amazonaws.*;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.kx.system.domain.XskyReq;
import com.kx.system.domain.XskyResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


@Slf4j
@Component
@RefreshScope
public class XskyUtil {
//    @Value("${xskys3-config.endPoint}")
    private String endPoint;
//    @Value("${xskys3-config.ak}")
    private String ak;
//    @Value("${xskys3-config.sk}")
    private String sk;
//    @Value("${xskys3-config.bucket}")
    private String bucket;

//    @Autowired
//    CommonServiceFeign commonServiceFeign;

    private static final String SUCCESS = "0000";
    private static final String SUCCESSMSG = "上传成功";
    private static final String FAIL = "9999";
    private static final String FAILMSG = "系统异常";
    private static String URL ;
    private static AmazonS3 client;
    private AmazonS3 getClient() {
        if (null == client) {
            synchronized (XskyUtil.class) {
                if (null == client) {
                    client = clientBuilder();
                }
            }
        }
        return client;
    }
    //创建连接
    private AmazonS3 clientBuilder() {
        log.info("AmazonS3连接 初始化创建");
        AWSCredentials credentials = null;
//        Map<String, Object> commConfig = commonServiceFeign.config();
//        URL = (String) commConfig.get(CommonEnum.XSKY_URL.getCode());
        URL = endPoint;
        try {
//            String accessKey = (String)commConfig.get(CommonEnum.XSKY_HFMS_ACCESS_KEY.getCode());
//            String secretKey = (String)commConfig.get(CommonEnum.XSKY_HFMS_SECRET_KEY.getCode());
            String accessKey = ak;
            String secretKey = sk;
                    credentials = new BasicAWSCredentials(accessKey, secretKey);
        } catch (Exception e) {
            /*throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                            "Please make sure that your credentials file is at the correct " +
                            "location (~/.aws/credentials), and is in valid format.", e);*/
            log.error("初始化 credentials失败", e);
            e.printStackTrace();
        }
        //设置连接时的参数
        ClientConfiguration config = new ClientConfiguration();
        config.setMaxConnections(100);
        //设置连接方式为HTTP，可选参数为HTTP和HTTPS
        config.setProtocol(Protocol.HTTP);
        //config.setProtocol(Protocol.HTTPS);
        config.setSignerOverride("S3SignerType"); // 设置签名版本为V2
        //设置网络访问超时时间
        config.setConnectionTimeout(15000);
        config.setUseExpectContinue(true);
        config.setValidateAfterInactivityMillis(6000);
        //请求超时
        config.setRequestTimeout(15000);
        config.setSocketTimeout(15000);
        EndpointConfiguration end_point = null;
        end_point = new EndpointConfiguration(URL, "us-east-1");
        //创建连接,替换原AmazonS3Client接口
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withClientConfiguration(config)
                .withEndpointConfiguration(end_point)
                .withPathStyleAccessEnabled(true)
                .build();
    }
    //预签名对象URL
    public String generatePresignedUrl(XskyReq xskyReq) {
        String bucketName = xskyReq.getBucketName();
        String key = xskyReq.getKey();
        try {
            // 设置URL有效时间为1h
            Date expiration = new Date();
            //long expTimeMillis = expiration.getTime();
            //expTimeMillis += 1000 * 60 * 60;
            //expiration.setTime(expTimeMillis);
            expiration.setTime(0);
            // 生成URL
            GeneratePresignedUrlRequest generatePresignedUrlRequest =
                    new GeneratePresignedUrlRequest(bucketName, key)
                            .withMethod(HttpMethod.GET)
                            .withExpiration(expiration);
            java.net.URL url = getClient().generatePresignedUrl(generatePresignedUrlRequest);
            return url.toString();
        } catch (AmazonServiceException e) {
            log.info("Error Message:    " + e.getMessage());
            log.info("HTTP Status Code: " + e.getStatusCode());
            log.info("AWS Error Code:   " + e.getErrorCode());
            log.info("Error Type:       " + e.getErrorType());
            log.info("Request ID:       " + e.getRequestId());
            log.info("all error:        ", e);
            log.error("预签名对象URL 失败,AmazonServiceException ", e);
        } catch (SdkClientException e) {
            log.info("sdk error",e);
            log.error("预签名对象URL 失败,SdkClientException ", e);
        } catch (Exception e){
            log.info("error :",e);
            log.error("预签名对象URL 失败, Exception", e);
        }
        return null;
    }
    // 单个删除对象
    public void deletingAnObject(XskyReq xskyReq){
        String bucketName = xskyReq.getBucketName();
        String key = xskyReq.getKey();
        try {
            getClient().deleteObject(bucketName, key);
        }catch(AmazonServiceException ase) {
            log.info("Caught an AmazonServiceException when delete object of \""+key+"\".");
            log.info("Error Message:    " + ase.getMessage());
            log.info("HTTP Status Code: " + ase.getStatusCode());
            log.info("AWS Error Code:   " + ase.getErrorCode());
            log.info("Error Type:       " + ase.getErrorType());
            log.info("Request ID:       " + ase.getRequestId());
            log.error(" 单个删除对象 失败, AmazonServiceException", ase);
        }catch (Exception e){
            log.error(" 单个删除对象 失败, Exception", e);
        }
    }
    // 整体上传
    public XskyResult uploadFile(XskyReq xskyReq) {
        XskyResult xskyResult = new XskyResult();
        String bucketName = xskyReq.getBucketName();
        String key = xskyReq.getKey();
        File file = xskyReq.getFile();
        log.info("AmazonS3 整体上传 bucketName:{}",bucketName);
        log.info("AmazonS3 整体上传 key:{}",key);
        PutObjectRequest putRequest = new PutObjectRequest(bucketName, key, file);
        //未指定content-type 得到的Xsky路径为文件的下载路径
        if(StringUtils.isNotBlank(xskyReq.getContentType())){
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(xskyReq.getContentType());
            putRequest.setMetadata(metadata);
        }
        //设置为能公开
        //putRequest.withCannedAcl(CannedAccessControlList.PublicReadWrite);
        PutObjectResult putResult = null;
        try {
            AmazonS3 client = getClient();
            putResult = client.putObject(putRequest);
        } catch (AmazonServiceException ase) {
            log.info("Caught an AmazonServiceException when put object of \"" + key + "\".");
            log.info("Error Message:    " + ase.getMessage());
            log.info("HTTP Status Code: " + ase.getStatusCode());
            log.info("AWS Error Code:   " + ase.getErrorCode());
            log.info("Error Type:       " + ase.getErrorType());
            log.info("Request ID:       " + ase.getRequestId());
            log.error("上传失败", ase);
        }catch (Exception e){
            log.error("上传失败", e);
            xskyResult.setResultCode(FAIL);
            xskyResult.setResultMsg(FAILMSG);
            return xskyResult;
        }
        String fileUrl = generatePresignedUrl(xskyReq);
        if(StringUtils.isBlank(fileUrl)){
            log.error("Xsky获取文件路径失败");
            xskyResult.setResultCode(FAIL);
            xskyResult.setResultMsg(FAILMSG);
            return xskyResult;
        }
        xskyResult.setVersionId(putResult.getVersionId());
        xskyResult.setETag(putResult.getETag());
        Date date = putResult.getExpirationTime();
        if(date != null){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String expirationTime = sdf.format(date);
            xskyResult.setExpirationTime(expirationTime);
        }
        xskyResult.setExpirationTimeRuleId(putResult.getExpirationTimeRuleId());
        xskyResult.setContentMd5(putResult.getContentMd5());
        xskyResult.setFileUrl(fileUrl);
        xskyResult.setResultCode(SUCCESS);
        xskyResult.setResultMsg(SUCCESSMSG);
        return xskyResult;
    }
    public String getBucket(){
        return bucket;
    }
}
