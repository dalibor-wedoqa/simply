package net.simplyanalytics.pageobjects.sections.toolbar.histogram;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.export.ExportWindow;
import net.simplyanalytics.pageobjects.pages.main.export.quickexport.QuickExportWindow;
import net.simplyanalytics.pageobjects.sections.toolbar.BaseViewToolbar;
import net.simplyanalytics.pageobjects.sections.toolbar.DataFilterDropdown;
import net.simplyanalytics.pageobjects.sections.toolbar.DataVariableDropdown;
import io.qameta.allure.Step;

public class HistogramToolbar extends BaseViewToolbar {
  
  @FindBy(css = "a.sa-menu-button:nth-child(1) span.sa-button-text")
  private WebElement dataVariableSelector;
  
  @FindBy(css = "a.sa-menu-button:nth-child(3) span.sa-button-text")
  private WebElement sortingSelector;
  
  @FindBy(css = ".sa-button-arrow span.sa-button-text") 
  private WebElement activeDataVariable;
  
  
  public HistogramToolbar(WebDriver driver, ViewType nextViewType) {
    super(driver, ViewType.HISTOGRAM, nextViewType);
  }
  
  @Override
  public void isLoaded() {
    super.isLoaded();
  }
  
  public DataVariableDropdown openDataVariableMenu() {
    logger.debug("Open data variable menu");
    activeDataVariable.click();
    return new DataVariableDropdown(driver, ViewType.HISTOGRAM);
  }
  
  public SortingMenu openSortingMenu() {
    logger.debug("Open sort menu");
    waitForElementToBeClickable(sortingSelector, "Sorting menu is not clickable").click();
    return new SortingMenu(driver);
  }
  
  public String getActiveSortedByOption() {
    return sortingSelector.getText().trim();
  }
  
  public class SortingMenu extends DataFilterDropdown {
    
    @FindBy(xpath = ".//div[contains(@class, 'sa-menu-button-menu')]//a[.//span[normalize-space(.)='Zip Codes']]")
    private WebElement zipCodesSelector;
    
    @FindBy(xpath = ".//div[contains(@class, 'sa-menu-button-menu')]//a[.//span[normalize-space(.)='Census Tracts']]")
    private WebElement censusTractsSelector;
    
    @FindBy(xpath = ".//div[contains(@class, 'sa-menu-button-menu')]//a[.//span[normalize-space(.)='Block Groups']]")
    private WebElement blockGroupsSelector;
    
    public SortingMenu(WebDriver driver) {
      super(driver);
    }
    
    @Override
    public void isLoaded() {
      waitForElementToAppear(zipCodesSelector, "Zip Codes option is not present");
      waitForElementToAppear(censusTractsSelector, "Census Tracts option is not present");
      waitForElementToAppear(blockGroupsSelector, "Block Groups option is not present");
    }
    
    @Step("Click on the Zip Codes option")
    public void clickSortByZipCodes() {
      logger.debug("Click on the Zip Codes option");
      waitForElementToBeClickable(zipCodesSelector, "Zip Codes option is not clickable").click();
    }
    
    @Step("Click on the Census Tracts option")
    public void clickSortByCensusTracts() {
      logger.debug("Click on the Census Tracts option");
      waitForElementToBeClickable(censusTractsSelector, "Census Tracts option is not clickable").click();
    }
    
    @Step("Click on the Block Groups option")
    public void clickSortByBlockGroups() {
      logger.debug("Click on the Block Groups option");
      waitForElementToBeClickable(blockGroupsSelector, "Block Groups option is not clickable").click();
    }
  }
  
  @Step("Click on the Export button")
  @Override
  public QuickExportWindow clickExport() {
    return (QuickExportWindow) clickExportButton(ViewType.HISTOGRAM);
  }
  
  public DataVariable getActiveDataVariable() {
    return DataVariable.getByFullName(activeDataVariable.getText());
  }
  
  @Step("Open data filter dropdown")
  public DataFilterDropdown openDataFilterDropdown() {
    logger.debug("Open data filter dropdown");
    dataVariableSelector.click();
    return new DataFilterDropdown(driver);
  }

}
