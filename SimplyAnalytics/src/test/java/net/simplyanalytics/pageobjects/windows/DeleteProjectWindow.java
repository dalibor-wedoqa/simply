package net.simplyanalytics.pageobjects.windows;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.base.BasePage;
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

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class DeleteProjectWindow extends BasePage {
  
  private ViewType viewType;
  
  @FindBy(css = ".sa-delete-project-popup")
  private WebElement deleteContainerPopUpElement;
  
  @FindBy(css = ".sa-delete-project-popup-delete-btn")
  private WebElement deleteButtonElement;
  
  @FindBy(css = "a[class='sa-button x-unselectable sa-button-primary x-border-box']")
  private WebElement cancelButtonElement;
  
  @FindBy(css = ".sa-close")
  private WebElement closeWindowXButtonElement;
  
  public DeleteProjectWindow(WebDriver driver) {
    super(driver);
  }
  
  @Override
  public void isLoaded() {
    waitForElementToAppear(deleteContainerPopUpElement, "The delete pop up window is not loaded");
  }
  
  /**
   * Click on the Delete button.
   * @return ManageProjectPage
   */
  @Step("Click on the Delete button")
  public BasePage clickDelete() {
    logger.debug("Click on the Delete button");
    deleteButtonElement.click();
    if (isPresent(NewProjectLocationWindow.class, driver)) {
      return new NewProjectLocationWindow(driver);
    } else {
      viewType = new ViewChooserSection(driver).getTopViewType();
      switch (viewType) {
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
        throw new AssertionError("View type is not supported: " + this);
     }
    }
  }
}
