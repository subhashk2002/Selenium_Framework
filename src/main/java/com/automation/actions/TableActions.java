package com.automation.actions;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.automation.driver.DriverManager;

import java.util.*;
import java.util.stream.Collectors;

/**
 * TableActions - Specialized class for handling HTML table operations
 */
@Slf4j
public class TableActions {

    private static final WebDriver driver = DriverManager.getDriver();

    /**
     * Get row count from table
     *
     * @param tableLocator - Table locator
     * @param rowXpath - XPath for row elements (relative to table)
     * @return - Number of rows
     */
    public static int getTableRowCount(By tableLocator, String rowXpath) {
        try {
            log.debug("Getting table row count");
            WebElement table = driver.findElement(tableLocator);
            List<WebElement> rows = table.findElements(By.xpath(rowXpath));
            int rowCount = rows.size();
            log.info("Table has {} rows", rowCount);
            return rowCount;
        } catch (Exception e) {
            log.error("Get row count failed: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Get column count from table
     *
     * @param tableLocator - Table locator
     * @param cellXpath - XPath for cell elements in header
     * @return - Number of columns
     */
    public static int getTableColumnCount(By tableLocator, String cellXpath) {
        try {
            log.debug("Getting table column count");
            WebElement table = driver.findElement(tableLocator);
            List<WebElement> cells = table.findElements(By.xpath(cellXpath));
            int columnCount = cells.size();
            log.info("Table has {} columns", columnCount);
            return columnCount;
        } catch (Exception e) {
            log.error("Get column count failed: {}", e.getMessage());
            return 0;
        }
    }

    /**
     * Get cell value from table
     *
     * @param tableLocator - Table locator
     * @param rowIndex - Row index (1-based)
     * @param columnIndex - Column index (1-based)
     * @return - Cell text value
     */
    public static String getTableCellValue(By tableLocator, int rowIndex, int columnIndex) {
        try {
            log.debug("Getting table cell value at row {} col {}", rowIndex, columnIndex);
            WebElement table = driver.findElement(tableLocator);
            By cellLocator = By.xpath(".//tr[" + rowIndex + "]/td[" + columnIndex + "]");
            WebElement cell = table.findElement(cellLocator);
            String cellValue = cell.getText();
            log.info("Cell value: {}", cellValue);
            return cellValue;
        } catch (Exception e) {
            log.error("Get cell value failed: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Get entire row data from table
     *
     * @param tableLocator - Table locator
     * @param rowIndex - Row index (1-based)
     * @return - List of cell values in row
     */
    public static List<String> getTableRowData(By tableLocator, int rowIndex) {
        try {
            log.debug("Getting table row data for row {}", rowIndex);
            WebElement table = driver.findElement(tableLocator);
            By rowLocator = By.xpath(".//tr[" + rowIndex + "]/td");
            List<WebElement> cells = table.findElements(rowLocator);
            List<String> rowData = cells.stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
            log.info("Retrieved row with {} columns", rowData.size());
            return rowData;
        } catch (Exception e) {
            log.error("Get row data failed: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Get all table data as List of Maps (column_name -> value)
     *
     * @param tableLocator - Table locator
     * @param headerRowXpath - XPath for header row
     * @param dataRowXpath - XPath for data rows
     * @return - List of maps with table data
     */
    public static List<Map<String, String>> getTableDataAsMaps(By tableLocator, String headerRowXpath, String dataRowXpath) {
        try {
            log.debug("Getting table data as maps");
            WebElement table = driver.findElement(tableLocator);

            // Get headers
            List<WebElement> headerCells = table.findElements(By.xpath(headerRowXpath + "//th | " + headerRowXpath + "//td"));
            List<String> headers = headerCells.stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());

            // Get data rows
            List<WebElement> dataRows = table.findElements(By.xpath(dataRowXpath));
            List<Map<String, String>> tableData = new ArrayList<>();

            for (WebElement row : dataRows) {
                List<WebElement> cells = row.findElements(By.xpath(".//td"));
                Map<String, String> rowMap = new HashMap<>();

                for (int i = 0; i < headers.size() && i < cells.size(); i++) {
                    rowMap.put(headers.get(i), cells.get(i).getText());
                }
                tableData.add(rowMap);
            }

            log.info("Retrieved {} rows with {} columns", tableData.size(), headers.size());
            return tableData;
        } catch (Exception e) {
            log.error("Get table data as maps failed: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Search for text in table and get row index
     *
     * @param tableLocator - Table locator
     * @param searchText - Text to search for
     * @param rowXpath - XPath for rows
     * @return - Row index (1-based) or -1 if not found
     */
    public static int findRowIndexByText(By tableLocator, String searchText, String rowXpath) {
        try {
            log.debug("Searching for text in table: {}", searchText);
            WebElement table = driver.findElement(tableLocator);
            List<WebElement> rows = table.findElements(By.xpath(rowXpath));

            for (int i = 0; i < rows.size(); i++) {
                String rowText = rows.get(i).getText();
                if (rowText.contains(searchText)) {
                    log.info("Text found at row {}", i + 1);
                    return i + 1;
                }
            }
            log.warn("Text not found in table: {}", searchText);
            return -1;
        } catch (Exception e) {
            log.error("Search in table failed: {}", e.getMessage());
            return -1;
        }
    }

    /**
     * Click action link in table row
     *
     * @param tableLocator - Table locator
     * @param rowIndex - Row index (1-based)
     * @param actionXpath - XPath to action element (relative to row)
     */
    public static void clickTableRowAction(By tableLocator, int rowIndex, String actionXpath) {
        try {
            log.debug("Clicking action in table row {}", rowIndex);
            WebElement table = driver.findElement(tableLocator);
            By actionLocator = By.xpath(".//tr[" + rowIndex + "]" + actionXpath);
            WebElement actionElement = table.findElement(actionLocator);
            CommonActions.clickWithRetry(By.xpath(".//tr[" + rowIndex + "]" + actionXpath));
            log.info("Action clicked in row {}", rowIndex);
        } catch (Exception e) {
            log.error("Click table action failed: {}", e.getMessage());
            throw new RuntimeException("Click table action failed", e);
        }
    }

    /**
     * Get table headers
     *
     * @param tableLocator - Table locator
     * @param headerXpath - XPath for header cells
     * @return - List of header texts
     */
    public static List<String> getTableHeaders(By tableLocator, String headerXpath) {
        try {
            log.debug("Getting table headers");
            WebElement table = driver.findElement(tableLocator);
            List<WebElement> headers = table.findElements(By.xpath(headerXpath));
            List<String> headerTexts = headers.stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
            log.info("Retrieved {} headers", headerTexts.size());
            return headerTexts;
        } catch (Exception e) {
            log.error("Get headers failed: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Verify row exists in table
     *
     * @param tableLocator - Table locator
     * @param searchText - Text to search for
     * @param rowXpath - XPath for rows
     * @return - true if row exists, false otherwise
     */
    public static boolean doesRowExist(By tableLocator, String searchText, String rowXpath) {
        try {
            log.debug("Verifying row exists with text: {}", searchText);
            int rowIndex = findRowIndexByText(tableLocator, searchText, rowXpath);
            return rowIndex != -1;
        } catch (Exception e) {
            log.error("Row verification failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Get column data (all values in specific column)
     *
     * @param tableLocator - Table locator
     * @param columnIndex - Column index (1-based)
     * @param rowXpath - XPath for rows
     * @return - List of column values
     */
    public static List<String> getTableColumnData(By tableLocator, int columnIndex, String rowXpath) {
        try {
            log.debug("Getting table column {} data", columnIndex);
            WebElement table = driver.findElement(tableLocator);
            List<WebElement> rows = table.findElements(By.xpath(rowXpath));
            List<String> columnData = new ArrayList<>();

            for (WebElement row : rows) {
                By cellLocator = By.xpath(".//td[" + columnIndex + "]");
                try {
                    WebElement cell = row.findElement(cellLocator);
                    columnData.add(cell.getText());
                } catch (Exception e) {
                    log.warn("Cell not found in row");
                }
            }
            log.info("Retrieved {} values from column {}", columnData.size(), columnIndex);
            return columnData;
        } catch (Exception e) {
            log.error("Get column data failed: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Sort table column (verify sorting works)
     *
     * @param tableLocator - Table locator
     * @param columnHeaderLocator - Column header locator to click for sorting
     */
    public static void sortTableByColumn(By tableLocator, By columnHeaderLocator) {
        try {
            log.debug("Sorting table by column");
            CommonActions.clickWithRetry(columnHeaderLocator);
            CommonActions.pause(500); // Wait for sort animation
            log.info("Table sorted by column");
        } catch (Exception e) {
            log.error("Sort table failed: {}", e.getMessage());
            throw new RuntimeException("Sort table failed", e);
        }
    }

    /**
     * Verify table is sorted ascending
     *
     * @param columnData - List of column data to verify
     * @return - true if sorted ascending, false otherwise
     */
    public static boolean isTableSortedAscending(List<String> columnData) {
        try {
            log.debug("Verifying table sorted ascending");
            for (int i = 0; i < columnData.size() - 1; i++) {
                int comparison = columnData.get(i).compareTo(columnData.get(i + 1));
                if (comparison > 0) {
                    log.warn("Table not sorted ascending");
                    return false;
                }
            }
            log.info("Table is sorted ascending");
            return true;
        } catch (Exception e) {
            log.error("Sort verification failed: {}", e.getMessage());
            return false;
        }
    }
}
