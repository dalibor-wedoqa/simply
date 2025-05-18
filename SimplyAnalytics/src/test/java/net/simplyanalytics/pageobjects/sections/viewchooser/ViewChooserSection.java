package net.simplyanalytics.pageobjects.sections.viewchooser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.NewViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.BaseEditViewPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditBusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRankingPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditRingStudyPage;
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
import io.qameta.allure.Step;

public class ViewChooserSection extends BasePage {
  
  @FindBy(css = "button[data-index='add']")
  private WebElement newViewElement;
  
  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-view-chooser");
  protected WebElement root;
  
  public ViewChooserSection(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
    root = waitForElementToAppearByLocator(ROOT_LOCATOR, "The root element should be present");
  }
  
  @Override
  public void isLoaded() {
    waitForElementToAppear(newViewElement, "The New View button  is not loaded");
  }
  
  @Override
  protected void load() {
    PageFactory.initElements(new DefaultElementLocatorFactory(driver.findElement(ROOT_LOCATOR)),
        this);
  }
  
  /**
   * Click on view with name.
   * @param viewName view name
   * @return choosen view page
   */
  @Step("Click on view with name \"{0}\"")
  public BaseViewPage clickView(String viewName) {
    logger.debug("Click on view with name: " + viewName);
    getViewRoot(viewName).click();
    ViewType view = getViewType(viewName);
    switch (view) {
      case MAP:
        return new MapPage(driver);
      case COMPARISON_REPORT:
        return new ComparisonReportPage(driver);
      case RANKING:
        return new RankingPage(driver);
      case RING_STUDY:
        return new RingStudyPage(driver);
      case BUSINESSES:
        return new BusinessesPage(driver);
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
  
  /**
   * Click on view with name.
   * @param viewName view name
   * @param boo boo
   * @return edit view page
   */
  @Step("Click on view with name \"{0}\"")
  public BaseEditViewPage clickView(String viewName, Boolean... boo) {
    logger.debug("Click on view with name: " + viewName);
    getViewRoot(viewName).click();
    ViewType view = getViewType(viewName);
    switch (view) {
      
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
      case MAP:
        throw new AssertionError("You can call this method with Map View");
      default:
        throw new AssertionError("View type is not supported: " + view);
    }
  }
  
  /**
   * Click on the New View button.
   * @return NewViewPage
   */
  @Step("Click on the New View button")
  public NewViewPage clickNewView() {
    logger.debug("Click on the New View button");
    newViewElement.click();
    return new NewViewPage(driver);
  }
  
  /**
   * Getting active view name.
   * @return active view name
   */
  public String getActiveViewName() {
    WebElement root = getActiveView();
    String classAttribute = root.getAttribute("class");
    if (classAttribute.contains("sa-view-chooser-item-draggable")) {
      // real view
      return root.findElement(By.cssSelector(".sa-view-chooser-name")).getText();
    } else {
      // new view button
      return root.getText();
    }
  }
  
  /**
   * Open the menu for view with name.
   * @param viewName view name
   * @return ViewMenu
   */
  @Step("Open the menu for view with name \"{0}\"")
  public ViewMenu openViewMenu(String viewName) {
    logger.debug("Open the menu for view with name: " + viewName);
    getViewRoot(viewName).findElement(By.cssSelector(".sa-view-chooser-item-menu-btn")).click();
    return new ViewMenu(driver, getViewType(viewName));
  }
  
  /**
   * Getting view census release badge.
   * @param viewName view name
   * @return view badge
   */
  public String getViewBadge(String viewName) {
    logger.info("Get census release badge for view with name: " + viewName);
    return getViewRoot(viewName).findElement(By.cssSelector(".sa-view-chooser-census-release-range-badge")).getText().trim();
  }
  
  /**
   * Getting view type.
   * @param viewName view name
   * @return view type
   */
  public ViewType getViewType(String viewName) {
    WebElement icon = getViewRoot(viewName)
        .findElement(By.cssSelector(".sa-view-chooser-icon use"));
    String iconName = icon.getAttribute("href");
    switch (iconName) {
      case "#sa-icon-comparison-report":
        return ViewType.COMPARISON_REPORT;
      case "#sa-icon-map":
        return ViewType.MAP;
      case "#sa-icon-ranking-report":
        return ViewType.RANKING;
      case "#sa-icon-ring-study":
        return ViewType.RING_STUDY;
      case "#sa-icon-business-report":
        return ViewType.BUSINESSES;
      case "#sa-icon-related-data-report":
        return ViewType.RELATED_DATA;
      case "#sa-icon-quick-report":
        return ViewType.QUICK_REPORT;
      case "#sa-icon-time-series-report":
        return ViewType.TIME_SERIES;
      case "#sa-icon-bar-chart":
        return ViewType.BAR_CHART;
      case "#sa-icon-histogram":
        return ViewType.HISTOGRAM;
      case "#sa-icon-crosstab":
        if(viewName.contains("Simmons"))
          return ViewType.CROSSTAB_TABLE;
        else
          return ViewType.SCARBOROUGH_CROSSTAB_TABLE;
      case "#sa-icon-scatter-plot":
        return ViewType.SCATTER_PLOT;
      default:
        throw new AssertionError("This icon is unknown: " + iconName);
    }
  }
  
  /**
   * Click the actual view name to open rename panel.
   * @return RenameViewPanel
   */
  @Step("Click the actual view name to open rename panel")
  public RenameViewPanel clickActualViewName() {
    logger.debug("Click the actual view name to open rename panel");
    getActiveView().findElement(By.cssSelector(".sa-view-chooser-name-editable")).click();
    return new RenameViewPanel(driver);
  }
  
  private WebElement getViewRoot(String viewName) {
    return root.findElement(By.xpath(
        "//button[contains(@class, 'sa-view-chooser-item')]//div[text()='"
          + viewName + "']/.."));
  }
  
  private WebElement getActiveView() {
    return getElementOrNull(By.cssSelector(".sa-view-chooser-item-selected"));
  }
  
  public ViewType getTopViewType() {
	  sleep(3000);
	  WebElement e = waitForElementToAppearWithCustomTime(
			  root.findElements(By.cssSelector(".sa-view-chooser-scroll-wrapper > .sa-view-chooser-item.sa-view-chooser-item-draggable")).get(0),
		        "No main container", 120);
	  
	  return ViewType.getViewTypeByName(e.findElement(By.cssSelector(".sa-view-chooser-name"))
			  .getText().trim());
  }
  
}
