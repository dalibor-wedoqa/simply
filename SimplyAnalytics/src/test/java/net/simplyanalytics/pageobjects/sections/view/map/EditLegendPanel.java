package net.simplyanalytics.pageobjects.sections.view.map;

import java.util.List;

import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.panels.color.ColorSelectionPanel;
import net.simplyanalytics.utils.ColorUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class EditLegendPanel extends BasePage {
  
  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-legend"); //-edit-mode
  protected WebElement viewRoot;
  protected WebElement root;
  
  @FindBy(css = ".sa-legend-range")
  private List<WebElement> rangeCategoriesElements;
  
  @FindBy(css = "#combo-button-1145")
  private WebElement classificationMethodButton;
  
  @FindBy(css = ".sa-menu-button-menu:not([style*='hidden'])")
  private WebElement classificationMethodMenu;
  
//  private By numberOfCategoriesCombo = By.cssSelector("a[class='sa-button sa-menu-button sa-button-arrow x-unselectable sa-button-default x-border-box']");
//  private By numberOfCategoriesCombo = By.cssSelector("#combo-button-1141");

  @FindBy(xpath = "//div[text()= 'Number of Categories']/../a")
  private WebElement numberOfCategoriesCombo;

  private By numberOfCategoriesPanel = By
      .xpath("//div[contains(@class, 'sa-combo-button-menu')][2]");
  
  @FindBy(css = ".sa-legend-color-scheme-menu-button")
  private WebElement colorSchemeMenuButton;
  
  private By colorSchemeElements = By.cssSelector("span[class='x-menu-item-text'] .sa-legend-color-scheme-wrap");
  
  private By colorSchemeButtonElements = By.className("sa-legend-color-scheme-color");
  
  @FindBy(css = "[id*='legend-outline-options'] a:not(.sa-legend-outline-color-button)")
  private WebElement outlineThicknessButton;
  
  @FindBy(css = ".sa-legend-outline-color-button")
  private WebElement outlineColorButton;
  
  private By outlineThicknessMenu = By.cssSelector("div[class^='x-panel sa-arrow-ct sa-menu-button-menu "
      + "sa-combo-button-menu']:not([style*='visibility: hidden;'])");
  
  @FindBy(css = "div[class^='x-component x-box-item'][id^='menucheckitem-']")
  private List<WebElement> outlineThicknessMenuItems;
  
  @FindBy(css = ".sa-legend-header-title")
  private WebElement legendHeader;
  
  private By doneButton = By.xpath(".//a[.//span[contains(normalize-space(.), 'Done')]]");
  
  private By categoryRangesDisplay = By.cssSelector(".sa-legend-ranges");
  
  /**
   * EditLegendPanel constructor.
   * 
   * @param driver      WebDriver
   * @param rootElement Legend Panel
   */
  public EditLegendPanel(WebDriver driver, WebElement rootElement) {
    super(driver, rootElement.findElement(ROOT_LOCATOR));
    viewRoot = rootElement;
    root = rootElement.findElement(ROOT_LOCATOR);
  }
  
  @Override
  public void isLoaded() {
    // TODO
  }
  
  public int getLegendWidth() {
    return root.getSize().getWidth();
  }
  
  /**
   * Clicking on Done button.
   * 
   * @return Legend Panel
   */
  @Step("Click on the Done button")
  public LegendPanel clickDone() {
    logger.debug("Click on the Done button");
    WebElement done = driver.findElement(doneButton);
    waitForElementToStop(done);
    done = driver.findElement(doneButton);
    waitForElementToBeClickable(done, "Done button is not clickable").click();
    sleep(1000);
    waitForElementToAppearByLocator(By.cssSelector(".sa-legend:not(.sa-legend-edit-mode)"),"Legend panel was not edited succesfuly");
    // waitForElementToStopChanging(driver.findElement(By.cssSelector(".sa-legend")),
    // "class");
	 //TODO stabilize this method
    waitForLoadingToDisappear();
    return new LegendPanel(driver, viewRoot);
  }
  
  /**
   * Open number of categories combobox.
   */
  @Step("Open number of categories combobox")
  private void clickNumberOfCategoriesCombox() {
    logger.debug("Open category number combobox");
    waitForElementToAppear(numberOfCategoriesCombo, "Number of categories combo is not loaded").click();
    sleep(2000);
    waitForElementToAppear(driver.findElement(numberOfCategoriesPanel), "Categories numbers panel is not loaded");
  }
  
  /**
   * Select number of categories.
   * 
   * @param number number of categories
   */
  @Step("Select number of categories {0}")
  private void clickNumberOfCategoriesElementNumber(int number) {
    logger.debug("Select number of categories to be: " + number);
    waitForElementToAppear(driver.findElement(By.xpath("//a[@class='x-menu-item-link']/span[text()='" + number + "']")),
        "Number of categories element is not loaded").click();
    waitForElementToAppear(driver.findElement(categoryRangesDisplay), "Category ranges is not loaded");
  }
  
  public int numberOfCategoriesElements() {
    return rangeCategoriesElements.size();
  }
  
  /**
   * Change number of categories.
   * 
   * @param number number of categories
   */
  @Step("Change number of categories to be {0}")
  public void changeNumberOfCategories(int number) {
    clickNumberOfCategoriesCombox();
    clickNumberOfCategoriesElementNumber(number);
    waitForElementToAppear(root.findElement(By.cssSelector(".sa-legend-range:nth-of-type(" + (number) + ")")),
        "Categories did not changed correctly");
  }
  
  /**
   * Change classification method.
   * 
   * @param elementName classification method
   */
  @Step("Change classification method to be {0}")
  public void changeClassificationMethodbutton(String elementName) {
    classificationMethodButton.click();
    waitForElementToAppear(root.findElement(By.xpath("//span[contains(text(), '" + elementName + "')]")),
        "Classification method menu has not appeared").click();
  }
  
  public String getActualClassificationMethod() {
    return classificationMethodButton.getText();
  }
  
  /**
   * Change color scheme.
   * 
   * @param index index of color scheme
   */
  @Step("Change color scheme by index {0}")
  public void changeColorSchemeByIndex(int index) {
    
    colorSchemeMenuButton.click();
    waitForElementToAppear(driver.findElements(colorSchemeElements).get(index),
        "Color scheme selection panel has not appeared").click();
  }
  
  public String getColorSchemeButtonElementBackground(int i) {
    return colorSchemeMenuButton.findElements(colorSchemeButtonElements).get(i).getCssValue("background");
  }
  
  /**
   * Open outline thickness combobox.
   */
  @Step("Open outline thickness combobox")
  public void clickOutlineThicknesCombox() {
    logger.debug("Open outline thickness combobox");
    waitForElementToAppear(outlineThicknessButton, "Outline thickness combo is not loaded").click();
    // sleep(2000);
    waitForElementToAppear(driver.findElement(outlineThicknessMenu), "Outline thickness panel is not loaded");
  }
  
  /**
   * Select outline thickness.
   * 
   * @param number outline thickness
   */
  @Step("Select outline thickness {0}")
  public void clickOutlineThicknessElementNumber(String number) {
    logger.debug("Select outline thickness to be: " + number);
    waitForElementToAppear(
        driver.findElement(outlineThicknessMenu).findElement(By.xpath("//span[text()='" + number + "']")),
        "Number of categories element is not loaded").click();
  }
  
  public String getActualOutlineThickness() {
    return outlineThicknessButton.getText();
  }
  
  @Step("click on outline color button")
  public ColorSelectionPanel clickOnOutlineColorButton() {
    outlineColorButton.click();
    return new ColorSelectionPanel(driver);
  }
  
  public String getActualOutlineColor() {
    return outlineColorButton.findElement(By.cssSelector(".sa-legend-outline-color-button-swatch"))
        .getCssValue("background");
  }
  
  @Step("Get the category range row with index {0} (counting from 0)")
  public EditRangeRow getRangeRow(int index) {
    logger.debug("Get the category range row with index " + index + " (counting from 0)");
    return new EditRangeRow(driver, rangeCategoriesElements.get(index));
  }
  
  public class EditRangeRow extends BasePage {
    
    @SuppressWarnings("ucd")
    protected WebElement root;
    
    @FindBy(css = ".sa-legend-color-block")
    private WebElement color;
    
    @FindBy(css = ".sa-legend-range-text-lower")
    private WebElement lowerEdge;
    
    @FindBy(css = "input.sa-legend-range-input")
    private WebElement upperEdgeInputField;
    
    protected EditRangeRow(WebDriver driver, WebElement root) {
      super(driver, root);
      this.root = root;
    }
    
    @Override
    protected void isLoaded() {
      waitForElementToAppear(color, "Color icon should appear");
      waitForElementToAppear(lowerEdge, "Lower edge should appear");
      waitForElementToAppear(upperEdgeInputField, "Upper edge input field should appear");
    }
    
    @Step("Click on the color icon")
    public ColorSelectionPanel clickColorIcon() {
      logger.debug("Click on the color icon");
      color.click();
      return new ColorSelectionPanel(driver);
    }
    
    public String getSelectedColorCode() {
      return ColorUtils.getBackgroundHexColor(color);
    }
    
    public String getLowerEdge() {
      return lowerEdge.getText();
    }
    
    public String getUpperEdge() {
      return upperEdgeInputField.getAttribute("value");
    }
    
    @Step("Enter upper edge value: {0}")
    public void enterUpperEdge(String value) {
      logger.debug("Enter upper edge value: " + value);
      upperEdgeInputField.click();
      upperEdgeInputField.clear();
      upperEdgeInputField.sendKeys(value + Keys.ENTER);
      logger.info("Wait for the map to change");
      sleep(2000);
    }
    
  }
  
}
