package com.automation.actions;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.automation.driver.DriverManager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * FileUploadActions - Specialized class for handling file upload operations
 */
@Slf4j
public class FileUploadActions {

    private static final WebDriver driver = DriverManager.getDriver();

    /**
     * Upload file using sendKeys (for input type=file)
     * This is the standard and fastest method
     *
     * @param fileInputLocator - File input element locator
     * @param filePath - Absolute file path to upload
     */
    public static void uploadFileBySendKeys(By fileInputLocator, String filePath) {
        try {
            log.info("Uploading file: {}", filePath);
            verifyFileExists(filePath);

            CommonActions.waitForElementPresence(fileInputLocator);
            WebElement fileInput = driver.findElement(fileInputLocator);

            // Send the file path to the input element
            fileInput.sendKeys(filePath);

            log.info("File uploaded successfully: {}", filePath);
        } catch (Exception e) {
            log.error("File upload failed: {}", e.getMessage());
            throw new RuntimeException("File upload failed", e);
        }
    }

    /**
     * Upload multiple files
     *
     * @param fileInputLocator - File input element locator
     * @param filePaths - List of absolute file paths
     */
    public static void uploadMultipleFiles(By fileInputLocator, String... filePaths) {
        try {
            log.info("Uploading {} files", filePaths.length);

            for (String filePath : filePaths) {
                verifyFileExists(filePath);
            }

            CommonActions.waitForElementPresence(fileInputLocator);
            WebElement fileInput = driver.findElement(fileInputLocator);

            // Send multiple file paths separated by newline (for multiple file inputs)
            String filePathsString = String.join("\n", filePaths);
            fileInput.sendKeys(filePathsString);

            log.info("Multiple files uploaded successfully");
        } catch (Exception e) {
            log.error("Multiple file upload failed: {}", e.getMessage());
            throw new RuntimeException("Multiple file upload failed", e);
        }
    }

