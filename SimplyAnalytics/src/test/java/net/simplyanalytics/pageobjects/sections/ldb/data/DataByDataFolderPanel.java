package net.simplyanalytics.pageobjects.sections.ldb.data;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.stream.Collectors;

import net.simplyanalytics.enums.DataPackage;
import net.simplyanalytics.enums.Dataset;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DataByDatasetDropDown;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DataByImportedDataseDropdown;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.crypto.Data;

public class DataByDataFolderPanel extends DataBaseBrowsePanel {

  protected static final By ROOT_LOCATOR = By
      .cssSelector("[id*='data-sidebar'] .sa-data-folders-list");
  protected WebElement root;

  @FindBy(css = ".sa-data-folders-list-dataset.x-unselectable:not([data-dataset^='griqa'])")
  private List<WebElement> allNotImportedDataElements;
  
  @FindBy(css = ".sa-data-folders-list-dataset.x-unselectable[data-dataset^='griqa']")
  private List<WebElement> importedDataElements;

  @FindBy(css = ".sa-data-folders-list-dataset.x-unselectable.sa-data-folders-list-dataset-is-non-import:not([disabled=\"\"])")
  private List<WebElement> dataVendors;

  @FindBy(css = ".sa-data-folders-list-package")
  private List<WebElement> dataPackages;
  
  @FindBy(css = ".sa-data-folders-list-dataset")
  private List<WebElement> datasets;

  public DataByDataFolderPanel(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
  }

  @Override
  protected void isLoaded() {
  }

