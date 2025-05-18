package net.simplyanalytics.pageobjects.panels.color;

import java.awt.geom.Point2D;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class HslTab extends BaseColorTab {

  @FindBy(css = ".sa-color-window-slider:nth-child(1) input[id*='sa-numberfield']")
  private WebElement hueInputField;
  @FindBy(css = ".sa-color-window-slider:nth-child(1) [id*='color-window-slide-bar']")
  private WebElement hueRoot;
  private ColorWindowSlideBar hueBar;

  @FindBy(css = ".sa-color-window-slider:nth-child(2) input[id*='sa-numberfield']")
  private WebElement satInputField;
  @FindBy(css = ".sa-color-window-slider:nth-child(2) [id*='color-window-slide-bar']")
  private WebElement satRoot;
  private ColorWindowSlideBar satBar;

  @FindBy(css = ".sa-color-window-slider:nth-child(3) input[id*='sa-numberfield']")
  private WebElement lumInputField;
  @FindBy(css = ".sa-color-window-slider:nth-child(3) [id*='color-window-slide-bar']")
  private WebElement lumRoot;
  private ColorWindowSlideBar lumBar;

  @FindBy(css = ".sa-color-window-spectrum")
  private WebElement colorSpectrum;

  @FindBy(css = ".sa-color-window-spectrum-dot")
  private WebElement dot;

  protected static final By slider = By.cssSelector(".sa-color-window-slider-arrow");
  protected static final By rootLocator = By.cssSelector("[id*='hsl-tab']");

  protected HslTab(WebDriver driver) {
    super(driver, rootLocator);
  }

  @Override
  protected void isLoaded() {
    waitForElementToAppear(hueInputField, "The hue input field missing");
    hueBar = new ColorWindowSlideBar(driver, hueRoot);

    waitForElementToAppear(satInputField, "The green input field missing");
    satBar = new ColorWindowSlideBar(driver, satRoot);

    waitForElementToAppear(lumInputField, "The blue input field missing");
    lumBar = new ColorWindowSlideBar(driver, lumRoot);
  }

  public int getHueValue() {
    return Integer.parseInt(hueInputField.getAttribute("value"));
  }

  /**
   * Enter hue value.
   * 
   * @param value hue value
   */
  @Step("Enter hue value: {0}")
  public void enterHueValue(int value) {
    logger.debug("Enter hue value: " + value);
    hueInputField.click();
    hueInputField.clear();
    if (value > 100) {
      hueInputField
          .sendKeys("" + value / 100 + Keys.LEFT + Keys.BACK_SPACE + Keys.RIGHT + value % 100);
    } else {
      hueInputField.sendKeys("" + value);
    }
    waitForElementToStop(getHueBar().arrow);
  }

  public ColorWindowSlideBar getHueBar() {
    return hueBar;
  }

  public int getSatValue() {
    return Integer.parseInt(satInputField.getAttribute("value"));
  }

  /**
   * Enter sat value.
   * 
   * @param value sat value
   */
  @Step("Enter sat value: {0}")
  public void enterSatValue(int value) {
    logger.debug("Enter sat value: " + value);
    satInputField.click();
    satInputField.clear();
    if (value > 100) {
      satInputField
          .sendKeys("" + value / 100 + Keys.LEFT + Keys.BACK_SPACE + Keys.RIGHT + value % 100);
    } else {
      satInputField.sendKeys("" + value);
    }
    waitForElementToStop(getSatBar().arrow);
  }

  public ColorWindowSlideBar getSatBar() {
    return satBar;
  }

  public int getLumValue() {
    return Integer.parseInt(lumInputField.getAttribute("value"));
  }

  /**
   * Enter lum value.
   * 
   * @param value lum value
   */
  @Step("Enter lum value: {0}")
  public void enterLumValue(int value) {
    logger.debug("Enter lum value: " + value);
    lumInputField.click();
    lumInputField.clear();
    if (value > 100) {
      lumInputField
          .sendKeys("" + value / 100 + Keys.LEFT + Keys.BACK_SPACE + Keys.RIGHT + value % 100);
    } else {
      lumInputField.sendKeys("" + value);
    }
    waitForElementToStop(getLumBar().arrow);
  }

  public ColorWindowSlideBar getLumBar() {
    return lumBar;
  }

  /**
   * Click on the color spectrum bar on the hue value from the left side and lum
   * value from the top.
   * 
   * @param hue hue value
   * @param lum lum value
   */
  @Step("Click on the color spectrum bar on the {0}% from the left side and {1}% from the top")
  public void clickOnColorSpectrum(double hue, double lum) {
    logger.debug(String.format("Click on the color spectrum bar on the %.2f value "
        + "from the left side and %.2f  from the top", hue, lum));
    Actions actions = new Actions(driver);
    int x = (int) ((colorSpectrum.getSize().getWidth() - 2) * hue);
    int y = (int) ((colorSpectrum.getSize().getHeight() - 2) * lum);
    actions.moveToElement(colorSpectrum, x, y).click().build().perform();

  }

  /**
   * Getting color spectrum position.
   * 
   * @return color spectrum position
   */
  public Point2D.Double getColorSpectrumPosition() {
    int width = colorSpectrum.getSize().getWidth() - 2; // 1 pixel margin
    double dotLeft = Double.parseDouble(
        dot.getAttribute("style").replace("left: ", "").replaceAll("px; top: .*px;", ""));
    int dotWidth = dot.getSize().getWidth();
    double dotXMiddle = dotLeft + (dotWidth / 2);
    double x = dotXMiddle / width;

    int height = colorSpectrum.getSize().getHeight() - 2; // 1 pixel margin
    double dotTop = Double.parseDouble(
        dot.getAttribute("style").replaceAll("left: .*px; top: ", "").replace("px;", ""));
    int dotHeight = dot.getSize().getHeight();
    double dotYMiddle = dotTop + (dotHeight / 2);
    double y = dotYMiddle / height;

    return new Point2D.Double(x, y);
  }
  
  /**
   * Moving Hue slider to get Hue value.
   * @param value hue value
   */
  public void moveHueSliderToValue(int value) {
    logger.info("Move Hue slider");
    Actions action = new Actions(driver);
    action.moveToElement(hueRoot.findElement(slider)).clickAndHold();
    if (getHueValue() < value) {
      while (getHueValue() != value) {
        action.moveByOffset(1, 0).perform();
      }
    } else if (getHueValue() >= value) {
      while (getHueValue() != value) {
        action.moveByOffset(-1, 0).perform();
      }
    }

    action.release().perform();
  }
  
  /**
   * Moving Sat slider to get Sat value.
   * @param value sat value
   */
  public void moveSatSliderToValue(int value) {
    logger.info("Move Sat slider");

    Actions action = new Actions(driver);
    action.moveToElement(satRoot.findElement(slider)).clickAndHold();
    if (getHueValue() < value) {
      while (getSatValue() != value) {
        action.moveByOffset(1, 0).perform();
      }
    } else if (getSatValue() >= value) {
      while (getSatValue() != value) {
        action.moveByOffset(-1, 0).perform();
      }
    }

    action.release().perform();
  }
  
  /**
   * Moving Lum slider to get Lum value.
   * @param value lum value
   */
  public void moveLumSliderToValue(int value) {
    logger.info("Move Lum slider");

    Actions action = new Actions(driver);
    action.moveToElement(lumRoot.findElement(slider)).clickAndHold();
    if (getLumValue() < value) {
      while (getLumValue() != value) {
        action.moveByOffset(1, 0).perform();
      }
    } else if (getLumValue() >= value) {
      while (getLumValue() != value) {
        action.moveByOffset(-1, 0).perform();
      }
    }

    action.release().perform();
  }
}
