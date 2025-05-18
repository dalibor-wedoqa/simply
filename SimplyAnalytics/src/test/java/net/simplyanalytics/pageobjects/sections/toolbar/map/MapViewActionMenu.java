package net.simplyanalytics.pageobjects.sections.toolbar.map;

import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.sections.toolbar.ViewActionsMenu;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class MapViewActionMenu extends ViewActionsMenu {
  
  @FindBy(xpath = "//a[span[text()='Export Shapefiles…']]")
  private WebElement exportShapefiles;
  
  @FindBy(xpath = "//a[span[text()='Show Legend']]")
  private WebElement showLegend;
  
  @FindBy(xpath = "//a[span[text()='Create Data Table']]")
  private WebElement createTable;
  
  @FindBy(xpath = "//a[span[text()='Show Map Labels']]")
  private WebElement showMapLabels;
  
  @FindBy(xpath = "//a[span[text()='Highlight Location']]")
  private WebElement highlightLocations;
  
  @FindBy(xpath = "//a[span[text()='Apply Location Mask']]")
  private WebElement applyLocationMask;
  
  public MapViewActionMenu(WebDriver driver) {
    super(driver, ViewType.MAP);
  }
  
  @Override
  public void isLoaded() {
    super.isLoaded();
    waitForElementToAppear(exportShapefiles, "Export Shapefiles button should appear");
    waitForElementToAppear(showLegend, "Show Legend button should appear");
    waitForElementToAppear(showMapLabels, "Show Map Labels button should appear");
    waitForElementToAppear(highlightLocations, "Highlight Location button should appear");
    waitForElementToAppear(applyLocationMask, "Apply Location Mask button should appear");
    waitForElementToAppear(createTable, "Create Data Table button should appear");
  }
  
  @Step("Click on the Show Legend button")
  public void clickShowLegend() {
    logger.debug("Click on the Show Legend button");
    showLegend.click();
  }
  
  @Step("Click on the Shop Map Labels button")
  public void clickShowMapLabels() {
    logger.debug("Click on the Show Map Labels button");
    showMapLabels.click();
  }
  
  @Step("Click on the Highlight Location button")
  public void clickHighlightLocations() {
    logger.debug("Click on the Highlight Location button");
    highlightLocations.click();
  }
  
  /**
   * Click on the Apply Location Mask button.
   * @return MapPage
   */
  @Step("Click on the Apply Location Mask button")
  public MapPage clickApplyLocationMask() {
    logger.debug("Click on the Apply Location Mask button");
    applyLocationMask.click();
    return new MapPage(driver);
  }
  
  /**
   * Click on the Export Shapefiles button.
   * @return ExportShapefilesWindow
   */
  @Step("Click on the Export Shapefiles button")
  public ExportShapefilesWindow clickExportShapefiles() {
    logger.debug("Click on the Export Shapefiles button");
    exportShapefiles.click();
    return new ExportShapefilesWindow(driver);
  }
  
  /**
   * Click on the Create Data Table button.
   * @return RankingPage
   */
  @Step("Click on the Create Data Table button")
  public RankingPage clickOnCreateDataTable() {
    logger.debug("Click on the Create Data Table button");
    createTable.click();
    return new RankingPage(driver);
  }
  
  
}
