package net.simplyanalytics.pageobjects.sections.view.editview;

import net.simplyanalytics.enums.EditContainerType;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.BaseContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxContainerPanel;

import org.openqa.selenium.WebDriver;

public class EditQuickReportPanel extends BaseEditViewPanel {
  
  public EditQuickReportPanel(WebDriver driver) {
    super(driver, EditContainerType.CHECKBOXES, EditContainerType.NONE, EditContainerType.NONE);
  }
  
  public CheckboxContainerPanel getLocationsPanel() {
    return (CheckboxContainerPanel) locations;
  }
  
  @Deprecated
  public BaseContainerPanel getDataPanel() {
    throw new AssertionError("This panel is not present here");
  }
  
  @Deprecated
  public BaseContainerPanel getBusinessesPanel() {
    throw new AssertionError("This panel is not present here");
  }
}
