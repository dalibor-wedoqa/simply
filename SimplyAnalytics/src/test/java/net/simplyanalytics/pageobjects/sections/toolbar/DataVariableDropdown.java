package net.simplyanalytics.pageobjects.sections.toolbar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.views.BarChartPage;
import net.simplyanalytics.pageobjects.pages.main.views.BaseViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.BusinessesPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.QuickReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.pages.main.views.RelatedDataReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.RingStudyPage;
import net.simplyanalytics.pageobjects.pages.main.views.TimeSeriesPage;
import io.qameta.allure.Step;

public class DataVariableDropdown extends BasePage {

  private ViewType view;
  private static final By rootElement = By
      .cssSelector(".x-panel:not([style*='visibility: hidden'])");

  private WebElement root;

  @FindBy(xpath = ".//span[normalize-space(.)='Location Name']")
  private WebElement locationNameBtn;

  public DataVariableDropdown(WebDriver driver, ViewType view) {
    super(driver, rootElement);
    root = driver.findElement(rootElement);
    this.view = view;
  }

  @Override
  public void isLoaded() {
    // waitForElementToAppear(locationNameBtn, "Location name button is not
    // present");
  }

  /**
   * Click on the Sort by Location Name button.
   * 
   * @return RankingPage
   */
  @Step("Click on the Sort by Location Name button")
  public BaseViewPage clickOnLocationName() {
    logger.debug("Click on the Sort by Location Name button");
    locationNameBtn.click();
    switch (view) {
      case COMPARISON_REPORT:
        return new ComparisonReportPage(driver);
      case MAP:
        return new MapPage(driver);
      case RANKING:
        return new RankingPage(driver);
      case BUSINESSES:
        return new BusinessesPage(driver);
      case QUICK_REPORT:
        return new QuickReportPage(driver);
      case RING_STUDY:
        return new RingStudyPage(driver);
      case RELATED_DATA:
        return new RelatedDataReportPage(driver);
      case TIME_SERIES:
        return new TimeSeriesPage(driver);
      case BAR_CHART:
        return new BarChartPage(driver);
      default:
        throw new AssertionError("view type is not supported: " + view);
    }
  }

  /**
   * Click on the Sort by button.
   * 
   * @param dataVariable data variable
   * @return RankingPage
   */
  @Step("Click on the data variable {0} button")
  public BaseViewPage clickSortByDatavariable(DataVariable dataVariable) {
    logger.debug("Click on the data variable " + dataVariable.getFullName() + " button");

    waitForElementToBeClickable(
        root.findElement(
            By.xpath(".//span[normalize-space(.)=\"" + dataVariable.getFullName() + "\"]")),
        "Data variable " + dataVariable.getFullName() + " element is not present").click();
    switch (view) {
      case COMPARISON_REPORT:
        return new ComparisonReportPage(driver);
      case MAP:
        return new MapPage(driver);
      case RANKING:
        return new RankingPage(driver);
      case BUSINESSES:
        return new BusinessesPage(driver);
      case QUICK_REPORT:
        return new QuickReportPage(driver);
      case RING_STUDY:
        return new RingStudyPage(driver);
      case RELATED_DATA:
        return new RelatedDataReportPage(driver);
      case TIME_SERIES:
        return new TimeSeriesPage(driver);
      case BAR_CHART:
        return new BarChartPage(driver);
      default:
        throw new AssertionError("view type is not supported: " + view);
    }
  }

}
