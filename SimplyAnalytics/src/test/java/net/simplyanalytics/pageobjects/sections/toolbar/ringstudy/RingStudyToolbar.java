package net.simplyanalytics.pageobjects.sections.toolbar.ringstudy;

import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.export.ExportWindow;
import net.simplyanalytics.pageobjects.sections.toolbar.BaseViewToolbar;
import net.simplyanalytics.pageobjects.sections.toolbar.DropdownToolbar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RingStudyToolbar extends BaseViewToolbar {
  
  @FindBy(xpath = "(//a[@class='sa-button sa-pvt-location-dropdown sa-menu-button sa-button-arrow x-unselectable sa-button-underline x-border-box'])[2]")
  private WebElement activeLocation;
  
  public RingStudyToolbar(WebDriver driver, ViewType nextViewType) {
    super(driver, ViewType.RING_STUDY, nextViewType);
  }
  
  @Override
  public void isLoaded() {
    super.isLoaded();
  }
  
  @Override
  public ExportWindow clickExport() {
    return (ExportWindow) clickExportButton(ViewType.RING_STUDY);
  }
  
  public Location getActiveLocation() {
    return Location.getByName(activeLocation.getText());
  }
  
  private DropdownToolbar openLocationMenu() {
    logger.debug("Open location list");
    activeLocation.click();
    return new DropdownToolbar(driver);
  }
  
  public void changeLocation(Location locationName) {
    logger.debug("Select business with name: " + locationName);
    openLocationMenu().clickonLocation(locationName);
  }
  
}