    /**
     * Verify file exists before upload
     *
     * @param filePath - File path to verify
     */
    public static void verifyFileExists(String filePath) {
        try {
            log.debug("Verifying file exists: {}", filePath);
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                throw new FileNotFoundException("File not found: " + filePath);
            }
            if (!Files.isReadable(path)) {
                throw new IOException("File is not readable: " + filePath);
            }
            log.debug("File verified: {}", filePath);
        } catch (IOException e) {
            log.error("File verification failed: {}", e.getMessage());
            throw new RuntimeException("File verification failed", e);
        }
    }

    /**
     * Get file size
     *
     * @param filePath - File path
     * @return - File size in bytes
     */
    public static long getFileSize(String filePath) {
        try {
            log.debug("Getting file size: {}", filePath);
            Path path = Paths.get(filePath);
            long size = Files.size(path);
            log.info("File size: {} bytes", size);
            return size;
        } catch (IOException e) {
            log.error("Get file size failed: {}", e.getMessage());
            throw new RuntimeException("Get file size failed", e);
        }
    }

    /**
     * Create temporary test file
     *
     * @param fileName - File name
     * @param content - File content
     * @return - Absolute file path
     */
    public static String createTemporaryTestFile(String fileName, String content) {
        try {
            log.debug("Creating temporary test file: {}", fileName);
            Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));
            Path filePath = tempDir.resolve(fileName);
            Files.writeString(filePath, content);
            log.info("Temporary file created: {}", filePath);
            return filePath.toAbsolutePath().toString();
        } catch (IOException e) {
            log.error("Create temporary file failed: {}", e.getMessage());
            throw new RuntimeException("Create temporary file failed", e);
        }
    }

    /**
     * Create temporary CSV file for testing
     *
     * @param fileName - File name
     * @param csvContent - CSV content (rows separated by \n, columns by ,)
     * @return - Absolute file path
     */
    public static String createTemporaryCsvFile(String fileName, String csvContent) {
        try {
            log.debug("Creating temporary CSV file: {}", fileName);
            return createTemporaryTestFile(fileName, csvContent);
        } catch (Exception e) {
            log.error("Create CSV file failed: {}", e.getMessage());
            throw new RuntimeException("Create CSV file failed", e);
        }
    }

    /**
     * Create temporary text file
     *
     * @param fileName - File name
     * @param content - File content
     * @return - Absolute file path
     */
    public static String createTemporaryTextFile(String fileName, String content) {
        try {
            log.debug("Creating temporary text file: {}", fileName);
            return createTemporaryTestFile(fileName, content);
        } catch (Exception e) {
            log.error("Create text file failed: {}", e.getMessage());
            throw new RuntimeException("Create text file failed", e);
        }
    }

    /**
     * Delete file after test
     *
     * @param filePath - File path to delete
     */
    public static void deleteFile(String filePath) {
        try {
            log.debug("Deleting file: {}", filePath);
            Path path = Paths.get(filePath);
            if (Files.exists(path)) {
                Files.delete(path);
                log.info("File deleted: {}", filePath);
            }
        } catch (IOException e) {
            log.error("Delete file failed: {}", e.getMessage());
            // Don't throw exception for cleanup operations
        }
    }

    /**
     * Wait for file to be downloaded
     *
     * @param downloadDir - Download directory
     * @param fileName - Expected file name
     * @param timeoutSeconds - Timeout in seconds
     * @return - true if file downloaded, false if timeout
     */
    public static boolean waitForFileDownload(String downloadDir, String fileName, int timeoutSeconds) {
        try {
            log.info("Waiting for file download: {}", fileName);
            Path downloadPath = Paths.get(downloadDir, fileName);
            long startTime = System.currentTimeMillis();
            long timeout = timeoutSeconds * 1000L;

            while (System.currentTimeMillis() - startTime < timeout) {
                if (Files.exists(downloadPath) && Files.isRegularFile(downloadPath)) {
                    log.info("File downloaded successfully: {}", fileName);
                    return true;
                }
                Thread.sleep(500);
            }

            log.warn("File download timeout: {}", fileName);
            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("File download wait interrupted", e);
            return false;
        }
    }

    /**
     * Verify file was downloaded
     *
     * @param downloadDir - Download directory
     * @param fileName - File name to verify
     * @return - true if file exists and is readable
     */
    public static boolean isFileDownloaded(String downloadDir, String fileName) {
        try {
            log.debug("Verifying file download: {}", fileName);
            Path filePath = Paths.get(downloadDir, fileName);
            boolean exists = Files.exists(filePath) && Files.isRegularFile(filePath);
            if (exists) {
                log.info("File download verified: {}", fileName);
            } else {
                log.warn("File not found: {}", fileName);
            }
            return exists;
        } catch (Exception e) {
            log.error("File verification failed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Get list of files in directory
     *
     * @param directoryPath - Directory path
     * @return - Array of file names
     */
    public static String[] getFilesInDirectory(String directoryPath) {
        try {
            log.debug("Getting files in directory: {}", directoryPath);
            File directory = new File(directoryPath);
            if (!directory.isDirectory()) {
                throw new IllegalArgumentException("Path is not a directory: " + directoryPath);
            }
            File[] files = directory.listFiles();
            if (files == null) {
                return new String[0];
            }
            String[] fileNames = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                fileNames[i] = files[i].getName();
            }
            log.info("Found {} files in directory", fileNames.length);
            return fileNames;
        } catch (Exception e) {
            log.error("Get files failed: {}", e.getMessage());
            return new String[0];
        }
    }

    /**
     * Clear/empty directory
     *
     * @param directoryPath - Directory path to clear
     */
    public static void clearDirectory(String directoryPath) {
        try {
            log.info("Clearing directory: {}", directoryPath);
            File directory = new File(directoryPath);
            if (directory.isDirectory()) {
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile()) {
                            Files.delete(file.toPath());
                        }
                    }
                }
            }
            log.info("Directory cleared");
        } catch (IOException e) {
            log.error("Clear directory failed: {}", e.getMessage());
            throw new RuntimeException("Clear directory failed", e);
        }
    }

    /**
     * Upload file and wait for success message/element
     *
     * @param fileInputLocator - File input element locator
     * @param filePath - File path to upload
     * @param successElementLocator - Element that appears on successful upload
     */
    public static void uploadFileAndWaitForSuccess(By fileInputLocator, String filePath, By successElementLocator) {
        try {
            log.info("Uploading file and waiting for success");
            uploadFileBySendKeys(fileInputLocator, filePath);
            CommonActions.waitForElementVisible(successElementLocator, 10);
            log.info("File upload successful");
        } catch (Exception e) {
            log.error("File upload and success wait failed: {}", e.getMessage());
            throw new RuntimeException("File upload and success wait failed", e);
        }
    }

    /**
     * Get file extension
     *
     * @param filePath - File path
     * @return - File extension (without dot)
     */
    public static String getFileExtension(String filePath) {
        try {
            log.debug("Getting file extension: {}", filePath);
            Path path = Paths.get(filePath);
            String fileName = path.getFileName().toString();
            int dotIndex = fileName.lastIndexOf('.');
            if (dotIndex > 0) {
                return fileName.substring(dotIndex + 1);
            }
            return "";
        } catch (Exception e) {
            log.error("Get file extension failed: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Verify file has correct extension
     *
     * @param filePath - File path
     * @param expectedExtension - Expected extension (without dot)
     * @return - true if extension matches
     */
    public static boolean hasCorrectExtension(String filePath, String expectedExtension) {
        try {
            log.debug("Verifying file extension");
            String actualExtension = getFileExtension(filePath);
            boolean matches = actualExtension.equalsIgnoreCase(expectedExtension);
            log.info("Extension verification: {}", matches);
            return matches;
        } catch (Exception e) {
            log.error("Extension verification failed: {}", e.getMessage());
            return false;
        }
    }
}
