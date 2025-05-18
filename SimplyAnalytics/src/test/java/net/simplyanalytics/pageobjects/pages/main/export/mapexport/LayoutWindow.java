package net.simplyanalytics.pageobjects.pages.main.export.mapexport;

import java.text.DecimalFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import net.simplyanalytics.enums.StandardSize;
import net.simplyanalytics.pageobjects.pages.main.export.MainExport;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

/**
 * .
 *
 * @author wedoqa
 */
public class LayoutWindow extends MainExport {
	
  protected static final By OVERLAY_EDIT_PANEL = By.cssSelector(".sa-abstract-edit-window");
  protected static final By ADD_TEXT_LABEL_PANEL = By
      .cssSelector(".sa-export-add-text-label-window");
  protected static final By LABEL_EDIT_PANEL = By.cssSelector(".sa-label-edit-window");
  protected static final By LABEL = By.xpath(".//div[contains(@id,'vector-map-export-text-label-overlay')]");
  @FindBy(css = ".sa-map-export-header")
  private WebElement dialogTitle;

  @FindBy(xpath = ".//span[text()='In Inches']/parent::label/parent::div")
  private WebElement pageSizeInInches;

  @FindBy(xpath = ".//span[text()='In Pixels']/parent::label/parent::div")
  private WebElement pageSizeInPixels;

  @FindBy(css = ".sa-menu-button")
  private WebElement standartSizesCombo;

  @FindBy(xpath = ".//span[text()='Landscape']/parent::label/parent::div")
  private WebElement orientationLandscape;

  @FindBy(xpath = ".//span[text()='Portrait']/parent::label/parent::div")
  private WebElement orientationPortrait;

  @FindBy(xpath = "//table[.//label[text()='Width:']]")
  private WebElement widthRow;

  @FindBy(xpath = ".//table[.//label[text()='Height:']]")
  private WebElement heightRow;

  @FindBy(css = ".sa-export-aspect-ratio-button")
  private WebElement ratioLockButton;

  @FindBy(xpath = ".//div[text()='Legend:']/parent::div/div[contains(@class,'sa-checkbox')]")
  private WebElement legendCheckBox;

  @FindBy(xpath = ".//span[text()='Light']/parent::label/parent::div")
  private WebElement legendThemeLight;

  @FindBy(xpath = ".//span[text()='Dark']/parent::label/parent::div")
  private WebElement legendThemeDark;

  @FindBy(xpath = ".//div[text()='Scale Bar:']/parent::div/div[contains(@class,'sa-checkbox')]")
  private WebElement scaleBarCheckbox;

  @FindBy(xpath = ".//div[text()='Inset Map:']/parent::div/div[contains(@class,'sa-checkbox')]")
  private WebElement insetMapCheckbox;

  @FindBy(xpath = ".//div[text()='North Arrow:']/parent::div/div[contains(@class,'sa-checkbox')]")
  private WebElement northArrow;

  @FindBy(css = ".sa-export-preview-page")
  private WebElement paper;

  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Return to Cropping']]")
  private WebElement returnToCroppingButton;

  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Continue to Export']]")
  private WebElement continueToExportButton;

  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Add Text Label…']]")
  private WebElement addTextLabelButton;

  @FindBy(xpath = ".//span[text()='Cancel']/..")
  private WebElement cancelButton;

  @FindBy(css = ".sa-close")
  private WebElement close;

  @FindBy(xpath = "//*[contains(@class, 'sa-vector-map-export-overlay')][contains(@id,'vector-map-export-legend-overlay')]")
  private WebElement legend;

  @FindBy(xpath = "//div[contains(@class, 'sa-simple-container-default-resizable')]")
  private WebElement map;
  
  @FindBy(xpath = "//div[contains(@class, 'sa-abstract-edit-window')][contains(@id,'export-overlay-edit-window')]") //old locator - > xpath = "//*[contains(@class, 'sa-vector-map-export-overlay')][contains(@id,'export-preview')]/img/..")
  private WebElement mapCoordinates;

  @FindBy(xpath = "//*[contains(@class, 'sa-vector-map-export-overlay')]"
      + "[contains(@id,'vector-map-export-scale-bar')]")
  private WebElement scaleBar;

