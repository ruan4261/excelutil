package org.ruan.excelutil.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTMarker;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PictureUtil {

    /**
     * 03版本源文件
     *
     * @param fileInputStream
     * @param sheetIndex
     * @param signCol
     * @param rowMap
     * @return
     * @throws IOException
     */
    public static Map<Integer, PictureData> getPictureMap2003(FileInputStream fileInputStream, Integer sheetIndex, Integer signCol, Map rowMap, StringBuilder log) throws IOException {
        Map<Integer, PictureData> map = new HashMap<>();
        Workbook workbook;
        HSSFSheet sheet;

        // try
        workbook = new HSSFWorkbook(fileInputStream);
        sheet = (HSSFSheet) workbook.getSheetAt(sheetIndex);
        List<HSSFShape> list = sheet.getDrawingPatriarch().getChildren();
        for (HSSFShape shape : list) {
            if (shape instanceof HSSFPicture) {
                HSSFPicture picture = (HSSFPicture) shape;
                HSSFClientAnchor cAnchor = picture.getClientAnchor();
                if (map.get(cAnchor.getRow1()) != null) {
                    log.append("同行图片覆盖发生在->RowIndex:").append(cAnchor.getRow1()).append("，这可能是由于图片浮动引起的最高基准点定位失误，请手动检查。（ps:此条信息可能并不准确，也许实际上并没有发生覆盖，但由于某些未知原因会引发这个警告。）\n");
                }
                map.put(cAnchor.getRow1(), picture.getPictureData());
            }
        }

        for (Integer row : map.keySet()) {
            try {
                Cell cell = sheet.getRow(row).getCell(signCol);
                rowMap.put(row, cell.getStringCellValue());
            } catch (NullPointerException e) {
                e.printStackTrace();
                log.append("Index(Row:").append(row).append(",Col:").append(signCol).append(")发生NPE，无法获取，该行图片已被跳过，请手动导出。\n");
                map.remove(row);
                rowMap.remove(row);
            }
        }
        workbook.close();
        return map;
    }

    /**
     * 07版本源文件
     *
     * @param fileInputStream
     * @param sheetIndex
     * @param signCol
     * @param rowMap
     * @return
     * @throws IOException
     */
    public static Map<Integer, PictureData> getPictureMap2007(FileInputStream fileInputStream, Integer sheetIndex, Integer signCol, Map rowMap, StringBuilder log) throws IOException {
        Map<Integer, PictureData> map = new HashMap<>();
        Workbook workbook;
        XSSFSheet sheet;

        // try
        workbook = new XSSFWorkbook(fileInputStream);
        sheet = (XSSFSheet) workbook.getSheetAt(sheetIndex);
        List<POIXMLDocumentPart> list = sheet.getRelations();
        for (POIXMLDocumentPart part : list) {
            if (part instanceof XSSFDrawing) {
                XSSFDrawing draw = (XSSFDrawing) part;
                List<XSSFShape> shapes = draw.getShapes();
                for (XSSFShape shape : shapes) {
                    XSSFPicture picture = (XSSFPicture) shape;
                    XSSFClientAnchor anchor = ((XSSFPicture) shape).getClientAnchor();
                    CTMarker marker = anchor.getFrom();
                    if (map.get(marker.getRow()) != null) {
                        log.append("同行图片覆盖发生在->RowIndex:").append(marker.getRow()).append("，这可能是由于图片浮动引起的最高基准点定位失误，请手动检查。（ps:此条信息可能并不准确，也许实际上并没有发生覆盖，但由于某些未知原因会引发这个警告。）\n");
                    }
                    map.put(marker.getRow(), picture.getPictureData());
                }
            }
        }

        for (Integer row : map.keySet()) {
            try {
                Cell cell = sheet.getRow(row).getCell(signCol);
                rowMap.put(row, cell.getStringCellValue());
            } catch (NullPointerException e) {
                e.printStackTrace();
                log.append("Index(Row:").append(row).append(",Col:").append(signCol).append(")发生NPE，无法获取，该行图片已被跳过，请手动导出。\n");
                map.remove(row);
                rowMap.remove(row);
            }
        }
        workbook.close();
        return map;
    }

}