  @Step("Click on dataset {0}")
  public void clickDatasetVoid(Dataset dataset) throws ElementClickInterceptedException {
    String datasetName = dataset.getDatasetName();
    System.out.println("Attempting to click on dataset: " + datasetName);

    try {
      WebDriverWait waitShort = new WebDriverWait(driver, 1);
      By datasetXpath = By.xpath("//span[text()='" + datasetName + "']/..");

      WebElement scrollContainer = driver.findElement(By.className("sa-data-folders-list"));
      System.out.println("Located scroll container for dataset list.");

      JavascriptExecutor js = (JavascriptExecutor) driver;
      int maxScrolls = 15; // arbitrary limit to avoid infinite scroll loop
      boolean elementVisible = false;
      WebElement specificDatasetElement = null;

      for (int i = 0; i < maxScrolls; i++) {
        try {
          specificDatasetElement = waitShort.until(ExpectedConditions.presenceOfElementLocated(datasetXpath));
          if (specificDatasetElement.isDisplayed()) {
            System.out.println("Element is visible on scroll iteration: " + i);
            elementVisible = true;
            break;
          }
        } catch (Exception e) {
          System.out.println("Element not visible yet on scroll iteration: " + i);
        }
        js.executeScript("arguments[0].scrollTop = arguments[0].scrollTop + 100;", scrollContainer);
        Thread.sleep(200); // small delay to allow DOM update
      }

      if (!elementVisible || specificDatasetElement == null) {
        System.out.println("Dataset element not visible after scrolling.");
        logger.error("Could not find the element for " + datasetName);
        throw new ElementNotInteractableException("Specific dataset element not visible.");
      }

      // Now try to click
      try {
        Actions actions = new Actions(driver);
        actions.moveToElement(specificDatasetElement).perform();
        System.out.println("Moved to dataset element: " + datasetName);
        logger.debug("Moved to dataset element: " + datasetName);

        specificDatasetElement.click();
        System.out.println("Clicked on dataset: " + datasetName);
        logger.debug("Clicked on dataset: " + datasetName);

      } catch (Exception e) {
        System.out.println("Failed to click on dataset after scrolling: " + datasetName);
        logger.error("Could not click the element for " + datasetName, e);
        throw new ElementNotInteractableException("Specific dataset element not clickable after scrolling.");
      }

    } catch (Exception e) {
      System.out.println("Exception while clicking dataset: " + datasetName);
      logger.error("Fallback: Clicking dataset from getDataset() method", e);
      logger.debug("Clicking on dataset " + datasetName);
      getDataset(dataset).click();
      throw new ElementNotInteractableException("Specific dataset element not found or interactable.");
    }
  }


//  @Step("Click on dataset {0}") //the method throws this exception in case the dataset is disabled
//  public void clickDatasetVoid(Dataset dataset) throws ElementClickInterceptedException {
//    String datasetName = dataset.getDatasetName();
//    logger.debug("Attempting to click on dataset: " + datasetName);
//
////    if ("MRI-SimmonsLOCAL".equals(datasetName)) {
////      logger.debug("Dataset name is MRI-SimmonsLOCAL. Attempting to locate the specific span element.");
////
////      try {
////        WebDriverWait wait = new WebDriverWait(driver, 10);
////        WebElement specificDatasetElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'sa-data-folders-tree-item')][.//span[text()='MRI-SimmonsLOCAL']]")));
////        logger.debug("Found specific dataset element. Scrolling to it.");
////
////        Actions actions = new Actions(driver);
////        actions.moveToElement(specificDatasetElement).perform();
////
////        logger.debug("Attempting to click the specific dataset element.");
////        specificDatasetElement.click();
////      } catch (NoSuchElementException e) {
////        logger.error("Could not find the element for MRI-SimmonsLOCAL.", e);
////        throw new ElementNotInteractableException("Specific dataset element not found or interactable.");
////      }
////    } else if ("Consumer Buying Power".equals(datasetName)) {
////      logger.debug("Dataset name is Consumer Buying Power. Attempting to locate the specific span element.");
////      try {
////        WebDriverWait wait = new WebDriverWait(driver, 10);
////        WebElement specificDatasetElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Consumer Buying Power']/..")));
////        logger.debug("Found specific dataset element. Scrolling to it.");
////
////        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", specificDatasetElement);
////
////
////        logger.debug("Attempting to click the specific dataset element.");
////        specificDatasetElement.click();
////      } catch (Exception e) {
////        logger.error("Could not find the element for Consumer Buying Power.", e);
////        throw new ElementNotInteractableException("Specific dataset element not found or interactable.");
////      }
////    } else {
//
//      try {
//        WebDriverWait wait = new WebDriverWait(driver, 1);
//        System.out.println(datasetName);
//        WebElement specificDatasetElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='"+datasetName+"']/..")));
//        WebElement scrollContainer = driver.findElement(By.id("data-folders-list-1044"));
//        logger.debug("Found specific dataset element. Scrolling to it.");
//
//        Actions actions = new Actions(driver);
//        actions.moveToElement(specificDatasetElement).perform();
//
//        logger.debug("Attempting to click the specific dataset element.");
//        specificDatasetElement.click();
//      } catch (NoSuchElementException e) {
//        logger.error("Could not find the element for " + datasetName, e);
//        logger.debug("Clicking on dataset" + datasetName);
//        getDataset(dataset).click();
//        throw new ElementNotInteractableException("Specific dataset element not found or interactable.");
//        }
//  }


  /**
   * Click on a data folder.
   * 
   * @param dataset data folder
   * @return
   */
  @Step("Click on the dataset: {0}")
  public DataByDatasetDropDown clickDataset(Dataset dataset) {
    clickDatasetVoid(dataset);
      return new DataByDatasetDropDown(driver, dataset.getRootName());
  }
  //previous code: clickDatasetVoid(dataset);
    //clickDatasetVoid(dataset);
    //    DataByDatasetDropDown datasetDropDown = new DataByDatasetDropDown(driver, dataset.getRootName());
    //    return datasetDropDown;