  @FindBy(xpath = "//*[contains(@class, 'sa-vector-map-export-overlay')]"
      + "[contains(@id,'vector-map-export-inset-map')]")
  private WebElement miniMap;

  @FindBy(xpath = "//*[contains(@class, 'sa-vector-map-export-overlay')]"
      + "[contains(@id,'vector-map-export-north-arrow')]")
  private WebElement arrow;

  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-map-export.sa-map-export-layout");
  protected WebElement root;

  public LayoutWindow(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
  }

  @Override
  public void isLoaded() {
    waitForLoadingToDisappear();
    waitForElementToAppear(close, "The layout window is not loaded");
  }

  public String getTitle() {
    return dialogTitle.getText();
  }

  public String getRowWidth() { return widthRow.getText(); }

  /**
   * Click on the close button (x icon).
   */
  @Step("Click on the close button (x icon)")
  public void clickClose() {
    logger.debug("Click on the close button (x icon)");
    close.click();
    waitForElementToDisappear(root);
  }

  /**
   * Click on the Cancel button.
   */
  @Step("Click on the Cancel button")
  public void clickCancel() {
    logger.debug("Click on the Cancel button");
    cancelButton.click();
    waitForElementToDisappear(root);
  }

  /**
   * Click on the Continue to Export button.
   * 
   * @return ExportWindow
   */
  @Step("Click on the Continue to Export button")
  public ExportWindow clickContinueToExport() {
    logger.debug("Click on the Continue to Export button");
    waitForElementToBeClickable(continueToExportButton,
        "Continue to Export button is not clickable at a point").click();
    waitForElementToDisappear(root);
    return new ExportWindow(driver);
  }

  /**
   * Click on the Return to Cropping button.
   * 
   * @return CroppingWindow
   */
  @Step("Click on the Return to Cropping button")
  public CroppingWindow clickReturnToCropping() {
    logger.debug("Click on the Return to Cropping button");
    waitForElementToBeClickable(returnToCroppingButton,
        "Return to Cropping button is not clickable at a point").click();
    waitForElementToDisappear(root);
    return new CroppingWindow(driver);
  }

  /**
   * Click on the Add Text Label.
   * 
   * @return AddTextLabeLPanel
   */
  @Step("Click on the Add Text Label")
  public AddTextLabeLPanel clickAddTextLabel() {
    logger.debug("Click on the Add Text Label");
    waitForElementToBeClickable(addTextLabelButton,
        "Add Text Label button is not clickable at a point").click();
    waitForElementToDisappear(root);
    return new AddTextLabeLPanel(driver);
  }

  /**
   * Click on the Page Size: In Inches radio button.
   */
  @Step("Click on the Page Size: In Inches radio button")
  public void clickInInches() {
    if (!isPageSizeInInches()) {
      logger.debug("Click on the Page Size: In Inches radio button");
      waitForElementToBeClickable(pageSizeInInches,
          "Page Size: In Inches radio button is not clickable at a point").click();
    }
  }

  /**
   * Click on the Page Size: In Pixels radio button.
   */
  @Step("Click on the Page Size: In Pixels radio button")
  public void clickInPixels() {
    if (!isPageSizeInPixels()) {
      logger.debug("Click on the Page Size: In Pixels radio button");
      waitForElementToBeClickable(pageSizeInPixels,
          "Page Size: In Pixels radio button is not clickable at a point").click();
    }
  }

  public boolean isPageSizeInInches() {
    return pageSizeInInches.getAttribute("class").contains("sa-radio-checked");
  }

  public boolean isPageSizeInPixels() {
    return pageSizeInPixels.getAttribute("class").contains("sa-radio-checked");
  }

  public Dimension getPaperSize() {
    return paper.getSize();
  }

  public Point getPaperLocation() {
    return paper.getLocation();
  }

  /**
   * Click on the Standard Size combobox.
   */
  @Step("Click on the Standard Size combobox")
  private void clickStandardSize() {
    logger.debug("Click on the Standard Size combobox");
    waitForElementToBeClickable(standartSizesCombo,
        "Standart Sizes Combo is not clickable at a point").click();
  }

  public String getStandardSizeComboText() {
    return standartSizesCombo.getText();
  }

