package net.simplyanalytics.pageobjects.sections.toolbar.relateddatareport;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.export.ExportWindow;
import net.simplyanalytics.pageobjects.sections.toolbar.BaseViewToolbar;
import net.simplyanalytics.pageobjects.sections.toolbar.DataVariableDropdown;
import net.simplyanalytics.pageobjects.sections.toolbar.DropdownToolbar;
import io.qameta.allure.Step;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RelatedDataReportToolbar extends BaseViewToolbar {

  @FindBy(css = "a:nth-child(1)")
  private WebElement activeDataVariable;

  @FindBy(css = "a:nth-child(3)")
  private WebElement activeLocation;

  @FindBy(css = "a:nth-child(5)")
  private WebElement activeDataType;

  public RelatedDataReportToolbar(WebDriver driver, ViewType nextViewType) {
    super(driver, ViewType.RELATED_DATA, nextViewType);
  }

  @Override
  public void isLoaded() {
    super.isLoaded();
  }

  @Step("Open data variable list menu")
  public DataVariableDropdown clickDataVariable() {
    logger.debug("Open data variable list menu");
    activeDataVariable.click();
    return new DataVariableDropdown(driver, ViewType.RELATED_DATA);
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

  public DataTypeDropdown openDataTypeMenu() {
    logger.debug("Open data type menu");
    waitForElementToBeClickable(activeDataType, "Data type is not clickable").click();
    return new DataTypeDropdown(driver);
  }

  public String getActiveDataType() {
    return activeDataType.getText().trim();
  }
  
  @Override
  public ExportWindow clickExport() {
    return (ExportWindow) clickExportButton(ViewType.RELATED_DATA);
  }

  public DataVariable getActiveDataVariable() {
    String activeData = activeDataVariable.getText();
    if (activeData.contains("...")) {
      String[] activeDataArray = activeData.split("\n", -1);
      return DataVariable.getByFullName(activeDataArray[0]);
    } else {
      return DataVariable.getByFullName(activeData);
    }
  }

}
