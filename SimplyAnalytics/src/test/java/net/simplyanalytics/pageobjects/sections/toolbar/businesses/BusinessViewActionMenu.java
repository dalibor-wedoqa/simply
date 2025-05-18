package net.simplyanalytics.pageobjects.sections.toolbar.businesses;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.toolbar.ViewActionsMenu;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class BusinessViewActionMenu extends ViewActionsMenu {
  
  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Columns']]")
  private WebElement columnsViewButton;
  
  public BusinessViewActionMenu(WebDriver driver) {
    super(driver, ViewType.BUSINESSES);
  }
  
  @Override
  public void isLoaded() {
    super.isLoaded();
    waitForElementToAppear(columnsViewButton, "The columns button missing");
  }
  
  /**
   * Click on the Columns button.
   * @return BusinessColumnsPanel
   */
  @Step("Click on the Columns button")
  public BusinessColumnsPanel clickColumnButton() {
    logger.debug("Click on the Columns button");
    columnsViewButton.click();
    return new BusinessColumnsPanel(driver);
  }
}