  /**
   * Click Standard Size Combo and Choose standard size.
   * 
   * @param standartSize standard size
   */
  @Step("Click Standard Size Combo and Choose standard size {0}")
  public void chooseStandardSize(StandardSize standartSize) {
    clickStandardSize();

    allureStep("Choose standard size " + standartSize);
    waitForElementToAppear(
        driver.findElement(By.xpath("//div[contains(@class, 'sa-combo-button-menu')]"
            + "[not(contains(@style, 'visibility: hidden;'))]"
            + "//a[@class='x-menu-item-link']/span[text()='" + standartSize.getValue() + "']"
            + "/parent::a")),
        "").click();
  }

  /**
   * Click on the Landscape orientation radio button.
   */
  @Step("Click on the Landscape orientation radio button")
  public void clickLandscape() {
    if (!isLandscapeSelected()) {
      logger.debug("Click on the Landscape orientation radio button");
      waitForElementToBeClickable(orientationLandscape,
          "Orientation Landscape radio button is not clickable at a point").click();
    }
  }

  /**
   * Click on the Portrait orientation radio button.
   */
  @Step("Click on the Portrait orientation radio button")
  public void clickPortrait() {
    if (!isPortraitSelected()) {
      logger.debug("Click on the Portrait orientation radio button");
      waitForElementToBeClickable(orientationPortrait,
          "Orientation Portrait radio button is not clickable at a point").click();
    }
  }

  public boolean isLandscapeSelected() {
    return orientationLandscape.getAttribute("class").contains("sa-radio-checked");
  }

  public boolean isPortraitSelected() {
    return orientationPortrait.getAttribute("class").contains("sa-radio-checked");
  }

  public double getWidth() {
    return Double.parseDouble(widthRow.findElement(By.cssSelector("input")).getAttribute("value"));
  }

  /**
   * Enter page width.
   * 
   * @param width page width
   */
  @Step("Enter page width: {0}")
  public void enterWidth(int width) {
    logger.debug("Enter page width: " + width);
    sleep(200);
    WebElement inputField = widthRow.findElement(By.cssSelector("input"));
    sleep(200);
    Actions act = new Actions(driver);
    act.doubleClick(inputField).perform();
    sleep(200);
    inputField.sendKeys(String.valueOf(width));
    sleep(200);
    widthRow.findElement(By.cssSelector(".sa-export-unitsfield-units")).click();

    waitForLoadingToDisappear();
  }

  /**
   * Enter page width.
   * 
   * @param width page width
   */
  @Step("Enter page width: {0}")
  public void enterWidth(double width) {
    logger.debug("Enter page width: " + width);
    WebElement inputField = widthRow.findElement(By.cssSelector("input"));
    Actions act = new Actions(driver);
    act.doubleClick(inputField).perform();
    inputField.sendKeys(new DecimalFormat("#0.00").format(width));
    widthRow.findElement(By.cssSelector(".sa-export-unitsfield-units")).click();
  }

  /**
   * Enter page height.
   * 
   * @param height page height
   */
  @Step("Enter page height: {0}")
  public void enterHeight(int height) {
    logger.debug("Enter page width: " + height);
    WebElement inputField = heightRow.findElement(By.cssSelector("input"));
    Actions act = new Actions(driver);
    act.doubleClick(inputField).perform();
    inputField.sendKeys((CharSequence) String.valueOf(height));
    widthRow.findElement(By.cssSelector(".sa-export-unitsfield-units")).click();
  }

  /**
   * Enter page height.
   * 
   * @param height page height
   */
  @Step("Enter page height: {0}")
  public void enterHeight(double height) {
    logger.debug("Enter page height: " + height);
    WebElement inputField = heightRow.findElement(By.cssSelector("input"));
    Actions act = new Actions(driver);
    act.doubleClick(inputField).perform();
    inputField.sendKeys(new DecimalFormat("#0.00").format(height));
    heightRow.findElement(By.cssSelector(".sa-export-unitsfield-units")).click();
  }

  public String getWidthUnit() {
    return widthRow.findElement(By.cssSelector(".sa-export-unitsfield-units")).getText();
  }

