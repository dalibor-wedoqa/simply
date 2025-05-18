package net.simplyanalytics.pageobjects.pages.main.views;

import net.simplyanalytics.pageobjects.sections.toolbar.timeseriesreport.TimeSeriesToolbar;
import net.simplyanalytics.pageobjects.sections.view.TimeSeriesViewPanel;
import net.simplyanalytics.pageobjects.sections.viewchooser.ViewChooserSection;

import org.openqa.selenium.WebDriver;

public class TimeSeriesPage extends BaseViewPage {

  private final TimeSeriesToolbar timeSeriesReportToolbar;
  private final TimeSeriesViewPanel timeSeriesReportViewPanel;

  /**
   * TimeSeriesPage constructor.
   * 
   * @param driver WebDriver
   */
  public TimeSeriesPage(WebDriver driver) {
    super(driver);
    timeSeriesReportToolbar 
      = new TimeSeriesToolbar(driver, new ViewChooserSection(driver).getTopViewType());
    timeSeriesReportViewPanel = new TimeSeriesViewPanel(driver);
  }

  @Override
  public TimeSeriesToolbar getToolbar() {
    return timeSeriesReportToolbar;
  }

  @Override
  public TimeSeriesViewPanel getActiveView() {
    return timeSeriesReportViewPanel;
  }
  
}
