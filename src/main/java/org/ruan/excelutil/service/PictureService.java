package org.ruan.excelutil.service;

import java.io.IOException;

public interface PictureService {

    /**
     * 保存xls文件中的图片
     * 图片名根据指定列取
     * 格式png
     *
     * @param originFilePath xlsx源文件（绝对路径）
     * @param sheetIndex     表定位
     * @param nameCol        作为唯一名称的列（不唯一将进行覆盖）
     * @param savePath       保存的文件夹（绝对路径）
     * @param ver            版本
     * @return
     */
    public String savePicture(String originFilePath, Integer sheetIndex, Integer nameCol, String savePath, Integer ver);

}
