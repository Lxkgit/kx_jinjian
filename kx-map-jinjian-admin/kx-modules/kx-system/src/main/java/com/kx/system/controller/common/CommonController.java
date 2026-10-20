package com.kx.system.controller.common;


import cn.hutool.core.util.ZipUtil;
import com.alibaba.fastjson2.JSONObject;


import com.kx.common.core.exception.base.BaseException;
import com.kx.common.core.utils.StringUtils;
import com.kx.common.core.utils.bean.ProjectConfig;
import com.kx.common.core.utils.file.FileUtils;
import com.kx.common.core.web.domain.AjaxResult;
import com.kx.system.config.bean.ServerConfig;
import com.kx.system.config.constants.Constants;
import com.kx.system.config.exception.CustomException;
import com.kx.system.domain.dto.XskyDTO;
import com.kx.system.service.IXskyLogService;
import com.kx.system.util.FileUploadUtils;
import com.kx.system.util.MinioUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.ObjectUtils;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;

/**
 * 通用请求处理
 *
 * @author xiatian
 */
@Api(tags = "上传下载管理")
@RestController
@RefreshScope
public class CommonController {
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private MinioUtils minioUtils;
    @Value("${pictureType}")
    private String pictureType;
    @Value("${fileType}")
    private String fileSuffix;



