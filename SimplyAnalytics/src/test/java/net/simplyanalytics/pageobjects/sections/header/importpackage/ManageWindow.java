package net.simplyanalytics.pageobjects.sections.header.importpackage;

import java.util.List;
import java.util.stream.Collectors;

import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class ManageWindow extends BasePage {

  protected static final By rootLocator = By.cssSelector(".sa-manage-imports-window");
  protected WebElement root;

  @FindBy(css = ".sa-close")
  private WebElement closeButton;

  @FindBy(css = ".sa-imports-table-import-date-cell")
  private WebElement importDate;

  @FindBy(css = ".sa-imports-table-dataset-name")
  private List<WebElement> datasetName;

  @FindBy(css = ".sa-imports-table-dataset-units")
  private List<WebElement> datasetUnit;

  @FindBy(xpath = "//a[contains(@data-link-type,'editName')]")
  private List<WebElement> editButton;

  @FindBy(xpath = "//a[contains(@data-link-type,'delete')]")
  private List<WebElement> deleteButton;

  public ManageWindow(WebDriver driver) {
    super(driver, rootLocator);
    this.root = driver.findElement(rootLocator);
  }

  public void isLoaded() {

  }

  @Step("Click on Edit")
  public ManageRenameMenuWindow clickEdit() {
    logger.debug("Click on the Cancel button");
    editButton.get(0).click();
    return new ManageRenameMenuWindow(driver);
  }

  @Step("Click on Close")
  public void clickClose() {
    logger.debug("Click on the Close button");
    closeButton.click();
  }

  public int getNumberOfImportedDatasets() {
    getCurrentDatasetName();
    return datasetName.size();
  }
  
  private void getCurrentDatasetName() {
    datasetName = driver.findElements(By.cssSelector(".sa-imports-table-dataset-name"));
  }
  
  public List<String> getAllImportedDatasetName(){
    return datasetName.stream().map(dataset -> dataset.getText().trim()).collect(Collectors.toList());
  }

  /**
   * @param size The number of datasets to leave after deleting
   */
  @Step("Click on Delete")
  public void deleteDatasets(int size) {
    logger.debug("Click on the Delete button");
    while (deleteButton.size() > size) {
      for (WebElement activeElement : deleteButton) {
        waitForElementToBeClickable(activeElement, "Delete button is not clickable");
        clickDelete(activeElement).clickConfirm();
        collectElementsOfRow();
        break;
      }
    }
  }

  @Step("Click on Delete")
  public void deleteLastDatasets() {
    logger.debug("Delete the last imported dataset");
    waitForElementToBeClickable(deleteButton.get(0), "Delete button is not clickable");
    clickDelete(deleteButton.get(0)).clickConfirm();
    waitForElementToDisappear(deleteButton.get(0), 150);
    collectElementsOfRow();        
  }
  
  @Step("Click on Delete")
  public void deleteSingleDatasets() {
    logger.debug("Delete the last imported dataset");
    waitForElementToBeClickable(deleteButton.get(0), "Delete button is not clickable");
    clickDelete(deleteButton.get(0)).clickConfirm();
    waitForElementToDisappear(root);
  }

  public String getDatasetName() {
    return datasetName.get(datasetName.size() - 1).getText();
  }
  
  public String getFirstDataSetName() {
    return datasetName.get(0).getText().trim();
  }

  private DeleteDatasetWindow clickDelete(WebElement element) {
    element.click();
    return new DeleteDatasetWindow(driver);
  }

  private void collectElementsOfRow() {
    datasetName = root.findElements(By.cssSelector(".sa-imports-table-dataset-name"));
    datasetUnit = root.findElements(By.cssSelector(".sa-imports-table-dataset-units"));
    editButton = root.findElements(By.xpath("//a[contains(@data-link-type,'editName')]"));
    deleteButton = root.findElements(By.xpath("//a[contains(@data-link-type,'delete')]"));
  }

}
