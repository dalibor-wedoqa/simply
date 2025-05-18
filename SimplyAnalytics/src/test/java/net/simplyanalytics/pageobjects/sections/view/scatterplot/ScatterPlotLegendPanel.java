package net.simplyanalytics.pageobjects.sections.view.scatterplot;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.pageobjects.base.BasePage;
import io.qameta.allure.Step;

public class ScatterPlotLegendPanel extends BasePage {
  
  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-scatter-plot-legend");
  protected WebElement root;
  
  @FindBy(css = "a.sa-button-legend:not(.sa-legend-button)")
  protected WebElement editButton;
  
  @FindBy(css = ".sa-legend-header-title")
  private WebElement legendHeader;
  
  @FindBy(css = ".sa-scatter-plot-legend-value-label:nth-child(2)")
  private WebElement chartTitle;
  
  @FindBy(css = ".sa-scatter-plot-legend-value-label:nth-child(4)")
  private WebElement xAxisData;
  
  @FindBy(css = ".sa-scatter-plot-legend-value-label:nth-child(6)")
  private WebElement yAxisData;
  
  @FindBy(css = ".sa-scatter-plot-legend-value-label:nth-child(8)")
  private WebElement correlation;
  
  @FindBy(css = ".sa-button-legend")
  private WebElement doneButton;
  
  @FindBy(css = ".sa-legend-warning-msg")
  private WebElement legendWarningMessage;
  
  public ScatterPlotLegendPanel(WebDriver driver) {
    super(driver, driver.findElement(ROOT_LOCATOR));
    root = driver.findElement(ROOT_LOCATOR);
  }
  
  @Override
  protected void isLoaded() {
    waitForElementToAppear(editButton, "Edit button is not present");
  }
  
  /**
   * Click on the Edit button.
   */
  @Step("Click on the Edit button")
  public ScatterPlotEditLegendPanel clickEdit() {
    logger.debug("Click on the Edit button");
    waitForElementToAppear(editButton, "Edit button is not loaded").click();
    sleep(1000);
    waitForElementToAppear(doneButton, "Done button should be present");
    return new ScatterPlotEditLegendPanel(driver);
  }
  
  public String getChartTitle() {
    return chartTitle.getText().trim();
  }
  
  public String getXAxisData() {
    return xAxisData.getText().trim();
  }
  
  public String getYAxisData() {
    return yAxisData.getText().trim();
  }
  
  public String getLegendHeader() {
    return legendHeader.getText().trim();
  }
  
  public Point getLegendLocation() {
    return root.getLocation();
  }
  
  @Step("Move the legend panel")
  public void moveLegend(int x, int y) {
    logger.debug("Move the legend panel");
    Actions action = new Actions(driver);
    action.moveToElement(legendHeader).clickAndHold().moveByOffset(x, y).release().perform();
  }
  
  public boolean isLegendWarningMessageAppeared() {
    return isPresent(legendWarningMessage);
  }
  
  public String getLegendWarningMessage() {
    return legendWarningMessage.getText().trim();
  }
  
}
