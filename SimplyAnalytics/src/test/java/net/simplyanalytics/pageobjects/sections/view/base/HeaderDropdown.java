package net.simplyanalytics.pageobjects.sections.view.base;

import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.views.BarChartPage;
import net.simplyanalytics.pageobjects.pages.main.views.BaseViewPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.DataFolderPanel;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.windows.AliasLocationWindow;
import net.simplyanalytics.pageobjects.windows.MetadataWindow;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Random;

public class HeaderDropdown extends BasePage {
  
  private GenericTableViewPanel tableViewPanel;
  
  // sorting
  @FindBy(xpath = ".//span[contains(normalize-space(.), 'Sort')" + " and  contains(normalize-space(.), 'A-Z')]")
  private WebElement aZ;
  
  @FindBy(xpath = ".//span[contains(normalize-space(.), 'Sort'" + ") and  contains(normalize-space(.), 'Z-A')]")
  private WebElement zA;
  
  @FindBy(xpath = ".//span[text()='Sort, smallest to largest']")
  private WebElement smallestLargest;
  
  @FindBy(xpath = ".//span[text()='Sort, largest to smallest']")
  private WebElement largestSmallest;
  
  @FindBy(xpath = ".//span[text()='Sort by data variables']")
  private WebElement dataVariables;
  
  @FindBy(xpath = ".//span[text()='Reverse sort order']")
  private WebElement reverse;
  
  // Favorites
  @FindBy(xpath = ".//span[text()='Add to Favorites']")
  private WebElement addFavorites;
  
  @FindBy(xpath = ".//span[text()='Remove from Favorites']")
  private WebElement removeFavorites;
  
  // Location
  @FindBy(xpath = ".//span[text()='Add Alias Location Name']")
  private WebElement addAliasLocationName;
  
  @FindBy(xpath = ".//span[text()='Edit Alias Location Name']")
  private WebElement editAliasLocationName;
  
  @FindBy(xpath = ".//span[text()='Remove from this Report']")
  private WebElement hideLocation;
  
  // Data Variable
  @FindBy(xpath = ".//span[text()='View Metadata']")
  private WebElement viewMetadata;
  
  @FindBy(xpath = ".//span[text()='Remove from this Report']")
  private WebElement hideDataVariable;
  
  @FindBy(css = ".x-box-item[id*='menu-create-bar-chart']")
  private WebElement createBarChartButton;

  @FindBy(xpath = ".//span[text()='Open Data Folder']")
  private WebElement openDataFolder;

  @FindBy(xpath = ".//span[text()='Add Other Years']")
  private WebElement addOtherYears;

  @FindBy(xpath = ".//span[text()='Add Count']")
  private WebElement addCount;

  @FindBy(xpath = ".//span[text()='Add Percent']")
  private WebElement addPercent;

  
  @SuppressWarnings("ucd")
  protected WebElement root;
  
  /**
   * HeaderDropdown constructor.
   * 
   * @param tableViewPanel GenericTableViewPanel
   * @param driver         WebDriver
   * @param root           header dropdown WebElement
   */
  public HeaderDropdown(GenericTableViewPanel tableViewPanel, WebDriver driver, WebElement root) {
    super(driver, root);
    this.tableViewPanel = tableViewPanel;
    this.root = root;
  }
  
  @Override
  protected void isLoaded() {
    // no need, if root is loaded, all the elements appears at once
  }
  
  // sorting
  /**
   * Click on the Sort A-Z button.
   * 
   * @return sorted table
   */
  @Step("Click on the Sort A-Z button")
  public BaseViewPage clickSortAZ() {
    logger.debug("Click on the Sort A-Z button");
    aZ.click();
    return this.tableViewPanel.viewType.getNewPage(driver);
  }
  
  /**
   * Click on the Sort Z-A button.
   * 
   * @return sorted table
   */
  @Step("Click on the Sort Z-A button")
  public BaseViewPage clickSortZA() {
    logger.debug("Click on the Sort Z-A button");
    zA.click();
    return this.tableViewPanel.viewType.getNewPage(driver);
  }
  
