package com.study.utils;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class FtpUtils {

    private static Logger logger = Logger.getLogger(FtpUtils.class);

    /**
     * 上传文件到制定目录
     * 上传到指定目录
     * 文件名
     */
    public static boolean uploadFile(String ftpHost, String ftpUserName, String ftpPassword,
                                     int ftpPort, String ftpPath, String fileName,
                                     InputStream inputStream) {
        boolean flag = false;
        if (inputStream == null) {
            logger.error("上传流为空..........");
            return flag;
        }

        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            // 1.指定写入的目录
            ftpClient.changeWorkingDirectory(ftpPath);
            // 2.写操作
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.storeFile(new String(fileName.getBytes("utf-8"), "iso-8859-1"), inputStream);
            inputStream.close();
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            logger.error("上传异常", e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (Exception e) {
                    logger.error("exception", e);
                }
            }
        }
        return flag;
    }

    /**
     * 上传文件和目录均可
     */
    public static boolean uploadFile(String ftpHost, String ftpUserName, String ftpPassword,
                                     int ftpPort, String ftpPath, String fileName, File file) {
        boolean flag = false;
        try {
            if (null == file) {
                logger.error("FTP上传文件或文件目录为空");
                return flag;
            }
            if (file.isDirectory()) {
                // 文件目录上传
                FTPClient ftpClient = null;
                try {
                    ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
                    // 1.指定写入的目录
                    ftpClient.changeWorkingDirectory(ftpPath);
                    // 2.写操作
                    ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                    String[] fileLists = file.list();
                    for (int i = 0; i < fileLists.length; i++) {
                        File file1 = new File(file.getPath() + File.separator + fileLists[i]);
                        if (file1.isDirectory()) {
                            uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath,
                                    fileName, file);
                            ftpClient.changeToParentDirectory();
                        } else {
                            File file2 = new File(file.getPath() + File.separator + fileLists[i]);
                            FileInputStream input = new FileInputStream(file2);
                            ftpClient.storeFile(file2.getName(), input);
                            input.close();
                        }
                    }
                    ftpClient.logout();
                    flag = true;
                } catch (Exception e) {
                    logger.error("上传异常", e);
                } finally {
                    if (ftpClient.isConnected()) {
                        try {
                            ftpClient.disconnect();
                        } catch (Exception e) {
                            logger.error("exception", e);
                        }
                    }
                }
            } else {
                // 文件上传
                InputStream inputStream = new FileInputStream(file);
                flag = uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName,
                        inputStream);
            }
        } catch (Exception e) {
            logger.error("上传异常", e);
        }
        return flag;
    }

    /**
     * FTP文件下载
     *
     * @param ftpHost
     * @param ftpUserName
     * @param ftpPassword
     * @param ftpPort
     * @param ftpPath     ftp路径
     * @param fileName    文件名
     * @param localPath   下载到本地路径
     */
    public static void downFile(String ftpHost, String ftpUserName, String ftpPassword,
                                int ftpPort, String ftpPath, String fileName, String localPath) {
        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            // 4.指定要下载的目录
            ftpClient.changeWorkingDirectory(ftpPath);// 转移到FTP服务器目录
            // 5.遍历下载的目录
            FTPFile[] fs = ftpClient.listFiles();
            for (FTPFile ff : fs) {
                // 解决中文乱码问题，两次解码
                byte[] bytes = ff.getName().getBytes("iso-8859-1");
                String fn = new String(bytes, "utf8");
                if (fn.equals(fileName)) {
                    // 6.写操作，将其写入到本地文件中
                    File localFile = new File(localPath + ff.getName());
                    OutputStream is = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(ff.getName(), is);
                    is.close();
                }
            }
            ftpClient.logout();
        } catch (Exception e) {
            logger.error("从FTP下载文件出错", e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
    }

    /**
     * 在ftp服务器端创建目录（不支持一次创建多级目录）
     */
    public static boolean mkdir(String ftpHost, String ftpUserName, String ftpPassword,
                                int ftpPort, String ftpPath, String directoryName) {
        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            ftpClient.changeWorkingDirectory(ftpPath);// 转移到FTP服务器目录
            return ftpClient.makeDirectory(directoryName);
        } catch (IOException e) {
            logger.error("Could not mkdir.", e);
            return false;
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
    }

    /**
     * 一次性创建多级目录
     */
    public static boolean mkdirs(String ftpHost, String ftpUserName, String ftpPassword,
                                 int ftpPort, String ftpPath, String directoryName) {
        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            if (!ftpClient.changeWorkingDirectory(ftpPath)) {
                // 转移到FTP服务器目录
                String dirs[] = ftpPath.split("/");
                for (int i = 0; i < dirs.length; i++) {
                    if (!ftpClient.changeWorkingDirectory(dirs[i])) {
                        if (!ftpClient.makeDirectory(dirs[i])) {
                            logger.warn("FTP创建目录失败，无权限创建");
                            return false;
                        }
                    }
                    ftpClient.changeWorkingDirectory(dirs[i]);
                }
            }
            return ftpClient.makeDirectory(directoryName);
        } catch (IOException e) {
            logger.error("Could not mkdir.", e);
            return false;
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
    }

    /**
     * @param ftpHost
     * @param ftpUserName
     * @param ftpPassword
     * @param ftpPort
     * @param ftpPath     ftp路径
     * @param fileName    文件名
     */
    public static boolean isFileExists(String ftpHost, String ftpUserName, String ftpPassword,
                                       int ftpPort, String ftpPath, String fileName) {
        boolean flag = false;
        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            // 4.指定要下载的目录
            ftpClient.changeWorkingDirectory(ftpPath);// 转移到FTP服务器目录
            // 5.遍历下载的目录
            FTPFile[] fs = ftpClient.listFiles();
            for (FTPFile ff : fs) {
                // 解决中文乱码问题，两次解码
                byte[] bytes = ff.getName().getBytes("iso-8859-1");
                String fn = new String(bytes, "utf8");
                if (fn.equals(fileName)) {
                    flag = true;
                    break;
                }
            }
            ftpClient.logout();
        } catch (Exception e) {
            logger.error("从FTP下载文件出错", e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return flag;
    }

    /**
     * 获取FTPClient对象
     *
     * @param ftpHost     FTP主机服务器
     * @param ftpPassword FTP 登录密码
     * @param ftpUserName FTP登录用户名
     * @param ftpPort     FTP端口 默认为21
     * @return
     */
    public static FTPClient getFTPClient(String ftpHost, String ftpUserName, String ftpPassword,
                                         int ftpPort) {
        FTPClient ftpClient = null;
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                logger.info("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
                throw new RuntimeException("未连接到FTP，用户名或密码错误");
            } else {
                logger.info("FTP连接成功。");
            }
        } catch (SocketException e) {
            e.printStackTrace();
            logger.info("FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("FTP的端口错误,请正确配置。");
        }
        return ftpClient;
    }

    /**
     * 将流写为文件
     */
    public static void inputstreamtofile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取InputStream对象
     *
     * @param URLName ftp://sxftp:vbaosx@121.41.30.78/home/vftpuser/sxftp/20151019/
     *                账户登录后，访问权限为sxftp下的目录 URLName =
     *                "ftp://sxftp:vbaosx@121.41.30.78/20151019/201601130001_20160318_SF.xml"
     *                ;
     * @return
     */
    public static InputStream getUrlInputStream(String URLName) throws Exception {// URLName文件相对路径地址
        URL url = new URL(URLName); // 创建URL
        URLConnection urlconn = url.openConnection(); // 试图连接并取得返回状态码urlconn.connect();
        return urlconn.getInputStream();
    }

    /**
     * 删除文件
     */
    public static boolean deleteFile(String ftpHost, String ftpUserName, String ftpPassword,
                                     int ftpPort, String ftpPath, String fileName) {
        boolean flag = false;
        FTPClient ftpClient = null;
        try {
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            flag = ftpClient.deleteFile(ftpPath + fileName);
            ftpClient.logout();
        } catch (Exception e) {
            logger.error("上传异常", e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (Exception e) {
                    logger.error("exception", e);
                }
            }
        }
        return flag;
    }

    public static void main(String[] args) throws IOException, Exception {
        String ftpHost = "121.41.30.78";
        String ftpUserName = "customerbill";
        String ftpPassword = "customerbill";
        //		FtpUtils.deleteFile(ftpHost, ftpUserName, ftpPassword, 21,
        //				"/mnt/customerbill/batchpayment/201512150001/",
        //				"S20160510141350201000002-201512150001_1462860812717.xml");
        File file = new File("D:\\electron\\test\\201512150001\\20160828");
        FtpUtils.uploadFile(ftpHost, ftpUserName, ftpPassword, 21,
                "/mnt/customerbill/electronVoucher/201512150001/20160828/", "aa.txt", file);
        System.out.println("ftp upload success");
        /*
         * String ftpHost = "121.41.30.78"; String ftpUserName = "sxftp"; String
         * ftpPassword = "vbaosx"; int ftpPort = 21;
         */

        // File file = new File("D:\\001.txt");
        // try {
        // inputstreamtofile(getUrlInputStream("ftp://sxftp:vbaosx@121.41.30.78/home/vftpuser/sxftp/20151019/201601130001_20160318_SF.xml"),file);
        // SAXReader reader = new SAXReader();
        // Document document = reader.read(file);
        // String xml =
        // XmlUtils.replace2Blank(XmlUtils.replaceBlank(XmlUtils.toXML(document,"UTF-8")));
        // System.out.println(xml);
        // System.out.println("====================");
        // } catch (Exception e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // String result =
        // IOUtils.toString(getUrlInputStream("ftp://customerbill:customerbill@121.41.30.78"+File.separator+"201512250001_20160316_SF.txt"),
        // "UTF-8");
        // System.out.print(result);

        /*
         * try { BufferedReader br=new BufferedReader(new
         * InputStreamReader(getUrlInputStream(
         * "ftp://customerbill:customerbill@121.41.30.78/201512250001_20160316_SF.txt"
         * ))); String line=null; int lineNum = 0;
         * while((line=br.readLine())!=null){ lineNum++; String[] fieldArray =
         * line.split("\\|", -1); // 解析文件 // 第一行 对账文件汇总数据 if (lineNum == 1) {
         * logger.error("山西渠道对账文件第1行列数不是7列"); System.out.println(fieldArray[0]);
         * } else { logger.error("山西渠道对账文件交易明细数据不是17列"); } } br.close(); } catch
         * (IOException e) { // TODO Auto-generated catch block
         * logger.error("read txt file fail", e); }
         */
        //		 mkdir("121.41.30.78","sxftp","vbaosx",ftpPort,"/home/vftpuser/sxftp/20151019/","qqqq");

        //		mkdirs(ftpHost, ftpUserName, ftpPassword, 21, "/mnt/customerbill/electronVoucher/", "201512150001/20160828");
    }

    /**
     * 从已知的目录中得到一个未知的文件
     */
    public static String getExistFileFromDirectory(String ftpHost, String ftpUserName,
                                                   String ftpPassword, int ftpPort,
                                                   String finalDirectory) {
        FTPClient ftpClient = null;
        String fileName = null;
        try {
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            // 指定要查询的目录
            ftpClient.changeWorkingDirectory(finalDirectory);// 转移到FTP服务器目录
            // 遍历下载的目录
            FTPFile[] fs = ftpClient.listFiles();
            if (fs.length <= 0) {
                return null;
            }
            fileName = new String(fs[0].getName().getBytes("iso-8859-1"), "utf8");
            ftpClient.logout();
        } catch (Exception e) {
            logger.error("从FTP已知目录读取文件出错", e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return fileName;
    }

    /**
     * 从FTP服务器下载整个文件夹到本地
     */
    public static boolean downLoadDirectory(String ftpHost, String ftpUserName, String ftpPassword,
                                            int ftpPort, String ftpPath, String localPath) {
        FTPClient ftpClient = null;
        String fileName = null;
        try {
            ftpClient = getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            boolean change = ftpClient.changeWorkingDirectory(ftpPath);
            if (change) {
                FTPFile[] fileList = ftpClient.listFiles();
                File directory = new File(localPath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                for (int i = 0; i < fileList.length; i++) {
                    String localname = fileList[i].getName();
                    File localFile = new File(localPath + "/" + localname);
                    OutputStream fos = new FileOutputStream(localFile);
                    // ftpClient.setBufferSize(1024);
                    // ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                    ftpClient.retrieveFile(localname, fos);
                    fos.close();
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error("==========================从FTP下载文件夹出错==============", e);
            e.printStackTrace();
            return false;
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
    }

    public static Map<String, List<String>> getServerFileName(String ftpHost, String ftpUserName,
                                                              String ftpPassword, int ftpPort,
                                                              String path, String payType) {
        List<String> directorysList = new ArrayList<String>();// 文件目录
        Map<String, List<String>> fileNames = new HashMap<String, List<String>>();// 文件目录下的文件集合

        FTPClient ftpClient = null;
        try {
            ftpClient = FtpUtils.getFTPClient(ftpHost, ftpUserName, ftpPassword, ftpPort);
            // 指定要下载的目录
            ftpClient.changeWorkingDirectory(path);// 转移到FTP服务器目录
            ftpClient.enterLocalPassiveMode();
            // 遍历下载的目录
            FTPFile[] directorys = ftpClient.listFiles();
            for (FTPFile directory : directorys) {
                if (directory.isDirectory()) {
                    logger.info("文件夹名称为=====" + directory.getName());
                    directorysList.add(directory.getName());
                }
            }

            if (null != directorysList && directorysList.size() > 0) {
                for (String directory : directorysList) {
                    List<String> fileList = new ArrayList<String>();// 文件名集合
                    ftpClient.changeWorkingDirectory(path + directory);// 转移到FTP服务器目录
                    ftpClient.enterLocalPassiveMode();
                    FTPFile[] ftpFiles = ftpClient.listFiles();
                    for (FTPFile file : ftpFiles) {
                        if (file.isFile()) {
                            String fileType = file.getName().substring(
                                    file.getName().lastIndexOf("."), file.getName().length());
                            if (fileType.equals(".xml") && file.getName().startsWith(payType)) {
                                logger.info("文件名称为=====" + file.getName());
                                fileList.add(file.getName());
                            } else if (fileType.equals(".xml") && payType.equals("SF")) {
                                fileList.add(file.getName());
                            }
                        }
                    }
                    if (null != fileList && fileList.size() > 0) {
                        fileNames.put(directory, fileList);
                    }
                }
            }
            ftpClient.logout();
        } catch (Exception e) {
            logger.error("从FTP下载文件出错", e);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                }
            }
        }

        if (null != fileNames && fileNames.size() > 0) {
            Map<String, List<String>> fileNameNew = new HashMap<String, List<String>>();
            for (Map.Entry<String, List<String>> entry : fileNames.entrySet()) {
                List<String> fileList = entry.getValue();
                Collections.sort(fileList, String.CASE_INSENSITIVE_ORDER);
                fileNameNew.put(entry.getKey(), fileList);
            }
            return fileNameNew;
        }

        return null;
    }

}
