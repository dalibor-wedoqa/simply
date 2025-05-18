
package net.simplyanalytics.pageobjects.sections.view.editview;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.EditContainerType;
import net.simplyanalytics.enums.Ldb;
import net.simplyanalytics.enums.Location;
import net.simplyanalytics.pageobjects.sections.view.base.ViewPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.BaseContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxBarChartContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxCrosstabContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.CheckboxScatterPlotContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.RadioButtonContainerPanel;
import net.simplyanalytics.pageobjects.sections.view.editview.containers.ScarboroughCheckboxContainerPanel;
import io.qameta.allure.Step;

public abstract class BaseEditViewPanel extends ViewPanel {
  @FindBy(css = ".sa-edit-header-done-btn")
  private WebElement doneButton;

  @FindBy(css = ".sa-edit-header-done-btn")
  private WebElement doneButtonScatterPlot;

  @FindBy(css = ".sa-simmons-crosstab-edit-done-btn")
  private WebElement doneButtonCrosstab;

  @FindBy(css = ".sa-scarborough-crosstab-edit-done-btn")
  private WebElement doneButtonScarborough;

  @FindBy(css = ".sa-edit-header-warning")
  private WebElement errorMessage;

  @FindBy(css = ".sa-project-view:not(.sa-project-view-hidden) div[id*='edit-panel-enabled'] .sa-radio-checked span:nth-child(2)")
  private WebElement activeLocation;

  //@FindBy(css = ".sa-project-view:not(.sa-project-view-hidden) div[id*='edit-3-panel'] .sa-radio-checked span:nth-child(2)")
  @FindBy(css = ".sa-project-view:not(.sa-project-view-hidden) div[id*='edit-panel'] .sa-radio-checked span:nth-child(2) span:nth-child(1)")
  private WebElement activeDataVariable;

  private static final By LOCATION_ROOT_OTHER = By.cssSelector(".sa-project-view:not(.sa-project-view-hidden) div[id*='project-view-edit-locations-panel']");
  private static final By LOCATION_ROOT = By.xpath(".//div[contains(@class, 'sa-project-view') and not(contains(@class, 'sa-project-view-hidden'))]//div[contains(@class,'sa-edit-view-sections')]/div[contains(@class,'sa-edit-panel')][.//div[normalize-space(.)='Locations']]");
  //private static final By DATA_BAR_CHART = By.xpath(".//div[contains(@class,'sa-edit-view-sections')]/div[contains(@class,'sa-edit-panel')][.//span[normalize-space(.)='Data']]");
  private static final By DATA_BAR_CHART = By.xpath(".//div[contains(@class, 'sa-project-view') and not(contains(@class, 'sa-project-view-hidden'))]//div[contains(@class,'sa-edit-view-sections')]/div[contains(@class,'sa-edit-panel')][.//div[normalize-space(.)='Data']]");
  private static final By DATA_ROOT = By.cssSelector(".sa-project-view:not(.sa-project-view-hidden) "
          + "div[id*='project-view-edit-attributes-panel']");
  private static final By DATA_ROOT_CROSSTAB = By.cssSelector(".sa-project-view:not(.sa-project-view-hidden) "
          + "div.sa-crosstab-attributes-panel");
  private static final By DATA_ROOT_SCARBOROGH_CROSSTAB = By.cssSelector(".sa-project-view:not(.sa-project-view-hidden) "
          + "div.sa-scarborough-crosstab-attributes-panel");
  private static final By DATA_ROOT_SCATTER_PLOT = By.cssSelector(".sa-project-view:not(.sa-project-view-hidden) "
          + "div.sa-scatter-plot-attributes-panel");
  private static final By BUSINESSES_ROOT_MAP = By.cssSelector(".sa-project-view:not("
          + ".sa-project-view-hidden) " + "div[id*='project-view-edit-businesses-panel']");
  private static final By BUSINESSES_ROOT = By.xpath(".//div[contains(@class, 'sa-project-view') and not(contains(@class, 'sa-project-view-hidden'))]//div[contains(@class,'sa-edit-view-sections')]/div[contains(@class,'sa-edit-panel')][.//div[normalize-space(.)='Businesses']]");

  protected BaseContainerPanel locations;
  protected BaseContainerPanel data;
  protected BaseContainerPanel businesses;

