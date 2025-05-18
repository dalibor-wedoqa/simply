package net.simplyanalytics.pageobjects.sections.ldb.data.bydataset;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.Page;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.MainPage;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.pages.main.views.RelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RingStudyPage;
import io.qameta.allure.Step;

public class DataByImportedDataseDropdown extends BasePage {
  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-imports-data-browse-window");
  @SuppressWarnings("ucd")
  protected WebElement root;
  
  @FindBy(css = ".sa-close")
  private WebElement closeXButton;
  
  
  private DatasetSearchImportedDataPanel datasetImportedDataPanel;
  
  /**
   * DataByDatasetDropDown constructor.
   * 
   * @param driver         WebDriver
   * @param rootFolderName root folder name
   */
  public DataByImportedDataseDropdown(WebDriver driver, String rootFolderName) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
    
    datasetImportedDataPanel = new DatasetSearchImportedDataPanel(driver);
  }
  
  @Override
  protected void isLoaded() {
    // TODO
  }
  
  public DatasetSearchImportedDataPanel getDatasetSearchResultPanel() {
    return datasetImportedDataPanel;
  }
  
  public DatasetImportedNavigationPanel getDatasetNavigationPanel() {
    return new DatasetImportedNavigationPanel(driver);
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
  
  public boolean isDisplayed() {
    return isPresent(root);
  }


}
