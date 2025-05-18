package net.simplyanalytics.pageobjects.sections.view.editview;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.EditContainerType;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxScatterPlotContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.RadioButtonContainerPanel;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class EditScatterPlotPanel extends BaseEditViewPanel {
  
  @FindBy(css = ".sa-scatter-plot-edit-view-warning")
  private WebElement errorMessage;

  public EditScatterPlotPanel(WebDriver driver) {
    super(driver, EditContainerType.RADIOBUTTONS, EditContainerType.CHECKBOXES_SCATTERPLOT,
        EditContainerType.NONE);
  }
  
  public RadioButtonContainerPanel getLocationsPanel() {
    return (RadioButtonContainerPanel) locations;
  }
  
  public CheckboxScatterPlotContainerPanel getDataPanel() {
    return (CheckboxScatterPlotContainerPanel) data;
  }

  public RadioButtonContainerPanel getBusinessesPanel() {
    throw new AssertionError("This panel is not present here");
  }
  
  @Override
  public String getErrorMessage() {
    return errorMessage.getText();
  }
  
  @Override
  public boolean isErrorMessageDisplayed() {
    return errorMessage.isDisplayed();
  }
  
  @Override
  public void isLoaded() {
  }

}
