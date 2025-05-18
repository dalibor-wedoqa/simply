package net.simplyanalytics.pageobjects.sections.view.editview;

import net.simplyanalytics.enums.EditContainerType;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.BaseContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxContainerPanel;

import org.openqa.selenium.WebDriver;

public class EditComparisonReportPanel extends BaseEditViewPanel {
  
  public EditComparisonReportPanel(WebDriver driver) {
    super(driver, EditContainerType.CHECKBOXES, EditContainerType.CHECKBOXES,
        EditContainerType.NONE);
  }
  
  public CheckboxContainerPanel getLocationsPanel() {
    return (CheckboxContainerPanel) locations;
  }
  
  public CheckboxContainerPanel getDataPanel() {
    return (CheckboxContainerPanel) data;
  }
  
  @Deprecated
  public BaseContainerPanel getBusinessesPanel() {
    throw new AssertionError("This panel is not present here");
  }
}
