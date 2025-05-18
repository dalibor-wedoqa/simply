package net.simplyanalytics.pageobjects.panels.color;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.simplyanalytics.pageobjects.base.BasePage;
import net.simplyanalytics.utils.ColorUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.qameta.allure.Step;

public abstract class BaseColorTab extends BasePage {

  protected WebElement root;

  @FindBy(css = ".sa-recent-row-transparent-btn-inner")
  private WebElement transparentElement;

  @FindBy(css = ".sa-color-window-swatch-top")
  private WebElement newColor;

  @FindBy(css = ".sa-color-window-swatch-bottom")
  private WebElement oldColor;

  @FindBy(css = "[id*='sa-button']")
  private WebElement useNewButton;
  
  @FindBy(css = ".sa-color-window-recent .sa-color-window-item-inner")
  private List<WebElement> recentColors;

  protected BaseColorTab(WebDriver driver, By rootLocator) {
    super(driver, rootLocator);
    this.root = driver.findElement(rootLocator);
  }

  public boolean isTransparentOptionPresent() {
    return isPresent(transparentElement);
  }

  /**
   * Click on the transparent button.
   */
  @Step("Click on the transparent button")
  public void clickTransparentButton() {
    logger.debug("Click on the transparent button");
    transparentElement.click();
    waitForElementToDisappear(root);
  }

  /**
   * Getting list of recent choosen colors.
   * 
   * @return list of recent choosen colors
   */
  public List<String> getRecentColors() {
    List<String> results = new ArrayList<>();

    for (WebElement recentColor : recentColors) {
      results.add(ColorUtils.getBackgroundHexColor(recentColor));
    }

    return results;
  }

  /**
   * Click on the recent color with hex code.
   * 
   * @param hexcode hex code
   */
  @Step("Click on the recent color with hex code: {0}")
  public void clickOnRecentColorButton(String hexcode) {
    logger.debug("Click on the recent color with hex code: " + hexcode);
    try {
      root.findElement(By.cssSelector(".sa-color-window-recent [style*='" + hexcode + "']"))
          .click();
      waitForElementToDisappear(root);
    } catch (NoSuchElementException e) {
      throw new Error("The recent color button with hexcode not found: " + hexcode);
    }
  }

  public Color getNewColor() {
    return ColorUtils.getBackgroundColor(newColor);
  }

  public Color getOldColor() {
    return ColorUtils.getBackgroundColor(oldColor);
  }
  
  public void clickOnOldColor() {
    logger.debug("Clicking on the old color to use it.");
    oldColor.click();
    //TODO fix
    sleep(100);
  }

  public void clickToUseNewColor() {
    logger.debug("Clicking on the use new button to use new color");
    useNewButton.click();
  }
  
  public void clickOnRecentColorByIndex(int i) {
    recentColors.get(i).click();
    waitForLoadingToDisappear();
  }
  
}
