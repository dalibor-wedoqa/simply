package net.simplyanalytics.core.rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import net.simplyanalytics.core.TestBase;

public class SessionFailureRule implements TestRule {
  
  private TestBase testBase;
  
  public SessionFailureRule(TestBase testBase) {
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
        } 
        catch (Throwable e) {
          TestBase.logger.error(e.getMessage());
          if (e.getMessage().contains("unable to discover open pages") || e.getMessage().contains("session not created")) {
            TestBase.logger.debug("WebDriver exception was present");
            testBase.setRetryFlagTo(true);
          }
          throw e;
        }
      }
    };
  }
}
