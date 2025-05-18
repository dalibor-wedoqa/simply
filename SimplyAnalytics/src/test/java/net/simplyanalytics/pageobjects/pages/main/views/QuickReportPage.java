package net.simplyanalytics.pageobjects.pages.main.views;

import net.simplyanalytics.pageobjects.sections.toolbar.quickreport.QuickReportToolbar;
import net.simplyanalytics.pageobjects.sections.view.QuickReportViewPanel;
import net.simplyanalytics.pageobjects.sections.viewchooser.ViewChooserSection;

import org.openqa.selenium.WebDriver;

public class QuickReportPage extends BaseViewPage {
  
  private final QuickReportToolbar quickReportToolbar;
  private final QuickReportViewPanel quickReportViewPanel;
  
  /**
   * QuickReportPage constructor.
   * 
   * @param driver WebDriver
   */
  public QuickReportPage(WebDriver driver) {
    super(driver);
    quickReportToolbar 
      = new QuickReportToolbar(driver, new ViewChooserSection(driver).getTopViewType());
    quickReportViewPanel = new QuickReportViewPanel(driver);
  }
  
  @Override
  public QuickReportToolbar getToolbar() {
    return quickReportToolbar;
  }
  
  @Override
  public QuickReportViewPanel getActiveView() {
    return quickReportViewPanel;
  }
}
