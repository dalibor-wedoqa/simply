package net.simplyanalytics.enums;

import net.simplyanalytics.pageobjects.pages.main.views.BaseViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.CrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.QuickReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.pages.main.views.RelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RingStudyPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScarboroughCrosstabPage;
import net.simplyanalytics.pageobjects.pages.main.views.TimeSeriesPage;

import org.openqa.selenium.WebDriver;

public enum ViewType {
  
  COMPARISON_REPORT("Comparison Table", Page.COMPARISON_REPORT_VIEW),
  QUICK_REPORT("Quick Report", Page.QUICK_REPORT),
  MAP("Map", Page.MAP_VIEW),
  RANKING("Ranking", Page.RANKING_VIEW),
  BUSINESSES("Businesses", Page.BUSINESSES_VIEW),
  RING_STUDY("Ring Study", Page.RING_STUDY_VIEW),
  RELATED_DATA("Related Data Table", Page.RELATED_DATA_VIEW),
  TIME_SERIES("Time Series Table", Page.TIME_SERIES_VIEW),
  HISTOGRAM("Histogram", Page.HISTOGRAM),
  BAR_CHART("Bar Chart", Page.BAR_CHART),
  SCATTER_PLOT("Scatter Plot", Page.SCATTER_PLOT),
  CROSSTAB_TABLE("Simmons Crosstab", Page.SIMMONS_CROSSTAB_PAGE),
  SCARBOROUGH_CROSSTAB_TABLE("Scarborough Crosstab", Page.SCARBOROUGH_CROSSTAB_PAGE);
  
  private final String defaultName;
  private final Page page;
  
  ViewType(String defaultName, Page page) {
    this.defaultName = defaultName;
    this.page = page;
  }
  
  public String getDefaultName() {
    return defaultName;
  }
  
  @SuppressWarnings("ucd")
  public Page getPage() {
    return page;
  }

  @Override
  public String toString() {
    return defaultName;
  }
  
  public BaseViewPage getNewPage(WebDriver driver) {
    switch (this) {
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
      case SCARBOROUGH_CROSSTAB_TABLE:
        return new ScarboroughCrosstabPage(driver);
      default:
        throw new AssertionError("View type not supported: " + this);
    }
  }
  
  public static ViewType getViewTypeByName(String view) {
    for(ViewType viewType : ViewType.values()) {
      if(viewType.getDefaultName().equals(view))
        return viewType;
    }
    throw new AssertionError("View type is not supported: " + view);
  }
}