  /**
   * BaseEditViewPanel constructor.
   *
   * @param driver    WebDriver
   * @param location  EditContainerType
   * @param data      EditContainerType
   * @param busnisses EditContainerType
   */
  public BaseEditViewPanel(WebDriver driver, EditContainerType location, EditContainerType data,
                           EditContainerType busnisses) {
    super(driver);

    if (isPresent(LOCATION_ROOT)) {
      this.locations = getPanel(location, LOCATION_ROOT, Ldb.LOCATION.getPluralName());
    }else  {
      this.locations = getPanel(location, LOCATION_ROOT_OTHER, Ldb.LOCATION.getPluralName());

    }

    if (isPresent(DATA_ROOT_CROSSTAB)) {
      this.data = getPanel(data, DATA_ROOT_CROSSTAB, Ldb.DATA.getPluralName());
    }
    else if (isPresent(DATA_ROOT_SCATTER_PLOT)) {
      this.data = getPanel(data, DATA_ROOT_SCATTER_PLOT, Ldb.DATA.getPluralName());
    }
    else if (isPresent(DATA_ROOT_SCARBOROGH_CROSSTAB)) {
      this.data = getPanel(data, DATA_ROOT_SCARBOROGH_CROSSTAB, Ldb.DATA.getPluralName());
    } else if(isPresent(DATA_BAR_CHART)) {
      this.data = getPanel(data, DATA_BAR_CHART, Ldb.DATA.getPluralName());
    }
    else {
      this.data = getPanel(data, DATA_ROOT, Ldb.DATA.getPluralName());
    }

    if(isPresent(BUSINESSES_ROOT_MAP)) {
      this.businesses = getPanel(busnisses, BUSINESSES_ROOT_MAP, Ldb.BUSINESS.getPluralName());

    }else {
      this.businesses = getPanel(busnisses, BUSINESSES_ROOT, Ldb.BUSINESS.getPluralName());

    }
  }

  private BaseContainerPanel getPanel(EditContainerType type, By root, String panelName) {
    switch (type) {
      case CHECKBOXES:
        return new CheckboxContainerPanel(driver, root, panelName);
      case CHECKBOXES_CROSSTAB:
        return new CheckboxCrosstabContainerPanel(driver, root, panelName);
      case CHECKBOXES_SCARBOROUGH:
        return new ScarboroughCheckboxContainerPanel(driver, root, panelName);
      case CHECKBOXES_SCATTERPLOT:
        return new CheckboxScatterPlotContainerPanel(driver, root, panelName);
      case RADIOBUTTONS:
        return new RadioButtonContainerPanel(driver, root, panelName);
      case NONE:
      default:
        return null;
    }
  }

  @Override
  public void isLoaded() {
    //TODO add load locator
//    waitForElementToAppear(doneButton, "The done button should be present");
  }

  public BaseContainerPanel getLocationsPanel() {
    return locations;
  }

  public BaseContainerPanel getDataPanel() {
    return data;
  }

  public BaseContainerPanel getBusinessesPanel() {
    return businesses;
  }

  @Step("Click on the Done button")
  public void clickDoneButton() {
    logger.debug("Click on the Done button");
    waitForElementToAppear(doneButton, "Done button did not appear");
    waitForElementToBeClickable(doneButton, "Done button was not clickable").click();
  }

  @Step("Click on the Scatter Plot Done button")
  public void clickDoneButtonScatter() {
    logger.debug("Click on the Scatter Plot Done button");
    doneButtonScatterPlot.click();
  }

  public boolean isDoneButtonDisabled() {
    return waitForElementToAppear(doneButton, "Done button did not appear").getAttribute("class").contains("disabled");
  }

  public boolean isErrorMessageDisplayed() {
    return errorMessage.isDisplayed();
  }

  public String getErrorMessage() {
    return errorMessage.getText();
  }

  public DataVariable getActiveDataVariable() {
    Pattern pattern = Pattern.compile("<span>(.+)<span>");
    Matcher matcher = pattern.matcher(activeDataVariable.getAttribute("outerHTML"));
    if(matcher.find()) {
      return DataVariable.getByFullName(matcher.group(1).trim());
    }
    else {
      return DataVariable.getByFullName(activeDataVariable.getText());
    }
  }

  public Location getActiveLocation() {
    return Location.getByName(activeLocation.getText());
  }
}
