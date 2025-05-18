package net.simplyanalytics.pageobjects.sections.toolbar.timeseriesreport;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.views.TimeSeriesPage;
import net.simplyanalytics.pageobjects.sections.toolbar.ViewActionsMenu;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class TimeSeriesViewActionMenu extends ViewActionsMenu {

  @FindBy(xpath = ".//span[text()='Transpose Report']")
  private WebElement transposeReport;

  @FindBy(xpath = "(.//span[text()='Transpose Report'])[2]")
  private WebElement transposeReport1;

  public TimeSeriesViewActionMenu(WebDriver driver) {
    super(driver, ViewType.TIME_SERIES);
  }

  @Override
  public void isLoaded() {
    super.isLoaded();
    waitForElementToAppear(transposeReport, "Transpose Report button should appear");
  }

  /**
   * Click on the Transpose Report button.
   * 
   * @return ComparisonReportPage
   */
  @Step("Click on the Transpose Report button")
  public TimeSeriesPage clickTransposeReport() {
    logger.debug("Click on the Transpose Report button");
    transposeReport.click();
    return new TimeSeriesPage(driver);
  }
  @Step
  public TimeSeriesPage clickTransposeReport1() {
    logger.debug("Click on the Transpose Report button");
    transposeReport1.click();
    return new TimeSeriesPage(driver);
  }
}
