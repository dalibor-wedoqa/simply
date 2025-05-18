package net.simplyanalytics.pageobjects.sections.toolbar;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import net.simplyanalytics.containers.SimpleFilter;
import net.simplyanalytics.enums.DataVariable;
import net.simplyanalytics.enums.DataVariableRelation;
import net.simplyanalytics.enums.ViewType;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.views.BaseViewPage;
import net.simplyanalytics.pageobjects.pages.main.views.ComparisonReportPage;
import net.simplyanalytics.pageobjects.pages.main.views.MapPage;
import net.simplyanalytics.pageobjects.pages.main.views.RankingPage;
import net.simplyanalytics.pageobjects.pages.main.views.ScatterPlotPage;
import net.simplyanalytics.pageobjects.pages.main.views.TimeSeriesPage;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static net.simplyanalytics.utils.DatasetYearUpdater.updateDatasetYear;

public class Filtering extends BasePage {

  protected static final By ROOT = By.cssSelector(".sa-filters");
  protected WebElement root;

  @FindBy(xpath = ".//a[.//span[@class='sa-button-text'][normalize-space(.)='Apply']]")
  protected WebElement applyButton;

  @FindBy(xpath = ".//a[.//span[@class='sa-button-text'][normalize-space(.)='Close']]")
  protected WebElement closeButton;

  @FindBy(css = ".sa-close")
  protected WebElement closeIcon;

  @FindBy(css = ".sa-filters-visibility-options .sa-toggle-switch-button")
  protected WebElement activitySwitcher;

  @FindBy(css = ".sa-filters-attribute-placeholder")
  protected WebElement placeholder;

  private final String hideLocator = "//div[contains(@class,'sa-check-field')]" + "["
          + ".//span[text()='Hide']]";
  private By hideRadioButtonLocator = By.xpath(hideLocator);

  @FindBy(xpath = hideLocator)
  protected WebElement hideRadioButton;

  private final String strikeoutLocator = "//div[contains(@class,'sa-check-field')]"
          + "[.//span[text()='Strikeout']]";
  private By strikeoutRadioButtonLocator = By.xpath(strikeoutLocator);

  @FindBy(xpath = strikeoutLocator)
  protected WebElement strikeoutRadioButton;

  @FindBy(css = ".sa-filters-search-field-input input")
  protected WebElement filterField;

  private int activeDataVariable = 0;
  private ViewType viewType;
  String year = "2024";

  public Filtering(WebDriver driver, int dataVariables, ViewType viewType) {
    this(driver, viewType);
    this.activeDataVariable = dataVariables;
  }

  /**
   * Filtering constructor.
   * @param driver WebDriver
   * @param viewType ViewType
   */
  public Filtering(WebDriver driver, ViewType viewType) {
    super(driver, ROOT);
    root = driver.findElement(ROOT);
    this.viewType = viewType;
  }

  @Override
  protected void isLoaded() {
    waitForElementToAppear(activitySwitcher, "Activity switcher should be present always");
    waitForElementToAppear(applyButton, "Apply button should be present always");
    waitForElementToAppear(closeButton, "Close button should be present always");
    waitForElementToAppear(closeIcon, "Close icon should be present always");
    waitForElementToAppear(filterField, "Search filed should be present always");
  }
  public void applyEquals0Filter(WebDriver driver) {
    System.out.println("Clicking on the element with data-attribute='USACSSUB->education_20_pct->2023.2'");
    driver.findElement(By.xpath("//*[contains(@data-attribute, 'USACSSUB->education_20_pct')]")).click();

    try {
      System.out.println("Step 1: Waiting for the element to be visible.");
      WebDriverWait wait = new WebDriverWait(driver, 10);
      WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(
              By.cssSelector(".sa-button.x-box-item.sa-menu-button.sa-button-arrow.x-unselectable.sa-button-default")
      ));

      System.out.println("Step 2: Verifying if the element is enabled.");
      if (element.isEnabled()) {
        System.out.println("Step 3: Clicking the element.");
        element.click();
        System.out.println("Success: Element was clicked.");
      } else {
        System.out.println("Error: Element is not enabled for clicking.");
      }
    } catch (Exception e) {
      System.out.println("Error: Element was not visible within the timeout period.");
    }

