package com.automation.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Slf4j
public class ExcelDataReader {
    private final Workbook workbook;

    public ExcelDataReader(String filePath) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            this.workbook = new XSSFWorkbook(fileInputStream);
            log.info("Excel file loaded successfully: {}", filePath);
        } catch (IOException e) {
            log.error("Failed to load Excel file: {}", filePath, e);
            throw e;
        }
    }

    public List<Map<String, String>> getDataFromSheet(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new RuntimeException("Sheet not found: " + sheetName);
        }

        List<Map<String, String>> dataList = new ArrayList<>();
        Iterator<Row> rowIterator = sheet.iterator();

        if (!rowIterator.hasNext()) {
            log.warn("Sheet is empty: {}", sheetName);
            return dataList;
        }

        Row headerRow = rowIterator.next();
        List<String> headers = extractHeaders(headerRow);

        while (rowIterator.hasNext()) {
            Row dataRow = rowIterator.next();
            Map<String, String> map = new HashMap<>();

            for (int i = 0; i < headers.size(); i++) {
                Cell cell = dataRow.getCell(i);
                String value = cell != null ? getCellValue(cell) : "";
                map.put(headers.get(i), value);
            }
            dataList.add(map);
        }

        log.info("Loaded {} rows from sheet: {}", dataList.size(), sheetName);
        return dataList;
    }

    public List<Map<String, String>> getDataFromSheet(int sheetIndex) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        return getDataFromSheet(sheet.getSheetName());
    }

    public int getSheetCount() {
        return workbook.getNumberOfSheets();
    }

    public List<String> getSheetNames() {
        List<String> names = new ArrayList<>();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            names.add(workbook.getSheetName(i));
        }
        return names;
    }

    public String getCellValue(int sheetIndex, int rowNum, int colNum) {
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            return "";
        }

        Cell cell = row.getCell(colNum);
        return cell != null ? getCellValue(cell) : "";
    }

    public int getRowCount(String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new RuntimeException("Sheet not found: " + sheetName);
        }
        return sheet.getPhysicalNumberOfRows();
    }

    private List<String> extractHeaders(Row headerRow) {
        List<String> headers = new ArrayList<>();
        for (Cell cell : headerRow) {
            headers.add(getCellValue(cell));
        }
        return headers;
    }

    private String getCellValue(Cell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    yield cell.getDateCellValue().toString();
                } else {
                    yield String.valueOf((int) cell.getNumericCellValue());
                }
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "";
        };
    }

    public void close() throws IOException {
        workbook.close();
        log.info("Excel workbook closed");
    }
}
