package net.simplyanalytics.pageobjects.sections.toolbar.ranking;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.export.ExportWindow;
import net.simplyanalytics.pageobjects.pages.main.export.MainExport;
import net.simplyanalytics.pageobjects.pages.main.export.RankingChooseExportDropdown;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.sections.toolbar.BaseViewToolbar;
import net.simplyanalytics.pageobjects.sections.toolbar.DataVariableDropdown;
import net.simplyanalytics.pageobjects.sections.toolbar.DropdownToolbar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class RankingToolbar extends BaseViewToolbar {
  
 @FindBy(xpath = ".//*[.='sorted by']/../a[1]")
  private WebElement topNumberDropdownButton;
  
  @FindBy(xpath = ".//*[.='sorted by']/../a[2]")
  private WebElement locationTypeDropdownButton;
  
  @FindBy(xpath = ".//*[.='sorted by']/../a[3]")
  private WebElement locationDropdownButton;
  
  @FindBy(xpath = ".//*[.='sorted by']/../a[4]")
  private WebElement dataVariableDropdownButton;
  
  @FindBy(css = ".sa-pvt-ranking-export-button")
  protected WebElement exportButton;
  
  public RankingToolbar(WebDriver driver, ViewType nextViewType) {
    super(driver, ViewType.RANKING, nextViewType);
  }
  
  @Override
  public void isLoaded() {
    super.isLoaded();
    waitForElementToAppear(filteringButton, "The Filtering button should be present");
  }
  
  // List size
  
  public String getActiveListSize() {
    return topNumberDropdownButton.getText();
  }
  
  @Step("Open Top number list menu")
  private ListSizeDropdown openTopNumberListMenu() {
    logger.debug("Open Top number list menu");
    topNumberDropdownButton.click();
    return new ListSizeDropdown(driver);
  }
  
  public RankingPage clickTopNumberOfDataInTableCombo(int numberOfDataResults) {
    switch (numberOfDataResults) {
      case 10:
      case 100:
      case 1000:
      case 10000:
        return openTopNumberListMenu().clickTopDataResults(numberOfDataResults);
      default:
        throw new AssertionError("You have to entered a valid number(10, 100, 1000 or 10000)", null);
    }
  }
  
  // Location type
  
  public Location getActiveLocationType() {
    return Location.getByName(locationTypeDropdownButton.getText());
  }
  
  @Step("Open Filter by location type list menu")
  public LocationTypeDropdown clickFilterByLocationTypeListMenu() {
    logger.debug("Open Filter location type list menu");
    locationTypeDropdownButton.click();
    return new LocationTypeDropdown(driver);
  }
  
  // Location
  
  public Location getActiveLocation() {
    String activeLocation = locationDropdownButton.getText();
    if (activeLocation.contains("...")) {
      String[] activeLocationArray = activeLocation.split("\n", -1);
      return Location.getByName(activeLocationArray[0]);
    } else {
      return Location.getByName(activeLocation);
    }
  }
  
  @Step("Open location list")
  private DropdownToolbar clickDropdownLocation() {
    logger.debug("Open location list");
    locationDropdownButton.click();
    return new DropdownToolbar(driver);
  }
  
  public void changeLocation(Location locationName) {
    logger.debug("Select location with name: " + locationName);
    clickDropdownLocation().clickonLocation(locationName);
  }
  
  // Data Variable
  
  public DataVariable getActiveDataVariable() {
    String activeData = dataVariableDropdownButton.getText().trim();
    if (activeData.contains("...")) {
      String[] activeDataArray = activeData.split("\n", -1);
      return DataVariable.getByFullName(activeDataArray[0]);
    } else {
      return DataVariable.getByFullName(activeData);
    }
  }
  
  @Step("Open data variable list menu")
  public DataVariableDropdown clickDataVariable() {
    logger.debug("Open data variable list menu");
    dataVariableDropdownButton.click();
    return new DataVariableDropdown(driver, ViewType.RANKING);
  }
  
  public BasePage clickExportButton() {
    waitForElementToBeClickable(exportButton, "Export button").click();
    return new RankingChooseExportDropdown(driver);
  }

  @Override
  public MainExport clickExport() {
    // TODO Auto-generated method stub
    return null;
  }
  
}
