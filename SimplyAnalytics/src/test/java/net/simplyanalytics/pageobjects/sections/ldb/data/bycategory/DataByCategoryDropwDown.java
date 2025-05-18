package net.simplyanalytics.pageobjects.sections.ldb.data.bycategory;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.Page;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.MainPage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditMapPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.BarChartPage;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.CrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.pages.main.views.RelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RingStudyPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScatterPlotPage;
import net.simplyanalytics.pageobjects.pages.main.views.TimeSeriesPage;
import net.simplyanalytics.pageobjects.sections.ldb.data.bydataset.DataByDatasetDropDown;
import net.simplyanalytics.pageobjects.sections.ldb.data.filter.BaseFilter;
import io.qameta.allure.Step;

public class DataByCategoryDropwDown extends BasePage {

  protected static final By ROOT_LOCATOR = By.xpath("//div[contains(@class, 'sa-data-search-window')]");
  protected WebElement root;

  @FindBy(css = ".sa-data-search-results-header")
  private List<WebElement> dataFolderPath;

  @FindBy(css = ".sa-close")
  private WebElement closeXButton;

  private DataSearchResultPanel dataSearchResultPanel;
  private DataFilterResultPanel dataFilterResultPanel;
  private BaseFilter baseFilter;

  public DataByCategoryDropwDown(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
    dataSearchResultPanel = new DataSearchResultPanel(driver, this);
    dataFilterResultPanel = new DataFilterResultPanel(driver);
  }

  @Override
  public void isLoaded() {
    waitForElementToDisappearWithCustomTime(By.cssSelector(".sa-data-search-results-mask"), 20);
    waitForElementToAppearByLocator(ROOT_LOCATOR, "Category dorpdown is not present");
  }

  public DataSearchResultPanel getDataSearchResultsPanel() {
    return dataSearchResultPanel;
  }

  public DataFilterResultPanel getDataFilterResultPanel() {
    return dataFilterResultPanel;
  }

  public BaseFilter getBaseFilter() {
    baseFilter = new BaseFilter(driver);
    return baseFilter;
  }

  public void addRandomFilter() {
    dataFilterResultPanel.addRandomFilter();
  }

  /**
   * Click on data variable.
   * 
   * @param nameOfData name of data
   */
  @Step("Click on data variable \"{0}\"")
  public void clickOnADataResult(DataVariable nameOfData) {
    logger.debug("Click on data variable " + nameOfData);
    getDataSearchResultsPanel().getAllDataElements().stream()
        .filter(i -> getDataSearchResultsPanel().getDataVariableText(i).equalsIgnoreCase(nameOfData.getName())).findAny().get().click();
  }

  /**
   * Click on the close button.
   * 
   * @param page page
   * @return choosen page
   */
  @Step("Click on the close button")
  public MainPage clickClose(Page page) {
    logger.debug("Click on the close button");
    closeXButton.click();
    waitForElementToDisappear(root);
    switch (page) {
      case RANKING_VIEW:
        return new RankingPage(driver);
      case COMPARISON_REPORT_VIEW:
        return new ComparisonReportPage(driver);
      case BUSINESSES_VIEW:
        return new BusinessesPage(driver);
      case MAP_VIEW:
        return new MapPage(driver);
      case EDIT_MAP:
        return new EditMapPage(driver);
      case RING_STUDY_VIEW:
        return new RingStudyPage(driver);
      case RELATED_DATA_VIEW:
        return new RelatedDataReportPage(driver);
      case TIME_SERIES_VIEW:
        return new TimeSeriesPage(driver);
      case BAR_CHART:
        return new BarChartPage(driver);
      case SIMMONS_CROSSTAB_PAGE:
        return new CrosstabPage(driver);
      case EDIT_SIMMONS_CROSSTAB_PAGE:
        return new EditCrosstabPage(driver);
      case SCATTER_PLOT:
        return new ScatterPlotPage(driver);
      case SCARBOROUGH_CROSSTAB_PAGE:
        return new ScarboroughCrosstabPage(driver);
      case EDIT_SCARBOROUGH_CROSSTAB_PAGE:
        return new EditScarboroughCrosstabPage(driver);
      case NEW_VIEW:
        return new NewViewPage(driver);
      default:
        throw new AssertionError();
    }
  }

  /**
   * Click on a random data variable.
   * 
   * @return random selected data name and selected year
   */
  @Step("Click on a random data variable")
  public String clickOnARandomDataResult() {
    System.out.println("Step: Retrieve all data elements from the search results panel");
    List<WebElement> elementList = getDataSearchResultsPanel().getAllDataElements();
    System.out.println("Total number of data elements available: " + elementList.size());

    System.out.println("Step: Select a random index from the data element list");
    int index = new Random().nextInt(elementList.size() - 1);
    WebElement randomData = elementList.get(index);
    String randomSelectedDataName = randomData.getText();
    System.out.println("Randomly selected data variable: " + randomSelectedDataName);

    logger.debug("Click on random data variable");
    logger.trace(randomSelectedDataName);

    System.out.println("Step: Attempt to click on the randomly selected data variable");
    int attempts = 0;
    while (attempts < 2) {
      try {
        System.out.println("Attempt " + (attempts + 1) + ": Trying to click on the data variable");
        waitForElementToBeClickable(randomData, "Element is not clickable").click();
        System.out.println("Successfully clicked on the data variable: " + randomSelectedDataName);
        break;
      } catch (Exception e) {
        System.out.println("Attempt " + (attempts + 1) + " failed: " + e.getMessage());
      }
      attempts++;
    }

    System.out.println("Step: Retrieve the selected year from the data filter result panel");
    String yearSelected = getDataFilterResultPanel().getSelectedYear();
    System.out.println("Selected year for the data variable: " + yearSelected);

    System.out.println("Step: Returning the selected data variable and year");
    return randomSelectedDataName + ", " + yearSelected;
  }


  @Step("Click on a Year")
  public void clickOnYear(String year) {
    logger.debug("Click on year: " + year);
    root.findElement(By.cssSelector(".sa-sidebar-data-section-radio[data-value='" + year + "']"))
        .click();
    sleep(2000);
    waitForElementToAppearByLocator(By.xpath(".//span[text() = 'Year = " + year + "']"), "Filter: Year = " + year + " is not appeared");
    baseFilter.placeActiveFilters();
  }

  public boolean isResultsFound() {
    return dataSearchResultPanel.isResultsFound();
  }

  public boolean isDisplayed() {
    return isPresent(root);
  }
  
  public String[] getFirstFolderPath() {
    String[] path = dataFolderPath.get(0).findElement(By.cssSelector("span.sa-data-search-results-header-path"))
        .getAttribute("data-path").trim().split(" » ");
    //reverse the array
    for(int i = 0; i < path.length; i++) {
      String util = path[i];
      path[i] = path[path.length- i - 1];
      path[path.length - i - 1] = util;
    }
    return path;
  }
  
  @Step("Click Open data folder link")
  public DataByDatasetDropDown clickFirstOpenDataFolderLink() {
    logger.debug("Click on Open data folder link");
    String rootFolder = getFirstFolderPath()[0];
    waitForElementToBeClickable(dataFolderPath.get(0).findElement(By.cssSelector(".sa-data-search-results-header-open-data-folder-link")), 
        "Open data fodler link is not clickable").click();
    waitForLoadingToDisappear();
    return new DataByDatasetDropDown(driver, rootFolder);
  }
}
