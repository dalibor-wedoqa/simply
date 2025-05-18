package net.simplyanalytics.pageobjects.sections.toolbar.map;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.CroppingWindow;
import net.simplyanalytics.pageobjects.sections.toolbar.BaseViewToolbar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class MapToolbar extends BaseViewToolbar {
  
  @FindBy(xpath = ".//div[text()='by']/../a[1]")
  private WebElement activeDataVariable;
  
  @FindBy(xpath = ".//div[text()='by']/../a[2]")
  private WebElement activeLocation;
  
  @FindBy(xpath = ".//div[text()='by']/../a[3]")
  private WebElement activeLocationType;
  
  @FindBy(css = ".sa-pvt-filtering-button")
  private WebElement filteringButton;
  
  public MapToolbar(WebDriver driver, ViewType nextViewType) {
    super(driver, ViewType.MAP, nextViewType);
  }
  
  @Override
  public void isLoaded() {
    super.isLoaded();
    waitForElementToAppear(activeDataVariable, "The active location element is not present");
    waitForElementToAppear(filteringButton, "The Filtering button should be present");
  }
  
  public DataVariable getActiveDataVariable() {
    if(activeDataVariable.getText().contains("...")) {
      return DataVariable.getByFullName(activeDataVariable.findElement(By.cssSelector(".sa-pvt-truncated-text-left")).getText());
    }
    return DataVariable.getByFullName(activeDataVariable.getText());
  }
  
  /**
   * Open data variable list menu.
   * @return
   */
  @Step("Open data variable list menu")
  public DataVariableComboElements openDataVariableListMenu() {
    logger.debug("Open data variable list menu");
    activeDataVariable.click();
    return new DataVariableComboElements(driver);
  }
  
  public String getNameOfActiveDataVariable() {
    return activeDataVariable.getText();
  }
  
  public Location getActiveLocation() {
    return Location.getByName(getNameOfActiveLocation());
  }
  
  public String getNameOfActiveLocation() {
    return activeLocation.getText();
  }
  
  /**
   * Open location list menu.
   * @return InMapViewComboElements
   */
  @Step("Open location list menu")
  public InMapViewComboElements openLocationListMenu() {
    logger.debug("Open location list menu");
    activeLocation.click();
    return new InMapViewComboElements(driver);
  }
  
  public LocationType getActiveLocationType() {
    return LocationType.getByPluralName(activeLocationType.getText());
  }
  
  /**
   * Open location type list menu.
   * @return ByMapViewComboElements
   */
  @Step("Open location type list menu")
  public ByMapViewComboElements openLocationTypeListMenu() {
    logger.debug("Open location type list menu");
    activeLocationType.click();
    return new ByMapViewComboElements(driver);
  }
  
  @Override
  public CroppingWindow clickExport() {
    return (CroppingWindow) clickExportButton(ViewType.MAP);
  }
  
  public boolean isDataVariableComboPresent() {
    return isPresent(DataVariableComboElements.rootLocator);
  }
}
