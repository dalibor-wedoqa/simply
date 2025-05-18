package net.simplyanalytics.pageobjects.pages.main.editviews;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.editview.EditBusinessesPanel;

import org.openqa.selenium.WebDriver;

public class EditBusinessesPage extends BaseEditViewPage {

  private final EditBusinessesPanel editBusinessesPanel;

  public EditBusinessesPage(WebDriver driver) {
    super(driver, ViewType.BUSINESSES);
    editBusinessesPanel = new EditBusinessesPanel(driver);
  }

  @Override
  public EditBusinessesPanel getActiveView() {
    return editBusinessesPanel;
  }

}
