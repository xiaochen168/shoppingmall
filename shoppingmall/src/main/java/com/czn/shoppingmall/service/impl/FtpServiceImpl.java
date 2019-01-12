package com.czn.shoppingmall.service.impl;

import com.czn.shoppingmall.service.IFileService;
import com.czn.shoppingmall.service.IFtpService;
import com.czn.shoppingmall.util.PropertiesUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service("iFtpService")
public class FtpServiceImpl implements IFtpService {

    private static final Logger logger = LoggerFactory.getLogger(FtpServiceImpl.class);

    private static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");

    private static int  ftpPort = 21;

    private static String ftpUser = PropertiesUtil.getProperty("ftp.server.user");

    private static String ftpPwd = PropertiesUtil.getProperty("ftp.server.pwd");

    private FTPClient ftpClient;

    public boolean upload(List<File> fileList) throws IOException {
        logger.info("文件上传之前");
        boolean result = uploadFile("img", fileList);
        logger.info("结束上传，上传结果是:{}", result);
        return result;
    }


    private boolean uploadFile(String remotePath, List<File> fileList) throws IOException {
        FileInputStream fis = null;
        if (!connectServer(ftpIp, ftpPort, ftpUser, ftpPwd)) {
            return false;
        }
        try {
            ftpClient.changeWorkingDirectory(remotePath);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setBufferSize(1024);
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.enterLocalPassiveMode();
            for (File file : fileList) {
                fis = new FileInputStream(file);
                ftpClient.storeFile(file.getName(),fis);
            }
        } catch (IOException e) {
            logger.error("FTP上传文件失败", e);
            return false;
        } finally {
            fis.close();
            ftpClient.disconnect();
        }
        return true;
    }

    private boolean connectServer(String ip, int port, String user, String pwd) {
        ftpClient = new FTPClient();
        boolean isConnected = false;
        try {
            ftpClient.connect(ip);
            isConnected =  ftpClient.login(user, pwd);
        } catch (IOException e) {
            logger.error("FTP服务器连接失败", e);
            isConnected = false;
        }
        return isConnected;
    }




}
