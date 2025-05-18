package net.simplyanalytics.pageobjects.sections.view.editview;

import io.qameta.allure.Step;
import net.simplyanalytics.enums.EditContainerType;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.sections.toolbar.BaseToolbar;
import net.simplyanalytics.pageobjects.sections.toolbar.DataFilterDropdown;
import net.simplyanalytics.pageobjects.sections.view.BarChartViewPanel;
import net.simplyanalytics.pageobjects.sections.view.TimeSeriesViewPanel;
import net.simplyanalytics.pageobjects.sections.view.base.TableViewWithoutDataVariableColoumnPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.BaseContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.RadioButtonContainerPanel;
import net.simplyanalytics.pageobjects.sections.toolbar.BaseToolbar;




import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class EditTimeSeriesPanel extends BaseEditViewPanel {


  /**
   * EditTimeSeriesPanel constructor.
   * @param driver WebDriver
   */
  public EditTimeSeriesPanel(WebDriver driver) {
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

  //N.B Code added
  @FindBy(css = ".sa-grid-cell")
  private List<WebElement> tableContent;

  @FindBy(xpath = ".//*[.='sorted by']/../a[1]")
  private WebElement dataVariableSelector;

  @FindBy(css = ".x-menu-item-text")
  private List<WebElement> elements;

  @FindBy(css = ".x-menu-item-checked")
  private List<WebElement> checkedElements;

  @FindBy(xpath = ".//*[.='sorted by']/../a[2]")
  private WebElement projectedDataSelector;

  //N.B Code added
  public List<String> getTableContent() {
    List<String> listContent = new ArrayList<String>();
    for (WebElement content : tableContent) {
      listContent.add(content.getText());
    }
    return listContent;
  }
  public DataFilterDropdown openDataFilterDropdown() {
    logger.debug("Click on data varibale dropdown button");
    dataVariableSelector.click();
    return new DataFilterDropdown(driver);
  }
  public List<WebElement> getActiveWindowElements() {
    List<WebElement> list = new ArrayList<WebElement>();
    for (WebElement webElement : elements) {
      if (webElement.isDisplayed()) {
        list.add(webElement);
      }
    }
    return list;
  }
  public WebElement getCheckedElement() {
    for (WebElement webElement : checkedElements) {
      if (webElement.isDisplayed()) {
        return webElement;
      }
    }
    return null;
  }
}