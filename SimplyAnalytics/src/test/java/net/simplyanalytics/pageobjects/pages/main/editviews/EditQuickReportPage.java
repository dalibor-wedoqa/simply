package net.simplyanalytics.pageobjects.pages.main.editviews;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.editview.EditQuickReportPanel;

import org.openqa.selenium.WebDriver;

public class EditQuickReportPage extends BaseEditViewPage {

  private final EditQuickReportPanel editQuickReportPanel;

  public EditQuickReportPage(WebDriver driver) {
    super(driver, ViewType.QUICK_REPORT);
    editQuickReportPanel = new EditQuickReportPanel(driver);
  }

  @Override
  public EditQuickReportPanel getActiveView() {
    return editQuickReportPanel;
  }
}
