package net.simplyanalytics.pageobjects.pages.main.editviews;

import org.openqa.selenium.WebDriver;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.editview.EditHistogramPanel;

public class EditHistogramPage extends BaseEditViewPage {

  private final EditHistogramPanel editHistogramPanel;

  public EditHistogramPage(WebDriver driver) {
    super(driver, ViewType.HISTOGRAM);
    editHistogramPanel = new EditHistogramPanel(driver);
  }

  @Override
  public EditHistogramPanel getActiveView() {
    return editHistogramPanel;
  }

}
