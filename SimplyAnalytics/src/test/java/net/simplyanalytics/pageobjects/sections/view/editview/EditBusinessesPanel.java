package net.simplyanalytics.pageobjects.sections.view.editview;

import net.simplyanalytics.enums.EditContainerType;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.BaseContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.RadioButtonContainerPanel;

import org.openqa.selenium.WebDriver;

public class EditBusinessesPanel extends BaseEditViewPanel {
  
  public EditBusinessesPanel(WebDriver driver) {
    super(driver, EditContainerType.RADIOBUTTONS, EditContainerType.NONE,
        EditContainerType.RADIOBUTTONS);
  }
  
  public RadioButtonContainerPanel getLocationsPanel() {
    return (RadioButtonContainerPanel) locations;
  }
  
  @Deprecated
  public BaseContainerPanel getDataPanel() {
    throw new AssertionError("This panel is not present here");
  }
  
  public RadioButtonContainerPanel getBusinessesPanel() {
    return (RadioButtonContainerPanel) businesses;
  }
}
