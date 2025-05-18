package net.simplyanalytics.pageobjects.pages.main.views;

import net.simplyanalytics.pageobjects.sections.toolbar.barchart.BarChartToolbar;
import net.simplyanalytics.pageobjects.sections.view.BarChartViewPanel;
import net.simplyanalytics.pageobjects.sections.viewchooser.ViewChooserSection;

import org.openqa.selenium.WebDriver;

public class BarChartPage extends BaseViewPage {

  private final BarChartToolbar barChartToolbar;
  private final BarChartViewPanel barChartViewPanel;

  /**
   * BarChartPage constructor.
   * 
   * @param driver WebDriver
   */
  public BarChartPage(WebDriver driver) {
    super(driver);
    barChartToolbar = new BarChartToolbar(driver, new ViewChooserSection(driver).getTopViewType());
    barChartViewPanel = new BarChartViewPanel(driver);
    waitForLoadingToDisappear();
  }

  @Override
  public BarChartToolbar getToolbar() {
    return barChartToolbar;
  }

  @Override
  public BarChartViewPanel getActiveView() {
    return barChartViewPanel;
  }

}
