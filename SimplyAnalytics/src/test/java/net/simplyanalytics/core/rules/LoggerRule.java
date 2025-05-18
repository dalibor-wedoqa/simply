package net.simplyanalytics.core.rules;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import net.simplyanalytics.core.TestBase;

import org.apache.commons.io.FileUtils;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.MDC;

import io.qameta.allure.Attachment;

public class LoggerRule implements TestRule {
  
  @Override
  public Statement apply(Statement base, Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        String path = "logs" + File.separator
            + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            + File.separator + description.getClassName() + "." + description.getMethodName()
            + ".log";
        try {
          // clear log
          PrintWriter writer = new PrintWriter(new File(path));
          writer.print("");
          writer.close();
          
          MDC.put("methodName", description.getClassName() + "." + description.getMethodName());
          base.evaluate();
        } catch (Throwable e) {
          TestBase.logger.error(e.getMessage());
          throw e;
        } finally {
          MDC.remove("methodName");
          appendLogToAllure(new File(path));
        }
      }
    };
  }

  @Attachment(value = "log", type = "text/plain")
  private byte[] appendLogToAllure(File file) {
    try {
      return FileUtils.readFileToByteArray(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