    @Autowired(required = false)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;



    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete   是否删除
     */
    @ApiOperation("服务器本地磁盘下载")
    @PostMapping ("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
        try
        {
            if (!FileUtils.isValidFilename(fileName))
            {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }

            String realFileName = fileName.substring(fileName.indexOf("_") + 1);
            String filePath = ProjectConfig.getDownloadPath() + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (!ObjectUtils.isEmpty(delete)&&delete)
            {
                FileUtils.deleteFile(filePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    /**
     * 通用上传请求
     */
    @ApiOperation("服务器本地磁盘上传")
    @PostMapping("/common/upload")
    public AjaxResult uploadFile(MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String filePath = ProjectConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", url);
            ajax.put("staticUrl", ProjectConfig.getProfile() + StringUtils.substringAfter(fileName, Constants.RESOURCE_PREFIX));
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 本地资源通用下载
     */
    @GetMapping("/common/download/resource")
    public void resourceDownload(String name, HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 本地资源路径
        String localPath = ProjectConfig.getProfile();
        // 数据库资源地址
        String downloadPath = localPath + StringUtils.substringAfter(name, Constants.RESOURCE_PREFIX);
        // 下载名称
        String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, downloadName));
        FileUtils.writeBytes(downloadPath, response.getOutputStream());
    }

    /**
     * 对象上传
     *
     * @param file
     * @return
     */
    @ApiOperation("minio对象上传")
    @PostMapping("/minio/upload")
    public AjaxResult minioUpload(@RequestPart MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String suffix = (originalFilename.substring(originalFilename.lastIndexOf(".")+1)).toLowerCase();

            if (!fileSuffix.contains(suffix)){
                throw new CustomException("上传文件格式不支持,请重新上传!");
            }
            String contentType = "application/octet-stream";

            if ("pdf".equals(suffix)){
                contentType = "application/pdf";
            }


            String fileName = System.currentTimeMillis() + file.getOriginalFilename().replaceAll(" ", "");
//            threadPoolTaskExecutor.execute(()-> minioUtils.putObject(minioUtils.getBucketName(), file, fileName));
            minioUtils.putObject(minioUtils.getBucketName(), file, fileName,contentType);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            log.error("fileName:{}",fileName);
            ajax.put("url", minioUtils.getUrlByObjectName(fileName));
            return ajax;
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 对象上传
     *
     * @param file
     * @return
     */
    @ApiOperation("minio对象上传")
    @PostMapping("/minio/post/")
    public AjaxResult minioPostUpload(MultipartFile file,
                                      @RequestParam(required = false) String name,
                                      @RequestParam(required = false) String index,
                                      @RequestParam(required = false) String i) {
        try {
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            threadPoolTaskExecutor.execute(()-> minioUtils.putObject(minioUtils.getBucketName(), file, fileName));
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", minioUtils.getUrlByObjectName(fileName));
            ajax.put("name",name);
            ajax.put("index",index);
            ajax.put("i",i);
            return ajax;
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 根据文件名称查询对象url
     *
     * @param fileName
     * @return
     */
    @ApiOperation("minio根据对象名称查询下载路径")
    @GetMapping("/minio/getUrlByObjectName")
    public AjaxResult getUrlByObjectName(String fileName) {
        String urlByObjectName = minioUtils.getUrlByObjectName(fileName);
        return AjaxResult.success(urlByObjectName);
    }
    /**
     * 批量对象上传
     * @param list
     * @return
     */
    @ApiOperation("minio对象批量上传")
    @PutMapping("/minio/batchUpload")
    public AjaxResult batchMinioUpload(List<MultipartFile> list){
        try {
            List<Map<String,Object>> listResultData = new ArrayList<>();
            for (MultipartFile file: list) {
                Map<String,Object> map = new HashMap<>();
                String fileName = System.currentTimeMillis() + file.getOriginalFilename();
                threadPoolTaskExecutor.execute(()-> minioUtils.putObject(minioUtils.getBucketName(), file, fileName));
                map.put("fileName", fileName);
                map.put("url", minioUtils.getUrlByObjectName(fileName));
                listResultData.add(map);
            }
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileList",listResultData);
            return ajax;
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }

    }

    @ApiOperation("内容文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "上传的文件", required = true, dataType = "__file")
    })
    @PostMapping(value = "/upload", consumes = "multipart/*", headers = {"content-type=multipart/form-data"})
    public AjaxResult uploadFileByMultipartFile(
            @RequestParam(value = "file") MultipartFile file)
    {
        String originalFilename = file.getOriginalFilename();
        long size = file.getSize();
        assert originalFilename != null;
        String suffix = (originalFilename.substring(originalFilename.lastIndexOf(".")+1)).toLowerCase();
        String fileType = "";
        //判断文件类型
        if  (pictureType.contains(suffix)) {
            fileType = Constants.PICTURE;
            //判断大小
            if (size > 20 * 1024 * 1024) {
                return AjaxResult.error(500, "图片类型不能大于20M");
            }
        } else if (fileSuffix.contains(suffix)){
            //其他的文件类型
            fileType = suffix;
            if (size > 200 * 1024 * 1024) {
                return AjaxResult.error(500, "文档类型不能大于200M");
            }
        } else {
            throw new CustomException("上传文件格式不支持,请重新上传!");
        }
        try {
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            threadPoolTaskExecutor.execute(()-> minioUtils.putObject(minioUtils.getBucketName(), file, fileName));
            JSONObject json = new JSONObject();
            json.put("fileUrl", minioUtils.getUrlByObjectName(fileName));
            json.put("fileType", fileType);
            json.put("fileName", fileName);
            return AjaxResult.success(json);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * VR全景图片上传
     *
     * @param file
     * @return
     */
    @ApiOperation("VR全景图片上传")
    @PostMapping("/minio/post/vr")
    public AjaxResult minioVrPicPostUpload(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            long size = file.getSize();
            assert originalFilename != null;
            String suffix = (originalFilename.substring(originalFilename.lastIndexOf(".")+1)).toLowerCase();
            if  (pictureType.contains(suffix)) {
                //判断大小
                if (size > 300 * 1024 * 1024) {
                    return AjaxResult.error(500, "图片类型不能大于300M");
                }
            } else {
                return AjaxResult.error(500, "上传文件格式不支持,请重新上传!");
            }
            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
            minioUtils.putObject(minioUtils.getBucketName(), file, fileName);
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", minioUtils.getUrlByObjectName(fileName));
            return ajax;
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 支行数据分析报告上传
     *
     * @param file
     * @return
     */
    @ApiOperation("支行数据分析报告上传")
    @PostMapping("/minio/post/analysisFile")
    public AjaxResult minioAnalysisFilePostUpload(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            long size = file.getSize();
            assert originalFilename != null;
            String suffix = (originalFilename.substring(originalFilename.lastIndexOf(".")+1)).toLowerCase();
            if  (fileSuffix.contains(suffix)) {
                //判断大小
                if (size > 300 * 1024 * 1024) {
                    return AjaxResult.error(500, "图片类型不能大于300M");
                }
            } else {
                return AjaxResult.error(500, "上传文件格式不支持,请重新上传!");
            }
            String[] split = file.getOriginalFilename().split("\\.");
            String fileName = split[0]+"_"+System.currentTimeMillis()+"."+split[1];
            minioUtils.putObject(minioUtils.getBucketName(), file, fileName);

            String fileSize = "";
            //获取文件大小
            if(size<1024){
                fileSize = "1K";
            }else if(size>1024&&size<1024*1024){
                fileSize = Math.round(size*100/1024)/100.0+"K";
            }else if(size>1024*1024&&size<1024*1024*1024){
                fileSize = Math.round(size*100/1024/1024)/100.0+"M";
            }
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", minioUtils.getUrlByObjectName(fileName));
            ajax.put("fileSize",fileSize);
            return ajax;
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error(e.getMessage());
        }
    }


    /**
     * @Description 下载文件
     * @Author gett
     */
    @GetMapping("/minio/download")
    public void download(String fileName, HttpServletResponse response) {
        try {
            boolean flag = minioUtils.bucketExists(minioUtils.getBucketName());
            if (flag){
                minioUtils.downloadFile(minioUtils.getBucketName(),fileName,"",response);
            }else {
                throw new BaseException("桶不存在！");
            }
        }catch (Exception e){
            log.info("下载接口异常：{}",e.getMessage());
            throw new BaseException("下载接口异常！");
        }
    }

    /**
     * 从MinIO下载多个文件
     *
     * @param response
     */
    @GetMapping("/minio/downloadMultiFileToMinIO")
    public void downloadMultiFileToMinIO(HttpServletResponse response,String fileNames,String zipName){

        if(StringUtils.isEmpty(fileNames)){
            throw new BaseException("请确认要下载的文件！");
        }

        String[] files = fileNames.split(",");
        if(files==null||files.length==0){
            throw new BaseException("请确认要下载的文件！");
        }

        if(StringUtils.isEmpty(zipName)){
            zipName = "新建文件夹.zip";
        }

        try {
            List<String> fileUrlList = new ArrayList<>(Arrays.asList(files));
            //被压缩文件InputStream
            InputStream[] srcFiles = new InputStream[fileUrlList.size()];
            //被压缩文件名称
            String[] srcFileNames = new String[fileUrlList.size()];
            for (int i = 0; i < fileUrlList.size(); i++) {
                String fileUrl = fileUrlList.get(i);
                InputStream inputStream = minioUtils.getObject(minioUtils.getBucketName(), fileUrl);
                if (inputStream == null) {
                    continue;
                }
                srcFiles[i] = inputStream;
                String[] splitFileUrl = fileUrl.split("/");
                srcFileNames[i] = splitFileUrl[splitFileUrl.length - 1];
            }
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(zipName, "UTF-8"));
            //多个文件压缩成压缩包返回
            ZipUtil.zip(response.getOutputStream(), srcFileNames, srcFiles);
        }catch (Exception e){
            e.printStackTrace();
            throw new BaseException("文件下载出现异常！");

        }

    }

    /**
     * 通过httpClient上传文件
     * @param fileName
     * @param path
     * @return 外部域名的下载url
     */
    @PostMapping("/minio/uploadFileByHttpClient")
    public AjaxResult UploadFileByHttpClient(String fileName,String path, String url) {
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        InputStream content = null;
        BufferedReader in = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            //HttpMultipartMode.RFC6532参数的设定是为避免文件名为中文时乱码
            MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
            httpPost.addHeader("header1", "111");//头部放文件上传的head可自定义
            File file = new File(path+fileName); //上传文件的路径
            builder.addBinaryBody("file", file, ContentType.MULTIPART_FORM_DATA, fileName);
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);// 执行提交
            HttpEntity responseEntity = response.getEntity();//接收调用外部接口返回的内容
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                // 返回的内容都在content中
                content = responseEntity.getContent();
                // 定义BufferedReader输入流来读取URL的响应
                in = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                if(!StringUtils.isBlank(result)) {
                    log.info("上传文件"+fileName+"返回参数==>"+result);
                }else {
                    log.error("上传文件失败：返回result为null");
                }

            }
        }catch(Exception e) {
            log.error("上传文件失败：",e);
        }finally {//处理结束后关闭httpclient的链接
            try {
                httpClient.close();
                content .close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return AjaxResult.success(result);
    }



}

