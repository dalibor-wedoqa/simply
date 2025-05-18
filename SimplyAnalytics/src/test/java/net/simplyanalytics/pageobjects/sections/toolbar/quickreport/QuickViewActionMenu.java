package net.simplyanalytics.pageobjects.sections.toolbar.quickreport;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.toolbar.ViewActionsMenu;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class QuickViewActionMenu extends ViewActionsMenu {
  
  @FindBy(xpath = ".//span[contains(@id, 'menu-hide-country')]")
  private WebElement addRemoveUsaFromReport;
  
  public QuickViewActionMenu(WebDriver driver) {
    super(driver, ViewType.QUICK_REPORT);
  }
  
  @Override
  public void isLoaded() {
    super.isLoaded();
    waitForElementToAppear(addRemoveUsaFromReport, "Remove button should appear");
  }
  
  @Step("Click on the Add/Remove USA button")
  public void clickAddRemoveUsa() {
    logger.debug("Click on the Add/Remove USA button");
    addRemoveUsaFromReport.click();
  }
  
}
