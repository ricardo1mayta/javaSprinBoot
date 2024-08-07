package pe.com.avivel.sistemas.guiaselectronicas.restapi.util.poi;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static pe.com.avivel.sistemas.guiaselectronicas.commons.util.Util.*;

@SuppressWarnings("unused")
@Slf4j
public class POIUtil {

    public static byte[] exportToByteArray(Map<Object, Object[]> data) {
        SXSSFWorkbook book = new SXSSFWorkbook();
        book.setCompressTempFiles(true);

        SXSSFSheet sheet = book.createSheet("Hoja");
        sheet.setRandomAccessWindowSize(100);

        Font defaultFont = book.createFont();
        defaultFont.setFontHeightInPoints((short) 10);
        defaultFont.setFontName("Arial");
        defaultFont.setColor(IndexedColors.BLACK.getIndex());
        defaultFont.setBold(false);
        defaultFont.setItalic(false);

        Font fontTitle = book.createFont();
        fontTitle.setFontHeightInPoints((short) 10);
        fontTitle.setFontName("Arial");
        fontTitle.setColor(IndexedColors.BLACK.getIndex());
        fontTitle.setBold(true);
        fontTitle.setItalic(false);

        if (data == null) {
            data = new TreeMap<>();
        }

        Set<Object> keyset = data.keySet();
        int rownum = 0;

        CellStyle defaultStyle = book.createCellStyle();
        CellStyle titleStyle = book.createCellStyle();

        for (Object key : keyset) {
            Row row = sheet.createRow(rownum);

            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);

                if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Integer) {
                    cell.setCellValue((Integer) obj);
                } else if (obj instanceof Double) {
                    cell.setCellValue((Double) obj);
                } else if (obj instanceof Timestamp) {
                    cell.setCellValue(dateToString((Date) obj, DEFAULT_DATETIME_WMS_FORMAT));
                } else if (obj instanceof Date) {
                    cell.setCellValue(dateToString((Date) obj, DEFAULT_DATE_FORMAT));
                } else if (obj instanceof BigDecimal) {
                    cell.setCellValue(getBigDecimal(obj).setScale(3, RoundingMode.HALF_UP).doubleValue());
                }  else if (obj instanceof BigInteger) {
                    cell.setCellValue(getBigInteger(obj).intValue());
                }

                if (rownum == 0) {
                    titleStyle.setFont(fontTitle);
                    titleStyle.setAlignment(HorizontalAlignment.CENTER);
                    cell.setCellStyle(titleStyle);
                } else {
                    defaultStyle.setFont(defaultFont);
                    defaultStyle.setWrapText(true);
                    cell.setCellStyle(defaultStyle);
                }
            }

            sheet.setDefaultColumnWidth(12);

            /*if (rownum == 1) {
                for (int i = 0; i < objArr.length; i++) {
                    sheet.autoSizeColumn(i);
                }
            }*/

            rownum++;
        }

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            book.write(bos);
            book.close();
            return bos.toByteArray();
        } catch (IOException e) {
            log.error("Error al exportar archivo xlsx", e);
            return null;
        }
    }
}