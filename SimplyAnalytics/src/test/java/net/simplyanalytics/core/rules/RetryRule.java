package net.simplyanalytics.core.rules;

import net.simplyanalytics.core.TestBase;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class RetryRule implements TestRule {
  private int retryCount;
  private TestBase testBase;
  
  public RetryRule(int retryCount, TestBase testBase) {
    this.retryCount = retryCount;
    this.testBase = testBase;
  }
  
  public Statement apply(Statement base, Description description) {
    return statement(base, description);
  }
  
  private Statement statement(final Statement base, final Description description) {
    return new Statement() {
      @Override
      public void evaluate() throws Throwable {
        Throwable caughtThrowable = null;
        
        // implement retry logic here
        for (int i = 0; i <= retryCount; i++) {
          try {
            base.evaluate();
            return;
          } catch (Throwable t) {
            if (!testBase.getRetryFlag()) {
              i = retryCount;
            }
            TestBase.logger.debug("The test failed, retry test run");
            testBase.setRetryFlagTo(false);
            caughtThrowable = t;
          }
        }
        throw caughtThrowable;
      }
    };
  }
}
