package com.kx.system.util;

import com.kx.common.core.utils.StringUtils;
import com.kx.system.config.exception.BusinessValidationException;
import com.kx.system.domain.enums.FtpSyncFileEnum;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

@Component
public class FtpUtil {
    private static final Logger log = LoggerFactory.getLogger(FtpUtil.class);

    private static String _ftpUrl;
    private static Integer _ftpPort;
    private static String _ftpUserName;
    private static String _ftpPassword;

//    public FtpUtil(@Value("${ftp.url}")
//                           String ftpUrl,
//                   @Value("${ftp.port:21}")
//                           Integer ftpPort,
//                   @Value("${ftp.username}")
//                           String ftpUserName,
//                   @Value("${ftp.password}")
//                           String ftpPassword) {
//        FtpUtil._ftpUrl = ftpUrl;
//        FtpUtil._ftpPort = ftpPort;
//        FtpUtil._ftpUserName = ftpUserName;
//        FtpUtil._ftpPassword = ftpPassword;
//    }


    public static void getFileInputStream(FtpSyncFileEnum syncFile, Consumer<InputStream> consumer) {
        getFileInputStream(syncFile, new Date(), null, consumer);
    }

    public static void getFileInputStream(FtpSyncFileEnum syncFile, Runnable checkCallback, Consumer<InputStream> consumer) {
        getFileInputStream(syncFile, new Date(), checkCallback, consumer);
    }

    public static void getFileInputStream(FtpSyncFileEnum syncFile, Date date, Consumer<InputStream> consumer) {
        getFileInputStreamWithCheck(syncFile.getPath(date), syncFile.getCheckFilePath(date), null, consumer);
    }

    public static void getFileInputStream(FtpSyncFileEnum syncFile, Date date, Runnable checkCallback, Consumer<InputStream> consumer) {
        getFileInputStreamWithCheck(syncFile.getPath(date), syncFile.getCheckFilePath(date), checkCallback, consumer);
    }


    private static void getFileInputStreamWithCheck(String path, String checkPath, Runnable checkCallback, Consumer<InputStream> consumer) {
        FTPClient ftpClient = null;
        try {
            ftpClient = createFTPClient();
            checkFileExist(ftpClient, checkPath);
            if (checkCallback != null) {
                checkCallback.run();
            }
            try (InputStream inputStream = ftpClient.retrieveFileStream(path)) {
                consumer.accept(inputStream);
            }
        } catch (IOException e) {
            log.error("读取ftp文件出错,", e);
            throw new RuntimeException(e);
        } finally {
            closeFTPClient(ftpClient);
        }
    }

    public static <T> List<T> parseFtpCsvFileToBean(FtpSyncFileEnum syncFile, Date date) {
        List<T> result = new ArrayList<>();
        getFileInputStreamWithCheck(syncFile.getPath(date), syncFile.getCheckFilePath(date), null, is -> result.addAll(CsvReadUtil.readCsv(is, syncFile)));
        return result;
    }

    public static <T> void streamBatchFtpCsvFileToBean(FtpSyncFileEnum syncFile, Runnable checkCallback, int batchSize, Consumer<List<T>> consumer) {
        streamBatchFtpCsvFileToBean(syncFile, new Date(), batchSize, checkCallback, consumer);
    }

    public static <T> void streamBatchFtpCsvFileToBean(FtpSyncFileEnum syncFile, Date date, int batchSize, Runnable checkFile, Consumer<List<T>> consumer) {
        getFileInputStreamWithCheck(syncFile.getPath(date), syncFile.getCheckFilePath(date), checkFile,
                is -> CsvReadUtil.streamBatchCsvFileToBean(is, syncFile, batchSize, consumer));
    }


    private static void checkFileExist(FTPClient ftpClient, String checkPath) throws IOException {
        if (StringUtils.isEmpty(checkPath)) {
            //不需要检查
            return;
        }
        String parent = new File(checkPath).getParent().replaceAll("\\\\", "/");
        if (ftpClient.changeWorkingDirectory(parent)) {
            FTPFile[] ftpFiles = ftpClient.listFiles(checkPath);
            if (ftpFiles != null && ftpFiles.length > 0) {
                return;
            }
        }
        throw new BusinessValidationException(checkPath + "文件不存在，不进行文件读取");
    }

    private static FTPClient createFTPClient() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(_ftpUrl, _ftpPort);
        ftpClient.login(_ftpUserName, _ftpPassword);
        return ftpClient;
    }

    private static void closeFTPClient(FTPClient ftpClient) {
        if (ftpClient != null && ftpClient.isConnected()) {
            try {
                ftpClient.logout();
                ftpClient.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