  public double getHeight() {
    return Double.parseDouble(heightRow.findElement(By.cssSelector("input")).getAttribute("value"));
  }

  public String getHeightUnit() {
    return heightRow.findElement(By.cssSelector(".sa-export-unitsfield-units")).getText();
  }

  @Step("Click on the aspect ratio lock button")
  public void clickLockRatio() {
    allureStep("Click on the aspect ratio lock button");
    ratioLockButton.click();
  }

  public boolean isRatioLocked() {
    return ratioLockButton.getAttribute("class").contains("sa-export-aspect-ratio-button-locked");
  }

  /**
   * Click on the Legend combobox.
   */
  @Step("Click on the Legend combobox")
  public void clickLegendCheckBox() {
    logger.debug("Click on the legend combobox");
    waitForElementToBeClickable(legendCheckBox, "Legend CheckBox is not clickable at a point")
        .click();
    waitForLoadingToDisappear();
  }

  public boolean isLegendCheckBoxChecked() {
    return legendCheckBox.getAttribute("class").contains("sa-checkbox-checked");
  }

  /**
   * Click on the Legend Theme: Light radio button.
   */
  @Step("Click on the Legend Theme: Light radio button")
  public void clickLight() {
    if (!isLegendThemeLightSelected()) {
      logger.debug("Click on the Legend Theme: Light radio button");
      waitForElementToBeClickable(legendThemeLight,
          "Legend Theme: Light radio button is not clickable at a point").click();
    }
  }

  /**
   * Click on the Legend Theme: Dark radio button.
   */
  @Step("Click on the Legend Theme: Dark radio button")
  public void clickDark() {
    if (!isLegendThemeDarkSelected()) {
      logger.debug("Click on the Legend Theme: Dark radio button");
      waitForElementToBeClickable(legendThemeDark,
          "Legend Theme: Dark radio button is not clickable at a point").click();
    }
  }

  public boolean isLegendThemeLightSelected() {
    return legendThemeLight.getAttribute("class").contains("sa-radio-checked");
  }

  public boolean isLegendThemeDarkSelected() {
    return legendThemeDark.getAttribute("class").contains("sa-radio-checked");
  }

  /**
   * Click on the Scale Bar checkbox.
   */
  @Step("Click on the Scale Bar checkbox")
  public void clickScaleBarCheckBox() {
    logger.debug("Click on the Scale Bar checkbox");
    waitForElementToBeClickable(scaleBarCheckbox, "Scale Bar checkbox is not clickable at a point")
        .click();
    waitForLoadingToDisappear();
  }

  public boolean isScaleBarBoxChecked() {
    return scaleBarCheckbox.getAttribute("class").contains("sa-checkbox-checked");
  }

  /**
   * Click on the Insert Map checkbox.
   */
  @Step("Click on the Insert Map checkbox")
  public void clickInsetMapCheckBox() {
    logger.debug("Click on the Insert Map checkbox");
    waitForElementToBeClickable(insetMapCheckbox, "Insert Map checkbox is not clickable at a point")
        .click();
    waitForLoadingToDisappear();
  }

  public boolean isInsetMapBoxChecked() {
    return insetMapCheckbox.getAttribute("class").contains("sa-checkbox-checked");
  }

  /**
   * Click on the North Arrow checkbox.
   */
  @Step("Click on the North Arrow checkbox")
  public void clickNorthArrowCheckBox() {
    logger.debug("Click on the North Arrow checkbox");
    waitForElementToBeClickable(northArrow, "North Arrow checkbox is not clickable at a point")
        .click();
    waitForLoadingToDisappear();
  }

  public boolean isNorthArrowChecked() {
    return northArrow.getAttribute("class").contains("sa-checkbox-checked");
  }

  /*
   * Text Label Image:
   */
  public boolean isTextLabelPresent() {
    return isPresent(LABEL);
  }

  public List<TextLabel> getTextLabels() {
    return driver.findElements(LABEL).stream().map(element -> new TextLabel(driver, element))
        .collect(Collectors.toList());
  }

  /**
   * Click on the map to open the Overlay Edit panel.
   * 
   * @return OverlayEditPanel
   */
  @Step("Click on the map to open the Overlay Edit panel")
  public OverlayEditPanel clickMap() {
    logger.debug("Click on the map to open the Overlay Edit panel");
    waitForElementToBeClickable(map, "Map is not clickable at a point").click();
    return new OverlayEditPanel(driver);
  }

