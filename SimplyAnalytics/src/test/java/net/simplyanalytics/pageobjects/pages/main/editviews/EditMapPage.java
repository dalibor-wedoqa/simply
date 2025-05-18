package net.simplyanalytics.pageobjects.pages.main.editviews;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.editview.EditMapPanel;

import org.openqa.selenium.WebDriver;

public class EditMapPage extends BaseEditViewPage {
  
  private final EditMapPanel editMapPanel;
  
  public EditMapPage(WebDriver driver) {
    super(driver, ViewType.MAP);
    editMapPanel = new EditMapPanel(driver);
  }
  
  @Override
  public EditMapPanel getActiveView() {
    return editMapPanel;
  }
  
}
