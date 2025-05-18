package net.simplyanalytics.pageobjects.pages.main.editviews;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.editview.EditTimeSeriesPanel;

import org.openqa.selenium.WebDriver;

public class EditTimeSeriesPage extends BaseEditViewPage {

  private final EditTimeSeriesPanel editTimeSeriesPanel;

  public EditTimeSeriesPage(WebDriver driver) {
    super(driver, ViewType.TIME_SERIES);
    editTimeSeriesPanel = new EditTimeSeriesPanel(driver);
  }

  @Override
  public EditTimeSeriesPanel getActiveView() {
    return editTimeSeriesPanel;
  }
}
