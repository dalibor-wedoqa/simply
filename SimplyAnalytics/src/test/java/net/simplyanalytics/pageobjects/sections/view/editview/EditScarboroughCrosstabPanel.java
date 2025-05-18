package net.simplyanalytics.pageobjects.sections.view.editview;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.EditContainerType;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.RadioButtonContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.ScarboroughCheckboxContainerPanel;

public class EditScarboroughCrosstabPanel extends BaseEditViewPanel {

  @FindBy(css = ".sa-edit-header-warning div")
  private List<WebElement> errorMessage;

  public EditScarboroughCrosstabPanel(WebDriver driver) {
    super(driver, EditContainerType.RADIOBUTTONS, EditContainerType.CHECKBOXES_SCARBOROUGH,
        EditContainerType.NONE);
  }
  
  public RadioButtonContainerPanel getLocationsPanel() {
    return (RadioButtonContainerPanel) locations;
  }
  
  public ScarboroughCheckboxContainerPanel getDataPanel() {
    return (ScarboroughCheckboxContainerPanel) data;
  }
  
  public RadioButtonContainerPanel getBusinessesPanel() {
    throw new AssertionError("This panel is not present here");
  }
  
  @Override
  public String getErrorMessage() {
    return errorMessage.get(0).getText();
  }
  
  public String getLimitErrorMessage() {
    return errorMessage.get(0).getText();
  }
  
  public boolean isErrorMessageDisplayed() {
    return errorMessage.get(0).isDisplayed();
  }
  
  public boolean isLimitErrorMessageDisplayed() {
    return errorMessage.get(0).isDisplayed();
  }
  
  @Override
  public void isLoaded() {
  }
  
}