  public boolean isMapPresent() {
    return isPresent(map);
  }

  public Dimension getMapSize() {
    return map.getSize();
  }

  public Point getMapLocation() {
    return map.getLocation();
  }

  /**
   * Move map with the following vector.
   * 
   * @param x point on x-axis
   * @param y point on y-axis
   */
  @Step("Move map with the following vector: ({0},{1})")
  public OverlayEditPanel moveMap(int x, int y) {
    //map.click();
    logger.debug("Move map with the following vector: (" + x + "," + y + ")");
    if (isChildPresent(root, LayoutWindow.OVERLAY_EDIT_PANEL)) {
      WebElement panel = root.findElement(LayoutWindow.OVERLAY_EDIT_PANEL);
      Actions actions = new Actions(driver);
      actions.dragAndDropBy(map, x, y).build().perform();
      waitForElementToDisappear(panel);
      return new OverlayEditPanel(driver);
    } else {
      Actions actions = new Actions(driver);
      actions.dragAndDropBy(map, x, y).build().perform();
      waitForElementToStop(map);
      //sleep(1000);
    }
      map.click();
      return new OverlayEditPanel(driver);

  }
  
  public int getMapTop() {
    String style =  mapCoordinates.getAttribute("style").trim();
    Pattern r = Pattern.compile("top: ([\\d]+)");
    Matcher m = r.matcher(style);
    if (m.find( )) {
      return Integer.parseInt(m.group(1));
    }
    return 0;
  }
  
  public int getMapLeft() {
    String style =  mapCoordinates.getAttribute("style").trim();
    Pattern r = Pattern.compile("left: ([\\d]+)");
    Matcher m = r.matcher(style);
    if (m.find( )) {
      return Integer.parseInt(m.group(1));
    }
    return 0;
  }


  /**
   * Click on the legend to open the Overlay Edit panel.
   * 
   * @return OverlayEditPanel
   */
  @Step("Click on the legend to open the Overlay Edit panel")
  public OverlayEditPanel clickLegend() {
    logger.debug("Click on the legend to open the Overlay Edit panel");
    waitForElementToBeClickable(legend, "Legend is not clickable at a point").click();
    return new OverlayEditPanel(driver);
  }

  public boolean isLegendPresent() {
    return isPresent(legend);
  }

  public Dimension getLegendSize() {
    return legend.getSize();
  }

  public Point getLegendLocation() {
    return legend.getLocation();
  }

  /**
   * Move legend with the following vector.
   * 
   * @param x point on x-axis
   * @param y point on y-axis
   */
  @Step("Move legend with the following vector: ({0},{1})")
  public OverlayEditPanel moveLegend(int x, int y) {
    legend.click();
    logger.debug("Move legend with the following vector: (" + x + "," + y + ")");
    if(isChildPresent(root, LayoutWindow.OVERLAY_EDIT_PANEL)) {
      WebElement panel = root.findElement(LayoutWindow.OVERLAY_EDIT_PANEL);
      Actions actions = new Actions(driver);
      actions.dragAndDropBy(legend, x, y).build().perform();
      waitForElementToDisappear(panel);
    } else {
      Actions actions = new Actions(driver);
      actions.dragAndDropBy(legend, x, y).build().perform();
    }
    legend.click();
    return new OverlayEditPanel(driver);
  }

  /**
   * Click on scale bar.
   * 
   * @return OverlayEditPanel
   */
  @Step("Click on the scale bar to open the Overlay Edit panel")
  public OverlayEditPanel clickScaleBar() {
    logger.debug("Click on the scale bar to open the Overlay Edit panel");
    waitForElementToBeClickable(scaleBar, "Legend is not clickable at a point").click();
    return new OverlayEditPanel(driver);
  }

  public boolean isScaleBarPresent() {
    return isPresent(scaleBar);
  }

  public Dimension getScaleBarSize() {
    return scaleBar.getSize();
  }

  public Point getScaleBarLocation() {
    return scaleBar.getLocation();
  }

