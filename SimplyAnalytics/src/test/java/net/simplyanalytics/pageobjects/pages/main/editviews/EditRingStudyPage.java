package net.simplyanalytics.pageobjects.pages.main.editviews;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.view.editview.EditRingStudyPanel;

import org.openqa.selenium.WebDriver;

public class EditRingStudyPage extends BaseEditViewPage {

  private final EditRingStudyPanel editRingStudyPanel;

  public EditRingStudyPage(WebDriver driver) {
    super(driver, ViewType.RING_STUDY);
    editRingStudyPanel = new EditRingStudyPanel(driver);
  }

  @Override
  public EditRingStudyPanel getActiveView() {
    return editRingStudyPanel;
  }
}
