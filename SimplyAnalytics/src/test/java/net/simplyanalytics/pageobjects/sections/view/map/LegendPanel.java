package net.simplyanalytics.pageobjects.sections.view.map;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.LocationType;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.panels.color.ColorSelectionPanel;
import net.simplyanalytics.pageobjects.sections.viewchooser.RenameViewPanel;
import net.simplyanalytics.utils.ColorUtils;
import io.qameta.allure.Step;

public class LegendPanel extends BasePage {

  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-legend"); //:not(.sa-legend-edit-mode)
  protected WebElement viewRoot;
  protected WebElement root;

  private LegendMapCenterPanel legendMapCenterPanel;
  private LegendHeaderMenuPanel legendHeaderMenuPanel;

  @FindBy(css = ".sa-legend-map-title")
  private WebElement titleElement;
  By mapCenter = By.cssSelector(".sa-legend-map-center");

  @FindBy(css = ".sa-legend-businesses")
  private WebElement businessRootElement;

  @FindBy(css = ".sa-legend-businesses-icon-wrap")
  private WebElement businessBulletElement;

  @FindBy(css = ".sa-legend-businesses-name")
  private WebElement businessNameElement;

  @FindBy(css = ".sa-legend-businesses-remove")
  private WebElement businessRemoveElement;

  @FindBy(css = ".sa-legend-range")
  private List<WebElement> rangeCategoriesElements;

  @FindBy(css = "a[id^='ext-gen'][class^='sa-legend-header-button']")
  private WebElement headerButton;

  @FindBy(css = ".sa-legend-range-text-upper")
  private List<WebElement> categoryRangeUpperValues;

  @FindBy(css = ".sa-legend-header-title")
  private WebElement legendHeader;

  @FindBy(css = ".sa-button-legend:not([style*='display: none;'])")
  private WebElement editButton;
    
