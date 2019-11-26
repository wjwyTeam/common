package com.unis.common.utils;


import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**张新亮
 * Created with IntelliJ IDEA.
 * User: zhangxinliang
 * Date: 15-4-8
 * Time: 下午5:26
 * To change this template use File | Settings | File and Code Templates.
 */
public class POIUtil {


    public static String winPath="D:/upload/yn_resource/excel/";

    public static String linuxPath="/home/file/ftp/static/excel/";



    public static  void exportExcel(
            String fileName,
            String[] titles,
            List<Object[]> data,
            HttpServletResponse response
    ) {
        String excelName = DateUtil.DateToStr(new Date(), "yyyyMMddHHmmss");
        String path = null;
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {
            isFile(path);
            //上传文件路径
            path =winPath +excelName + ".xlsx";
        } else if (os.toLowerCase().startsWith("lin")) {
            isFile(path);
            path =linuxPath+excelName + ".xlsx";

        }

        System.out.println("excek保存路径" + path);
        SXSSFWorkbook workbook = null;
        Sheet sheet = null;
        try {
            System.out.println("开始写excel");
            workbook = new SXSSFWorkbook(500);
            System.out.println("开始");
            sheet = workbook.createSheet();
            workbook.setSheetName(0, fileName);
            Row row = sheet.createRow(0);
            //列宽
            sheet.setDefaultColumnWidth(40);
            //行高
            sheet.setDefaultRowHeightInPoints(16);
            //写表头
            initTitle(workbook, row, titles);
            //写内容
            initContentData(workbook, sheet, data, path);
            export(fileName, path, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("保存完excel");

    }

    //如果文件夹不存就创建
    private static void isFile(String path) {
        File file =new File(linuxPath);
        if(!file.exists()  && !file.isDirectory()){
            System.out.println("//不存在");
            file.mkdir();
        }
    }


    private static void initContentData(Workbook workbook, Sheet sheet, List<Object[]> data, String path) throws IOException {
        System.out.println("开始excel内容");
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path);
            //设置整型、字符串类型 的格式
            CellStyle style = workbook.createCellStyle();     //单元格样式
            style.setWrapText(true);//设置自动换行
            XSSFFont font = (XSSFFont) workbook.createFont();//创建字体对象
            font.setFontName("宋体");
            style.setFont(font);
            font.setFontHeightInPoints((short) 10);//设置字体大小
            style.setFont(font);    //设置字体
            style.setAlignment(HorizontalAlignment.CENTER); // 居中
            //设置日期 的格式
            CreationHelper creationHelper = workbook.getCreationHelper();
            CellStyle style1 = workbook.createCellStyle();
            style1.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
            style1.setFont(font);
            font.setFontHeightInPoints((short) 10);//设置字体大小
            style1.setFont(font);    //设置字体
            style1.setAlignment(HorizontalAlignment.CENTER); // 居中
            Integer rrNumber = 1;
            for (Object[] rowlist : data) {
                Row row = sheet.createRow(rrNumber);
                int colIndex = 0;
                for (Object col : rowlist) {
                    Cell cell = row.createCell(colIndex);
                    cell.setCellStyle(style);
                    if (col == null) {
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue("无");
                    } else if (col instanceof String) {
                        cell.setCellType(CellType.STRING);
                        cell.setCellValue(col.toString());
                    } else if (col instanceof Integer) {
                        cell.setCellType(CellType.NUMERIC);
                        cell.setCellValue((Integer) col);
                    } else if (col instanceof Long) {
                        cell.setCellType(CellType.NUMERIC);
                        cell.setCellValue((Long) col);
                    } else if (col instanceof Double) {
                        cell.setCellType(CellType.NUMERIC);
                        cell.setCellValue((Double) col);
                    } else if (col instanceof Date) {
                        cell.setCellValue((Date) col);
                        cell.setCellStyle(style1);
                    }
                    colIndex++;
                }
                rrNumber++;
            }
            workbook.write(out);
            System.out.println("写完excel内容");
            out.flush();
            out.close();
            System.gc();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


    /**
     * 写入表头
     *
     * @param
     * @param titles
     */
    private static void initTitle(Workbook workbook, Row row, String[] titles) {
        System.out.println("excel表头");
        if (titles != null && titles.length > 0) {
            CellStyle style = workbook.createCellStyle();     //单元格样式
            style.setWrapText(true);//设置自动换行
            XSSFFont font = (XSSFFont) workbook.createFont();//创建字体对象
            font.setFontName("微软雅黑");
            style.setFont(font);
            font.setFontHeightInPoints((short) 10);//设置字体大小
            style.setFont(font);    //设置字体
            style.setAlignment(HorizontalAlignment.CENTER); // 居中
            for (int i = 0; i < titles.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(style);
                cell.setCellValue(titles[i]);
            }
        }

    }


    /**
     * 下载文件
     * @param path
     * @date 2018/8/23
     * @return org.springframework.http.ResponseEntity<byte[]>
     */
    public static void export(String fileName,String path, HttpServletResponse response) throws IOException {
        // 设置文件名，根据业务需要替换成要下载的文件名
        //设置文件路径
        File file = new File(path);
        System.out.println("下载excel的路径 =========" + path);
        if (file.exists()) {
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            //解决中文乱码问题
            response.setHeader("Content-Disposition", "attachment; fileName=" + fileName + ";filename*=utf-8''" + URLEncoder.encode(fileName, "UTF-8")+".xlsx");

            try {
                InputStream myStream = new FileInputStream(path);
                IOUtils.copy(myStream, response.getOutputStream());
                response.flushBuffer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



//
//
//    //导出excel
//    public static void export(String fileName, String path, HttpServletResponse response) throws IOException {
//        System.out.println("下载excel的路径 =========" + path);
//        FileInputStream fis = null;
//        OutputStream os = null;
//        fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
//        try {
//            fis = new FileInputStream(path);
//            os = response.getOutputStream();// 取得输出流
//            response.reset();// 清空输出流
////            response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName + ".xlsx"));
//            response.addHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
//            response.setContentType("application/octet-stream");
//            response.setCharacterEncoding("UTF-8");
//            byte[] mybyte = new byte[8192];
//            int len = 0;
//            while ((len = fis.read(mybyte)) != -1) {
//                os.write(mybyte, 0, len);
//            }
//            os.flush();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//
//        //删除生成的excel文件
//        File file =new File(path);
//        file.delete();
//    }
//
//



}
