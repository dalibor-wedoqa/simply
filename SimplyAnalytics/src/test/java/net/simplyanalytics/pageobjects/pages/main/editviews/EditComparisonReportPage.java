package net.simplyanalytics.pageobjects.pages.main.editviews;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.editview.EditComparisonReportPanel;

import org.openqa.selenium.WebDriver;

public class EditComparisonReportPage extends BaseEditViewPage {
  
  private final EditComparisonReportPanel editComparisonReportPanel;
  
  public EditComparisonReportPage(WebDriver driver) {
    super(driver, ViewType.COMPARISON_REPORT);
    editComparisonReportPanel = new EditComparisonReportPanel(driver);
  }
  
  @Override
  public EditComparisonReportPanel getActiveView() {
    return editComparisonReportPanel;
  }
  
}
