package net.simplyanalytics.pageobjects.pages.projectsettings;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.pageobjects.base.BasePage;
import io.qameta.allure.Step;

public class ProjectSettingsHeader extends BasePage {
    
  @FindBy(xpath = ".//a[contains(normalize-space(.), 'General Settings')]")
  private WebElement generalSettingButton;
  
  @FindBy(xpath = ".//a[contains(normalize-space(.), 'Manage Views')]")
  private WebElement manageViewsButton;
  
  @FindBy(xpath = ".//a[contains(normalize-space(.), 'Remove Locations, Data, or Businesses')]")
  private WebElement removeLDBButton;
  
  public ProjectSettingsHeader(WebDriver driver) {
    super(driver);
  }
  
  @Override
  protected void isLoaded() {

  }

  @Step("Click on the General Settings button")
  public GeneralSettingsPanel clickGeneralSettingsButton() {
    logger.debug("Click on the General Settings Button");
    waitForElementToBeClickable(generalSettingButton, "General Setting Button is not clickable").click();
    return new GeneralSettingsPanel(driver);
  }
  
  @Step("Click on the Manage Views button")
  public ManageViewsPanel clickManageViewsButton() {
    logger.debug("Click on the Manage Views Button");
    waitForElementToBeClickable(manageViewsButton, "Manage Views Button is not clickable").click();
    return new ManageViewsPanel(driver);
  }
  
  @Step("Click on the Remove LDS button")
  public RemoveLDBPanel clickRemoveLDBButton() {
    logger.debug("Click on the Remove LDB Button");
    waitForElementToBeClickable(removeLDBButton, "Remove LDB Button is not clickable").click();
    return new RemoveLDBPanel(driver);
  }
  
  public boolean isGeneralSettingsSelected() {
    return generalSettingButton.getAttribute("class").contains("sa-project-settings-header-link-selected");
  }
  
  public boolean isManageViewsSelected() {
    return manageViewsButton.getAttribute("class").contains("sa-project-settings-header-link-selected");
  }
  
  public boolean isRemoveLDBSelected() {
    return removeLDBButton.getAttribute("class").contains("sa-project-settings-header-link-selected");
  }
  
}
