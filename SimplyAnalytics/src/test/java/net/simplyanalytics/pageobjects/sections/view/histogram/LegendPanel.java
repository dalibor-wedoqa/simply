package net.simplyanalytics.pageobjects.sections.view.histogram;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.pageobjects.base.BasePage;
import io.qameta.allure.Step;

public class LegendPanel extends BasePage {

  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-chart-legend:not(.sa-legend-edit-mode)");
  protected WebElement viewRoot;
  protected WebElement root;

  @FindBy(css = ".sa-chart-legend-content .sa-chart-legend-summary-property:nth-child(1) .sa-chart-legend-summary-property-value")
  private WebElement titleElement;

  @FindBy(css = ".sa-chart-legend-content .sa-chart-legend-summary-property:nth-child(2) .sa-chart-legend-summary-property-value")
  private WebElement yAxis;

  @FindBy(css = ".sa-chart-legend-content .sa-chart-legend-summary-property:nth-child(3) .sa-chart-legend-summary-property-value")
  private WebElement xAxis;

  @FindBy(css = ".sa-chart-legend-content .sa-chart-legend-summary-property:nth-child(4) .sa-chart-legend-summary-property-value")
  private WebElement barWidth;

  @FindBy(css = ".sa-chart-legend-header")
  private WebElement legendHeader;

  @FindBy(css = ".sa-chart-legend-mode-button:not([style*='display: none;'])")
  private WebElement editButton;
    
  /**
   * LegendPanel constructor.
   * @param driver WebDriver
   * @param rootElement Legend Panel
   */
  public LegendPanel(WebDriver driver, WebElement rootElement) {
    super(driver, rootElement.findElement(ROOT_LOCATOR));
    viewRoot = rootElement;
    root = rootElement.findElement(ROOT_LOCATOR);
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(editButton, "Edit Button is not present");
  }

  /**
   * Getting title.
   * @return Legend Title
   */
  public String getLegendTitle() {
    String title = titleElement.getText();
    int count = 0;
    while (count < 5) {
      if (!isLegendTitleDisplayed()) {
        sleep(500);
        count++;
      } else {
        return title;
      }
    }
    return title;
  }

  public boolean isLegendTitleDisplayed() {
    return titleElement.isDisplayed();
  }

  public DataVariable getActiveData() {
    return DataVariable.getByFullName(xAxis.getText().split("by")[0].trim());
  }

  public LocationType getActiveLocationType() {
    return LocationType.getByPluralName(yAxis.getText().split("by")[1].trim());
  }

  public int getLegendWidth() {
    return root.getSize().getWidth();
  }

  /**
   * Click on the Edit button.
   */
  @Step("Click on the Edit button")
  public EditLegendPanel clickEdit() {
    logger.debug("Click on the Edit button");
    waitForElementToBeClickable(editButton, "Edit button is not loaded").click();
    return new EditLegendPanel(driver, viewRoot);
  }
  
  public boolean mapTitleIsDisappeared() {
    waitForElementToBeInvisible(By.cssSelector(".sa-legend-map-title"), "Histogram title is visible");
    return !titleElement.isDisplayed();
  }

  /**
   * Move the legend panel.
   * @param x offset by x-axis
   * @param y offset by y-axis
   */
  @Step("Move the legend panel")
  public void moveLegend(int x, int y) {
    logger.debug("Move the legend panel");
    Actions action = new Actions(driver);
    action.moveToElement(legendHeader).clickAndHold().moveByOffset(x, y).release().perform();
  }

  public Point getLegendLocation() {
    return root.getLocation();
  }

}
