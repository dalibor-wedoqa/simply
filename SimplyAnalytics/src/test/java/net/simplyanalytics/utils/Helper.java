package net.simplyanalytics.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class Helper {

    // Method to time implicit wait for a non-existent element using findElements
    public static long measureImplicitWaitTime(WebDriver driver) {
            Instant start = Instant.now();
            List<WebElement> elements = driver.findElements(By.cssSelector("bogusSelector"));
            Instant end = Instant.now();
            return Duration.between(start, end).toMillis();
    }
}