    System.out.println("Selecting the menu item with text 'is equal to'.");
    WebDriverWait wait = new WebDriverWait(driver, 1);
    WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//*[contains(@class, 'x-menu-item-text') and contains(text(), 'is equal to')]")
    ));
    element.click();

    System.out.println("Clicking Apply.");
    applyButton.click();
  }
  /**
   * Enter search for data variable.
   * @param search search for data variable
   */
  @Step("Enter search for data variable: {0}")
  public void enterDataVaraibleSearch(String search) {
    logger.debug("Enter search for data variable: " + search);
    final WebElement dataVariableRoot = root
            .findElement(By.cssSelector(".sa-filters-search-field-input + div"));
    filterField.click();
    filterField.clear();
    filterField.sendKeys(search + Keys.ENTER);
    waitForElementToDisappear(dataVariableRoot);// SERE counted as disappear
  }

  /**
   * Checking if data variable is selectable.
   * @param dataVariable data variable
   * @return result
   */
  public boolean isDataVariableSelectable(DataVariable dataVariable) {
    return isChildPresent(root,
            By.xpath(".//div[text()=\"" + dataVariable.getFullName() + " " + "\"]"));
  }

  public List<String> getPresentDataVariables() {
    return root.findElements(By.xpath(".//div[contains(@class, 'sa-filters-attribute-list-item')]"))
            .stream().map(element -> element.getText()).collect(Collectors.toList());
  }

  /**
   * Click on data variable.
   * @param dataVariable data variable
   * @return FilteringConditionRow
   */
  @Step("Click on data variable {1}")
  public FilteringConditionRow clickDataVariable(DataVariable dataVariable) {
    logger.debug("Click on data variable " + dataVariable);
    WebElement dataVariableElement = root
            .findElement(By.xpath(".//div[contains(@class, 'sa-filters-attribute-list-item')]"
                    + "[contains(text(),\"" + dataVariable.getFullName() + "\")]"));
    dataVariableElement.click();
    activeDataVariable++;
    WebElement rowElement = root.findElement(By.xpath(
            ".//div[contains(@class, 'sa-filters-condition-row')][not(contains(@style, 'display'))]["
                    + activeDataVariable + "]"));
    return new FilteringConditionRow(driver, rowElement);
  }

  /**
   * Focus on the condition row with data varaible.
   * @param dataVariable data varaible
   * @return FilteringConditionRow
   */
  @Step("Focus on the condition row with data variable {1}")
  public FilteringConditionRow getRowForDataVariable(DataVariable dataVariable) {
    System.out.println(dataVariable.getFullName());
    WebElement rowElement = root.findElement(By.xpath(
            ".//div[contains(@class, 'sa-filters-condition-row')][not(contains(@style, 'display'))]"
                    + "//div[contains(text(), \"" + dataVariable.getFullName()
                    + "\")]/ancestor::div[contains(@class, 'sa-filters-condition-row')]"));
    return new FilteringConditionRow(driver, rowElement);
  }

  public FilteringConditionRow getRowForPercent() {
    WebElement rowElement = root.findElement(By.xpath(
            ".//div[contains(@class, 'sa-filters-condition-row')][not(contains(@style, 'display'))]"
                    + "//div[contains(text(), \"" + updateDatasetYear("% Educational Attainment | Bachelor's degree or higher, 2023",year)
                    + "\")]/ancestor::div[contains(@class, 'sa-filters-condition-row')]"));
    return new FilteringConditionRow(driver, rowElement);
  }

  /**
   * Getting all condition rows.
   * @return list with all condition rows
   */
  public List<FilteringConditionRow> getAllConditionRow() {
    try {
      driver.manage().timeouts().implicitlyWait(SHORT_WAIT, TimeUnit.SECONDS);
      List<WebElement> rows = root
              .findElements(By.xpath(".//div[contains(@class, 'sa-filters-condition-row')]"
                      + "[not(contains(@style, 'display'))]"));
      return rows.stream().map(rowElement -> new FilteringConditionRow(driver, rowElement))
              .collect(Collectors.toList());
    } finally {
      driver.manage().timeouts().implicitlyWait(FULL_WAIT, TimeUnit.SECONDS);
    }
  }

  public boolean isApplyButtonDisabled() {
    return applyButton.getAttribute("class")
            .contains("x-item-disabled");
  }

  @Step("Click on the Apply button")
  public void clickApply() {
    logger.debug("Click on the Apply button");
    applyButton.click();
    waitForLoadingToDisappear();
  }

  /**
   * Click on the Cancel button.
   */
  @Step("Click on the Close button")
  public void clickClose() {
    logger.debug("Click on the Close button");
    closeButton.click();
    waitForElementToDisappear(root);
  }

  @Step("Click on the Off-On switcher")
  public void switchActivity() {
    logger.debug("Click on the Off-On switcher");
    activitySwitcher.click();
  }

  /**
   * Close the dropdown by clicking on the x icon on the upper right corner.
   * @return
   */
  @Step("Close the dropdown by clicking on the x icon on the upper right corner")
  public BaseViewPage clickCloseFiltering() {
    logger.debug("Close the dropdown by clicking on the x icon on the upper right corner");
    closeIcon.click();
    waitForElementToDisappear(root, 30);
    switch (viewType) {
      case COMPARISON_REPORT:
        return new ComparisonReportPage(driver);
      case RANKING:
        return new RankingPage(driver);
      case MAP:
        return new MapPage(driver);
      case TIME_SERIES:
        return new TimeSeriesPage(driver);
      case SCATTER_PLOT:
        return new ScatterPlotPage(driver);
      default:
        throw new AssertionError("View Type not supported");
    }

  }

  public boolean isHidePresent() {
    return hideRadioButton.isDisplayed();
  }

  public boolean isHideActive() {
    return hideRadioButton.getAttribute("class").contains("sa-radio-checked");
  }

  /**
   * Click on the Hide radio button.
   */
  @Step("Click on the Hide radio button")
  public void clickHideRadioButton() {
    logger.debug("Click on the Hide radio button");
    hideRadioButton.click();
    waitForAttibuteToContain(root, hideRadioButtonLocator, "class", "sa-radio-checked");
  }

  public boolean isStrikeoutPresent() {
    return strikeoutRadioButton.isDisplayed();
  }

  public boolean isStrikeoutActive() {
    return strikeoutRadioButton.getAttribute("class").contains("sa-radio-checked");
  }

  /**
   * Click on the Strikeout radio button.
   */
  @Step("Click on the Strikeout radio button")
  public void clickStrikeoutRadioButton() {
    logger.debug("Click on the Strikeout radio button");
    strikeoutRadioButton.click();
    waitForAttibuteToContain(root, strikeoutRadioButtonLocator, "class", "sa-radio-checked");
  }

  /**
   * Click on the and/or button between the conditions.
   * @param conditionNumber condition number
   * @return AndOrDropwdown
   */
  @Step("Click on the {1}. and/or button between the conditions")
  public AndOrDropwdown clickAndOrButtonAfterCondition(int conditionNumber) {
    logger.debug("Click on the " + conditionNumber + ". and/or button between the conditions");
    Assert.assertTrue(
            "The number of condition should be higher or equal then the given number.\n"
                    + "Conditions: " + activeDataVariable + "\n" + "Given number: " + conditionNumber,
            conditionNumber <= activeDataVariable);
    root.findElement(By.xpath(
                    "(.//a[contains(@class, 'sa-divider-combo-button-button')])[" + conditionNumber + "]"))
            .click();
    WebElement dropdownRoot = driver
            .findElement(By.cssSelector("div.sa-combo-button-menu:not([style*='hidden'])"));
    return new AndOrDropwdown(driver, dropdownRoot);
  }

  /**
   * Getting selected combination.
   * @param conditionNumber condition number
   * @return selected combination
   */
  public String getSelectedCombination(int conditionNumber) {
    return root
            .findElement(By.xpath(
                    "(.//a[contains(@class, 'sa-divider-combo-button-button')])[" + conditionNumber + "]"))
            .getText();
  }

  public class FilteringConditionRow extends BasePage {

    @FindBy(xpath = ".//div[@class= 'x-component sa-filters-attribute x-box-item x-component-default']")
    private WebElement dataVariableButton;

    @FindBy(css = "a.sa-menu-button")
    private WebElement relationButton;

    @FindBy(xpath = ".//table[1][not(contains(@style,'display'))]//input")
    private WebElement inputField1;

    @FindBy(xpath = ".//table[2][not(contains(@style,'display'))]//input")
    private WebElement inputField2;

    @FindBy(css = "a.sa-remove-button")
    private WebElement removeButton;

    private WebElement root;

    private FilteringConditionRow(WebDriver driver, WebElement root) {
      super(driver, root);
      this.root = root;
    }

    @Override
    public void isLoaded() {
      waitForElementToAppear(dataVariableButton, "The data variable label should appear");
      waitForElementToAppear(relationButton, "The relation button should appear");
      waitForElementToAppear(inputField1, "The first input filed should be present");
    }

    /**
     * Select the relation for the condition.
     * @param relation relation
     * @return FilteringConditionRow
     */
    @Step("Select the relation {1} for the condition")
    public FilteringConditionRow selectRelation(DataVariableRelation relation) {
      allureStep("Click on the relation combobox to open it");
      relationButton.click();
      WebElement dropdownRoot = driver
              .findElement(By.cssSelector("div.sa-combo-button-menu:not([style*='hidden'])"));
      new RelationSelectionDropdown(driver, dropdownRoot).clickRelation(relation);
      return new FilteringConditionRow(driver, root);
    }

    /**
     * Enter value into the first input field.
     * @param value value
     */
    @Step("Enter value {1} into the first input field")
    public void enterValue1(String value) {
      logger.debug("Enter value " + value + " into the first input field");
      inputField1.click();
      inputField1.clear();
      inputField1.sendKeys(Keys.HOME);
      int index = inputField1.getAttribute("value").indexOf(".");
      if (index == -1) {
        inputField1.sendKeys(Keys.RIGHT);
      } else {
        for (int i = 0; i < index; i++) {
          inputField1.sendKeys(Keys.RIGHT);
        }
      }
      inputField1.sendKeys(value);
      inputField1.sendKeys(Keys.ENTER);
      dataVariableButton.click();
    }

    /**
     * Enter value into the second input field.
     * @param value value
     */
    @Step("Enter value {1} into the second input field")
    public void enterValue2(String value) {
      waitForElementToAppear(inputField2,
              "The second input field should be visible to use this method");
      logger.debug("Enter value " + value + " into the second input field");
      inputField2.click();
      inputField2.clear();
      inputField2.clear();
      inputField2.sendKeys(Keys.HOME);
      int index = inputField2.getAttribute("value").indexOf(".");
      if (index == -1) {
        inputField2.sendKeys(Keys.RIGHT);
      } else {
        for (int i = 0; i < index; i++) {
          inputField2.sendKeys(Keys.RIGHT);
        }
      }
      inputField2.sendKeys(value);
      inputField2.sendKeys(Keys.ENTER);
      dataVariableButton.click();
    }

    /**
     * Click on the x icon after the condition.
     */
    @Step("Click on the x icon after the condition")
    public void clickRemoveButton() {
      logger.debug("Click on the x icon after the condition");
      removeButton.click();
      activeDataVariable--;
      waitForElementToDisappear(root);
      waitForElementToStop(placeholder);
    }

    /**
     * Getting actual values.
     * @return actual values
     */
    public SimpleFilter getActualValues() {
      SimpleFilter simpleFilter = new SimpleFilter();
      simpleFilter.setDataVariable(DataVariable.getByFullName(dataVariableButton.getText().substring(0, dataVariableButton.getText().length() - 8)));
      simpleFilter.setCondition(DataVariableRelation.getByName(relationButton.getText()));
      simpleFilter.setValue(inputField1.getAttribute("value"));
      if (isPresent(inputField2)) {
        simpleFilter.setValue2(inputField2.getAttribute("value"));
      }
      return simpleFilter;
    }
  }

  public class RelationSelectionDropdown extends BasePage {

    protected WebElement root;

    protected RelationSelectionDropdown(WebDriver driver, WebElement root) {
      super(driver, root);
      this.root = root;
    }

    @Override
    protected void isLoaded() {
      // TODO
    }

    /**
     * Click on the relation.
     * @param relation relation
     */
    @Step("Click on the relation {1}")
    public void clickRelation(DataVariableRelation relation) {
      logger.debug("Click on the relation \"" + relation + "\"");
      root.findElement(By.xpath(".//span[text()='" + relation.getName() + "']")).click();
      waitForElementToDisappear(root);
    }
  }

  public class AndOrDropwdown extends BasePage {

    protected WebElement root;

    protected AndOrDropwdown(WebDriver driver, WebElement root) {
      super(driver, root);
      this.root = root;
    }

    @Override
    protected void isLoaded() {
      // TODO
    }

    /**
     * Click on the And button.
     */
    @Step("Click on the And button")
    public void clickAnd() {
      logger.debug("Click on the And button");
      root.findElement(By.xpath(".//span[text()='and']")).click();
      waitForElementToDisappear(root);
    }

    /**
     * Click on the Or button.
     */
    @Step("Click on the Or button")
    public void clickOr() {
      logger.debug("Click on the Or button");
      root.findElement(By.xpath(".//span[text()='or']")).click();
      waitForElementToDisappear(root);
    }

  }

  public boolean isDisplayed() {
    return isPresent(root);
  }

}
