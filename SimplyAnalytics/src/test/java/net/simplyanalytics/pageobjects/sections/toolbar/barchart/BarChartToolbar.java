package net.simplyanalytics.pageobjects.sections.toolbar.barchart;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.export.ExportWindow;
import net.simplyanalytics.pageobjects.sections.toolbar.BaseViewToolbar;
import net.simplyanalytics.pageobjects.sections.toolbar.DataFilterDropdown;
import net.simplyanalytics.pageobjects.sections.toolbar.DataVariableDropdown;
import io.qameta.allure.Step;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class BarChartToolbar extends BaseViewToolbar {
  
  @FindBy(xpath = ".//*[.='sorted']/../a[1]")
  private WebElement dataVariableSelector;
  
  @FindBy(xpath = ".//*[.='sorted']/../a[2]")
  private WebElement sortingSelector;
  
  @FindBy(css = ".sa-button-arrow span.sa-button-text") 
  private WebElement activeDataVariable;
  
  
  public BarChartToolbar(WebDriver driver, ViewType nextViewType) {
    super(driver, ViewType.BAR_CHART, nextViewType);
  }
  
  @Override
  public void isLoaded() {
    super.isLoaded();
  }
  
  public DataVariableDropdown openDataVariableMenu() {
    logger.debug("Open data variable menu");
    activeDataVariable.click();
    return new DataVariableDropdown(driver, ViewType.BAR_CHART);
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
    
    @FindBy(xpath = ".//div[contains(@class, 'sa-menu-button-menu')]//a[.//span[normalize-space(.)='Descending']]")
    private WebElement descendingSelector;
    
    @FindBy(xpath = ".//div[contains(@class, 'sa-menu-button-menu')]//a[.//span[normalize-space(.)='Ascending']]")
    private WebElement ascendingSelector;
    
    public SortingMenu(WebDriver driver) {
      super(driver);
    }
    
    @Override
    public void isLoaded() {
      waitForElementToAppear(descendingSelector, "Descending option is not present");
      waitForElementToAppear(ascendingSelector, "Ascending option is not present");
    }
    
    @Step("Click on the Descending option")
    public void clickSortByDescending() {
      logger.debug("Click on the Descending option");
      waitForElementToBeClickable(descendingSelector, "Descending option is not clickable").click();
    }
    
    @Step("Click on the Ascending option")
    public void clickSortByAscending() {
      logger.debug("Click on the Ascending option");
      waitForElementToBeClickable(ascendingSelector, "Ascending option is not clickable").click();
    }
    
  }
  
  @Step("Click on the Export button")
  @Override
  public ExportWindow clickExport() {
    return (ExportWindow) clickExportButton();
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
