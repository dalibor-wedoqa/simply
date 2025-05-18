package net.simplyanalytics.pageobjects.sections.toolbar.quickreport;

import net.simplyanalytics.enums.ReportContent;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.views.QuickReportPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class ReportContentComboElements extends BasePage {

  @FindBy(xpath = ".//*[.='Report Content:']/../a[1]")
  private WebElement dropdownElement;

  public ReportContentComboElements(WebDriver driver) {
    super(driver);
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(dropdownElement, "The dropdown is not opened");
  }

  /**
   * Click on report conent.
   * @param reportContent report conent
   * @return QuickReportPage
   */
  @Step("Click on report conent {0}")
  public QuickReportPage clickReportContent(ReportContent reportContent) {
    logger.debug("Click on report conent : " + reportContent.getName());
    WebElement button = dropdownElement.findElement(
        By.xpath("//span[normalize-space(text()) = '" + reportContent.getName() + "']/.."));
    waitForElementToAppear(button, "The dropdown button is not present");
    button.click();
    waitForElementToDisappear(By.cssSelector(".sa-load-mask"));
    return new QuickReportPage(driver);
  }
}
