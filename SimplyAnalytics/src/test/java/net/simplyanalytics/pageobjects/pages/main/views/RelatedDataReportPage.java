package net.simplyanalytics.pageobjects.pages.main.views;

import net.simplyanalytics.pageobjects.sections.toolbar.relateddatareport.RelatedDataReportToolbar;
import net.simplyanalytics.pageobjects.sections.view.RelatedDataReportViewPanel;
import net.simplyanalytics.pageobjects.sections.viewchooser.ViewChooserSection;

import org.openqa.selenium.WebDriver;

public class RelatedDataReportPage extends BaseViewPage {

  private final RelatedDataReportToolbar relatedDataReportToolbar;
  private final RelatedDataReportViewPanel relatedDataReportViewPanel;

  /**
   * RelatedDataReportPage constructor.
   * 
   * @param driver WebDriver
   */
  public RelatedDataReportPage(WebDriver driver) {
    super(driver);
    relatedDataReportToolbar 
      = new RelatedDataReportToolbar(driver, new ViewChooserSection(driver).getTopViewType());
    relatedDataReportViewPanel = new RelatedDataReportViewPanel(driver);
  }

  @Override
  public RelatedDataReportToolbar getToolbar() {
    return relatedDataReportToolbar;
  }

  @Override
  public RelatedDataReportViewPanel getActiveView() {
    return relatedDataReportViewPanel;
  }
}
