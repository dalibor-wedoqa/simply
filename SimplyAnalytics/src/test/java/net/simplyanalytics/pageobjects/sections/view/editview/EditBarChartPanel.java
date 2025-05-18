package net.simplyanalytics.pageobjects.sections.view.editview;

import net.simplyanalytics.enums.EditContainerType;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.RadioButtonContainerPanel;

import org.openqa.selenium.WebDriver;

public class EditBarChartPanel extends BaseEditViewPanel {
  
  /**
   * EditBarChartPanel constructor.
   * @param driver WebDriver
   */
  public EditBarChartPanel(WebDriver driver) {
    super(driver, EditContainerType.CHECKBOXES, EditContainerType.RADIOBUTTONS,
        EditContainerType.NONE);
  }
  
  public CheckboxContainerPanel getLocationsPanel() {
    return (CheckboxContainerPanel) locations;
  }
  
  public RadioButtonContainerPanel getDataPanel() {
    return (RadioButtonContainerPanel) data;
  }
}
