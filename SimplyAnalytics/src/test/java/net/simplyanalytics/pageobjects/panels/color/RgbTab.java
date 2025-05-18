package net.simplyanalytics.pageobjects.panels.color;

import java.awt.Color;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public class RgbTab extends BaseColorTab {

  @FindBy(css = ".sa-color-window-slider:nth-child(1) input[id*='sa-numberfield']")
  private WebElement redInputField;
  @FindBy(css = ".sa-color-window-slider:nth-child(1) [id*='color-window-slide-bar']")
  private WebElement redRoot;
  private ColorWindowSlideBar redBar;

  @FindBy(css = ".sa-color-window-slider:nth-child(2) input[id*='sa-numberfield']")
  private WebElement greenInputField;
  @FindBy(css = ".sa-color-window-slider:nth-child(2) [id*='color-window-slide-bar']")
  private WebElement greenRoot;
  private ColorWindowSlideBar greenBar;

  @FindBy(css = ".sa-color-window-slider:nth-child(3) input[id*='sa-numberfield']")
  private WebElement blueInputField;
  @FindBy(css = ".sa-color-window-slider:nth-child(3) [id*='color-window-slide-bar']")
  private WebElement blueRoot;
  private ColorWindowSlideBar blueBar;

  @FindBy(css = "[id^='hex-field-'][id$='inputEl']")
  private WebElement hexInputField;

  protected static final By rootLocator = By.cssSelector("[id*='rgb-tab']");
  protected static final By slider = By.cssSelector(".sa-color-window-slider-arrow");

  protected RgbTab(WebDriver driver) {
    super(driver, rootLocator);
  }

  @Override
  protected void isLoaded() {
    waitForElementToAppear(redInputField, "The red input field missing");
    redBar = new ColorWindowSlideBar(driver, redRoot);

    waitForElementToAppear(greenInputField, "The green input field missing");
    greenBar = new ColorWindowSlideBar(driver, greenRoot);

    waitForElementToAppear(blueInputField, "The blue input field missing");
    blueBar = new ColorWindowSlideBar(driver, blueRoot);
  }

  public int getRedValue() {
    return Integer.parseInt(redInputField.getAttribute("value"));
  }

  /**
   * Enter red value.
   * 
   * @param value red value
   */
  @Step("Enter red value: {0}")
  public void enterRedValue(int value) {
    logger.debug("Enter red value: " + value);
    redInputField.click();
    redInputField.clear();
    if (value > 100) {
      redInputField
          .sendKeys("" + value / 100 + Keys.LEFT + Keys.BACK_SPACE + Keys.RIGHT + value % 100);
    } else {
      redInputField.sendKeys("" + value);
    }
    waitForElementToStop(getRedBar().arrow);
  }

  public ColorWindowSlideBar getRedBar() {
    return redBar;
  }

  public int getGreenValue() {
    return Integer.parseInt(greenInputField.getAttribute("value"));
  }

  /**
   * Enter green value.
   * 
   * @param value green value
   */
  @Step("Enter green value: {0}")
  public void enterGreenValue(int value) {
    logger.debug("Enter green value: " + value);
    greenInputField.click();
    greenInputField.clear();
    if (value > 100) {
      greenInputField
          .sendKeys("" + value / 100 + Keys.LEFT + Keys.BACK_SPACE + Keys.RIGHT + value % 100);
    } else {
      greenInputField.sendKeys("" + value);
    }
    waitForElementToStop(getGreenBar().arrow);
  }

  public ColorWindowSlideBar getGreenBar() {
    return greenBar;
  }

  public int getBlueValue() {
    return Integer.parseInt(blueInputField.getAttribute("value"));
  }

  /**
   * Enter blue value.
   * 
   * @param value blue value
   */
  @Step("Enter blue value: {0}")
  public void enterBlueValue(int value) {
    logger.debug("Enter blue value: " + value);
    blueInputField.click();
    blueInputField.clear();
    if (value > 100) {
      blueInputField
          .sendKeys("" + value / 100 + Keys.LEFT + Keys.BACK_SPACE + Keys.RIGHT + value % 100);
    } else {
      blueInputField.sendKeys("" + value);
    }
    waitForElementToStop(getBlueBar().arrow);
  }

  public ColorWindowSlideBar getBlueBar() {
    return blueBar;
  }

  /**
   * Enter hex value.
   * 
   * @param value hex value
   */
  @Step("Enter hex value: {0}")
  public void enterHexValue(String value) {
    logger.debug("Enter hex value: " + value);
    hexInputField.click();
    hexInputField.sendKeys(Keys.CONTROL + "a");
    hexInputField.sendKeys(Keys.DELETE);
    hexInputField.sendKeys("" + value);
    int maxWait = 10;
    int count = 0;
    while (!getNewColor().equals(new Color(Integer.parseInt(value, 16))) && count < maxWait) {
      sleep(200);
    }
  }

  public void moveRedSliderToValue(int value) {
    logger.debug("Move red slider");
    Actions action = new Actions(driver);
    action.moveToElement(redRoot.findElement(slider)).clickAndHold();
    if (getRedValue() < value) {
      while (getRedValue() != value) {
        action.moveByOffset(1, 0).perform();
      }
    } else if (getRedValue() >= value) {
      while (getRedValue() != value) {
        action.moveByOffset(-1, 0).perform();
      }
    }

    action.release().perform();
  }

  public void moveGreenSliderToValue(int value) {
    logger.debug("Move green slider");
    Actions action = new Actions(driver);
    action.moveToElement(greenRoot.findElement(slider)).clickAndHold();
    if (getGreenValue() < value) {
      while (getGreenValue() != value) {
        action.moveByOffset(1, 0).perform();
      }
    } else if (getGreenValue() >= value) {
      while (getGreenValue() != value) {
        action.moveByOffset(-1, 0).perform();
      }
    }

    action.release().perform();
  }

  public void moveBlueSliderToValue(int value) {
    logger.debug("Move blue slider");
    Actions action = new Actions(driver);
    action.moveToElement(blueRoot.findElement(slider)).clickAndHold();
    if (getBlueValue() < value) {
      while (getBlueValue() != value) {
        action.moveByOffset(1, 0).perform();
      }
    } else if (getBlueValue() > value) {
      while (getBlueValue() != value) {
        action.moveByOffset(-1, 0).perform();
      }
    }

    action.release().perform();
  }

  public String getHexValue() {
    return hexInputField.getAttribute("value");
  }
}
