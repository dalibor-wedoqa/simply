package net.simplyanalytics.pageobjects.sections.toolbar.scatterplot;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.export.ExportWindow;
import net.simplyanalytics.pageobjects.sections.toolbar.BaseViewToolbar;
import net.simplyanalytics.pageobjects.sections.toolbar.DropdownToolbar;
import io.qameta.allure.Step;

public class ScatterPlotToolbar extends BaseViewToolbar {
  
  @FindBy(xpath = ".//*[.='Points represent:']/../a[2]")
  private WebElement activeLocation;
  
  @FindBy(xpath = ".//*[.='Points represent:']/../a[1]")
  private WebElement locationTypeDropdownButton;
  
  public ScatterPlotToolbar(WebDriver driver, ViewType nextViewType) {
    super(driver, ViewType.SCATTER_PLOT, nextViewType);
  }
  
  @Override
  @Step("Click on the Export button")
  public ExportWindow clickExport() {
    return (ExportWindow) clickExportButton(ViewType.SCATTER_PLOT);
  }
  
  public Location getActiveLocation() {
    return Location.getByName(activeLocation.getText());
  }
  
  @Step("Open location menu")
  public DropdownToolbar openLocationMenu() {
    logger.debug("Open location list");
    activeLocation.click();
    return new DropdownToolbar(driver);
  }
  
  public LocationType getActiveLocationType() {
    return LocationType.getByPluralName(locationTypeDropdownButton.getText());
  }
  
  @Step("Open Filter by location type list menu")
  public DropdownToolbar clickFilterByLocationTypeListMenu() {
    logger.debug("Open Filter location type list menu");
    locationTypeDropdownButton.click();
    return new DropdownToolbar(driver);
  }

}
