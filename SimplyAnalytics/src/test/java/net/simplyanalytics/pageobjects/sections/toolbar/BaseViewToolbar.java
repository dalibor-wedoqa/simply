package net.simplyanalytics.pageobjects.sections.toolbar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.export.ExportWindow;
import net.simplyanalytics.pageobjects.pages.main.export.MainExport;
import net.simplyanalytics.pageobjects.pages.main.export.mapexport.CroppingWindow;
import net.simplyanalytics.pageobjects.pages.main.export.quickexport.QuickExportWindow;
import net.simplyanalytics.pageobjects.sections.toolbar.businesses.BusinessViewActionMenu;
import net.simplyanalytics.pageobjects.sections.toolbar.comparisonreport.ComparisonViewActionMenu;
import net.simplyanalytics.pageobjects.sections.toolbar.map.MapViewActionMenu;
import net.simplyanalytics.pageobjects.sections.toolbar.quickreport.QuickViewActionMenu;
import net.simplyanalytics.pageobjects.sections.toolbar.timeseriesreport.TimeSeriesViewActionMenu;
import io.qameta.allure.Step;

import java.util.List;

public abstract class BaseViewToolbar extends BaseToolbar {

  //@FindBy(xpath = "//div[contains(@class,'sa-simple-container sa-toolbar sa-simple-container-default') and not(@style='display: none;')]//span[normalize-space(.)='View Actions']")
  @FindBy(xpath = ".//a[.//span[normalize-space(.)='View Actions']]")
  protected List <WebElement> viewActionsButton;

  @FindBy(css = ".sa-pvt-export-button")
  protected WebElement exportButton;

  @FindBy(css = ".sa-pvt-filtering-button")
  protected WebElement filteringButton;
  
  private ViewType nextViewType;

  public BaseViewToolbar(WebDriver driver, ViewType view, ViewType nextViewType) {
    super(driver);
    this.view = view;
    this.nextViewType = nextViewType;
  }

  private ViewType view;

  @Override
  public void isLoaded() {
    super.isLoaded();
    waitForAllElementsToAppear(viewActionsButton, "View Actions button should be present");
  }

  /**
   * Click on the View Actions button.
   * 
   * @return choosen ViewAction Menu
   */
  @Step("Click on the View Actions button")
  public ViewActionsMenu clickViewActions() {
    logger.debug("Click on the View Actions button");
    viewActionsButton.get(viewActionsButton.size()-1).click();
    switch (view) {
      case BUSINESSES:
        return new BusinessViewActionMenu(driver);
      case COMPARISON_REPORT:
        return new ComparisonViewActionMenu(driver);
      case MAP:
        return new MapViewActionMenu(driver);
      case QUICK_REPORT:
        return new QuickViewActionMenu(driver);
      case RANKING:
      case RING_STUDY:
      case RELATED_DATA:
        return new ViewActionsMenu(driver, view);
      case TIME_SERIES:
        return new TimeSeriesViewActionMenu(driver);
      case BAR_CHART:
        return new ViewActionsMenu(driver, view);
      case CROSSTAB_TABLE:
        return new ViewActionsMenu(driver, view);
      case SCATTER_PLOT:
        return new ViewActionsMenu(driver, view);
      case SCARBOROUGH_CROSSTAB_TABLE:
        return new ViewActionsMenu(driver, view); 
      case HISTOGRAM:
          return new ViewActionsMenu(driver, view);
      default:
        throw new AssertionError("View type is not supported: " + view);
    }
  }



  public abstract MainExport clickExport();

  @Step("Click on the Export button")
  protected BasePage clickExportButton(ViewType... view) {
    logger.debug("Click on the Export button");
    exportButton.click();
    waitForLoadingToDisappear();
    if (view.length != 0) {
      switch (view[0]) {
        case MAP:
          return new CroppingWindow(driver);
        case QUICK_REPORT:
          return new QuickExportWindow(driver);
        case HISTOGRAM:
            return new QuickExportWindow(driver);
        default:
          return new ExportWindow(driver);
      }
    }
    return new ExportWindow(driver);
  }
  
  @Step("Click on the Export button")
  public void clickExportButtonVoid() {
    logger.debug("Click on the Export button");
    exportButton.click();
    waitForLoadingToDisappear();
  }

  @Step("Click on the filtering button")
  private void clickFilteringVoid() {
    switch (view) {
      case BUSINESSES:
      case QUICK_REPORT:
      case RELATED_DATA:
      case RING_STUDY:
        throw new AssertionError("The view type: " + view + " not supports filtering");
      case COMPARISON_REPORT:
      case MAP:
      case RANKING:
        logger.trace("View type " + view + " supports filtering");
        break;
      default:
        break;
    }
    logger.debug("Click on the filtering button");
    waitForElementToAppear(filteringButton, "The filtering button is missing").click();
  }

  public boolean isFilterActive() {
    return filteringButton.getAttribute("class").contains("sa-pvt-filtering-button-on");
  }

  public Filtering clickFiltering() {
    clickFilteringVoid();
    return new Filtering(driver, view);
  }

  public Filtering clickFiltering(int conditionCount) {
    clickFilteringVoid();
    return new Filtering(driver, conditionCount, view);
  }

}
