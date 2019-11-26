package com.unis.common.utils;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 功能描述：文件上传方法类
 *
 * @author yangy
 */
@SuppressWarnings("restriction")
@Component
public class FilesUtils {

    /**
     * /home/ftpadmin/static/
     */
    
    private static String filesPath;
    
    private static String fileIp;
    
    @Value("${filesPath}")
	public static void setFilesPath(String filesPath) {
		FilesUtils.filesPath = filesPath;
	}
	@Value("${fileIp}")
	public static void setFileIp(String fileIp) {
		FilesUtils.fileIp = fileIp;
	}

    /**
     * 下载文件时，针对不同浏览器，进行附件名的编码
     *
     * @param filename
     *            下载文件名
     * @param agent
     *            客户端浏览器
     * @return 编码后的下载附件名
     * @throws IOException
     */
    public static String encodeDownloadFilename(String filename, String agent)
            throws IOException {
        if (agent.contains("Firefox")) { // 火狐浏览器
            filename = "=?UTF-8?B?"
                    + new BASE64Encoder().encode(filename.getBytes("utf-8"))
                    + "?=";
            filename = filename.replaceAll("\r\n", "");
        } else { // IE及其他浏览器
            filename = URLEncoder.encode(filename, "utf-8");
            filename = filename.replace("+"," ");
        }
        return filename;
    }

    /**
     * @param
     * @return java.lang.String
     * @author yangyh
     * @Description: 文件上传
     * @date 2018/6/28 18:01
     */
    public static String upload(MultipartFile multipartFile, String name) {
        if (multipartFile.isEmpty()) {
            return "文件为空。";
        }
        try {
            /*感觉没什么用都是上传到服务器，实现的效果一样，不过通过ftp可以在浏览器中浏览文件
            FTPClient ftp = new FTPClient();
            InputStream local = null;
            ftp.connect("192.168.80.76", 21);
            ftp.login("ftpadmin", "passwordftp");*/

            String dateStr = (new SimpleDateFormat("yyyyMMddHHmmssSSS")).format(new Date());
            String originalFilename = multipartFile.getOriginalFilename();
            String newFileName = dateStr + originalFilename.substring(originalFilename.lastIndexOf("."));
            if (StringUtils.isEmpty(name)) {
                name = name + "/";
            }
            //获取后缀名
            String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            File upload = null;
            String uploadPath = newFileName;

            if (suffix.equals("jpg") || suffix.equals("png")) {
                uploadPath =  "images/"+ name + newFileName;
                upload = new File(filesPath + "images/"+ name);
            } else if (suffix.equals("doc") || suffix.equals("docx") || suffix.equals("txt")) {
                uploadPath =  "text/" + name + newFileName;
                upload = new File(filesPath + "text/" + name);
            } else if (suffix.equals("xls") || suffix.equals("xlsx")) {
                uploadPath = "xlsx/" + name + newFileName;
                upload = new File(filesPath + "xlsx/" + name);
            } else if (suffix.equals("mp4")) {
                uploadPath = "video/" + name + newFileName;
                upload = new File(filesPath + "video/" + name);
            } else {
                uploadPath = "other/" + name + newFileName;
                upload = new File(filesPath + "other/" + name);
            }
            if (!upload.exists())
                upload.mkdirs();
            String finalPath = (upload + "\\" + newFileName).replaceAll("\\\\", "/");
            File file = new File(finalPath);
            multipartFile.transferTo(file);
            return uploadPath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @return java.lang.String
     * @Author yang yunhai
     * @Description: 多文件上传
     * @Date: 时间:2018/6/13 15:32
     * * @param files
     */
    public static String filesUpload(MultipartFile[] files, String name) {
        //判断file数组不能为空并且长度大于0
        try {
            if (files != null && files.length > 0) {
                //循环获取file数组中的文件
                for (int i = 0; i < files.length; i++) {
                    MultipartFile file = files[i];
                    //保存文件
                    upload(file, name);
                }
            }
            return "file upload success.";
        } catch (Exception e) {
            return "file upload errors.";
        }
    }

    /**
     * 下载文件
     * @param realPath
     * @date 2018/8/23
     * @return org.springframework.http.ResponseEntity<byte[]>
     */
    public static void downloadFile(String realPath, HttpServletResponse response) throws IOException {
        // 设置文件名，根据业务需要替换成要下载的文件名
        String fileName = realPath.substring(realPath.lastIndexOf("/") + 1);
        //设置文件路径
        String substring = realPath.substring(fileIp.length(), realPath.length());
        StringBuffer stringBuffer = new StringBuffer(filesPath);
        StringBuffer append = stringBuffer.append(substring);
        File file = new File(append.toString());
        if (file.exists()) {
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            //解决中文乱码问题
            response.setHeader("Content-Disposition", "attachment; fileName="+  fileName +";filename*=utf-8''"+ URLEncoder.encode(fileName,"UTF-8"));

            try {
                InputStream myStream = new FileInputStream(append.toString());
                IOUtils.copy(myStream, response.getOutputStream());
                response.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 缩略图生成工具
     * @param srcFile   源文件路径
     * @param descFile  生成后的缩略图路径
     * @param width     要生成的缩略图宽度
     * @param height    要生成的缩略图高度
     */
    public static void thumbnailForFile(String srcFile, String descFile, int width, int height) {
        try {
            Thumbnails.of(srcFile).size(width, height).toFile(descFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("缩略图生成失败！");
        }
    }




//    public MultipartFile getMulFile(File file, String filename) {
//        FileItem fileItem = createFileItem(file, filename);
//        MultipartFile mfile = new CommonsMultipartFile(fileItem);
//        return mfile;
//    }
//
//    public FileItem createFileItem(File file, String filename) {
//        String filePath = file.getPath();
//        FileItemFactory factory = new DiskFileItemFactory(16, null);
//        String textFieldName = "file";
////        int num = filePath.lastIndexOf(".");
////        String extFile = filePath.substring(num);
//        FileItem item = factory.createItem(textFieldName, "multipart/form-data", true, filename);
//        int bytesRead = 0;
//        byte[] buffer = new byte[8192];
//        try {
//            FileInputStream fis = new FileInputStream(file);
//            OutputStream os = item.getOutputStream();
//            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
//                os.write(buffer, 0, bytesRead);
//            }
//            os.close();
//            fis.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return item;
//    }

    public static MultipartFile getMultipartFile(String picPath) {
        FileItem fileItem = createFileItem(picPath);
        MultipartFile mfile = new CommonsMultipartFile(fileItem);
        return mfile;
    }

    private static FileItem createFileItem(String filePath) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        String textFieldName = "textField";
        int num = filePath.lastIndexOf(".");
        String extFile = filePath.substring(num);
        FileItem item = factory.createItem(textFieldName,"text/plain", true,"MyFileName" + extFile);
        File newfile = new File(filePath);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(newfile);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }

}