  /**
   * Click on the Reverse sort order button.
   * 
   * @return sorted table
   */
  @Step("Click on the Reverse sort order button")
  public BaseViewPage clickReverseSort() {
    logger.debug("Click on the Reverse sort order button");
    waitForElementToBeClickable(reverse, "Reverse sort is not clickable").click();
    return this.tableViewPanel.viewType.getNewPage(driver);
  }
  
  /**
   * Click on the Sort, smallest to largest button.
   * 
   * @return sorted table
   */
  @Step("Click on the Sort, smallest to largest button")
  public BaseViewPage clickSortSmallestToLargest() {
    logger.debug("Click on the Sort, smallest to largest button");
    smallestLargest.click();
    return this.tableViewPanel.viewType.getNewPage(driver);
  }
  
  /**
   * Click on the Sort, largest to smallest button.
   * 
   * @return sorted table
   */
  @Step("Click on the Sort, largest to smallest button")
  public BaseViewPage clickSortLargestToSmallest() {
    logger.debug("Click on the Sort, largest to smallest button");
    largestSmallest.click();
    return this.tableViewPanel.viewType.getNewPage(driver);
  }
  /**
   * Click on the Sort by variables button.
   * 
   * @return sorted table
   */
  @Step("Click on the Sort by data variables button")
  public BaseViewPage clickSortByDataVariables() {
    logger.debug("Click on the Sort by data variables button");
    dataVariables.click();
    return this.tableViewPanel.viewType.getNewPage(driver);
  }
  
  // Favorites
  /**
   * Click on the Add to Favorites button.
   * 
   * @return tableViewPanel
   */
  @Step("Click on the Add to Favorites button")
  public BaseViewPage clickAddFavorites() {
    logger.debug("Click on the Add to Favorites button");
    waitForElementToStop(addFavorites);
    addFavorites.click();
    return this.tableViewPanel.viewType.getNewPage(driver);
  }
  
  /**
   * Click on the Remove from Favorites button.
   * 
   * @return tableViewPanel
   */
  @Step("Click on the Remove from Favorites button")
  public BaseViewPage clickRemoveFavorites() {
    logger.debug("Click on the Remove from Favorites button");
    removeFavorites.click();
    return this.tableViewPanel.viewType.getNewPage(driver);
  }
  
  // Location
  /**
   * Click on the Add Alias Location Name button.
   * 
   * @return AliasLocationWindow
   */
  @Step("Click on the Add Alias Location Name button")
  public AliasLocationWindow clickAddAliasLocationName() {
    // not so fast...
    sleep(500);
    
    logger.debug("Click on the Add Alias Location Name button");
    addAliasLocationName.click();
    return new AliasLocationWindow(driver);
  }
  
  /**
   * Click on the Edit Alias Location Name button.
   * 
   * @return AliasLocationWindow
   */
  @Step("Click on the Edit Alias Location Name button")
  public AliasLocationWindow clickEditAliasLocationName() {
    logger.debug("Click on the Edit Alias Location Name button");
    editAliasLocationName.click();
    return new AliasLocationWindow(driver);
  }
  
  /**
   * Click on the Hide Location from this Report button.
   * 
   * @return tableViewPanel
   */
  @Step("Click on the Hide Location from this Report button")
  public BaseViewPage clickHideLocation() {
    logger.debug("Click on the Hide Location from this Report button");
    waitForElementToBeClickable(hideLocation, "Remove button is not clickable").click();
    return this.tableViewPanel.viewType.getNewPage(driver);
  }
  
  // Data variable
  /**
   * Click on the View Metadata button.
   * 
   * @return MetadataWindow
   */
  @Step("Click on the View Metadata button")
  public MetadataWindow clickViewMetadata() {
    logger.debug("Click on the View Metadata button");
    viewMetadata.click();
    waitForLoadingToDisappear();
    return new MetadataWindow(driver);
  }
  
  /**
   * Click on the Hide Data Variable from this Report button.
   * 
   * @return tableViewPanel
   */
  @Step("Click on the Hide Data Variable from this Report button")
  public BaseViewPage clickHideDataVariable() {
    logger.debug("Click on the Hide Data Variable from this Report button");
    hideDataVariable.click();
    return this.tableViewPanel.viewType.getNewPage(driver);
  }
  
