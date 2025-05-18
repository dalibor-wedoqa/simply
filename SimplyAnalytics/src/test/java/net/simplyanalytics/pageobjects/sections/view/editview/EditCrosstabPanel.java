package net.simplyanalytics.pageobjects.sections.view.editview;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.EditContainerType;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxCrosstabContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.RadioButtonContainerPanel;

public class EditCrosstabPanel extends BaseEditViewPanel {
  
	@FindBy(css = ".sa-edit-header-warning")
	private List<WebElement> errorMessage;

  public EditCrosstabPanel(WebDriver driver) {
    super(driver, EditContainerType.RADIOBUTTONS, EditContainerType.CHECKBOXES_CROSSTAB,
        EditContainerType.NONE);
  }
  
  public RadioButtonContainerPanel getLocationsPanel() {
    return (RadioButtonContainerPanel) locations;
  }
  
  public CheckboxCrosstabContainerPanel getDataPanel() {
    return (CheckboxCrosstabContainerPanel) data;
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
