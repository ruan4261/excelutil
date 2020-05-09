package org.ruan.excelutil.service.impl;

import org.apache.poi.ss.usermodel.PictureData;
import org.ruan.excelutil.service.PictureService;
import org.ruan.excelutil.util.FileOutputUtil;
import org.ruan.excelutil.util.PictureUtil;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class PictureServiceImpl implements PictureService {

    @Override
    public String savePicture(String originFilePath, Integer sheetIndex, Integer nameCol, String savePath, Integer ver) {
        StringBuilder result = new StringBuilder();
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            // 输入流
            fileInputStream = new FileInputStream(originFilePath);
            Map<Integer, String> nameMap = new HashMap<>();
            Map<Integer, PictureData> map = null;
            if (ver == 7) {
                map = PictureUtil.getPictureMap2007(fileInputStream, sheetIndex, nameCol, nameMap);
            } else if (ver == 3) {
                map = PictureUtil.getPictureMap2003(fileInputStream, sheetIndex, nameCol, nameMap);
            }

            // 输出流
            if (savePath.charAt(savePath.length() - 1) != '/' && savePath.charAt(savePath.length() - 1) != '\\') {
                savePath += "/";
            }

            File folder = new File(savePath);
            if (!folder.exists() || !folder.isDirectory()) {
                if (folder.mkdirs())
                    result.append("目标文件夹不存在，已自动创建。").append("\n");
                else {
                    return result.append("目标文件夹不存在，且自动创建失败，已强制退出。").append("\n").toString();
                }
            }

            // 每张图片都创建流
            for (Map.Entry entry : map.entrySet()) {
                fileOutputStream = new FileOutputStream(savePath + nameMap.get(entry.getKey()) + ".png");
                FileOutputUtil.upload(fileOutputStream, ((PictureData) entry.getValue()).getData());
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    result.append(e.getMessage()).append("\n");
                }
            }

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return result.append(e.getMessage()).append("\n").toString();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    result.append(e.getMessage()).append("\n");
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    result.append(e.getMessage()).append("\n");
                }
            }
        }
        return result.append("Result->操作已完成。").toString();
    }

}
