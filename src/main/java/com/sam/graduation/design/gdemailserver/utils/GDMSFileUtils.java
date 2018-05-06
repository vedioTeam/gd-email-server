/**
 *
 */
package com.sam.graduation.design.gdemailserver.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Paths;
import java.util.Calendar;

/**
 * 文件操作Util
 *
 * @author louxinhua
 */
public class GDMSFileUtils {


    private static final Logger logger = LoggerFactory.getLogger(GDMSFileUtils.class);

    /**
     * 移动上传的文件到itemPackageRootPath目录下面的特定目录
     *
     * @param srcFile             源目录
     * @param itemPackageRootPath 目标目录的根路径
     * @param userID              用户userid，用来生成用户目录
     * @return
     */
    public static String moveItemPackage(MultipartFile srcFile, String itemPackageRootPath, Long userID) {

        if (StringUtils.isBlank(itemPackageRootPath) || userID < 0) {
            logger.error("itemPackageRootPath is null, or userID < 0. itemPackageRootPath : {}, userID : {}", itemPackageRootPath, userID);
            return "";
        } else {

        }

        // FIXME:目前只支持zip，未来肯定需要支持rar

        // 根据时间生成子目录
        // 年/月/日/时/用户id， 前期先到时
        //
        // 文件名用 uuid 来生成

        String relPath = getTimePath() + SYSTEM_PATH_MARK + userID + SYSTEM_PATH_MARK;


        String fileName = UUIDUtil.getUUIDWithoutLine() + ".zip";

        // 目标
        File desFile = Paths.get(itemPackageRootPath, relPath, fileName).toFile();
        if (desFile != null && desFile.exists()) {
            FileUtils.deleteQuietly(desFile);
        } else {
        }

        String filePath = "";

        // 移动
        try {
            FileUtils.copyToFile(srcFile.getInputStream(), desFile);
            filePath = relPath + fileName;
        } catch (Exception e) {
            logger.error("move file error. getOriginalFilename:{}, desFile:{}, userID:{}",
                    srcFile.getOriginalFilename(), desFile.getPath(), userID);
        }
        return filePath;
    }

    /**
     * 移动文件方法
     *
     * @param file                       源文件
     * @param picRecognizeResultRootPath 根目录
     * @param userID                     userID
     * @return 图片路径字符串
     */
    public static String movePicRecognizeResult(MultipartFile file, String picRecognizeResultRootPath, Long userID) {
        // 判断根目录和用户id是否合法
        if (StringUtils.isBlank(picRecognizeResultRootPath) || userID < 0) {
            logger.error("文件根路径为空或者ID不正确");
            return "";
        }

        // 源目录
        String relPath = "itempackage" + SYSTEM_PATH_MARK + "pic" + SYSTEM_PATH_MARK + "recognize" + SYSTEM_PATH_MARK +
                "result" + SYSTEM_PATH_MARK + getTimePath() + SYSTEM_PATH_MARK + userID + SYSTEM_PATH_MARK;

        // 文件名
        String fileName = UUIDUtil.getUUIDWithoutLine() +
                file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), file.getOriginalFilename().length());

        // 目标文件
        File desFile = Paths.get(picRecognizeResultRootPath, relPath, fileName).toFile();
        // 判断目标文件是否为空
        if (desFile != null && desFile.exists()) {
            FileUtils.deleteQuietly(desFile);
        }

        // 文件路径
        String filePath = "";

        try {
            FileUtils.copyToFile(file.getInputStream(), desFile);
            // 复制成功则返回文件目录
            filePath = relPath + fileName;
        } catch (Exception e) {
            logger.error("文件移动错误，文件名：{}，目标文件：{}，用户id：{}。", file.getOriginalFilename(), desFile.getPath(), userID);
        }

        return filePath;
    }


    // 私有方法

    private static final String SYSTEM_PATH_MARK = File.separator;

    public static String getTimePath() {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);

        String path = String.format("%d%s%d%s%d%s%d", year, SYSTEM_PATH_MARK,
                month, SYSTEM_PATH_MARK, day, SYSTEM_PATH_MARK, hour);

        return path;
    }

    /**
     * 根据文件路径，逐行读取文件，并匹配
     *
     * @param filePath
     * @param start
     * @return
     * @author heixia
     * @date 2017年11月29日下午5:50:12
     */
    public static String getValueByKeyFromFile(String filePath, String start) {

        String str = "";
        File file = Paths.get(filePath).toFile();
        if (file == null || !file.exists() || file.isHidden() || file.isDirectory()) {
            return null;
        } else {
        }

        try {
            str = readFile(file, start);
        } catch (Exception e) {
            System.out.println("e:" + e);
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 逐行读取文件，并匹配
     *
     * @param file
     * @param start
     * @return
     * @throws IOException
     * @author heixia
     * @date 2017年11月29日下午5:48:21
     */
    public static String readFile(File file, String start) throws IOException {
        String str = "";
        FileInputStream inputStream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = "";
        while ((line = bufferedReader.readLine()) != null) {
            if (line.startsWith(start)) {
                return line;
            } else {
            }
        }
        inputStream.close();
        reader.close();
        bufferedReader.close();
        return str;
    }


    public static void downloadFile(String descFilePath, HttpServletResponse response) {

        File needDownLoadFile = Paths.get(descFilePath).toFile();

        // 设置HTTP响应头
        response.setHeader("content-type", "application/octet-stream");
        // 设置相应的文本内容
        response.setContentType("application/octet-stream");
        // 设置HTTP响应头
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(needDownLoadFile.getName().getBytes("UTF-8"), "ISO-8859-1"));
        } catch (Exception e) {
            logger.error("e:{}", e);
        }

        // 字节数组
        byte[] bytes = new byte[1024];
        BufferedInputStream bufferedInputStream = null;
        OutputStream outputStream = null;

        try {
            outputStream = response.getOutputStream();
            bufferedInputStream = new BufferedInputStream(new FileInputStream(needDownLoadFile));
            Integer i = bufferedInputStream.read(bytes);
            while (i != -1) {
                // 写入到输出流
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
                i = bufferedInputStream.read(bytes);
            }
        } catch (Exception e) {
            logger.error("e:{}.", e);
        } finally {
            // 关闭输出流
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("e:{}.", e);
                }
            }
            // 关闭缓冲流
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    logger.error("e:{}.", e);
                }
            }
        }
    }
}
