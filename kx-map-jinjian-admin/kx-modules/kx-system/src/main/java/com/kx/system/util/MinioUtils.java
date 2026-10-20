package com.kx.system.util;




import com.kx.common.core.utils.StringUtils;
//import io.minio.*;
//import io.minio.errors.ErrorResponseException;
//import io.minio.errors.InvalidExpiresRangeException;
//import io.minio.messages.Bucket;
//import io.minio.messages.DeleteError;
//import io.minio.messages.Item;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InvalidExpiresRangeException;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.DeleteError;
import io.minio.messages.Item;
import lombok.Cleanup;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @author 86198
 */
@Slf4j
@Data
@Component
public class MinioUtils {

    /**
     * 默认超时时间
     */
    private static final int DEFAULT_EXPIRY_TIME = 7 * 24 * 3600;
    /**
     * 定义一个单例的MinioClient对象
     */
    private static MinioClient minioClient;
    @Value("${minio.endpoint}")
    private String endpoint;
    @Value("${minio.url}")
    private String url;
    @Value("${minio.port}")
    private Integer port;
    @Value("${minio.accessKey}")
    private String accessKey;
    @Value("${minio.secretKey}")
    private String secretKey;
    @Value("${minio.bucketName}")
    private String bucketName;

    public MinioClient getInstance() {
        if (minioClient == null) {
            minioClient = MinioClient.builder().endpoint(endpoint).credentials(accessKey, secretKey).build();
        }
        return minioClient;
    }

    /**
     * 判断文件是否存在
     *
     * @param objectName 文件名称, 如果要带文件夹请用 / 分割, 例如 /help/index.html
     * @return true存在, 反之
     */
    public Boolean checkFileIsExist(String objectName) {
        try {
            minioClient.statObject(
                    StatObjectArgs.builder().bucket(bucketName).object(objectName).build()
            );
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 检查存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return
     */
    @SneakyThrows
    public boolean bucketExists(String bucketName) {
        BucketExistsArgs btest2 = BucketExistsArgs.builder().bucket(bucketName).build();
        boolean existFlag = getInstance().bucketExists(btest2);
        if (existFlag) {
            return true;
        }
        return false;
    }

    /**
     * 创建存储桶
     *
     * @param bucketName 存储桶名称
     */
    @SneakyThrows
    public boolean makeBucket(String bucketName) {
        boolean flag = bucketExists(bucketName);
        if (!flag) {
            minioClient.makeBucket(bucketName);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 列出所有存储桶名称
     *
     * @return
     */
    @SneakyThrows
    public List<String> listBucketNames() {
        List<Bucket> bucketList = listBuckets();
        List<String> bucketListName = new ArrayList<>();
        for (Bucket bucket : bucketList) {
            bucketListName.add(bucket.name());
        }
        return bucketListName;
    }

    /**
     * 列出所有存储桶
     *
     * @return
     */
    @SneakyThrows
    public List<Bucket> listBuckets() {
        return minioClient.listBuckets();
    }

    /**
     * 删除存储桶
     *
     * @param bucketName 存储桶名称
     * @return
     */
    @SneakyThrows
    public boolean removeBucket(String bucketName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            Iterable<Result<Item>> myObjects = listObjects(bucketName);
            for (Result<Item> result : myObjects) {
                Item item = result.get();
                // 有对象文件，则删除失败
                if (item.size() > 0) {
                    return false;
                }
            }
            // 删除存储桶，注意，只有存储桶为空时才能删除成功。
            minioClient.removeBucket(bucketName);
            flag = bucketExists(bucketName);
            if (!flag) {
                return true;
            }

        }
        return false;
    }

    /**
     * 列出存储桶中的所有对象名称
     *
     * @param bucketName 存储桶名称
     * @return
     */
    @SneakyThrows
    public List<String> listObjectNames(String bucketName) {
        List<String> listObjectNames = new ArrayList<>();
        boolean flag = bucketExists(bucketName);
        if (flag) {
            Iterable<Result<Item>> myObjects = listObjects(bucketName);
            for (Result<Item> result : myObjects) {
                Item item = result.get();
                listObjectNames.add(item.objectName());
            }
        }
        return listObjectNames;
    }

    /**
     * 列出存储桶中的所有对象
     *
     * @param bucketName 存储桶名称
     * @return
     */
    @SneakyThrows
    public Iterable<Result<Item>> listObjects(String bucketName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            return minioClient.listObjects(bucketName);
        }
        return null;
    }

    /**
     * 通过文件上传到对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param fileName   File name
     * @return
     */
    @SneakyThrows
    public boolean putObject(String bucketName, String objectName, String fileName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            minioClient.putObject(bucketName, objectName, fileName, null);
            ObjectStat statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.length() > 0) {
                return true;
            }
        }
        return false;

    }

