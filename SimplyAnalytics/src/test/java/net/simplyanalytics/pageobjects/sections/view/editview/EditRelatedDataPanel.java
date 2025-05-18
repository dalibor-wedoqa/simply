package net.simplyanalytics.pageobjects.sections.view.editview;

import net.simplyanalytics.enums.EditContainerType;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.BaseContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.RadioButtonContainerPanel;

import org.openqa.selenium.WebDriver;

public class EditRelatedDataPanel extends BaseEditViewPanel {
  
  public EditRelatedDataPanel(WebDriver driver) {
    super(driver, EditContainerType.CHECKBOXES, EditContainerType.RADIOBUTTONS,
        EditContainerType.NONE);
  }
  
  public CheckboxContainerPanel getLocationsPanel() {
    return (CheckboxContainerPanel) locations;
  }
  
  public RadioButtonContainerPanel getDataPanel() {
    return (RadioButtonContainerPanel) data;
  }
  
  @Deprecated
  public BaseContainerPanel getBusinessesPanel() {
    throw new AssertionError("This panel is not present here");
  }
}
