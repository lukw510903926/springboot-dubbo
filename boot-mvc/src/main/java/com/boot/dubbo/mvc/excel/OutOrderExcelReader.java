package com.boot.dubbo.mvc.excel;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : yangqi
 * @project : springboot-dubbo
 * @createTime : 2019-09-19 14:47
 * @email : lukewei@mockuai.com
 * @description : 外部订单读取
 */
@Slf4j
public class OutOrderExcelReader {

    /**
     * 07版本excel文件
     */
    private static final String XLSX = "xlsx";

    public static void main(String[] args) throws Exception {

        File file = new File("/Users/yangqi/Desktop/out_order.xlsx");
        readExcel(new FileInputStream(file), "out_order.xlsx");
    }

    public static void readExcel(InputStream inputStream, String fileName) throws Exception {

        boolean isE2007 = fileName.endsWith(XLSX);
        Workbook workbook = isE2007 ? new XSSFWorkbook(inputStream) : new HSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        int rowCount = sheet.getLastRowNum() + 1;
        int cellNum = sheet.getRow(0).getLastCellNum();
        List<List<String>> lists = new ArrayList<>(rowCount);
        List<String> list;
        for (int i = 1; i < rowCount; i++) {
            Row row;
            //合并行
            boolean mergedRow = false;
            list = new ArrayList<>(cellNum);
            for (int j = 0; j < cellNum; j++) {
                if (j == 0) {
                    mergedRow = isMergedRegion(sheet, i, j);
                }
                //合并单元格
                boolean mergedRegion = isMergedRegion(sheet, i, j);
                if (mergedRegion) {
                    list.add(getMergedRegionValue(sheet, i, j));
                } else {
                    row = sheet.getRow(i);
                    list.add(getCellValue(row.getCell(j)));
                }
            }
            if (mergedRow) {
                log.info("list : {}", list);
            }
            lists.add(list);
        }
        System.out.println(JSON.toJSONString(lists));
    }


    /**
     * 获取单元格的值
     *
     * @param cell
     * @return
     */
    private static String getCellValue(Cell cell) {

        if (cell == null) {
            return "";
        }
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue();
    }

    /**
     * 获取合并单元格的值
     *
     * @param sheet
     * @param row
     * @param column
     * @return
     */
    private static String getMergedRegionValue(Sheet sheet, int row, int column) {

        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell);
                }
            }
        }
        return "";
    }


    /**
     * 判断指定的单元格是否是合并单元格
     *
     * @param sheet
     * @param row    行下标
     * @param column 列下标
     * @return
     */
    private static boolean isMergedRegion(Sheet sheet, int row, int column) {

        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if (row >= firstRow && row <= lastRow) {
                if (column >= firstColumn && column <= lastColumn) {
                    return true;
                }
            }
        }
        return false;
    }

}
