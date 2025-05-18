package net.simplyanalytics.pageobjects.sections.header.importpackage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

import net.simplyanalytics.enums.DataFormat;
import net.simplyanalytics.enums.DataType;
import net.simplyanalytics.pageobjects.base.BasePage;

public class IdentifyDataView extends BasePage {

  protected static final By rootLocator = By.xpath(
      "//div[.//div[contains(@class,'step-indicator-step-active') and .//div[text()='Identify Data Columns']]]");
  protected WebElement root;

  @FindBy(css = ".sa-checkbox-button")
  private List<WebElement> listOfCheckbox;

  @FindBy(css = ".sa-import-column-row")
  private List<WebElement> listOfDataRow;

  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Back']]")
  private WebElement backButton;

  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Next']]")
  private WebElement nextButton;

  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Cancel']]")
  private WebElement cancelButton;

  @Override
  public void isLoaded() {

  }

  public IdentifyDataView(WebDriver driver) {
    super(driver, rootLocator);
    this.root = driver.findElement(rootLocator);
  }

  @Step("Click on Cancel")
  public void clickCancel() {
    logger.debug("Click on the Cancel button");
    cancelButton.click();
  }

  @Step("Click on Next")
  public ReviewAndImportView clickNext() {
    logger.debug("Click on the Next button");
    nextButton.click();
    return new ReviewAndImportView(driver);
  }

  @Step("Click on Back")
  public ImportSection clickBack() {
    logger.debug("Click on the Back button");
    backButton.click();
    return new ImportSection(driver);
  }

  public int getNumberOfColumn() {
    return listOfCheckbox.size();
  }

  @Step("Autofill the Data Type")
  public void autoFillDataType() {
    logger.debug("Autofill all of the fields");
    for (WebElement webElement : listOfDataRow) {
      if (webElement.isDisplayed()) {
        if (webElement.getText().equals("Name")) {
          clickCheckbox(webElement);
        } else if (webElement.getText().equals("FIPS")) {
          fillDataType(webElement, DataType.LOCATION_ID);
        } else if (webElement.getText().contains("%")) {
          fillDataType(webElement, DataType.PERCENT);
        } else if (webElement.getText().contains("#")) {
          fillDataType(webElement, DataType.COUNT);
          fillDataFormat(webElement, DataFormat.INTEGER);
        } else if (webElement.getText().contains("Income")) {
          fillDataType(webElement, DataType.COUNT);
          fillDataFormat(webElement, DataFormat.CURRENCY);
        } else {
          fillDataType(webElement, DataType.COUNT);
          fillDataFormat(webElement, DataFormat.TWO_DECIMA_PLACES);
        }
      }
    }
  }

  private void fillDataType(WebElement webElement, DataType type) {
    if (webElement.findElement(By.cssSelector(".sa-menu-button")).isEnabled()) {
      webElement.findElement(By.cssSelector(".sa-menu-button")).click();
    }
    GeographicUnitComboElements overflowType = new GeographicUnitComboElements(driver);
    overflowType.clickDataType(type);
  }

  private void fillDataFormat(WebElement webElement, DataFormat format) {
    if (webElement.findElement(By.cssSelector(".sa-import-column-row-format-drop-down"))
        .isEnabled()) {
      webElement.findElement(By.cssSelector(".sa-import-column-row-format-drop-down")).click();
    }
    GeographicUnitComboElements overflowType = new GeographicUnitComboElements(driver);
    overflowType.clickDataFormat(format);
  }

  private void clickCheckbox(WebElement webElement) {
    webElement.findElement(By.cssSelector(".sa-checkbox-button")).click();
  }

}