  @Step("Click on a random active dataset")
  public DataByDatasetDropDown clickRandomizedDataset() {
    int randomIndex = new Random().nextInt(dataVendors.size());
    dataVendors.get(randomIndex).click();
    Dataset dataset = Dataset.getDatasetByName(dataVendors.get(randomIndex).getText().trim());
    DataByDatasetDropDown datasetDropDown = new DataByDatasetDropDown(driver, dataset.getRootName());
    return datasetDropDown;
  }

  public boolean isDataSetClickable(Dataset dataset) {
    return getDataset(dataset).isEnabled();
  }

  private WebElement getDataset(Dataset dataset) {

    return root.findElement(By.cssSelector("[data-dataset='" + dataset.getId() + "']"));
  }
  
  @Step("Click imported dataset: {0}")
  public DataByImportedDataseDropdown clickImportedDataset(String dataSetName) {
    for(WebElement data : importedDataElements) {
      if(dataSetName.contains(data.findElement(By.cssSelector(".sa-data-folders-list-dataset-name")).getText().trim())) {
        waitForElementToBeClickable(data, "Dataset is not clickable").click();
        return new DataByImportedDataseDropdown(driver, dataSetName);
      }
    }
    throw new AssertionError("Imported dataset is not found");
  }
  
  @Step("Click on random imported dataset")
  public DataByImportedDataseDropdown clickRandomImportedDataset() {
    logger.debug("Click on random imported dataset");
    int randomIndex = new Random().nextInt(importedDataElements.size());
    logger.info("Dataset: " + importedDataElements.get(randomIndex).findElement(By.cssSelector(".sa-data-folders-list-dataset-name")).getText().trim());
    waitForElementToBeClickable(importedDataElements.get(randomIndex), "Dataset is not clickable").click();

    return new DataByImportedDataseDropdown(driver, 
        importedDataElements.get(randomIndex).findElement(By.cssSelector("span")).getText().trim());
  }
  
  
  /**
   * Click on a random data Dataset.
   * 
   * @return DataByDatasetDropDown
   */
  @Step("Click on a random data Dataset")
  public DataByDatasetDropDown clickRandomDataset() {
    int index = new Random().nextInt(allNotImportedDataElements.size() - 1);
    WebElement randomDataElement = allNotImportedDataElements.get(index);
    while(randomDataElement.getAttribute("class").contains("sa-data-folders-list-dataset-disabled")) {
      index = new Random().nextInt(allNotImportedDataElements.size() - 1);
      randomDataElement = allNotImportedDataElements.get(index);
    }
    String randomElementName = randomDataElement.getText();
    logger.debug("Click on a random data category: " + randomElementName);
    randomDataElement.click();
    return new DataByDatasetDropDown(driver,
        randomDataElement
            .findElements(By.xpath("//button[contains(@class, 'sa-data-folders-tree-item')]")).get(0)
            .getText());
  }

  public List<DataPackage> getDataPackages(){
    return dataPackages.stream().map(dataPackage -> DataPackage.getDataPackageByName(dataPackage
        .getText().trim())).collect(Collectors.toList());
  }
  
  public List<Dataset> getAllDatasets(String state) {
    if(state.contentEquals("Canada")) {
      return datasets.stream().map(dataset -> Dataset.getCanadianDatasetByName(dataset
          .findElement(By.cssSelector(".sa-data-folders-list-dataset-name")).getText().trim())).collect(Collectors.toList());
    }
    else {
      return datasets.stream().map(dataset -> Dataset.getDatasetByName(dataset
          .findElement(By.cssSelector(".sa-data-folders-list-dataset-name")).getText().trim())).collect(Collectors.toList());
     
    } 
  }
  
  public boolean isDatasetEnabled(Dataset dataset) {
    return root.findElement(By.xpath(".//div[contains(@class,'sa-data-folders-list-dataset')][.//span[normalize-space(.)='" 
        + dataset.getDatasetName() + "']]")).getAttribute("class").contains("sa-data-folders-list-dataset-disabled");
  }
}
