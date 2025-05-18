package net.simplyanalytics.pageobjects.sections.ldb.data;

import net.simplyanalytics.enums.DataBrowseType;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.sections.ldb.TabWithFavoritesMenuInterface;
import net.simplyanalytics.pageobjects.sections.ldb.TabWithRecentMenuInterface;
import net.simplyanalytics.pageobjects.sections.ldb.data.bycategory.DataByCategoryDropwDown;
import net.simplyanalytics.pageobjects.sections.ldb.search.RecentFavoriteMenu;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class DataTab extends BasePage
    implements TabWithRecentMenuInterface, TabWithFavoritesMenuInterface {

  protected static final By DROPDOWN_ROOT_LOCATOR = By.cssSelector(".x-panel");
    
  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-simple-container[id*='data']");
  protected final WebElement root;

  @FindBy(css = "input")
  private WebElement searchField;
  
  @FindBy(css = "[data-browse-by='category']")
  private WebElement categoryButton;
  
  @FindBy(css = "[data-browse-by='dataFolder']")
  private WebElement dataFolderButton;

  @FindBy(css = ".sa-data-sidebar-header-year-wrap .sa-sidebar-section-header-button-text")
  private WebElement year;
  
  @FindBy(xpath = ".//button[@title='Recent Data Variables']")
  private WebElement recentData;

  @FindBy(xpath = ".//button[@title='Favorite Data Variables']")
  private WebElement favoriteData;

  private DataBaseBrowsePanel browsePanel;

  public void isLoaded() {
    waitForElementToAppear(searchField, "The search field is not present");
  }

  /**
   * DataTab constructor.
   * 
   * @param driver     WebDriver
   * @param browseType DataBrowseType
   */
  public DataTab(WebDriver driver, DataBrowseType browseType) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
    switch (browseType) {
      case CATEGORY:
        this.browsePanel = new DataByCategoryPanel(driver);
        break;
      case DATA_FOLDER:
        this.browsePanel = new DataByDataFolderPanel(driver);
        break;       
      default:
        throw new AssertionError("Implement missing browse type panel: " + browseType);
    }
  }

  public DataTab(WebDriver driver) {
    this(driver, DataBrowseType.CATEGORY);
  }

  public DataBaseBrowsePanel getBrowsePanel() {
    return browsePanel;
  }

  /**
   * Enter search text into the search field and press enter.
   * 
   * @param searchText search text
   * @return DataByCategoryDropwDown
   */
  @Step("Enter {0} into the search field and press enter")
  public DataByCategoryDropwDown enterDataSearch(String searchText) {
    logger.debug("Enter " + searchText + " into the search field and press enter");
    searchField.click();
    searchField.clear();
    searchField.sendKeys(searchText + Keys.ENTER);
    waitForElementToAppear(driver.findElement(By.cssSelector(".sa-data-search-window")), "The data window did not appear");
    return new DataByCategoryDropwDown(driver);
  }

  /**
   * Click search by.
   * 
   * @param searchBy DataBrowseType
   * @return DataByCategoryPanel or DataByDatasetPanel
   */
  @Step("Click search by {0}")
  public DataBaseBrowsePanel clickSearchBy(DataBrowseType searchBy) {
    logger.debug("Click search by " + searchBy);
    switch (searchBy) {
      case CATEGORY:
        categoryButton.click();
        return new DataByCategoryPanel(driver);
      case DATA_FOLDER:
        dataFolderButton.click();
        waitForElementToStop(year);
        return new DataByDataFolderPanel(driver);
      default:
        throw new AssertionError("The search by is not supported: " + searchBy);
    }
    
  }
  
  /**
   * Click on the year dropdown.
   * 
   * @return YearDropdown
   */
  @Step("Click on the year dropdown")
  public YearDropdown openYearDropdown() {
    logger.debug("Click on the year dropdown");
    waitForElementToBeClickable(year, "the element is not clickable").click();
    return new YearDropdown(driver);
  }

  public class YearDropdown extends BasePage {

    protected final WebElement root;

    public YearDropdown(WebDriver driver) {
      super(driver, DROPDOWN_ROOT_LOCATOR);
      root = driver.findElement(DROPDOWN_ROOT_LOCATOR);
    }

    @Override
    protected void isLoaded() {
    }

    /**
     * Select year.
     * 
     * @param year year
     * @return DataByDatasetPanel
     */
    @Step("Select year {0}")
    public DataByDataFolderPanel clickYear(String year) {
      logger.debug("Select year " + year);
      root.findElement(By.xpath(".//span[text()=" + xpathSafe(year) + "]")).click();
      sleep(1000); // TODO some issue on the site
      return new DataByDataFolderPanel(driver);
    }
  }

  @Override
  public boolean isRecentPresent() {
    try {
      waitForElementToAppear(recentData, "");
      return true;
    } catch (TimeoutException e) {
      return false;
    }
  }

  @Override
  @Step("Click on the Recent Data Variables button")
  public RecentFavoriteMenu clickRecent() {
    logger.debug("Click on the Recent Data Variables button");
    waitForElementToAppear(recentData, "Recent Data Variables button is not loaded").click();
    return new RecentFavoriteMenu(driver);
  }

  @Override
  public boolean isFavoritesPresent() {
    return favoriteData.isDisplayed();
  }

  @Override
  @Step("Click on the Favorite Data Variables button")
  public RecentFavoriteMenu clickFavorites() {
    logger.debug("Click on the Favorite Data Variables button");
    waitForElementToAppearWithCustomTime(favoriteData, "Favorite Data Variables button is not loaded", 120).click();
    return new RecentFavoriteMenu(driver);
  }

  public void waitFavoritesToDisappear() {
	waitForElementToDisappear(favoriteData);
  }
  
}
