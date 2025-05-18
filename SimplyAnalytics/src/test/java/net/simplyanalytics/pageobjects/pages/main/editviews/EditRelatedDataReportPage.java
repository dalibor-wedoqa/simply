package net.simplyanalytics.pageobjects.pages.main.editviews;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.editview.EditRelatedDataPanel;
import org.openqa.selenium.WebDriver;

public class EditRelatedDataReportPage extends BaseEditViewPage {

  private final EditRelatedDataPanel editRelatedDataPanel;

  public EditRelatedDataReportPage(WebDriver driver) {
    super(driver, ViewType.RELATED_DATA);
    editRelatedDataPanel = new EditRelatedDataPanel(driver);
  }

  @Override
  public EditRelatedDataPanel getActiveView() {
    return editRelatedDataPanel;
  }
}
