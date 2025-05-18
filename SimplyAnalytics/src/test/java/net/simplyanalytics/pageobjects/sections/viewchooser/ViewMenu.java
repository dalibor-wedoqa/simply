package net.simplyanalytics.pageobjects.sections.viewchooser;

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
import net.simplyanalytics.pageobjects.pages.main.views.HistogramPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.QuickReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.pages.main.views.RelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RingStudyPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScatterPlotPage;
import net.simplyanalytics.pageobjects.pages.main.views.TimeSeriesPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class ViewMenu extends BasePage {
  
  protected static final By root = By.cssSelector(".x-menu:not([style*='hidden'])");
  
  @FindBy(xpath = ".//span[text()='Edit']")
  private WebElement editButton;
  
  @FindBy(xpath = ".//span[text()='Rename']")
  private WebElement renameButton;
  
  @FindBy(xpath = ".//span[text()='Delete']")
  private WebElement deleteButton;
  
  private ViewType viewType;
  private ViewType nextViewType;
  
  /**
   * ViewMenu constructor.
   * @param driver WebDriver
   * @param viewType ViewType
   */
  public ViewMenu(WebDriver driver, ViewType viewType) {
    super(driver, root);
    
    switch (viewType) {
      case MAP:
      case COMPARISON_REPORT:
      case CROSSTAB_TABLE:
      case RANKING:
      case BUSINESSES:
      case QUICK_REPORT:
      case RING_STUDY:
      case RELATED_DATA:
      case TIME_SERIES:
      case BAR_CHART:
      case SCATTER_PLOT:
      case SCARBOROUGH_CROSSTAB_TABLE:
      case HISTOGRAM:
        this.viewType = viewType;
        break;
      default:
        throw new AssertionError("This is not a valid view type");
    }
  }
  
  @Override
  public void isLoaded() {
    waitForElementToAppear(editButton, "Edit button missing");
    waitForElementToAppear(renameButton, "Rename button missing");
    waitForElementToAppear(deleteButton, "Delete button missing");
  }
  
  /**
   * Click on the Edit button.
   * @return edit view page
   */
  @Step("Click on the Edit button")
  public BaseEditViewPage clickEdit() {
    logger.debug("Click on the Edit button");
    editButton.click();
    switch (viewType) {
      case MAP:
        return new EditMapPage(driver);
      case COMPARISON_REPORT:
        return new EditComparisonReportPage(driver);
      case RANKING:
        return new EditRankingPage(driver);
      case RING_STUDY:
        return new EditRingStudyPage(driver);
      case BUSINESSES:
        return new EditBusinessesPage(driver);
      case RELATED_DATA:
        return new EditRelatedDataReportPage(driver);
      case QUICK_REPORT:
        return new EditQuickReportPage(driver);
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
        throw new AssertionError("This is not a valid view type");
    }
  }
  
  /**
   * Click on the Rename button.
   * @return RenameViewPanel
   */
  @Step("Click on the Rename button")
  public RenameViewPanel clickRename() {
    logger.debug("Click on the Rename button");
    renameButton.click();
    return new RenameViewPanel(driver);
  }
  
  @Step("Click on the Delete button")
  public BasePage clickDelete() {
    logger.debug("Click on the Delete button");
    deleteButton.click();
    nextViewType = new ViewChooserSection(driver).getTopViewType();
    sleep(500);
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
      case HISTOGRAM:
          return new HistogramPage(driver);
      default:
        return new NewViewPage(driver);
    }
  }
}
