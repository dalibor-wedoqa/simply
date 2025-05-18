package net.simplyanalytics.pageobjects.sections.ldb.data.bydataset;

import net.simplyanalytics.enums.Page;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.MainPage;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.pages.main.views.RelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RingStudyPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class DataByDatasetDropDown extends BasePage {
  
  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-data-folders-browser");
  @SuppressWarnings("ucd")
  protected WebElement root;
  
  @FindBy(css = ".sa-close")
  private WebElement closeXButton;

  //N.B. Novi elementi ispod komentara dva @FindBy
  @FindBy(css = ".sa-data-folders-browser-header.sa-simple-container.sa-simple-container-default.x-border-box > .sa-close.x-border-box.x-component.x-component-default")
  private WebElement getCloseButtonForDataPanel;

  @FindBy(xpath = "//body/div[@class='sa-project-view sa-project-view-default x-border-box']/div//a")
  private WebElement getDoneButtonForTheTimesSeriesButton;

  @FindBy(css = ".sa-data-folders-browser-filter-field input")
  private WebElement searchField;
  
  @FindBy(css = ".sa-text-field-clear")
  private WebElement clearSearchField;
  
  private DatasetNavigationPanel datasetNavigationPanel;
  private DatasetSearchResultPanel datasetSearchResultPanel;
  
  /**
   * DataByDatasetDropDown constructor.
   * 
   * @param driver         WebDriver
   * @param rootFolderName root folder name
   */
  public DataByDatasetDropDown(WebDriver driver, String rootFolderName) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
    
    datasetNavigationPanel = new DatasetNavigationPanel(driver, rootFolderName);
    datasetSearchResultPanel = new DatasetSearchResultPanel(driver);
  }
  
  @Override
  protected void isLoaded() {
    // TODO
  }
  
  public DatasetNavigationPanel getDatasetNavigationPanel() {
    return datasetNavigationPanel;
  }
  
  public DatasetSearchResultPanel getDatasetSearchResultPanel() {
    return datasetSearchResultPanel;
  }
  
  /**
   * Click on the close button.
   * 
   * @param page page
   * @return page
   */
  @Step("Click on the close button")
  public MainPage clickClose(Page page) {
    logger.debug("Click on the close button");
    closeXButton.click();
    switch (page) {
      case RANKING_VIEW:
        return new RankingPage(driver);
      case COMPARISON_REPORT_VIEW:
        return new ComparisonReportPage(driver);
      case BUSINESSES_VIEW:
        return new BusinessesPage(driver);
      case MAP_VIEW:
        return new MapPage(driver);
      case RING_STUDY_VIEW:
        return new RingStudyPage(driver);
      case RELATED_DATA_VIEW:
        return new RelatedDataReportPage(driver);
      default:
        throw new AssertionError();
    }
  }
  
  @Step("Click on the Close button")
  public void clickCloseVoid() {
    logger.debug("Click on the close button");
    closeXButton.click();
  }
  //N.B. dodao kod
  @Step("Click on the Close button for Data Panel")
  public void clickCloseButtonForDataPanel() {
    logger.debug("Click on the close button");
    getCloseButtonForDataPanel.click();
  }
  //N.B. dodao kod
  @Step("Click on the Done button of the time series view")
  public void clickDoneButtonForTheTimesSeriesButton() {
    logger.debug("Click on the close button");
    getDoneButtonForTheTimesSeriesButton.click();
  }

  
  public boolean isDisplayed() {
    return isPresent(root);
  }
  
  @Step("Enter text in the search field")
  public void searchText(String text) {
    logger.debug("Enter text in the search field: " + text);
    searchField.click();
    searchField.clear();
    searchField.sendKeys(text);
  }
  
  @Step("Clear search field")
  public void clickClearSearchFiled() {
    logger.debug("Click clear search field button");
    waitForElementToBeClickable(clearSearchField, "Clear search filed button is not clickable").click();
  }
  
}
