package com.boot.dubbo.mvc.excel;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author : yangqi
 * @email : lukewei@mockuai.com
 * @description : 修改单元格样式
 * @since : 2019/9/23 9:55 下午
 */
public class StyleExcelHandler implements CellWriteHandler {

    private CellStyle cellStyle;

    /**
     * 实际中如果直接获取原单元格的样式进行修改, 最后发现是改了整行的样式, 因此这里是新建一个样* 式
     */
    private CellStyle createStyle(Workbook workbook) {
        if (this.cellStyle == null) {
            this.cellStyle = workbook.createCellStyle();
            this.cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            this.cellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        }
        return this.cellStyle;
    }

    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {

    }

    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

        int rowIndex = cell.getRowIndex();
        int columnIndex = cell.getColumnIndex();
        final Workbook workbook = writeSheetHolder.getParentWriteWorkbookHolder().getWorkbook();
        if (columnIndex == 0 && rowIndex % 2 == 0 && !isHead) {
            CellStyle style = this.createStyle(workbook);
            cell.setCellStyle(style);
        }
    }

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {

    }
}