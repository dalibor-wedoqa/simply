package net.simplyanalytics.pageobjects.sections.toolbar.timeseriesreport;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.export.ExportWindow;
import net.simplyanalytics.pageobjects.sections.toolbar.BaseViewToolbar;
import net.simplyanalytics.pageobjects.sections.toolbar.DataFilterDropdown;
import net.simplyanalytics.pageobjects.sections.toolbar.DataVariableDropdown;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TimeSeriesToolbar extends BaseViewToolbar {
  
  @FindBy(xpath = ".//*[.='sorted by']/../a[1]")
  private WebElement dataVariableSelector;

  @FindBy(xpath = ".//*[.='sorted by']/../a[2]")
  private WebElement projectedDataSelector;
  
  @FindBy(xpath = ".//*[.='sorted by']/../a[3]")
  private WebElement sortingSelector;

  //NB added code for full text for active data variable
  @FindBy(css = ".sa-pvt-truncated-text-left")
  private WebElement getActiveDataVariable;

  public TimeSeriesToolbar(WebDriver driver, ViewType nextViewType) {
    super(driver, ViewType.TIME_SERIES, nextViewType);
  }
  
  @Override
  public void isLoaded() {
    super.isLoaded();
  }
  
  @Override
  public ExportWindow clickExport() {
    return (ExportWindow) clickExportButton(ViewType.TIME_SERIES);
  }
  
  public DataFilterDropdown openDataFilterDropdown() {
    logger.debug("Click on data varibale dropdown button");
    dataVariableSelector.click();
    return new DataFilterDropdown(driver);
  }
  
  public DataFilterDropdown openProjectDataDropdown() {
    logger.debug("Click on the project data dropdown button");
    projectedDataSelector.click();
    return new DataFilterDropdown(driver);
  }
  
  public DataFilterDropdown openLocationDropdown() {
    logger.debug("Click on the sorting dropdown button");
    sortingSelector.click();
    return new DataFilterDropdown(driver);
  }
  
  public DataVariable getActiveDataVariable() {
    Pattern pattern = Pattern.compile("<span>(.+)<span>");
    Matcher matcher = pattern.matcher(dataVariableSelector.getAttribute("outerHTML"));
    if(matcher.find()) {
      return DataVariable.getByFullName(matcher.group(1).trim());
    }
    else {
      return DataVariable.getByFullName(dataVariableSelector.getText());
    }
  }
  public DataVariable getActiveDataVariableFullName() {
    // Get the full name from the element's text content
    String fullName = getActiveDataVariable.getText().trim();

    // Return the DataVariable using the full name
    return DataVariable.getByFullName(fullName);
  }

}