  private By categoryRangesDisplay = By.cssSelector(".sa-legend-ranges");

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
    waitForElementToAppear(headerButton, "Header Button is not present");
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
    return DataVariable.getByFullName(getLegendTitle().split("by")[0].trim());
  }

  public LocationType getActiveLocationType() {
    return LocationType.getByPluralName(getLegendTitle().split("by")[1].trim());
  }

  public String getActiveBusiness() {
    return businessNameElement.getText();
  }

  public boolean isLegendBusinessDisplayed() {
    return businessNameElement.isDisplayed();
  }

  /**
   * Getting space height for business.
   * @return
   */
  public int getSpaceHeightForBusiness() {
    WebElement previusSeparator = businessRootElement
        .findElement(By.xpath("./preceding-sibling::div[contains(@class, 'sa-legend-separator')]"));
    WebElement nextSeparator = businessRootElement
        .findElement(By.xpath("./following-sibling::div[contains(@class, 'sa-legend-separator')]"));
    return nextSeparator.getLocation().getY() - previusSeparator.getLocation().getY();
  }

  public int getBusinessHeight() {
    return businessRootElement.getSize().getHeight();
  }

  public int getSumOfBusinessElementsWidth() {
    return businessBulletElement.getSize().getWidth() + businessNameElement.getSize().getWidth()
        + businessRemoveElement.getSize().getWidth();
  }

  public int getLegendWidth() {
    return root.getSize().getWidth();
  }

  public String getMapCenter() {
    List <WebElement> mapCenterList = driver.findElements(By.xpath("(.//div[contains(@class, 'sa-legend')]//div[text()='Map Center']/../div[2])"));
    return waitForElementToAppear(mapCenterList.get(mapCenterList.size() - 1), "The Map Center did not appear").getText();
  }

  /**
   * Click on the Edit button.
   */
  @Step("Click on the Edit button")
  public EditLegendPanel clickEdit() {
    logger.debug("Click on the Edit button");
    Actions builder = new Actions(driver);
    builder.moveToElement(legendHeader).perform();
    //WebElement editBtn = root.findElement(By.xpath(".//a[contains(@class, 'sa-button-legend') and not(contains(@style, 'display: none;'))]//span"));
    WebElement editBtn = driver.findElement(By.xpath(".//a[contains(@class, 'sa-button-legend') and not(contains(@style, 'display: none;'))]"));
    
    waitForElementToAppear(editBtn, "Edit button is not present");
    
    editBtn.click();
    return new EditLegendPanel(driver, viewRoot);
  }
  
  /**
   * Click on header button.
   * @return legendHeaderMenuPanel
   */
  @Step("Click on header button")
  public LegendHeaderMenuPanel clickOnHeaderButton() {
    logger.debug("Click on header button");
    headerButton.click();
    if (legendHeaderMenuPanel == null) {
      legendHeaderMenuPanel = new LegendHeaderMenuPanel(driver);
    } else {
      legendHeaderMenuPanel = null;
    }

    return legendHeaderMenuPanel;
  }

  public boolean mapCenterIsDisappeared() {
    waitForElementToBeInvisible(mapCenter, "Map center is visible");
    return !root.findElement(mapCenter).isDisplayed();
  }

  public boolean mapTitleIsDisappeared() {
    waitForElementToBeInvisible(By.cssSelector(".sa-legend-map-title"), "Map title is visible");
    return !titleElement.isDisplayed();
  }

  /**
   * Checking if map businesses is disappeared.
   * @return 
   */
  public boolean mapBusinessesIsDisappeared() {
    waitForElementToBeInvisible(By.cssSelector(".sa-legend-businesses"),
        "Map businesses is visible");
    return !businessRootElement.isDisplayed();
  }

  public boolean mapColorRangesIsDisappeared() {
    waitForElementToBeInvisible(categoryRangesDisplay, "Color ranges are visible");
    return !root.findElement(categoryRangesDisplay).isDisplayed();
  }

  /**
   * Getting legend background after changing theme.
   * @return legend background
   */
  public String getLegendBackgroundAfterChangingTheme() {
    waitForElementToStopChanging(root, "style");
    return root.getCssValue("background");
  }

  /**
   * Clicking on legend panels map center.
   * @return legendMapCenterPanel
   */
  @Step("Click on the legend panels Map Center")
  public LegendMapCenterPanel clickOnMapCenter() {
    logger.debug("Click on the legend panels Map Center");
    driver.findElement(mapCenter);
    waitForElementToBeClickableByLocator(mapCenter, "Map center is not clickable").click();

    if (legendMapCenterPanel == null) {
      legendMapCenterPanel = new LegendMapCenterPanel(driver);
    }
    return legendMapCenterPanel;
  }

  /**
   * Click on the legend business name.
   * @return RenameViewPanel
   */
  @Step("Click on the legend business name")
  public RenameViewPanel clickOnLegendBusinessName() {
    logger.debug("Click on the legend business name");
    businessNameElement.click();
    return new RenameViewPanel(driver);
  }

  /**
   * Click on remove business.
   */
  @Step("Click on remove business")
  public void removeBusiness() {
    logger.debug("Click on remove business");
    businessRemoveElement.click();
    waitForElementToDisappear(businessNameElement, "Business does not disappeared");
  }

  public String getCategoriRangeUpperValueByIndex(int index) {
    return categoryRangeUpperValues.get(index).getText();
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

  public boolean isDarkTheme() {
    return root.getAttribute("class").contains("sa-legend-dark");
  }
  
  public boolean isLightTheme() {
	    return root.getAttribute("class").contains("sa-legend-light");
	  }

  @Step("Get the category range row with index {0} (counting from 0)")
  public RangeRow getRangeRow(int index) {
    logger.debug("Get the category range row with index " + index + " (counting from 0)");
    return new RangeRow(driver, rangeCategoriesElements.get(index));
  }
  
  public class RangeRow extends BasePage {

    @SuppressWarnings("ucd")
    protected WebElement root;
    
    @FindBy(css = ".sa-legend-color-block")
    private WebElement color;
    
    @FindBy(css = ".sa-legend-range-text-lower")
    private WebElement lowerEdge;
    
    @FindBy(css = ".sa-legend-range-text-upper")
    private WebElement upperEdge;
    
    
    public RangeRow(WebDriver driver, WebElement root) {
      super(driver, root);
      this.root = root;
    }

    @Override
    protected void isLoaded() {
      waitForElementToAppear(color, "Color icon should appear");
      waitForElementToAppear(lowerEdge, "Lower edge should appear");
      waitForElementToAppear(upperEdge, "Upper edge should appear");
    }

    @Step("Click on the color icon")
    public ColorSelectionPanel clickColorIcon() {
      logger.debug("Click on the color icon");
      ((JavascriptExecutor) driver).executeScript("arguments[0].click();", color);
      return new ColorSelectionPanel(driver);
    }
    
    public String getSelectedColorCode() {
      return ColorUtils.getBackgroundHexColor(color);
    }
    
    public String getLowerEdge() {
      return lowerEdge.getText();
    }

    public String getUpperEdge() {
      return upperEdge.getText();
    }
    
  }
  
}
