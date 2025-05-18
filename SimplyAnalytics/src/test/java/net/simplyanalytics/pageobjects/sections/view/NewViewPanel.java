package net.simplyanalytics.pageobjects.sections.view;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.ViewType;
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
import net.simplyanalytics.pageobjects.pages.main.editviews.EditScatterPlotPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.EditTimeSeriesPage;
import net.simplyanalytics.pageobjects.pages.main.editviews.StartBySelectingScarboroughPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.sections.view.base.ViewPanel;
import net.simplyanalytics.pageobjects.windows.HistoricalYearWindow;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.qameta.allure.Step;

import java.time.Duration;

public class NewViewPanel extends ViewPanel {

  @FindBy(xpath = ".//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Map')]]")
  private WebElement map;

  @FindBy(xpath = ".//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Comparison Table')]]")
  private WebElement comparisonTable;

  @FindBy(xpath = ".//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Ranking Table')]]")
  private WebElement rankingTable;

  @FindBy(xpath = ".//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Quick Report')]]")
  private WebElement quickReportTable;

  @FindBy(xpath = ".//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Ring Study Table')]]")
  private WebElement ringStudyTable;

  @FindBy(xpath = ".//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Business Table')]]")
  private WebElement businessTable;

  @FindBy(xpath = ".//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Related Data Table')]]")
  private WebElement relatedDataTable;

  @FindBy(xpath = ".//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Time Series Table')]]")
  private WebElement timeSeriesTable;
  
  @FindBy(xpath = ".//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Bar Chart')]]")
  private WebElement barChart;
  
  @FindBy(xpath = ".//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Simmons Crosstab Table')]]")
  private WebElement crosstabTable;
  
  @FindBy(xpath = ".//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Scatter Plot')]]")
  private WebElement scatterPlotTable;
  
  @FindBy(xpath = ".//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Scarborough Crosstab Table')]]")
  private WebElement scarboroughCrosstabTable;
  
  @FindBy(css = "div:not([style='display: none;']) > div.sa-historical-toggle-label")
  private WebElement historicalViewsCheckbox;
  
  @FindBy(css = "div:not([style='display: none;']) > a.sa-historical-toggle-year-link")
  private WebElement historicalYear;
  
  @FindBy(xpath = ".//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Histogram')]]")
  private WebElement histogram;

  private static final By createButtonLocator = By.cssSelector("button[class='sa-button sa-button-default sa-new-view-list-button']");
  private static final By mapCard = By.xpath("//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Map')]]");
  private static final By timeSeriesCard = By.xpath(".//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Time Series Table')]]");
  private static final By comparisonTableCard = By.xpath("//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Comparison Table')]]");
  private static final By rankingTableCard = By.xpath("//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Ranking Table')]]");
  private static final By quickReportCard = By.xpath("//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Quick Report')]]");
  private static final By ringStudyCard = By.xpath("//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Ring Study Table')]]");
  private static final By businessCard = By.xpath("//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Business Table')]]");
  private static final By relatedDataCard = By.xpath("//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Related Data Table')]]");
  private static final By locationQueryCard = By.xpath("//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Location Query')]]");
  private static final By histogramCard = By.xpath("//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Histogram')]]");
  private static final By barChartCard = By.xpath("//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Bar Chart')]]");
  private static final By scatterPlotCard = By.xpath("//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Scatter Plot')]]");
  private static final By simmonsCrosstabCard = By.xpath("//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Simmons Crosstab Table')]]");
  private static final By scarboroughCrosstabCard = By.xpath("//div[contains(@class,'sa-new-view-list-item ')][.//div[@class = 'sa-new-view-list-item-header' and contains(text(),'Scarborough Crosstab Table')]]");




  @FindBy(css = "[class='sa-simple-container sa-new-view-list sa-simple-container-default x-border-box']")
  private WebElement root;
  @FindBy(css = ".sa-new-view-no-views-message")
  private WebElement newViewMessage;


  public NewViewPanel(WebDriver driver) {
    super(driver);
  }

  @Override
  public void isLoaded() {
    super.isLoaded();
    waitForElementToAppear(map, "Map panel should appear");
    waitForElementToAppear(comparisonTable, "Composon Table panel should appear");
    waitForElementToAppear(rankingTable, "Ranking Table panel should appear");
    waitForElementToAppear(quickReportTable, "Quick Report Table panel should appear");
    waitForElementToAppear(ringStudyTable, "Ring Study Table panel should appear");
    waitForElementToAppear(businessTable, "Business Table panel should appear");
    waitForElementToAppear(relatedDataTable, "Related Data Table panel should appear");
    waitForElementToAppear(timeSeriesTable, "Time Series Table panel should appear");
    waitForElementToAppear(barChart, "Bar Chart panel should appear");
    //waitForElementToAppear(crosstabTable, "Simmons Crosstab Table panel should appear");
    waitForElementToAppear(scatterPlotTable, "Scatter Table panel should appear");
    //waitForElementToAppear(scarboroughCrosstabTable, "Scarborough Crosstab Table panel should appear");

  }

