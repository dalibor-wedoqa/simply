package net.simplyanalytics.core.rules;

import net.simplyanalytics.core.TestBase;

import org.joda.time.format.DateTimeFormat;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;

public class ConsoleLogRule implements TestRule {
  
  private TestBase testBase;
  
  public ConsoleLogRule(TestBase testBase) {
    super();
    this.testBase = testBase;
  }
  
  @Override
  public Statement apply(Statement base, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        try {
          base.evaluate();
          isInternerIssuePresent(testBase);
        } 
        catch (Throwable e) {
          e.printStackTrace();
          isInternerIssuePresent(testBase);
          throw e;
        }
      }
    };
  }
  
  private void isInternerIssuePresent(TestBase testBase) {
    Logs logs = testBase.getDriver().manage().logs();
    LogEntries logEntries = logs.get(LogType.BROWSER);
    if (!logEntries.getAll().isEmpty()) {
      for (LogEntry logEntry : logEntries) {
    	TestBase.logger.trace("Console output from browser:" );
        TestBase.logger.trace("JS: " + logEntry.getLevel() + " at: "
            + DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss,SSSS").print(logEntry.getTimestamp()) + " : "
            + logEntry.getMessage().replace("\n", ""));
        if (logEntry.toString().contains("ERR_")) {
          TestBase.logger.debug("Internet Issue was present!");
          testBase.setRetryFlagTo(true);
          return;
        } 
      }
    }
    TestBase.logger.warn("off");
    TestBase.logger.debug("Console error was not found");
    TestBase.logger.debug("Internet issue was not found");
    TestBase.logger.warn("on");
    //testBase.setRetryFlagTo(false);
  }

}
