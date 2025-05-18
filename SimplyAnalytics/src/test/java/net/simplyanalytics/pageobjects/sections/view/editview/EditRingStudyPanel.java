package net.simplyanalytics.pageobjects.sections.view.editview;

import net.simplyanalytics.enums.EditContainerType;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.BaseContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.RadioButtonContainerPanel;

import org.openqa.selenium.WebDriver;

public class EditRingStudyPanel extends BaseEditViewPanel {
  
  public EditRingStudyPanel(WebDriver driver) {
    super(driver, EditContainerType.RADIOBUTTONS, EditContainerType.CHECKBOXES,
        EditContainerType.NONE);
  }
  
  public RadioButtonContainerPanel getLocationsPanel() {
    return (RadioButtonContainerPanel) locations;
  }
  
  public CheckboxContainerPanel getDataPanel() {
    return (CheckboxContainerPanel) data;
  }
  
  @Deprecated
  public BaseContainerPanel getBusinessesPanel() {
    throw new AssertionError("This panel is not present here");
  }
}
