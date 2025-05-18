package net.simplyanalytics.pageobjects.pages.projectsettings;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.sections.view.base.ViewPanel;
import net.simplyanalytics.pageobjects.windows.DeleteProjectWindow;
import io.qameta.allure.Step;

public class GeneralSettingsPanel extends ViewPanel {
  
  @FindBy(css = ".sa-general-settings-delete-button")
  private WebElement deleteProjectButton;
  
  public GeneralSettingsPanel(WebDriver driver) {
    super(driver);
  }
  
  @Step("Click on Delete Project button")
  public DeleteProjectWindow clickDeleteProject() {
    logger.debug("Click on the Delete Project button");
    deleteProjectButton.click();
    return new DeleteProjectWindow(driver);
  }
  
  public EnableGeographicUnitsPanel getEnableGeographicUnitsPanel() {
    return new EnableGeographicUnitsPanel(driver);
  }
  
  public EnableHistoricalViewsPanel getEnableHistoricalViewsPanel() {
    return new EnableHistoricalViewsPanel(driver); 
  }
  
  public class EnableGeographicUnitsPanel extends BasePage {
    
    @FindBy(xpath = ".//div[contains(@class, 'sa-enable-geographic-units-optional-section-list-item')][.//span[contains(normalize-space(.),'Regions')]]")
    private WebElement regions;
    
    @FindBy(xpath = ".//div[contains(@class, 'sa-enable-geographic-units-optional-section-list-item')][.//span[contains(normalize-space(.),'Divisions')]]")
    private WebElement divisions;
    
    @FindBy(xpath = ".//div[contains(@class, 'sa-enable-geographic-units-optional-section-list-item')][.//span[contains(normalize-space(.),'Nielsen Designated Marketing Area')]]")
    private WebElement dMA;
    
    @FindBy(xpath = ".//div[contains(@class, 'sa-enable-geographic-units-optional-section-list-item')][.//span[contains(normalize-space(.),'Core-based Statistical Areas')]]")
    private WebElement coreBasedStatisticalAreas;
    
    @FindBy(xpath = ".//div[contains(@class, 'sa-enable-geographic-units-optional-section-list-item')][.//span[contains(normalize-space(.),'Congress. Dist.')]]")
    private WebElement congressDistricts;
    
    @FindBy(xpath = ".//div[contains(@class, 'sa-enable-geographic-units-optional-section-list-item')][.//span[contains(normalize-space(.),'State Upper Districts')]]")
    private WebElement stateUpperDistricts;
    
    @FindBy(xpath = ".//div[contains(@class, 'sa-enable-geographic-units-optional-section-list-item')][.//span[contains(normalize-space(.),'State Lower Districts')]]")
    private WebElement stateLowerDistricts;
    
    @FindBy(xpath = ".//div[contains(@class, 'sa-enable-geographic-units-optional-section-list-item')][.//span[contains(normalize-space(.),'Secondary School Districts')]]")
    private WebElement secondarySchoolDistricts;
    
    @FindBy(xpath = ".//div[contains(@class, 'sa-enable-geographic-units-optional-section-list-item')][.//span[contains(normalize-space(.),'Elementary School Districts')]]")
    private WebElement elementarySchoolDistricts;
    
    public EnableGeographicUnitsPanel(WebDriver driver) {
      super(driver);
    }
    
    @Override
    protected void isLoaded() {
      waitForElementToAppear(regions, "Regions geographical unit should appear");
      waitForElementToAppear(divisions, "Divisions geographical unit should appear");
      waitForElementToAppear(dMA, "Nielsen Designated Marketing Area geographical unit should appear");
      waitForElementToAppear(coreBasedStatisticalAreas, "Core-based Statistical Areas geographical unit should appear");
      waitForElementToAppear(congressDistricts, "Congress Dist. geographical unit should appear");
      waitForElementToAppear(stateUpperDistricts, "State Upper Districts geographical unit should appear");
      waitForElementToAppear(stateLowerDistricts, "States Lower Districts geographical unit should appear");
      waitForElementToAppear(secondarySchoolDistricts, "Secondary School Districts geographical unit should appear");
      waitForElementToAppear(elementarySchoolDistricts, "Elementary School Districts geographical unit should appear");
    }
    
    @Step("Click on checkbox")
    public void clickCheckbox(LocationType geographicalUnit) {
      logger.debug("Click " + geographicalUnit.getPluralName() + " checkbox");
      waitForElementToBeClickable(driver.findElement(By.xpath(".//div[contains(@class, 'sa-enable-geographic-units-optional-section-list-item')]"
          + "[.//span[contains(normalize-space(.),'" + geographicalUnit.getPluralName() + "')]]//span[contains(@class,'sa-check-field-button')]")), "Checkbox is not selectable").click();
    }
    
    public boolean isCheckboxSelected(LocationType geographicalUnit) {
      return driver.findElement(By.xpath(".//div[contains(@class, 'sa-enable-geographic-units-optional-section-list-item')]"
          + "[.//span[contains(normalize-space(.),'" + geographicalUnit.getPluralName() + "')]]")).getAttribute("class").contains("sa-checkbox-checked");
    }
  }
  
  public class EnableHistoricalViewsPanel extends BasePage {
    
    @FindBy(css = ".sa-enable-historical-views-toggle")
    private WebElement historicalViewsToggleRoot;
    
    public EnableHistoricalViewsPanel(WebDriver driver) {
      super(driver);
    }
    
    @Override
    protected void isLoaded() {
      waitForElementToAppear(historicalViewsToggleRoot, "Historical Views Toggle button should appear");
    }
    
    @Step("Click on the Historical Views toggle button")
    public void switchHistoricalViewsToggle() {
      logger.debug("Swtich Historical Views toggle button");
      waitForElementToBeClickable(historicalViewsToggleRoot.findElement(By.cssSelector(".sa-toggle-switch-button")), "Switch toggle button is not clickable").click();
    }
    
    public boolean isHistoricalViewsToggleChecked() {
      return historicalViewsToggleRoot.getAttribute("class").contains("sa-toggle-switch-checked");
    }
  }
  
}
