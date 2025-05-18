package net.simplyanalytics.pageobjects.sections.header.importpackage;

import java.io.File;
import java.util.List;
import java.util.UUID;

import net.simplyanalytics.core.DriverConfiguration;
import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.pageobjects.base.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class ImportSection extends BasePage {
  
  protected static final By rootLocator = By
      .xpath("//div[.//div[contains(@class,'step-indicator-step-active') and .//div[text()='Select a File']]]");
  protected WebElement root;

  static String folderAddress = File.separator + File.separator + "Desktop-fcae3u6" + File.separator + "auto" + File.separator + "importSM"+ File.separator;

  @FindBy(css = ".sa-file-input-input")
  private List<WebElement> inputField;
  
  @FindBy(css = ".sa-import-file-list-item-geographic-unit-dropdown")
  private List<WebElement> geographicUnitButton;
  
  @FindBy(xpath = "//a[contains(@id,'combo-button')]")
  private WebElement locationDropdown;
  
  @FindBy(css = ".sa-text-field-input")
  private WebElement inputFieldDataset;
  
  @FindBy(css = ".sa-import-file-list-item-remove-btn")
  private WebElement removeButton;
  
  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Back']]")
  private WebElement backButton;
  
  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Next']]")
  private WebElement nextButton;
  
  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Cancel']]")
  private WebElement cancelButton;
  
  @FindBy(css = ".sa-import-file-step-add-file-link")
  private WebElement addImportFile;
  
  public ImportSection(WebDriver driver) {
    super(driver, rootLocator);
    this.root = driver.findElement(rootLocator);
  }
  
  @Override
  public void isLoaded() {
    // waitForElementToAppear(root, "The Import window not appeared");
  }
  
  @Step("Click on Cancel")
  public void clickCancel() {
    logger.debug("Click on the Cancel button");
    cancelButton.click();
  }
  
  @Step("Click on Next")
  private void clickNextVoid() {
    logger.debug("Click on the Next button");
    nextButton.click();
  }
  
  public IdentifyDataView clickNext() {
    clickNextVoid();
    return new IdentifyDataView(driver);
  }
  
  public ErrorWindow clickNextWithError() {
    clickNextVoid();
    return new ErrorWindow(driver);
  }
  
  @Step("Click Add new import file")
  public void addImportFile(LocationType geographicUnit, DriverConfiguration driverConf) {
    logger.debug("Add new import file");
    int pom = inputField.size();
    addImportFile.click();
    waitForElementToAppear(inputField.get(pom), "Import file is not added");
    selectGeographicUnit(geographicUnit);
    sendPath(geographicUnit, driverConf);
  }
  
  @Step("Choose \"Zip Codes\" as geographic unit")
  public void selectGeographicUnit(LocationType geographicUnit) {
    geographicUnitButton.get(geographicUnitButton.size() - 1).click();
    GeographicUnitComboElements overflow = new GeographicUnitComboElements(driver);
    overflow.clickLocation(geographicUnit);
  }
  
  public String getContentOfInputField() {
    return inputField.get(inputField.size() - 1).getText();
  }

  public static String getFilePath(String originalPath) {
    System.out.println("DEBUG:Trying to access the file at path: " + originalPath);
    File file = new File(originalPath);

    if (file.exists() && file.canRead()) {
      System.out.println("DEBUG:File exists and can be read: " + originalPath);
      return originalPath;
    } else {
      System.out.println("DEBUG:File cannot be accessed. Defining a new file path.");
      String newPath = folderAddress; // <- change this to your needs
      System.out.println("DEBUG:New file path defined as: " + newPath);
      return newPath;
    }
  }
  
  @Step("Send path to file")
  public void sendPath(LocationType geographicUnit, DriverConfiguration driverConf) {

    logger.debug("Send path to upload .csv file button");
    switch (driverConf.getTestMode()) {
      case LOCAL:
        switch (geographicUnit) {
          case ZIP_CODE:
            File file = new File("." + File.separator + "src" + File.separator + "test" + File.separator + "resources"
                + File.separator + "import" + File.separator + "ZIPCodes.csv");
            inputField.get(inputField.size() - 1).sendKeys(file.getAbsolutePath());
            break;
          case CENSUS_TRACT:
            File file1 = new File("." + File.separator + "src" + File.separator + "test" + File.separator + "resources"
                + File.separator + "import" + File.separator + "CensusTrack.csv");
            inputField.get(inputField.size() - 1).sendKeys(file1.getAbsolutePath());
            break;
          case CITY:
            File file2 = new File("." + File.separator + "src" + File.separator + "test" + File.separator + "resources"
                + File.separator + "import" + File.separator + "Cities.csv");
            inputField.get(inputField.size() - 1).sendKeys(file2.getAbsolutePath());
            break;
          default:
            logger.debug("Invalid location Type");
            break;
        }
        break;
      case REMOTE:
        switch (geographicUnit) {
          case ZIP_CODE:
            inputField.get(inputField.size() - 1).sendKeys(folderAddress + "ZIPCodes.csv");
            break;
          case CENSUS_TRACT:
            inputField.get(inputField.size() - 1).sendKeys(folderAddress + "CensusTrack.csv");
            break;
          case CITY:
            inputField.get(inputField.size() - 1).sendKeys(folderAddress + "Cities.csv");
            break;
          default:
            logger.debug("Invalid location Type");
            break;
        }
        break;
    }
    
  }
  
  @Step("Type \"Import test\" as the name of this dataset")
  public String sendNameOfDataset() {
    String uuid = UUID.randomUUID().toString();
    logger.debug("Type \"Import test\" as the name of this dataset");
    inputFieldDataset.sendKeys(uuid);
    return uuid;
  }
  
  @Step("Type \"Import test\" as the name of this dataset")
  public void sendSameNameOfDataset() {
    logger.debug("Type \"Import test\" as the name of this dataset");
    inputFieldDataset.sendKeys("Import test");
  }


}