  /**
   * Click on the Create button.
   * @param view ViewType
   * @return Edit(ViewType)Page
   */
  @Step("Click on the Create \"{0}\" button")
  public BaseEditViewPage clickCreate(ViewType view) {
    logger.debug("Click on the Create " + view.getDefaultName() + " button");
    WebDriverWait wait = new WebDriverWait(driver, 20);
    switch (view) {
      case MAP:
        waitForAttributeToDisappear(root, mapCard, "visibility", "hidden", 500);
        map.findElement(createButtonLocator).click();
        return new EditMapPage(driver);
      case COMPARISON_REPORT:
        waitForAttributeToDisappear(root, comparisonTableCard, "visibility", "hidden", 500);
        comparisonTable.findElement(createButtonLocator).click();
        return new EditComparisonReportPage(driver);
      case RANKING:
        waitForAttributeToDisappear(root, rankingTableCard, "visibility", "hidden", 500);
        rankingTable.findElement(createButtonLocator).click();
        return new EditRankingPage(driver);
      case RING_STUDY:
        waitForAttributeToDisappear(root, ringStudyCard, "visibility", "hidden", 500);
        ringStudyTable.findElement(createButtonLocator).click();
        return new EditRingStudyPage(driver);
      case BUSINESSES:
        waitForAttributeToDisappear(root, businessCard, "visibility", "hidden", 500);
        businessTable.findElement(createButtonLocator).click();
        return new EditBusinessesPage(driver);
      case RELATED_DATA:
        waitForAttributeToDisappear(root, relatedDataCard, "visibility", "hidden", 500);
        relatedDataTable.findElement(createButtonLocator).click();
        return new EditRelatedDataReportPage(driver);
      case QUICK_REPORT:
        waitForAttributeToDisappear(root, quickReportCard, "visibility", "hidden", 500);
        quickReportTable.findElement(createButtonLocator).click();
        return new EditQuickReportPage(driver);
      case TIME_SERIES:
        waitForAttributeToDisappear(root, timeSeriesCard, "visibility", "hidden", 500);
       timeSeriesTable.findElement(createButtonLocator).click();
        return new EditTimeSeriesPage(driver);
      case BAR_CHART:
        waitForAttributeToDisappear(root, barChartCard, "visibility", "hidden", 500);
        barChart.findElement(createButtonLocator).click();
        return new EditBarChartPage(driver);
      case CROSSTAB_TABLE:
        //clickHistoricalViewsCheckbox();
        waitForAttributeToDisappear(root, simmonsCrosstabCard, "visibility", "hidden", 500);
        crosstabTable.findElement(createButtonLocator).click();
        return new EditCrosstabPage(driver);
      case SCATTER_PLOT:
        waitForAttributeToDisappear(root, scatterPlotCard, "visibility", "hidden", 500);
       scatterPlotTable.findElement(createButtonLocator).click();
        return new EditScatterPlotPage(driver);
      case SCARBOROUGH_CROSSTAB_TABLE:
        //NB Cooment: Commented for clickHistoricalViewsCheckbox() new feature where the historical view is not needed for the Simmons CROSSTAB TABLE and SCARBOROUGH CROSSTAB TABLE
        //clickHistoricalViewsCheckbox();
        waitForAttributeToDisappear(root, scarboroughCrosstabCard, "visibility", "hidden", 500);
        scarboroughCrosstabTable.findElement(createButtonLocator).click();
        return new StartBySelectingScarboroughPage(driver);
      case HISTOGRAM:
        waitForAttributeToDisappear(root, histogramCard, "visibility", "hidden", 500);
    	histogram.findElement(createButtonLocator).click();
        return new EditHistogramPage(driver);
      default:
        throw new AssertionError("View type is not supported");
    }
  }
  
  public boolean isNewViewMessagePresent() {
    return (newViewMessage.findElement(By.cssSelector(".sa-new-view-no-views-message-lead")).getText().trim() + " "
             + newViewMessage.findElement(By.cssSelector(".sa-new-view-no-views-message-aside")).getText().trim())
           .equals("Please create a new view and add some locations and data variables to get started. If you have any questions or issues click on the support button at the top of the page.");
  }
  
  public void clickHistoricalViewsCheckbox() {
    logger.debug("Click Historical Views checkbox");
    waitForElementToAppear(historicalViewsCheckbox, "Checkbox should appeare").click();
  }


  public HistoricalYearWindow clickHistoricalYearLink() {
    logger.debug("Click on the Historical Year link");
    waitForElementToBeClickable(historicalYear, "Link is not clickable").click();
    return new HistoricalYearWindow(driver);
  }
  
  public boolean isViewEnabled(ViewType viewType) {
    WebElement view = null;
    switch (viewType) {
    case MAP:
      view = map;
    case COMPARISON_REPORT:
      view = comparisonTable;
    case RANKING:
      view = rankingTable;
    case RING_STUDY:
      view = ringStudyTable;
    case BUSINESSES:
      view = businessTable;
    case RELATED_DATA:
      view = relatedDataTable;
    case QUICK_REPORT:
      view = quickReportTable;
    case TIME_SERIES:
      view = timeSeriesTable;
    case BAR_CHART:
      view = barChart;
    case CROSSTAB_TABLE:
      view = crosstabTable;
    case SCATTER_PLOT:
      view = scatterPlotTable;
    case SCARBOROUGH_CROSSTAB_TABLE:
      view = scarboroughCrosstabTable;
    }
    return !view.getAttribute("class").contains("sa-new-view-list-item-disabled");
  }

}
