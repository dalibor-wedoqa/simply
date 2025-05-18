package net.simplyanalytics.pageobjects.sections.view.scatterplot;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.panels.color.ColorSelectionPanel;
import net.simplyanalytics.utils.ColorUtils;
import io.qameta.allure.Step;

public class ScatterPlotEditLegendPanel extends BasePage {
  
  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-scatter-plot-legend");
  protected WebElement root;
  
  @FindBy(css = ".sa-legend-header-title")
  private WebElement legendHeader;
  
  @FindBy(css = ".sa-button-legend")
  private WebElement doneButton;
  
  @FindBy(css = ".sa-text-field-input:nth-child(1)")
  private WebElement chartTitleInput;
  
  @FindBy(css = ".sa-menu-button:nth-child(4)")
  private WebElement xAxisData;
  
  @FindBy(css = "div.sa-arrow-align-top")
  private List<WebElement> axisDropdown;
  
  @FindBy(css = ".sa-menu-button:nth-child(6)")
  private WebElement yAxisData;
  
  @FindBy(css = ".sa-menu-button:nth-child(1)")
  private WebElement pointSize;
  
  @FindBy(css = ".x-panel-body")
  private WebElement pointSizeDropdown;
  
  @FindBy(css = " .x-box-item")
  private List<WebElement> sizeItems;
  
  @FindBy(css = ".sa-color-button")
  private WebElement pointColor;
  
  @FindBy(css = ".sa-color-window")
  private WebElement pointColorDropdown;
  
  @FindBy(css = ".sa-color-window-item")
  private List<WebElement> colorItems;
  
  @FindBy(css = ".sa-toggle-switch")
  private WebElement bestLineSwitchButton;
  
  public ScatterPlotEditLegendPanel(WebDriver driver) {
    super(driver, driver.findElement(ROOT_LOCATOR));
    root = driver.findElement(ROOT_LOCATOR);
  }
  
  @Override
  public void isLoaded() {
    waitForElementToAppear(doneButton, "Done button is not present");
  }
  
  @Step("Click on the Done button")
  public ScatterPlotLegendPanel clickDone() {
    logger.debug("Click on the Done button");
    waitForElementToAppear(doneButton, "Done button is not loaded").click();
    waitForElementToDisappear(doneButton);
    return new ScatterPlotLegendPanel(driver);
  }
  
  public void changeChartTitle(String title) {
    logger.debug("Click chart title text box");
    waitForElementToAppear(chartTitleInput, "Chart title text box is not present").click();
    chartTitleInput.clear();
    chartTitleInput.sendKeys(title);
  }
  
  @Step("Click on the Line of Best Fit switch button")
  public void clickLineBestFitSwitchButton() {
    logger.debug("Click on the Switch button");
    waitForElementToAppear(bestLineSwitchButton, "Switch button is not loaded").click();
  }
  
  public void changeXAxisDataByName(DataVariable dataVariable) {
    logger.debug("Click x-axis data");
    xAxisData.click();
    axisDropdown = driver.findElements(By.cssSelector(".sa-arrow-align-top"));
    waitForElementToAppear(axisDropdown.get(0), "x-axis dropdown is not present");
    logger.debug("Click on a dataVariable");
    axisDropdown.get(0).findElement(By
        .xpath(".//span[contains(normalize-space(.),\"" + dataVariable.getFullName() +  "\")]")).click();
  }
  
  public void changeYAxisDataByName(DataVariable dataVariable) {
    logger.debug("Click y-axis data");
    yAxisData.click();
    axisDropdown = driver.findElements(By.cssSelector(".sa-arrow-align-top"));
    waitForElementToAppear(axisDropdown.get(1), "y-axis dropdown is not present");
    logger.debug("Click on a dataVariable");
    axisDropdown.get(1).findElement(By
        .xpath(".//span[contains(normalize-space(.),\"" + dataVariable.getFullName() +  "\")]")).click();
  }
  
  public String getActualPointSize() {
    return pointSize.findElement(By.cssSelector(".sa-button-text"))
        .getText().trim();
  }
  
  public void changePointSize(int size) { 
    logger.info("Click to open point size dropdosn");
    pointSize.click();
    pointSizeDropdown = driver.findElement(By.cssSelector(".x-panel-body"));
    waitForElementToAppear(pointSizeDropdown, "Point size dropdown is not present");
    List<WebElement> pointItems = pointSizeDropdown.findElements(By.cssSelector(".x-box-item"));
    pointItems.get(size-1).click();
    
  }
  
  public String getActualPointColor() {
    return pointColor.findElement(By.cssSelector(".sa-color-button-swatch"))
        .getCssValue("background-color").trim();
  }
  
  public ColorSelectionPanel clickPointColor() {
    logger.debug("Click on the point color button");
    pointColor.click();
    return new ColorSelectionPanel(driver);
  }
  
  public void changePointColorByIndex(int index) {
    logger.debug("Click to open point color dropdown");
    waitForElementToBeClickable(pointColor, "Point color is not clickable");
    pointColor.click();
    pointColorDropdown = driver.findElement(By.cssSelector(".sa-color-window"));
    waitForElementToAppear(pointColorDropdown, "Point color dropdown is not present");
    colorItems = pointColorDropdown.findElements(By.cssSelector(".sa-color-window-item"));
    logger.debug("Click on the color with index " + index);
    colorItems.get(index).click();
  }
  
  public boolean isLineOfBestFitActive() {
    return bestLineSwitchButton.getAttribute("class").contains("sa-toggle-switch-checked");
  }
  
  public String getSelectedColorCode() {
    return ColorUtils.getBackgroundHexColor(pointColor.findElement(By.cssSelector(".sa-color-button-swatch")));
  }
  
}
