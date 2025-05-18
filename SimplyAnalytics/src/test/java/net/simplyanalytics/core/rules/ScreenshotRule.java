package net.simplyanalytics.core.rules;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import net.simplyanalytics.core.TestBase;

import org.apache.commons.io.FileUtils;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;

import io.qameta.allure.Attachment;

public class ScreenshotRule implements TestRule {
  
  private TestBase testBase;
  
  public ScreenshotRule(TestBase testBase) {
    super();
    this.testBase = testBase;
  }
  
  @Override
  public Statement apply(final Statement base, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        try {
          base.evaluate();
        } catch (Throwable t) {
          captureScreenshot(testBase.getClass().getSimpleName(), description.getMethodName(),
              testBase);
          captureAllureScreenshot(testBase);
          throw t; // rethrow to allow the failure to be reported to JUnit
        }
      }
      
      @Attachment(value = "Screenshot on failure", type = "image/png")
      private byte[] captureAllureScreenshot(TestBase testBase) {
        return ((TakesScreenshot) testBase.getDriver()).getScreenshotAs(OutputType.BYTES);
      }
      
      public void captureScreenshot(String className, String fileName, TestBase testBase) {
        try {
          File screenshot;
          TestBase.logger.debug("FAILURE TIME: " + LocalDateTime.now(ZoneOffset.UTC).toString());
          //retry all failed test cases
          testBase.setRetryFlagTo(true);
          screenshot = ((TakesScreenshot) testBase.getDriver()).getScreenshotAs(OutputType.FILE);
          FileUtils.copyFile(screenshot, new File("." + File.separator + "screenshots"
              + File.separator + className + "-" + fileName + ".png"));
        } catch (IOException | WebDriverException e) {
          e.printStackTrace();
        }
      }
      
    };
  }
  
}