    /**
     * MultipartFile格式文件上传
     *
     * @param bucketName
     * @param multipartFile
     */
    @SneakyThrows
    public void putObject(String bucketName, MultipartFile multipartFile, String filename) {
        @Cleanup
        InputStream inputStream = multipartFile.getInputStream();
        getInstance().putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(filename)
                .stream(inputStream, multipartFile.getSize(), -1)
                .contentType(multipartFile.getContentType())
                .build());
    }

    @SneakyThrows
    public void putObject(String bucketName, MultipartFile multipartFile, String filename,String contentType) {
        @Cleanup
        InputStream inputStream = multipartFile.getInputStream();
        getInstance().putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(filename)
                .stream(inputStream, multipartFile.getSize(), -1)
                .contentType(contentType)
                .build());
    }
    /**
     * 通过InputStream上传对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param stream     要上传的流
     * @param stream     要上传的文件类型 MimeTypeUtils.IMAGE_JPEG_VALUE
     * @return
     */
    @SneakyThrows
    public boolean putObject(String bucketName, String objectName, InputStream stream, String contentType) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            PutObjectOptions putObjectOptions = new PutObjectOptions(stream.available(), -1);
            /**
             * 开启公共类功能设置setContentType
             */
            if (!StringUtils.isEmpty(contentType)) {
                putObjectOptions.setContentType(contentType);
            }
            minioClient.putObject(bucketName, objectName, stream, putObjectOptions);
            ObjectStat statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.length() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 以流的形式获取一个文件对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return
     */
    @SneakyThrows
    public InputStream getObject(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            ObjectStat statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.length() > 0) {
                InputStream stream = minioClient.getObject(bucketName, objectName);
                return stream;
            }
        }
        return null;
    }

    /**
     * 以流的形式获取一个文件对象（断点下载）
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param offset     起始字节的位置
     * @param length     要读取的长度 (可选，如果无值则代表读到文件结尾)
     * @return
     */
    @SneakyThrows
    public InputStream getObject(String bucketName, String objectName, long offset, Long length) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            ObjectStat statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.length() > 0) {
                InputStream stream = minioClient.getObject(bucketName, objectName, offset, length);
                return stream;
            }
        }
        return null;
    }


    /**
     * 将对象下载到本地文件，返回路径
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param localDirectory  本地存储目录
     * @return
     */
    @SneakyThrows
    public String getObjectPathLocal(String bucketName, String objectName, String localDirectory) {
        boolean flag = bucketExists(bucketName);
        String localFilePath = localDirectory + objectName;
        File file = new File(localFilePath);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        if (flag) {
            ObjectStat statObject = getInstance().statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
            if (statObject != null && statObject.length() > 0) {
                InputStream stream = getInstance().getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
                int t;
                while ((t = stream.read()) != -1) {
                    fileOutputStream.write(t);
                }
                fileOutputStream.close();
                return localFilePath;
            }
        }
        return null;
    }

    /**
     * 下载并将文件保存到本地
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param fileName   File name
     * @return
     */
    @SneakyThrows
    public boolean getObject(String bucketName, String objectName, String fileName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            ObjectStat statObject = statObject(bucketName, objectName);
            if (statObject != null && statObject.length() > 0) {
                minioClient.getObject(bucketName, objectName, fileName);
                return true;
            }
        }
        return false;
    }

    /**
     * 删除一个对象
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     */
    @SneakyThrows
    public boolean removeObject(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            minioClient.removeObject(bucketName, objectName);
            return true;
        }
        return false;
    }

    /**
     * 删除指定桶的多个文件对象,返回删除错误的对象列表，全部删除成功，返回空列表
     *
     * @param bucketName  存储桶名称
     * @param objectNames 含有要删除的多个object名称的迭代器对象
     * @return
     */
    @SneakyThrows
    public List<String> removeObject(String bucketName, List<String> objectNames) {
        List<String> deleteErrorNames = new ArrayList<>();
        boolean flag = bucketExists(bucketName);
        if (flag) {
            Iterable<Result<DeleteError>> results = minioClient.removeObjects(bucketName, objectNames);
            for (Result<DeleteError> result : results) {
                DeleteError error = result.get();
                deleteErrorNames.add(error.objectName());
            }
        }
        return deleteErrorNames;
    }

    /**
     * 生成一个给HTTP GET请求用的presigned URL。
     * 浏览器/移动端的客户端可以用这个URL进行下载，即使其所在的存储桶是私有的。这个presigned URL可以设置一个失效时间，默认值是7天。
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param expires    失效时间（以秒为单位），默认是7天，不得大于七天
     * @return
     */
    @SneakyThrows
    public String presignedGetObject(String bucketName, String objectName, Integer expires) {
        boolean flag = bucketExists(bucketName);
        String url = "";
        if (flag) {
            if (expires < 1 || expires > DEFAULT_EXPIRY_TIME) {
                throw new InvalidExpiresRangeException(expires,
                        "expires must be in range of 1 to " + DEFAULT_EXPIRY_TIME);
            }
            url = minioClient.presignedGetObject(bucketName, objectName, expires);
        }
        return url;
    }

    /**
     * 生成一个给HTTP PUT请求用的presigned URL。
     * 浏览器/移动端的客户端可以用这个URL进行上传，即使其所在的存储桶是私有的。这个presigned URL可以设置一个失效时间，默认值是7天。
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @param expires    失效时间（以秒为单位），默认是7天，不得大于七天
     * @return
     */
    @SneakyThrows
    public String presignedPutObject(String bucketName, String objectName, Integer expires) {
        boolean flag = bucketExists(bucketName);
        String url = "";
        if (flag) {
            if (expires < 1 || expires > DEFAULT_EXPIRY_TIME) {
                throw new InvalidExpiresRangeException(expires,
                        "expires must be in range of 1 to " + DEFAULT_EXPIRY_TIME);
            }
            url = minioClient.presignedPutObject(bucketName, objectName, expires);
        }
        return url;
    }

    /**
     * 获取对象的元数据
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return
     */
    @SneakyThrows
    public ObjectStat statObject(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            ObjectStat statObject = minioClient.statObject(bucketName, objectName);
            return statObject;
        }
        return null;
    }

    /**
     * 文件访问路径
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return
     */
    @SneakyThrows
    public String getObjectUrl(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        String url = "";
        if (flag) {
            url = minioClient.getObjectUrl(bucketName, objectName);
        }
        return url;
    }


    /**
     * web下载
     *
     * @param bucketName   存储桶名称
     * @param fileName     文件名
     * @param originalName 原始文件名
     * @param response     输出对象
     */
    public void downloadFile(String bucketName, String fileName, String originalName, HttpServletResponse response) {
        try {
            InputStream file = getInstance().getObject(bucketName, fileName);
            if (!StringUtils.isEmpty(originalName)) {
                fileName = originalName;
            }
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            ServletOutputStream servletOutputStream = response.getOutputStream();
            int len;
            byte[] buffer = new byte[1024];
            while ((len = file.read(buffer)) > 0) {
                servletOutputStream.write(buffer, 0, len);
            }
            servletOutputStream.flush();
            file.close();
            servletOutputStream.close();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 按前缀批量下载文件到本地
     * @param bucketName 存储桶名称
     * @param prefix 文件前缀
     * @param saveDir 本地存储路径
     */
    @SneakyThrows
    public void downloadFiles(String bucketName, String prefix, String saveDir) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            Iterable<Result<Item>> objs = listObjects(bucketName);
            for (Result<Item> result : objs) {

                String objectName = result.get().objectName();
                ObjectStat statObject = statObject(bucketName, objectName);

                if (statObject != null && statObject.length() > 0) {
                    File file= new File(saveDir,bucketName+"/"+objectName);
                    File pfile= file.getParentFile();
                    if(!pfile.exists())
                        pfile.mkdirs();

                    minioClient.getObject(bucketName, objectName, file.getPath());
                }

            }

        }
    }

    /**
     * @return java.util.List<io.minio.messages.Bucket>
     * @Description 获取minio所有的桶
     **/
    @SneakyThrows
    public List<Bucket> getAllBucket() {
        // 获取minio中所以的bucket
        List<Bucket> buckets = getInstance().listBuckets();
        for (Bucket bucket : buckets) {
            log.info("bucket 名称: {} ,创建时间: {}", bucket.name(), DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(bucket.creationDate().withZoneSameInstant(ZoneId.of("Asia/Shanghai"))));
        }
        return buckets;
    }


    /**
     * @param inputStream: 输入流
     * @param objectName:  存储桶里的对象名称
     * @return void
     * @Description 将图片上传到minio服务器
     **/
    @SneakyThrows
    public void uploadToMinio(InputStream inputStream, String objectName) {
        long start = System.currentTimeMillis();
        try {
            long size = inputStream.available();
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(inputStream, size, -1)
                    .build();
            // 上传到minio
            getInstance().putObject(putObjectArgs);
        } catch (Exception e) {
            log.error("上传异常：", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("上传文件关闭流异常", e);
                }
            }
        }
        long end = System.currentTimeMillis();
        log.info("上传 {} 文件，总耗时为：{} ms", objectName, end - start);
    }

    /**
     * @param objectName: 存储桶里的对象名称
     * @return java.lang.String
     * @Description 根据指定的objectName获取下载链接，需要bucket设置可下载的策略
     **/
    @SneakyThrows
    public String getUrlByObjectName(String objectName) {
        try {
            return url+bucketName + "/"+objectName;
           // GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder().object(objectName).bucket(bucketName).expiry(1, TimeUnit.HOURS).method(Method.GET).build();
           // return getInstance().getPresignedObjectUrl(args);
        } catch (Exception e) {
            log.error("获取下载链接异常", e);
        }
        return null;
    }

    /**
     * 文件预览链接
     * @param objectName
     * @return
     */
    @SneakyThrows
    public String getPresignedObjectUrl(String objectName) {
        try {
             GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder().object(objectName).bucket(bucketName).expiry(1, TimeUnit.HOURS).method(Method.GET).build();
             return getInstance().getPresignedObjectUrl(args);
        } catch (Exception e) {
            log.error("获取下载链接异常", e);
        }
        return null;
    }



    /**
     * @param objectName: 存储桶里的对象名称
     * @param fileName:   文件名称
     * @param dir:        文件目录
     * @return void
     * @Description 根据objectName从minio中下载文件到指定的目录
     **/
    @SneakyThrows
    public void downloadFromMinioToFile(String objectName, String fileName, String dir) {
        long start = System.currentTimeMillis();
        GetObjectArgs objectArgs = GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build();
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        //使用BufferedInputStream和BufferedOutputStream可以提高IO效率
        try (BufferedInputStream bis = new BufferedInputStream(getInstance().getObject(objectArgs));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(dir, fileName.substring(fileName.lastIndexOf("/") + 1))))) {
            int length;//读取内容
            while ((length = bis.read()) != -1) {
                bos.write(length);
                bos.flush();//当读取的缓冲区到了8192字节后刷出到输出文件
            }
        } catch (Exception e) {
            log.error("下载异常", e);
        }
        long end = System.currentTimeMillis();
        log.info("下载 {} 文件，总耗时为：{} ms", objectName, end - start);
    }

    public void downloadNet(String fileUrl,String path,String fileName) throws Exception {
        // 下载网络文件
        int bytesum = 0;
        int byteread = 0;

        URL url = new URL(fileUrl);
        InputStream inStream = null;
        FileOutputStream fs = null;
        try {
            File file = new File(path+fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            URLConnection conn = url.openConnection();
            inStream = conn.getInputStream();
            fs = new FileOutputStream(path+fileName);

            byte[] buffer = new byte[1204];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                System.out.println(bytesum);
                fs.write(buffer, 0, byteread);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            inStream.close();
            fs.close();
        }
    }

    public String uploadFileByHttpClient(String fileName, String path, String url) {
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        InputStream content = null;
        BufferedReader in = null;
        File file = new File(path); //上传文件的路径
        try {
            HttpPost httpPost = new HttpPost(url);
            //HttpMultipartMode.RFC6532参数的设定是为避免文件名为中文时乱码
            MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
            httpPost.addHeader("header1", "111");//头部放文件上传的head可自定义
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
            e.printStackTrace();
            log.error("上传文件失败：",e);
        }finally {//处理结束后关闭httpclient的链接
            try {
                httpClient.close();
                content .close();
                in.close();
                //删除临时文件
                file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * base64字符串转换成图片
     * imgStr base64字符串
     * path 生成图片路径
     * photoName 图片名称
     */
    public String generateImage(String imgStr, String path,String photoName) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        FileInputStream fileInputStream = null;
        try {
            String baseValue = imgStr.replaceAll(" ", "+");
            byte[] b = decoder.decodeBuffer(baseValue.replace("data:image/jpeg;base64,", ""));
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            File targetFile = new File(path);
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            out = new FileOutputStream(path+"/"+photoName);
            out.write(b);
            out.flush();
            out.close();

            fileInputStream = new FileInputStream(path+"/"+photoName);
            uploadToMinio(fileInputStream,photoName);

            //上传完毕之后，删除磁盘的文件
            File tmpFile = new File(path+"/"+photoName);
            tmpFile.delete();

            return getUrlByObjectName(photoName);
        } catch (Exception e) {
            e.printStackTrace();
            return getUrlByObjectName(photoName);
        }finally {
            out.close();
            fileInputStream.close();
        }
    }

}
