package net.simplyanalytics.pageobjects.pages.main.views;

import net.simplyanalytics.pageobjects.sections.toolbar.comparisonreport.ComparisonReportToolbar;
import net.simplyanalytics.pageobjects.sections.view.ComparisonReportViewPanel;
import net.simplyanalytics.pageobjects.sections.viewchooser.ViewChooserSection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ComparisonReportPage extends BaseViewPage {

  private final ComparisonReportToolbar comparisonReportToolbar;
  private final ComparisonReportViewPanel comparisonReportViewPanel;

  /**
   * ComparisonReportPage constructor.
   * @param driver WebDriver
   */
  public ComparisonReportPage(WebDriver driver) {
    super(driver);
    comparisonReportToolbar 
      = new ComparisonReportToolbar(driver, new ViewChooserSection(driver).getTopViewType());
    comparisonReportViewPanel = new ComparisonReportViewPanel(driver);
  }

  @Override
  public void isLoaded() {
    waitForLoadingElementToDisappear(driver, By.cssSelector(".sa-boxed-spinner"), 150);
  }
  
  @Override
  public ComparisonReportToolbar getToolbar() {
    return comparisonReportToolbar;
  }

  @Override
  public ComparisonReportViewPanel getActiveView() {
    return comparisonReportViewPanel;
  }
}
