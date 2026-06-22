package com.automation.utils;

import io.qameta.allure.Allure;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.automation.driver.DriverManager;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class ScreenshotHelper {
    private static final String SCREENSHOT_DIR = "target/screenshots";

    static {
        new File(SCREENSHOT_DIR).mkdirs();
    }

    public static String captureScreenshot(String filename) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) DriverManager.getDriver();
            byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);

            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String filepath = SCREENSHOT_DIR + "/" + filename + "_" + timestamp + ".png";

            Files.write(Paths.get(filepath), screenshotBytes);
            log.info("Screenshot saved: {}", filepath);

            Allure.addAttachment("Screenshot - " + filename, "image/png",
                    new ByteArrayInputStream(screenshotBytes), "png");

            return filepath;
        } catch (IOException | NullPointerException e) {
            log.error("Failed to capture screenshot", e);
            return null;
        }
    }

    public static String captureScreenshot() {
        return captureScreenshot("screenshot");
    }

    public static void attachScreenshotToAllure(String filename) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) DriverManager.getDriver();
            byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(filename, "image/png",
                    new ByteArrayInputStream(screenshotBytes), "png");
            log.info("Screenshot attached to Allure: {}", filename);
        } catch (Exception e) {
            log.error("Failed to attach screenshot to Allure", e);
        }
    }
}
