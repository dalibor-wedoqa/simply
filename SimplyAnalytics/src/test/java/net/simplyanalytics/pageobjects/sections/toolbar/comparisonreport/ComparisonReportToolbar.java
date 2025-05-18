package net.simplyanalytics.pageobjects.sections.toolbar.comparisonreport;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.export.ExportWindow;
import net.simplyanalytics.pageobjects.sections.toolbar.BaseViewToolbar;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ComparisonReportToolbar extends BaseViewToolbar {
  
  @FindBy(css = ".sa-pvt-filtering-button")
  private WebElement filteringButton;
  
  public ComparisonReportToolbar(WebDriver driver, ViewType nextViewType) {
    super(driver, ViewType.COMPARISON_REPORT, nextViewType);
  }
  
  @Override
  public void isLoaded() {
    super.isLoaded();
    //waitForElementToAppear(filteringButton, "The Filtering button should be present");
  }
  
  @Override
  public ExportWindow clickExport() {
    return (ExportWindow) clickExportButton(ViewType.COMPARISON_REPORT);
  }
  
}
