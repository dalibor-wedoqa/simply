package net.simplyanalytics.pageobjects.sections.toolbar;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.BaseEditViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditBarChartPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditBusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditHistogramPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditMapPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditQuickReportPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRankingPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRingStudyPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScatterPlotPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditTimeSeriesPage;
import net.simplyanalytics.pageobjects.pages.main.views.BarChartPage;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.CrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.QuickReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.pages.main.views.RelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RingStudyPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScatterPlotPage;
import net.simplyanalytics.pageobjects.pages.main.views.TimeSeriesPage;
import net.simplyanalytics.pageobjects.sections.viewchooser.ViewChooserSection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class ViewActionsMenu extends BasePage {
  
  protected static final By ROOT = By.cssSelector(".sa-menu-button-menu:not([style*='hidden'])");
  
  @SuppressWarnings("ucd")
  protected WebElement root;
  
  @FindBy(xpath = ".//span[text()='Edit View']")
  private WebElement editViewButton;
  
  @FindBy(xpath = "//a[span[text()='Create Data Table']]")
  private WebElement createTable;
  
  @FindBy(xpath = ".//span[text()='Delete View']")
  private WebElement deleteViewButton;
  
  private ViewType view;
  private ViewType nextViewType;
  
  /**
   * ViewActionsMenu constructor.
   * 
   * @param driver WebDriver
   * @param view   ViewType
   */
  
  public ViewActionsMenu(WebDriver driver, ViewType view) {
    super(driver, ROOT);
    root = driver.findElement(ROOT);
    this.view = view;
  }
  

  @Override
  public void isLoaded() {
    waitForElementToAppear(editViewButton, "Edit View button should appear");
    waitForElementToAppear(deleteViewButton, "Delete View button should appear");
  }
  
  /**
   * Click on the Edit View button.
   * 
   * @return choosen edit view type
   */
  @Step("Click on the Edit View button")
  public BaseEditViewPage clickEditView() {
    logger.debug("Click on the Edit View button");
    editViewButton.click();
    switch (view) {
      case COMPARISON_REPORT:
        return new EditComparisonReportPage(driver);
      case MAP:
        return new EditMapPage(driver);
      case RANKING:
        return new EditRankingPage(driver);
      case BUSINESSES:
        return new EditBusinessesPage(driver);
      case QUICK_REPORT:
        return new EditQuickReportPage(driver);
      case RING_STUDY:
        return new EditRingStudyPage(driver);
      case RELATED_DATA:
        return new EditRelatedDataReportPage(driver);
      case TIME_SERIES:
        return new EditTimeSeriesPage(driver);
      case BAR_CHART:
        return new EditBarChartPage(driver);
      case CROSSTAB_TABLE:
        return new EditCrosstabPage(driver);
      case SCATTER_PLOT:
        return new EditScatterPlotPage(driver);
      case SCARBOROUGH_CROSSTAB_TABLE:
        return new EditScarboroughCrosstabPage(driver);
      case HISTOGRAM:
          return new EditHistogramPage(driver);
      default:
        throw new AssertionError("View type is not supported: " + view);
    }
  }
  
  /**
   * Click on the Delete View button.
   * 
   * @return ManageProjectPage
   */
  @Step("Click on the Delete View button")
  public BasePage clickDeleteView() {
    logger.debug("Click on the Delete View button");
    deleteViewButton.click();
    
    nextViewType = new ViewChooserSection(driver).getTopViewType();
    
    switch (nextViewType) {
      case BUSINESSES:
        return new BusinessesPage(driver);
      case COMPARISON_REPORT:
        return new ComparisonReportPage(driver);
      case MAP:
        return new MapPage(driver);
      case QUICK_REPORT:
        return new QuickReportPage(driver);
      case RANKING:
        return new RankingPage(driver);
      case RELATED_DATA:
        return new RelatedDataReportPage(driver);
      case TIME_SERIES:
        return new TimeSeriesPage(driver);
      case RING_STUDY:
        return new RingStudyPage(driver);
      case CROSSTAB_TABLE:
        return new CrosstabPage(driver);
      case SCATTER_PLOT:
        return new ScatterPlotPage(driver);
      case BAR_CHART:
        return new BarChartPage(driver);
      case SCARBOROUGH_CROSSTAB_TABLE:
        return new ScarboroughCrosstabPage(driver);
      default:
        return new NewViewPage(driver);
    }
  }
  
  @Step("Click on the Create data Table button")
  public BasePage clickCreateDataTable() {
    logger.debug("Click on the Create data Table button");
    createTable.click();
    nextViewType = new ViewChooserSection(driver).getTopViewType();
    switch (view) {
    case MAP:
      return new RankingPage(driver);
    case BAR_CHART:
      return new ComparisonReportPage(driver);
    case SCATTER_PLOT:
      return new RankingPage(driver);
    case HISTOGRAM:
        return new RankingPage(driver);
    default:
      throw new AssertionError("View type is not supported: " + view);
    }
  }
  
  
  
}