  /**
   * Click on the Create BarChart button.
   * 
   * @return BarChartPage
   */
  @Step("Click on the Create BarChart button")
  public BarChartPage clickCreateBarChartButton() {
    logger.debug("Click on the Create BarChart button");
    createBarChartButton.click();
    return new BarChartPage(driver);
  }
  
  public boolean isAddedToFavorites() {
    return isPresent(removeFavorites);
  }

  /**
   * Click on the Open Data Folder button
   *
   * @return DataByCategoryDropwDown
   */
  @Step("Click on the Open Data Folder button")
  public DataFolderPanel clickOpenDataFolder() {
    logger.debug("Click on the Open Data Folder button");
    openDataFolder.click();
    waitForLoadingToDisappear();
    return new DataFolderPanel(driver);
  }

  /**
   * Click on the Add Count button
   *
   * @return BaseViewPage
   */
  @Step("Click on the Add Count button")
  public BaseViewPage clickAddCount() {
    logger.debug("Click on the Add Count button");
    waitForLoadingToDisappear();
    waitForElementToBeClickable(addCount, "Element Add Count is not clickable.").click();
    return this.tableViewPanel.viewType.getNewPage(driver);
  }

  /**
   * Click on the Add Percent button
   *
   * @return BaseViewPage
   */
  @Step("Click on the Add Percent button")
  public BaseViewPage clickAddPercent() {
    logger.debug("Click on the Add Percent button");
    waitForLoadingToDisappear();
    waitForElementToBeClickable(addPercent, "Element Add Count is not clickable.").click();
    return this.tableViewPanel.viewType.getNewPage(driver);
  }

  @Step("Add an other year data variable.")
  public BaseViewPage addOtherYear() {
    logger.debug("Click on the Add Other Year button");
    clickAddOtherYears();
    //logger.debug("Click on a random Other Year");
    //sleep(2000);
    clickExactYear("2020");
    return this.tableViewPanel.viewType.getNewPage(driver);
  }

  @Step("Add an other year data variable with year 2028.")
  public BaseViewPage addOtherYearCanada() {
    logger.debug("Click on the Add Other Year button");
    clickAddOtherYears();

    WebDriverWait wait = new WebDriverWait(driver, 10);
    WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//span[@class='x-menu-item-text ' and contains(@id, 'menucheckitem') and contains(text(), '2028')]")));

    // Sleep for 2 seconds
    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    clickExactYearCanada("2028");
    return this.tableViewPanel.viewType.getNewPage(driver);
  }

  @Step("Click on the other year.")
  public void clickExactYearCanada(String year) {

    logger.debug("Clicking on the element containing the year {}", year);

    // Construct the XPath selector based on the provided year
    String xpathSelector = ".//span[contains(@class, 'x-menu-item-text') and contains(text()," + year + ")]";

    // Wait for the element to become clickable
    WebDriverWait wait = new WebDriverWait(driver, 10); // Wait up to 10 seconds
    WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathSelector)));

    // Click on the element
    element.click();
  }

  @Step("Click on the Add Other Year button")
  public void clickAddOtherYears() {
    addOtherYears.click();
    waitForLoadingToDisappear();
  }

  @Step("Click on the other year.")
  public void clickExactYear(String year) {
    List<WebElement> otherYearsList = driver.findElements(By.xpath(".//span[@class='x-menu-item-text ' and contains(@id, 'menucheckitem')]"));
    for (WebElement webElement : otherYearsList) {
      if (webElement.getText().equals(year)) {
        logger.debug("Click on the Year: " + year);
        webElement.click();
      }
    }
  }

  @Step("Click on the other year.")
  public String clickRandomYear() {
    List<WebElement> otherYearsList = driver.findElements(By.xpath(".//span[@class='x-menu-item-text ' and contains(@id, 'menucheckitem')]"));
    int random = new Random().nextInt(otherYearsList.size());
    logger.debug("Click on the Year: " + otherYearsList.get(random).getText());
    otherYearsList.get(random).click();
    return otherYearsList.get(random).getText();
  }

//  @Step("Click on a random other year")
//  public void clickRandomOtherYear() {
//      int rand = new Random().nextInt(otherYearsList.size());
//      logger.debug("Click on the Year: " + otherYearsList.get(rand).getText());
//      otherYearsList.get(rand).click();
//  }

}