  /**
   * Move scale bar with the following vector.
   * 
   * @param x point on x-axis
   * @param y point on y-axis
   */
  @Step("Move scale bar with the following vector: ({0},{1})")
  public OverlayEditPanel moveScaleBar(int x, int y) {
    //scaleBar.click();
    logger.debug("Move scale bar with the following vector: (" + x + "," + y + ")");
    if(isChildPresent(root, LayoutWindow.OVERLAY_EDIT_PANEL)) {
      WebElement panel = root.findElement(LayoutWindow.OVERLAY_EDIT_PANEL);
      Actions actions = new Actions(driver);
      actions.dragAndDropBy(scaleBar, x, y).build().perform();
      waitForElementToDisappear(panel);
      return new OverlayEditPanel(driver);
    } else {
      Actions actions = new Actions(driver);
      actions.dragAndDropBy(scaleBar, x, y).build().perform();
    }
      scaleBar.click();
      return new OverlayEditPanel(driver);
    }


  /**
   * Click mini map.
   * 
   * @return
   */
  @Step("Click on the mini map to open the Overlay Edit panel")
  public OverlayEditPanel clickMiniMap() {
    logger.debug("Click on the mini map to open the Overlay Edit panel");
    waitForElementToBeClickable(miniMap, "Legend is not clickable at a point").click();
    return new OverlayEditPanel(driver);
  }

  public boolean isMiniMapPresent() {
    return isPresent(miniMap);
  }

  public Dimension getMiniMapSize() {
    return miniMap.getSize();
  }

  public Point getMiniMapLocation() {
    return miniMap.getLocation();
  }

  /**
   * Move mini map with the following vector.
   * 
   * @param x point on x-axis
   * @param y point on y-axis
   */
  @Step("Move mini map with the following vector: ({0},{1})")
  public OverlayEditPanel moveMiniMap(int x, int y) {
    logger.debug("Move mini map with the following vector: (" + x + "," + y + ")");
    if (isChildPresent(root, LayoutWindow.OVERLAY_EDIT_PANEL)) {
      WebElement panel = root.findElement(LayoutWindow.OVERLAY_EDIT_PANEL);
      Actions actions = new Actions(driver);
      actions.dragAndDropBy(miniMap, x, y).build().perform();
      waitForElementToDisappear(panel);
      return new OverlayEditPanel(driver);
    } else {
      Actions actions = new Actions(driver);
      actions.dragAndDropBy(miniMap, x, y).build().perform();
    }
      miniMap.click();
      return new OverlayEditPanel(driver);
    }


  /**
   * Click on arrow.
   * 
   * @return OverlayEditPanel
   */
  @Step("Click on the north arrow to open the Overlay Edit panel")
  public OverlayEditPanel clickArrow() {
    logger.debug("Click on the north arrow to open the Overlay Edit panel");
    waitForElementToBeClickable(arrow, "Legend is not clickable at a point").click();
    return new OverlayEditPanel(driver);
  }

  public boolean isArrowPresent() {
    return isPresent(arrow);
  }

  public Dimension getArrowSize() {
    return arrow.getSize();
  }

  public Point getArrowLocation() {
    return arrow.getLocation();
  }

  /**
   * Move north arrow with the following vector.
   * 
   * @param x point on x-axis
   * @param y point on y-axis
   */
  @Step("Move north arrow with the following vector: ({0},{1})")
  public OverlayEditPanel moveArrow(int x, int y) {
    logger.debug("Move north arrow with the following vector: (" + x + "," + y + ")");
    if (isChildPresent(root, LayoutWindow.OVERLAY_EDIT_PANEL)) {
      WebElement panel = root.findElement(LayoutWindow.OVERLAY_EDIT_PANEL);
      Actions actions = new Actions(driver);
      actions.dragAndDropBy(arrow, x, y).build().perform();
      waitForElementToDisappear(panel);
      return new OverlayEditPanel(driver);
    } else {
      Actions actions = new Actions(driver);
      actions.dragAndDropBy(arrow, x, y).build().perform();
    }
       arrow.click();
      return new OverlayEditPanel(driver);
    }


  public boolean isDisplayed() {
    return isPresent(root);
  }

  // TODO add text label check behavior and change data on click

}
