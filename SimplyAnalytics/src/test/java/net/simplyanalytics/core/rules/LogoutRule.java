package net.simplyanalytics.core.rules;

import net.simplyanalytics.core.TestBase;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.sections.header.Header;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.By;

public class LogoutRule implements TestRule {
  
  private TestBase testBase;
  
  public LogoutRule(TestBase testBase) {
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
        } finally {
          try {
            if (BasePage.isPresent(Header.class, testBase.getDriver())) {
              Header header = new Header(testBase.getDriver());
              header.clickUser().clickSignOut();
              testBase.getDriver().findElement(By.cssSelector(".vc_btn3-container a[title]"));
            }
          } catch (Throwable e) {
            TestBase.logger.trace("Logout failed after the test");
          }
        }
      }
      
    };
  }
  
}
