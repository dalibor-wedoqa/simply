package net.simplyanalytics.core.rules;

import java.io.File;

import net.simplyanalytics.core.HeadlessIssue;
import net.simplyanalytics.core.TestBase;

import org.apache.commons.io.FileUtils;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KillDriverRule implements TestRule {
  
  private TestBase testBase;
  
  protected static Logger logger = LoggerFactory.getLogger(KillDriverRule.class);
  
  public KillDriverRule(TestBase testBase) {
    super();
    this.testBase = testBase;
  }
  
  @Override
  public Statement apply(final Statement base, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        try {
          boolean isDownloadTest = description.getMethodName().contains("Download");
          HeadlessIssue headlessIssue = (HeadlessIssue) testBase.getClass().getMethod(description.getMethodName().replaceAll("\\[.*\\]", "")).getAnnotation(HeadlessIssue.class);
          boolean headless = TestBase.getDriverConfiguration().isHeadless() && !isDownloadTest && headlessIssue == null;
          testBase.initDriver(headless);
          base.evaluate();
        } finally {
            try {
              File folder = new File(testBase.getDownloadFilePath());
              testBase.getDriver().quit();
              if (folder.exists()) {
                FileUtils.deleteDirectory(folder);
              }
            } catch (NullPointerException e) {
                logger.debug("Downloaded file path is not found");
            }
        }
      }
      
    };
  }
  
}
