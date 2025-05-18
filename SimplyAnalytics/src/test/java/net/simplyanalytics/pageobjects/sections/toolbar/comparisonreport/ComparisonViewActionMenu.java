package net.simplyanalytics.pageobjects.sections.toolbar.comparisonreport;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.sections.toolbar.ViewActionsMenu;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class ComparisonViewActionMenu extends ViewActionsMenu {

  @FindBy(xpath = ".//span[contains(@id, 'menu-hide-country')]")
  private WebElement addRemoveUsaFromReport;

  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Transpose Report']]")
  private WebElement transposeReport;

  public ComparisonViewActionMenu(WebDriver driver) {
    super(driver, ViewType.COMPARISON_REPORT);
  }

  @Override
  public void isLoaded() {
    super.isLoaded();
    waitForElementToAppear(addRemoveUsaFromReport, "Remove button should appear");
    waitForElementToAppear(transposeReport, "Transpose Report button should appear");
  }

  /**
   * Click on the Transpose Report button.
   * 
   * @return ComparisonReportPage
   */
  @Step("Click on the Transpose Report button")
  public ComparisonReportPage clickTransposeReport() {
    logger.debug("Click on the Transpose Report button");
    transposeReport.click();
    return new ComparisonReportPage(driver);
  }

  /**
   * Click on the Add/Remove USA button.
   */
  @Step("Click on the Add/Remove USA button")
  public void clickAddRemoveUsa() {
    logger.debug("Click on the Add/Remove USA button");
    addRemoveUsaFromReport.click();
  }

}
