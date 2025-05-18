package net.simplyanalytics.pageobjects.pages.main.views;

import org.openqa.selenium.WebDriver;

import net.simplyanalytics.pageobjects.sections.toolbar.histogram.HistogramToolbar;
import net.simplyanalytics.pageobjects.sections.view.HistogramViewPanel;
import net.simplyanalytics.pageobjects.sections.viewchooser.ViewChooserSection;

public class HistogramPage extends BaseViewPage {

  private final HistogramToolbar histogramToolbar;
  private final HistogramViewPanel histogramViewPanel;

  /**
   * BarChartPage constructor.
   * 
   * @param driver WebDriver
   */
  public HistogramPage(WebDriver driver) {
    super(driver);
    histogramToolbar = new HistogramToolbar(driver, new ViewChooserSection(driver).getTopViewType());
    histogramViewPanel = new HistogramViewPanel(driver);
    waitForLoadingToDisappear();
  }

  @Override
  public HistogramToolbar getToolbar() {
    return histogramToolbar;
  }

  @Override
  public HistogramViewPanel getActiveView() {
    return histogramViewPanel;
  }

}
