package net.simplyanalytics.pageobjects.sections.view.editview;

import net.simplyanalytics.enums.EditContainerType;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.RadioButtonContainerPanel;

import org.openqa.selenium.WebDriver;

public class EditMapPanel extends BaseEditViewPanel {
  
  public EditMapPanel(WebDriver driver) {
    super(driver, EditContainerType.RADIOBUTTONS, EditContainerType.RADIOBUTTONS,
        EditContainerType.RADIOBUTTONS);
  }
  
  public RadioButtonContainerPanel getLocationsPanel() {
    return (RadioButtonContainerPanel) locations;
  }
  
  public RadioButtonContainerPanel getDataPanel() {
    return (RadioButtonContainerPanel) data;
  }
  
  public RadioButtonContainerPanel getBusinessesPanel() {
    return (RadioButtonContainerPanel) businesses;
  }
}
