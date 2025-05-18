package net.simplyanalytics.pageobjects.pages.main.editviews;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.MainPage;
import net.simplyanalytics.pageobjects.pages.main.views.BarChartPage;
import net.simplyanalytics.pageobjects.pages.main.views.BaseViewPage;
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
import net.simplyanalytics.pageobjects.sections.toolbar.BaseToolbar;
import net.simplyanalytics.pageobjects.sections.view.editview.BaseEditViewPanel;
import org.openqa.selenium.WebElement;

public abstract class BaseEditViewPage extends MainPage {

  private BaseToolbar toolbar;
  private ViewType view;

  /**
   * BaseEditViewPage constructor.
   * 
   * @param driver WebDriver
   * @param view   ViewType
   */
  public BaseEditViewPage(WebDriver driver, ViewType view) {
    super(driver);
    this.view = view;
    //No visible toolbar
    //toolbar = new BaseToolbar(driver);
  }

  @Override
  public abstract BaseEditViewPanel getActiveView();

  @Override
  public BaseToolbar getToolbar() {
    return toolbar;
  }

  /**
   * Click Done button.
   * 
   * @return view page
   */
  public BaseViewPage clickDone() {
	  
    getActiveView().clickDoneButton();

    
    switch (view) {
      case COMPARISON_REPORT:
        return new ComparisonReportPage(driver);
      case MAP:
        return new MapPage(driver);
      case RANKING:
        return new RankingPage(driver);
      case BUSINESSES:
        return new BusinessesPage(driver);
      case RING_STUDY:
        return new RingStudyPage(driver);
      case RELATED_DATA:
        return new RelatedDataReportPage(driver);
      case QUICK_REPORT:
        return new QuickReportPage(driver);
      case TIME_SERIES:
        return new TimeSeriesPage(driver);
      case BAR_CHART:
        return new BarChartPage(driver);
      case CROSSTAB_TABLE:
          return new CrosstabPage(driver);
      case SCATTER_PLOT:
        return new ScatterPlotPage(driver);
      case SCARBOROUGH_CROSSTAB_TABLE:
        return new ScarboroughCrosstabPage(driver);
      case HISTOGRAM:
          return new HistogramPage(driver);
      default:
        throw new AssertionError("View type is not supported: " + view);
    }
  }

}
