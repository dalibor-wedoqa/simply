package net.simplyanalytics.pageobjects.pages.main.editviews;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.editview.EditBarChartPanel;

import org.openqa.selenium.WebDriver;

public class EditBarChartPage extends BaseEditViewPage {

  private final EditBarChartPanel editBarChartPanel;

  public EditBarChartPage(WebDriver driver) {
    super(driver, ViewType.BAR_CHART);
    editBarChartPanel = new EditBarChartPanel(driver);
  }

  @Override
  public EditBarChartPanel getActiveView() {
    return editBarChartPanel;
  }

}
