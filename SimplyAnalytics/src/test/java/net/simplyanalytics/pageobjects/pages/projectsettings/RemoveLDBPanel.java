package net.simplyanalytics.pageobjects.pages.projectsettings;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.pageobjects.sections.view.base.ViewPanel;
import io.qameta.allure.Step;

public class RemoveLDBPanel extends ViewPanel {
  
  @FindBy(css = "a[data-section='locations']")
  private WebElement locationsElement;
  
  @FindBy(css = "a[data-section='attributes']")
  private WebElement dataElement;
  
  @FindBy(css = "a[data-section='businessSearches']")
  private WebElement businessesElement;
  
  public RemoveLDBPanel(WebDriver driver) {
    super(driver);
  }
  
  public ManageProjectLdbPanel getLdbPanel() {
    return new ManageProjectLdbPanel(driver);
  }
  
  public ManageProjectLdbPanel getItemsPanel() {
    return new ManageProjectLdbPanel(driver);
  }
  
  /**
   * Click on the Locations button.
   * @return ManageProjectLdbPanel
   */
  @Step("Click on the Locations button")
  public ManageProjectLdbPanel clickLocations() {
    logger.debug("Click on the Locations button");
    locationsElement.click();
    return new ManageProjectLdbPanel(driver);
  }
  
  /**
   * Click on the Data button.
   * @return ManageProjectLdbPanel
   */
  @Step("Click on the Data button")
  public ManageProjectLdbPanel clickData() {
    logger.debug("Click on the Data button");
    dataElement.click();
    return new ManageProjectLdbPanel(driver);
  }
  
  /**
   * Click on the Businesses button.
   * @return ManageProjectLdbPanel
   */
  @Step("Click on the Businesses button")
  public ManageProjectLdbPanel clickBusinesses() {
    logger.debug("Click on the Businesses button");
    businessesElement.click();
    return new ManageProjectLdbPanel(driver);
  }
  

}
