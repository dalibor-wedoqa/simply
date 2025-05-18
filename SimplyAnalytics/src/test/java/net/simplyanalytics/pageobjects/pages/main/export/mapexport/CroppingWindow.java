package net.simplyanalytics.pageobjects.pages.main.export.mapexport;

import net.simplyanalytics.enums.StandardSize;
import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.pageobjects.pages.main.export.MainExport;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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
public class CroppingWindow extends MainExport {

  @FindBy(id = "export-cropping-window-3913")
  private WebElement mapExportDialog;

  @FindBy(css = ".sa-map-export-header")
  private WebElement dialogTitle;

  @FindBy(css = ".sa-crop-box-mask-hole")
  private WebElement cropLinesElement;

  @FindBy(xpath = ".//span[text()='Landscape']/parent::label/parent::div")
  private WebElement orientationLandscape;

  @FindBy(xpath = ".//span[text()='Portrait']/parent::label/parent::div")
  private WebElement orientationPortrait;

  @FindBy(css = ".sa-checkbox-button")
  private WebElement lockAspectRatio;

  @FindBy(css = ".sa-menu-button")
  private WebElement standartSizesCombo;

  @FindBy(xpath = ".//a[.//span[normalize-space(.)='Continue to Layout']]")
  private WebElement continueToLayoutButton;

  @FindBy(xpath = ".//div[contains(@class,'sa-map-export-footer')]//a[.//span[normalize-space(.)='Cancel']]")
  private WebElement cancelButton;

  @FindBy(css = ".sa-close")
  private WebElement closeButton;

  @FindBy(css = ".sa-crop-box-mask-hole")
  private WebElement maskHole;

  protected static final By ROOT_LOCATOR = By.cssSelector(".sa-map-export-cropping");
  protected WebElement root;

  public CroppingWindow(WebDriver driver) {
    super(driver, ROOT_LOCATOR);
    root = driver.findElement(ROOT_LOCATOR);
  }

  @Override
  public void isLoaded() {
    waitForElementToAppear(cropLinesElement, "Cropping window is not loaded");
    waitForLoadingToDisappear();
    waitForElementToAppear(maskHole, "the mask hole is missing");
  }

  public String getTitle() {
    return dialogTitle.getText();
  }

  /**
   * Click on the close button (x icon).
   */
  @Step("Click on the close button (x icon)")
  public void clickClose() {
    logger.debug("Click on the close button (x icon)");
    closeButton.click();
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

  @Step("Click on the Lock Aspect Radio checkbox")
  public void clickLockAspectRatioCheckbox() {
    logger.debug("Click on the Lock Aspect Radio checkbox");
    lockAspectRatio.click();
  }

  /**
   * Click on the Continue to Layout button.
   * 
   * @return LayoutWindow
   */
  @Step("Click on the Continue to Layout button")
  public LayoutWindow clickContinueToLayout() {
    logger.debug("Click on the Continue to Layout button");
    waitForElementToBeClickable(continueToLayoutButton,
        "Continue Layout Button is not clickable at a point").click();
    return new LayoutWindow(driver);
  }

  /**
   * Click on the Landscape orientation radio button.
   */
  @Step("Click on the Landscape orientation radio button")
  public void clickLandscape() {
    if (!isLandscapeSelected()) {
      logger.debug("Click on the Landscape orientation radio button");
      waitForElementToBeClickable(orientationLandscape,
          "Orientation Landscape Radio Button is not clickable at a point").click();
      updateMaskHole();
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
          "Orientation Portrait Radio Button is not clickable at a point").click();
      updateMaskHole();
    }
  }

  /**
   * Click on the Standard Sizes combobox.
   */
  @Step("Click on the Standard Sizes combobox")
  private void clickStandardSize() {
    logger.debug("Click on the Standard Size combobox");
    waitForElementToBeClickable(standartSizesCombo,
        "Standart Sizes Combo is not clickable at a point").click();
  }

  /**
   * Click on the Standard Size Combo and Choose a standard size.
   * 
   * @param standartSize standard size
   */
  @Step("Click on the Standard Size Combo and Choose a standard size: {0}")
  public void chooseStandardSize(StandardSize standartSize) {
    clickStandardSize();
    logger.debug("Choose standard size: " + standartSize.getValue());
    waitForElementToAppear(driver.findElement(
        By.xpath("//a[@class='x-menu-item-link']/span[text()='" + standartSize.getValue() + "']")),
        "").click();
    updateMaskHole();
  }

  public String getStandardSizeComboText() {
    return standartSizesCombo.getText();
  }

  public boolean isLandscapeSelected() {
    return orientationLandscape.getAttribute("class").contains("sa-radio-checked");
  }

  public boolean isPortraitSelected() {
    return orientationPortrait.getAttribute("class").contains("sa-radio-checked");
  }

  private void updateMaskHole() {
    new BasePage(driver, By.cssSelector(".sa-crop-box-mask-hole"), true) {
      @Override
      protected void isLoaded() {
      }

    };
    maskHole = root.findElement(By.cssSelector(".sa-crop-box-mask-hole"));
  }

  public Dimension getHoleSize() {
    return maskHole.getSize();
  }

  public Point getHoleLocation() {
    return maskHole.getLocation();
  }

  /**
   * Move hole to location.
   * 
   * @param x point on x-axis
   * @param y point on y-axis
   */
  @Step("Move hole")
  public void moveHole(int x, int y) {
    sleep(2000);
    logger.debug("Move the mask hole with vector: (" + x + ", " + y + ")");
    Actions actions = new Actions(driver);
    actions.dragAndDropBy(driver.findElement(
            By.cssSelector(".sa-crop-box-mask-hole")), x, y)
        .build().perform();
    waitForElementToStop(maskHole);
    updateMaskHole();
  }

  /**
   * Resize hole.
   * 
   * @param x resizable point on x-axis
   * @param y resizable point on y-axis
   */
  @Step("Resize hole")
  public void resizeHole(int x, int y) {
    logger.debug("Resize the mask hole with vector: (" + x + ", " + y + ")");
    Actions actions = new Actions(driver);
    actions.moveToElement(driver.findElement(By.cssSelector(".sa-crop-box-mask-hole")),
        -5 - maskHole.getSize().getWidth()/2,
        -5 - maskHole.getSize().getHeight()/2)
        .clickAndHold().moveByOffset(x, y).release().build().perform();
    waitForElementToStop(maskHole);
    updateMaskHole();
  }

  public boolean exportIsDisplayed() {
    return isPresent(mapExportDialog);
  }

}
